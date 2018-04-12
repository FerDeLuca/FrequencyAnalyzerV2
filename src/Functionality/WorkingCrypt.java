package Functionality;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringJoiner;


public final class WorkingCrypt {
    public static boolean toLow;            //Смена регистра
    public static boolean colorText;        //Подстветка изменений
    public static boolean writeAll;         //Подстветка изменений

    public static ObservableList<CryptRowType> cryptRows= FXCollections.observableArrayList();//Список символов для замены в тексте

    private static String originalText;     // Текст считаннный из файла
    private static String modifiedText;     // Текст после обработки

    public WorkingCrypt(){
        clearData(true,true);
        toLow=true;
        colorText=true;
        writeAll=false;
    }




    //Очистка класса
    public static void clearData(boolean clearRows,boolean clearText){
        if(clearRows && cryptRows != null)
            cryptRows.clear();
        if(clearText) {
            originalText = "";
            modifiedText = "";
        }
    }

    public static void setOriginalText(String text){
        originalText = text;
        UpdateReplChars();
    }
    public static String getOriginalText(){
        if(originalText==null){
            originalText="";
        }
        if(toLow)                                   // Перевод в нижний регистр
            return originalText.toLowerCase();
        else
            return originalText;
    }

    /**
     * Помещение изменённых символов в строку
     * @return  Возвращает строку готовую для записи в файл
     */
    public static String getStringChars(){
        String split = " ";

        //Создание разделителя строк
        StringJoiner sJoin = new StringJoiner("\r\n");
        if(cryptRows.size()<1)
            return "";

        for(CryptRowType sc : cryptRows)
            if(sc.getActive() || (writeAll && sc.getReplace()!=" "))
                sJoin.add(sc.getSymbol()+split+sc.getReplace()+split+sc.getActive());
        split = sJoin.toString();
        return split;
    }

    /**
     * Получение параметров символа из строки
     * @param mStr  Массив из файла
     */
    public static void setStringChars(String[] mStr) {
        String split = " ";
        String[] subStr=new String[]{};
        int id;

        for (String str : mStr) {
            subStr = str.split(split);
            id = WorkingCrypt.getColumnName(subStr[0]);
            if (id >= 0) {
                WorkingCrypt.cryptRows.get(id).setReplace(subStr[1]);
                WorkingCrypt.cryptRows.get(id).setActive(Boolean.valueOf(subStr[2]));
            }
        }
    }

    /**
     *  Добавить заполнение изменённого текста
     */
    public static void updateModifiedText() {
        String temp = getOriginalText().toString();

        //хеш-таблица строка,строка
        Map<Character,Character> hashSet= new HashMap<Character, Character>();
            for(CryptRowType sc : cryptRows)
                if(sc.getActive() && sc.getReplace().charAt(0) !=' ')
                    hashSet.put(sc.getSymbol().charAt(0),sc.getReplace().charAt(0));

        //раскраска цвета
        String[] span= new String[2];
        if(colorText){
            span[1]="</span>";
            span[0]="<span style=\"color:black;\">";
        }
        else
            span[0]=span[1]="";


        //Проход по массиву (создавая строку)
        //Если символ имеется в хеш тогда он заменяется на
        StringBuilder sb = new StringBuilder();
        for (char c : temp.toCharArray())
            if (hashSet.containsKey(c))
            {
                sb.append(span[0]);
                sb.append(hashSet.get(c));
                sb.append(span[1]);
            }
            else
                sb.append(c);

        modifiedText = sb.toString();
        //после прохода строка собирается и записывается
    }

    /**
     *  Получение результата в html тегах
     * @return
     */
    public static String getModifiedText(){
        if(modifiedText==null)  //Заполнение пустой строки
            modifiedText="";

        String str;
        if(toLow) // Переход в нижний регистр
            str= modifiedText.toLowerCase();
        else
            str = modifiedText;

        if(colorText) // Цветовая подсветка
            return "<html><body style=\"color:#c70000;\">" + str + "</body></html>";
        else
            return "<html><body style=\"color:black;\">" + str + "</body></html>";
    }

    /**
     *      Вывод строки на печать
     * @return строка изменённого текста
     */
    public static String getToPrintText(){
        return modifiedText.replaceAll("<[^>]*>", "");
    }

//    /**
//     * Перевод данных из файла в исходный текст
//     * @param fileName  Имя файла
//     * @throws IOException
//     */
//    public static void ReadFile(String fileName) throws IOException {
//        setOriginalText(new String(Files.readAllBytes(Paths.get(fileName))));  // Считывание текста из файла
//        UpdateReplChars();
//    }

    /**
     * Обновление символов замены
     */
    public static void UpdateReplChars() {
        //Создание хеш-таблицы
        HashMap<Character, Integer> charMap = new HashMap<Character, Integer>();

        if (getOriginalText().length()<1)    //Текст пуст
            return;

        //Проход по тексту
        for (Character ch : getOriginalText().toCharArray()) {
            if (charMap.containsKey(ch))
                charMap.put(ch, charMap.get(ch)+1);
            else
                charMap.put(ch, 1);
        }

        // Чистка от разделения строки и табов
        charMap.remove('\n');
        charMap.remove('\r');
        charMap.remove('\t');
        charMap.remove(' ');
        charMap.remove('.');
        charMap.remove(',');
        charMap.remove('!');
        charMap.remove('?');

        //Очистка прошлых строк
        clearData(true,false);

        // Заполнение массива
        for(Iterator<Character> it = charMap.keySet().iterator(); it.hasNext(); )
        {
            Character key = it.next();
            cryptRows.add(new CryptRowType(key.toString(),charMap.get(key)));
        }

        //TODO Можно добавить упорядоченный список символов
        charMap.clear();    // Очистка памяти
        updateModifiedText();
    }

    /**
     * Получение идентификатора
     * @param name имя столбца
     * @return
     */
    public static int getColumnName(String name){
        for (int i = 0; i< cryptRows.size(); i++)
            if(cryptRows.get(i).getSymbol()==name)
                return i;
        return -1;
    }

//    //Простая сортировка массива
//    public static void Sorting(CryptRowType[] mass) {
//        for (int i = mass.length - 1; i > 0; i--) {
//            for (int j = 0; j < i; j++) {
//                if (mass[j].count < mass[j + 1].count) {
//                    CryptRowType tmp = mass[j];
//                    mass[j] = mass[j + 1];
//                    mass[j + 1] = tmp;
//                }
//            }
//        }
//    }

    public ObservableList<CryptRowType> getCryptRows(){
        return cryptRows;
    }

    /**
     *
     * @return Размер массива объектов
     */
    public static int size(){
        if(WorkingCrypt.cryptRows ==null || WorkingCrypt.cryptRows.size()<1)
            return 0;
        else
            return WorkingCrypt.cryptRows.size();
    }
}