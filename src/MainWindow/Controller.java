package MainWindow;

import Basic.CryptType;
import Basic.tFile;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;

public class Controller {
    boolean checkColor=true;
    boolean checkRegistry=true;
    boolean checkSaveAll=false;

    @FXML
    private WebView fxTextOriginal;
    @FXML
    private WebView fxTextResult;
    @FXML
    private SplitPane fxSplitter;
    @FXML
    private BorderPane fxProgress;

    /**
     * Закрыть проект
     * @param actionEvent
     */
    public void CloseProject(ActionEvent actionEvent){

        fxSplitter.getItems().clear(); //Очистка существущих окон
        CryptType.clear();
        symbRows.clear();
        Test test;
        test = (x,y,z)->(x+y)*z;
        int result = test.calculate(2,3,4);



    }

    interface  Test{
        int calculate(int x,int y,int z);
    }

    /**
     * Завершить программу
     * @param actionEvent
     */
    public void goClose(ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     * Панель загрузки
     * @param status отображение панели загрузки
     */
    public void setWait(boolean status) {
        fxProgress.setDisable(!status);
        fxProgress.setVisible(status);
    }

    /**
     * Обновление вида ткстовых блоков
     * @param actionEvent
     */
    public void ChoiceView(ActionEvent actionEvent) {

        int val = Integer.valueOf(((RadioMenuItem) actionEvent.getSource()).getId());

        switch (val) {  //Создание новых
            case 1:
                TextsSplit(true,false);
                break;
            case 2:
                TextsSplit(false,true);
                break;
            default:
                TextsSplit(true,true);
                break;
        }
        UpdateModeText(true,true);

    }

    /**
     * Создание тексторого окна с промоткой
     * @param id    Идентификатор окна
     * @return      Возвращает окно с заданным идентификатором
     */
    private static WebView FastScreen(int id) {   //Создание текстового окна со скролом
        WebView textFlow = new WebView();

        AnchorPane anchorPane = new AnchorPane(textFlow);

        ScrollPane scrollPane = new ScrollPane(anchorPane);
        scrollPane.setId(String.valueOf(id));
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setLayoutX(10);
        scrollPane.setLayoutY(10);

        AnchorPane.setTopAnchor(textFlow, 0.0d);
        AnchorPane.setBottomAnchor(textFlow, 0.0d);
        AnchorPane.setLeftAnchor(textFlow, 0.0d);
        AnchorPane.setRightAnchor(textFlow, 0.0d);

        return textFlow;
    }

    /**
     * Обновление блоков текста в разделителе
     * @param Original  включить блок оригинального текста
     * @param Result    включить блок текста после обработки(результата)
     */
    public void TextsSplit(boolean Original, boolean Result){
        fxSplitter.getItems().clear(); //Очистка существущих элементов
        if(Original)
        {
            fxTextOriginal=FastScreen(1);
            fxSplitter.getItems().add(fxTextOriginal);
        }
        if(Result)
        {
            fxTextResult=FastScreen(2);
            fxSplitter.getItems().add(fxTextResult);
        }

    }

    /**
     * Открытие файла с шифротекстом
     * @param actionEvent
     */
    public void OpenProject(ActionEvent actionEvent) {

        tFile file = new tFile();
        String readStr = file.ReadFile();



        if (readStr != null) {    //Открытие файла
            CryptType.setOriginalText(readStr);
        }
        if(fxSplitter.getItems().size()==0) //Создание блоков текста, если отсутствуют
            TextsSplit(true,true);

        UpdateModeText(true,true);
        UpdateTable();

    }

    private void UpdateTable() {
        if (symbRows ==null)
            symbRows =FXCollections.observableArrayList();
        else
            symbRows.clear();
        for(CryptType.StatChar sc : CryptType.replChars){
            symbRows.add(CryptType.StatChar.toSymbRow(sc));
        }
    }

    /**
     * Обновление текстовых блоков
     * @param boolOrig Обновить оригинал
     * @param boolRes Обновить результат
     */
    private void UpdateModeText(boolean boolOrig, boolean boolRes) {

        if (boolOrig  && fxTextOriginal!=null) {
//            list = fxTextOriginal.getChildren();//Ссылка на суб-объекты
//
//            if (list.size() > 0)   //Очистка текста
//                list.clear();
//            //Разделение текста данных на строки
//            String[] mas = CryptType.getOriginalText().split("\r\n");
//
//            //Перенос по строкам
//            for(String str : mas)
//                list.add(new Text(str));
            fxTextOriginal.getEngine().loadContent(CryptType.getOriginalText(),"text/html");
        }
        if (boolRes  && fxTextResult!=null) {
            fxTextResult.getEngine().loadContent(CryptType.getModifiedText(),"text/html");
        }

    }

    public void TextParsing(ActionEvent actionEvent) {
        //TODO Сделать окно с анализом и графиком
    }

    /**
     *
     * @param actionEvent
     */
    public void CheckReg(ActionEvent actionEvent) {
        checkRegistry = ((CheckMenuItem) actionEvent.getSource()).isSelected();
        CryptType.toLow=checkRegistry;
        CryptType.UpdateReplChars();
        UpdateModeText(true,true);
    }
    public void CheckColor(ActionEvent actionEvent) {
        checkColor = ((CheckMenuItem) actionEvent.getSource()).isSelected();
        CryptType.colorText=checkColor;
        CryptType.updateModifiedText();
        UpdateModeText(false,true);
    }

    /**
     * Сохранение файла символов
     * @param actionEvent
     */
    public void SaveData(ActionEvent actionEvent) {

        tFile file = new tFile();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Сохранение данных");
        alert.setHeaderText(null);

        if (file.SaveToFile(CryptType.getStringChars(checkSaveAll)))  //Сохранение в файл
            alert.setContentText("Файл успешно сохранён!");
        else
            alert.setContentText("Операция отменена, файл не сохранён!");

        alert.showAndWait();
    }

    /**
     * Открытие файла символов
     * @param actionEvent
     */
    public void LoadData(ActionEvent actionEvent) {

        tFile file = new tFile();
        String readStr = file.ReadFile();//Открытие файла
        String[] mStrs;

        if (readStr != null) {
            mStrs = readStr.split("[\\r\\n]+");//сплит по знаку строки
            CryptType.setStringChars(mStrs); //Запись в класс

            UpdateTable();
            UpdateModeText(true,true);
        }

    }

    public void UpdateData(ActionEvent actionEvent) {
        UpdateTable();
        UpdateModeText(false,true); //Обновление окон
    }

    /**
     * Сохранение результата из текстового блока
     * @param actionEvent
     */
    public void SaveProject(ActionEvent actionEvent) {

        tFile file = new tFile();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Сохранение текста");
        alert.setHeaderText(null);

        if (file.SaveToFile(CryptType.getToPrintText()))  //Сохранение в файл
            alert.setContentText("Файл успешно сохранён!");
        else
            alert.setContentText("Операция отменена, файл не сохранён!");

        alert.showAndWait();

    }

    /**
     * Сохранение всех символов, даже не задействованых
     * @param actionEvent
     */
    public void CheckSaveAll(ActionEvent actionEvent) {
        checkSaveAll = ((CheckMenuItem) actionEvent.getSource()).isSelected();
        CryptType.toLow=checkSaveAll;
    }


        private ObservableList<SymbRow> symbRows = FXCollections.observableArrayList();

        public ObservableList getSymbRows(){
            return symbRows;
        }

        @FXML
        private TableView<SymbRow> fxTableData;
        @FXML
        private TableColumn<SymbRow, String> fxColumnOld;
        @FXML
        private TableColumn<SymbRow, String> fxColumnNew;
        @FXML
        private TableColumn<SymbRow, Boolean> fxColumnOn;
        @FXML
        private TableColumn<SymbRow, Integer> fxColumnCount;

        public void initialize() {

            SymbRow test = new SymbRow("s", "b", false, 100);
            fxColumnNew.setEditable(true);
            fxColumnNew.setCellFactory(TextFieldTableCell.forTableColumn());
            fxColumnOld.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
            fxColumnNew.setCellValueFactory(cellData -> cellData.getValue().renewProperty());
            fxColumnOn.setCellValueFactory(cellData -> cellData.getValue().activeProperty().asObject());
            fxColumnCount.setCellValueFactory(cellData -> cellData.getValue().countProperty().asObject());
            // заполняем таблицу данными
            fxTableData.setItems(symbRows);


    }




}
