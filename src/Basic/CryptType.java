package Basic;

import MainWindow.SymbRow;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringJoiner;


public final class CryptType {
    public static boolean toLow=true;            //Смена регистра
    public static boolean colorText=true;        //Подстветка изменений

    public static StatChar[] replChars; //Массив символов для замены в тексте

    private static String originalText;     // Текст считаннный из файла
    private static String modifiedText;

    public CryptType(){
        clear();
    }

    //Очистка класса
    public static void clear(){
        replChars = new StatChar[]{new StatChar(' ',0)};
        originalText="";
        modifiedText="";
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
     * @param allFields Записывать неотмеченные поля
     * @return  Возвращает строку готовую для записи в файл
     */
    public static String getStringChars(boolean allFields){
        String split = " ";

        //Создание разделителя строк
        StringJoiner sJoin = new StringJoiner("\r\n");
        if(replChars.length<1)
            return "";


        for(StatChar sc : replChars)
            if(sc.active || (allFields && sc.renew!=' '))
                sJoin.add( sc.name+split+sc.renew+split+sc.count);
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
            id = CryptType.StatChar.getColumnName(subStr[0].charAt(0));
            if (id >= 0) {
                CryptType.replChars[id].renew = subStr[1].charAt(0);
                CryptType.replChars[id].active = true;
            }
        }
    }

    /**
     *  Добавить заполнение изменённого текста
     */
    public static void updateModifiedText() {
        String temp = new String(getOriginalText());

        //хеш-таблица строка,строка
        Map<Character,Character> hashSet= new HashMap<Character, Character>();
            for(StatChar sc : replChars)
                if(sc.active && sc.renew !=' ')
                    hashSet.put(sc.name,sc.renew);

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
            str = new String(modifiedText);

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

    /**
     * Перевод данных из файла в исходный текст
     * @param fileName  Имя файла
     * @throws IOException
     */
    public static void ReadFile(String fileName) throws IOException {
        setOriginalText(new String(Files.readAllBytes(Paths.get(fileName))));  // Считывание текста из файла
        UpdateReplChars();
    }

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

        //Создание массива
        replChars = new StatChar[charMap.size()];
        int n=0;

        // Заполнение массива
        for(Iterator<Character> it = charMap.keySet().iterator(); it.hasNext(); )
        {
            Character key = it.next();
            replChars[n]= new StatChar(key,charMap.get(key));
            n++;
        }

        StatChar.Sorting(replChars);
        charMap.clear();    // Очистка памяти
        updateModifiedText();
    }

    /**
     * Класс для хранения параметров символов
     */
    public static class StatChar {
        public char name;
        public char renew;
        public int count;
        public boolean active;

        public StatChar(StatChar statChar){
            this.name = ' ';
            this.count = 0;
            this.active = false;
            this.renew = ' ';
        }
        public StatChar(char name, int count) {
            this.name = name;
            this.count = count;
            this.active = false;
            this.renew = ' ';
        }
        public StatChar(char name,char renew,boolean active, int count) {
            this.name = name;
            this.renew = renew;
            this.count = count;
            this.active = active;
        }

        //Простая сортировка массива
        public static void Sorting(StatChar[] mass) {
            for (int i = mass.length - 1; i > 0; i--) {
                for (int j = 0; j < i; j++) {
                    if (mass[j].count < mass[j + 1].count) {
                        StatChar tmp = mass[j];
                        mass[j] = mass[j + 1];
                        mass[j + 1] = tmp;
                    }
                }
            }
        }

        //Получение данных по столбцам
        public Object getData(int column){
            Object resObj;
            switch (column){
                case 0:
                    resObj = this.name;
                    break;
                case 1:
                    resObj = this.renew;
                    break;
                case 2:
                    resObj = this.count;
                    break;
                case 3:
                    resObj = this.active;
                    break;
                default:
                    resObj = new Object();
            }
            return resObj;
        }

        //Запись данных по столбцам
        public void setData(int column, Object obj) {
            try {
                switch (column) {
                    case 0:
                        this.name = (char) obj;
                        break;
                    case 1:
                        this.renew = (char) obj;
                        break;
                    case 2:
                        this.count = (int) obj;
                        break;
                    case 3:
                        this.active = (boolean) obj;
                        break;
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        public static int getColumnName(char name){
            for (int i = 0;i<replChars.length;i++)
                if(replChars[i].name==name)
                    return i;
            return -1;
        }

        public static SymbRow toSymbRow(StatChar sc){
            return new SymbRow(String.valueOf(sc.name),String.valueOf(sc.renew),sc.active,sc.count);
        }
    }

    /**
     *
     * @return Размер массива объектов
     */
    public static int size(){
        if(CryptType.replChars==null || CryptType.replChars.length<1)
            return 0;
        else
            return CryptType.replChars.length;
    }
}