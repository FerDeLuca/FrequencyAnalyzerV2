package ChartsWindow;

import Functionality.CryptRowType;
import Functionality.WorkingCrypt;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;

import java.io.ObjectInputStream;
import java.sql.Time;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class ChartsWindow {

    @FXML
    private ComboBox fxChoiceStandart;
    @FXML
    private BarChart<Number, String> fxBarText;
    @FXML
    private BarChart<Number, String> fxBarCompare;
    @FXML
    private BarChart<Number, String> fxBarStandart;

    private XYChart.Series series1 = new XYChart.Series();
    private XYChart.Series series21 = new XYChart.Series();
    private XYChart.Series series22 = new XYChart.Series();
    private XYChart.Series series3 = new XYChart.Series();

    private String activeTab="1";

    public void initialize() {
        fxChoiceStandart.getItems().add("Английский стандарт");
        fxChoiceStandart.getItems().add("Русский стандарт");
        fxChoiceStandart.getItems().add("Украинский стандарт");
        fxBarText.getXAxis().setLabel("Символ");
        fxBarCompare.getXAxis().setLabel("Символ");
        fxBarStandart.getXAxis().setLabel("Символ");
        fxBarText.getYAxis().setLabel("Повторений, %");
        fxBarCompare.getYAxis().setLabel("Повторений, %");
        fxBarStandart.getYAxis().setLabel("Повторений, %");

        // Тут покоятся костыли.
        fxChoiceStandart.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            setSeries2(fxChoiceStandart.getSelectionModel().getSelectedIndex());
            UpdateBar(activeTab);
        }));
        setSeries1(WorkingCrypt.cryptRows,WorkingCrypt.getOriginalText().length());
    }

    /**
     * Выбор данных для таблиц
     */
    private void setSeries1(ObservableList<CryptRowType> cryptData, int strSize) {
        series1.getData().clear();
        series21.getData().clear();
        series1.setName("Пользовательские данные");
        series21.setName("Пользовательские данные");
        double sizePercent=100;
        if(strSize>0)
            sizePercent=100d/strSize;
        //Создание промежуточного класса для ортировки
        Map<Number,String> treeMap = new TreeMap<>(Collections.reverseOrder());
        for(CryptRowType crt: cryptData)
            if(WorkingCrypt.toLow)
                treeMap.put(crt.getCount()*sizePercent,crt.getSymbol().toUpperCase());
            else
                treeMap.put(crt.getCount()*sizePercent,crt.getSymbol());

        //Сборка отсортированных данных для гистограммы
        for(Map.Entry<Number,String> cell:treeMap.entrySet()){
            series1.getData().add(new XYChart.Data(cell.getValue(),cell.getKey()));
            series21.getData().add(new XYChart.Data(cell.getValue(),cell.getKey()));
        }
        //Передача данных в гистограммы


    }

    /**
     * Выбор данных для таблиц
     * @param selectedIndex индекс
     */
    private void setSeries2(int selectedIndex) {
        switch (selectedIndex) {
            case 0:
                series22=SetEngStandart();
                series3=SetEngStandart();
                break;
            case 1:
                series22=SetRusStandart();
                series3=SetRusStandart();
                break;
            case 2:
                series22=SetUkrStandart();
                series3=SetUkrStandart();
                break;
            default:
                return;
        }
       // series3.getNode().setStyle("-fx-bar-fill: #6dce80;");
        //series22.getNode().setStyle("-fx-bar-fill: green;");
    }

    private XYChart.Series SetEngStandart() {
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Английская раскладка");
        series1.getData().add(new XYChart.Data("E", 12.7));
        series1.getData().add(new XYChart.Data("T", 9.06));
        series1.getData().add(new XYChart.Data("A", 8.17));
        series1.getData().add(new XYChart.Data("O", 7.51));
        series1.getData().add(new XYChart.Data("I", 6.97));
        series1.getData().add(new XYChart.Data("N", 6.75));
        series1.getData().add(new XYChart.Data("S", 6.33));
        series1.getData().add(new XYChart.Data("H", 6.09));
        series1.getData().add(new XYChart.Data("R", 5.99));
        series1.getData().add(new XYChart.Data("D", 4.25));
        series1.getData().add(new XYChart.Data("L", 4.03));
        series1.getData().add(new XYChart.Data("C", 2.78));
        series1.getData().add(new XYChart.Data("U", 2.76));
        series1.getData().add(new XYChart.Data("M", 2.41));
        series1.getData().add(new XYChart.Data("W", 2.36));
        series1.getData().add(new XYChart.Data("F", 2.23));
        series1.getData().add(new XYChart.Data("G", 2.02));
        series1.getData().add(new XYChart.Data("Y", 1.97));
        series1.getData().add(new XYChart.Data("P", 1.93));
        series1.getData().add(new XYChart.Data("B", 1.49));
        series1.getData().add(new XYChart.Data("V", 0.98));
        series1.getData().add(new XYChart.Data("K", 0.77));
        series1.getData().add(new XYChart.Data("X", 0.15));
        series1.getData().add(new XYChart.Data("J", 0.15));
        series1.getData().add(new XYChart.Data("Q", 0.1));
        series1.getData().add(new XYChart.Data("Z", 0.0));
        return series1;
    }
    private XYChart.Series SetRusStandart() {
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Русская раскладка");
        series1.getData().add(new XYChart.Data("О",10.97));
        series1.getData().add(new XYChart.Data("Е",8.45));
        series1.getData().add(new XYChart.Data("А",8.01));
        series1.getData().add(new XYChart.Data("И",7.35));
        series1.getData().add(new XYChart.Data("Н",6.70));
        series1.getData().add(new XYChart.Data("Т",6.26));
        series1.getData().add(new XYChart.Data("С",5.47));
        series1.getData().add(new XYChart.Data("Р",4.73));
        series1.getData().add(new XYChart.Data("В",4.54));
        series1.getData().add(new XYChart.Data("Л",4.40));
        series1.getData().add(new XYChart.Data("К",3.49));
        series1.getData().add(new XYChart.Data("М",3.21));
        series1.getData().add(new XYChart.Data("Д",2.98));
        series1.getData().add(new XYChart.Data("П",2.81));
        series1.getData().add(new XYChart.Data("У",2.62));
        series1.getData().add(new XYChart.Data("Я",2.01));
        series1.getData().add(new XYChart.Data("Ы",1.90));
        series1.getData().add(new XYChart.Data("Ь",1.74));
        series1.getData().add(new XYChart.Data("Г",1.70));
        series1.getData().add(new XYChart.Data("З",1.65));
        series1.getData().add(new XYChart.Data("Б",1.59));
        series1.getData().add(new XYChart.Data("Ч",1.44));
        series1.getData().add(new XYChart.Data("Й",1.21));
        series1.getData().add(new XYChart.Data("Х",0.97));
        series1.getData().add(new XYChart.Data("Ж",0.94));
        series1.getData().add(new XYChart.Data("Ш",0.73));
        series1.getData().add(new XYChart.Data("Ю",0.64));
        series1.getData().add(new XYChart.Data("Ц",0.48));
        series1.getData().add(new XYChart.Data("Щ",0.36));
        series1.getData().add(new XYChart.Data("Э",0.32));
        series1.getData().add(new XYChart.Data("Ф",0.26));
        series1.getData().add(new XYChart.Data("Ъ",0.04));
        series1.getData().add(new XYChart.Data("Ё",0.04));
        return series1;
    }
    private XYChart.Series SetUkrStandart() {
        //https://k0derz.ru/freq-ukr-alphabet-crypt/
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Украинская раскладка");
        series1.getData().add(new XYChart.Data("О",9.515753626));
        series1.getData().add(new XYChart.Data("А",9.439352950));
        series1.getData().add(new XYChart.Data("И",6.616174864));
        series1.getData().add(new XYChart.Data("Н",5.506135801));
        series1.getData().add(new XYChart.Data("Т",5.155462499));
        series1.getData().add(new XYChart.Data("В",5.075829496));
        series1.getData().add(new XYChart.Data("Е",4.851813284));
        series1.getData().add(new XYChart.Data("І",4.721218964));
        series1.getData().add(new XYChart.Data("С",4.361824484));
        series1.getData().add(new XYChart.Data("Л",4.188053841));
        series1.getData().add(new XYChart.Data("Р",4.110144211));
        series1.getData().add(new XYChart.Data("К",3.668173980));
        series1.getData().add(new XYChart.Data("У",3.609691856));
        series1.getData().add(new XYChart.Data("Д",3.421489318));
        series1.getData().add(new XYChart.Data("М",3.038541155));
        series1.getData().add(new XYChart.Data("П",2.814449384));
        series1.getData().add(new XYChart.Data("Я",2.459930428));
        series1.getData().add(new XYChart.Data("З",2.275976415));
        series1.getData().add(new XYChart.Data("Б",2.005217190));
        series1.getData().add(new XYChart.Data("Г",1.883963170));
        series1.getData().add(new XYChart.Data("Ь",1.729941845));
        series1.getData().add(new XYChart.Data("Ч",1.518768338));
        series1.getData().add(new XYChart.Data("Й",1.505886034));
        series1.getData().add(new XYChart.Data("Х",1.419314796));
        series1.getData().add(new XYChart.Data("Ж",1.020785894));
        series1.getData().add(new XYChart.Data("Ш",0.902959292));
        series1.getData().add(new XYChart.Data("Ю",0.884944276));
        series1.getData().add(new XYChart.Data("Щ",0.615601867));
        series1.getData().add(new XYChart.Data("Ї",0.587121615));
        series1.getData().add(new XYChart.Data("Ц",0.544681049));
        series1.getData().add(new XYChart.Data("Є",0.503155629));
        series1.getData().add(new XYChart.Data("Ф",0.047642449));
        return series1;
    }

    public void ChangeTab(Event event){
        activeTab=((Tab)event.getTarget()).getId();
        UpdateBar(activeTab);
        if(fxChoiceStandart!=null)
            fxChoiceStandart.setVisible(!((Tab)event.getTarget()).getId().equals("1"));
    }

    private void UpdateBar(String choice) {
        switch (choice) {
            case "1":
                if(fxBarText.getData()!=null)
                    fxBarText.getData().clear();
                fxBarText.layout();
                fxBarText.getData().add(series1);
                break;

            case "2":
                if(fxBarCompare.getData()!=null)
                    fxBarCompare.getData().clear();
                fxBarStandart.layout();
                fxBarCompare.getData().addAll(series22,series21);
                break;

            case "3":
                if(fxBarStandart.getData()!=null)
                    fxBarStandart.getData().clear();
                fxBarStandart.layout();
                fxBarStandart.getData().add(series3);
                break;
        }
    }
}
