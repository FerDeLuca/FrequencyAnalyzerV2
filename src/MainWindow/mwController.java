package MainWindow;

import Functionality.*;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;
import javafx.util.Callback;

import static javafx.scene.input.KeyCode.T;
//TODO Сделать jar и exe фалы. Проверить на версии 8
public class mwController {

    ObservableList<CryptRowType> cryptRows = WorkingCrypt.cryptRows;
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
     *
     * @param actionEvent
     */
    public void CloseProject(ActionEvent actionEvent) {
        fxSplitter.getItems().clear(); //Очистка существущих окон
        WorkingCrypt.clearData(true, true);
    }

    /**
     * Завершить программу
     *
     * @param actionEvent
     */
    public void goClose(ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     * Обновление вида ткстовых блоков
     *
     * @param actionEvent
     */
    public void ChoiceView(ActionEvent actionEvent) {

        int val = Integer.valueOf(((RadioMenuItem) actionEvent.getSource()).getId());

        switch (val) {  //Создание новых
            case 1:
                TextsSplit(true, false);
                break;
            case 2:
                TextsSplit(false, true);
                break;
            default:
                TextsSplit(true, true);
                break;
        }
        UpdateModeText(true, true);
    }

    /**
     * Создание тексторого окна с промоткой
     *
     * @param id Идентификатор окна
     * @return Возвращает окно с заданным идентификатором
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
     *
     * @param Original включить блок оригинального текста
     * @param Result   включить блок текста после обработки(результата)
     */
    private void TextsSplit(boolean Original, boolean Result) {
        fxSplitter.getItems().clear(); //Очистка существущих элементов
        if (Original) {
            fxTextOriginal = FastScreen(1);
            fxSplitter.getItems().add(fxTextOriginal);
        }
        if (Result) {
            fxTextResult = FastScreen(2);
            fxSplitter.getItems().add(fxTextResult);
        }

    }

    private void UpdateTable() {
//        if (cryptRowTypes ==null)
//            cryptRowTypes =FXCollections.observableArrayList();
//        else
//            cryptRowTypes.clear();
//        for(WorkingCrypt.CryptRowType sc : WorkingCrypt.cryptRows){
//            cryptRowTypes.add(WorkingCrypt.CryptRowType.toSymbRow(sc));
//        }
    }

    /**
     * Обновление текстовых блоков
     *
     * @param boolOrig Обновить оригинал
     * @param boolRes  Обновить результат
     */
    private void UpdateModeText(boolean boolOrig, boolean boolRes) {
        if (boolOrig && fxTextOriginal != null) {
            fxTextOriginal.getEngine().loadContent(WorkingCrypt.getOriginalText(), "text/html");
        }
        if (boolRes && fxTextResult != null) {
            fxTextResult.getEngine().loadContent(WorkingCrypt.getModifiedText(), "text/html");
        }

    }

    public void TextParsing(ActionEvent actionEvent) {
        //TODO Сделать окно с анализом и графиком
    }

    /**
     * @param actionEvent
     */
    public void CheckReg(ActionEvent actionEvent) {
        WorkingCrypt.toLow = ((CheckMenuItem) actionEvent.getSource()).isSelected();
        WorkingCrypt.UpdateReplChars();
        UpdateModeText(true, true);
    }

    public void CheckColor(ActionEvent actionEvent) {
        WorkingCrypt.colorText = ((CheckMenuItem) actionEvent.getSource()).isSelected();
        WorkingCrypt.updateModifiedText();
        UpdateModeText(false, true);
    }

    /**
     * Сохранение всех символов, даже не задействованых
     *
     * @param actionEvent
     */
    public void CheckSaveAll(ActionEvent actionEvent) {
        WorkingCrypt.writeAll = !((CheckMenuItem) actionEvent.getSource()).isSelected();
    }

    /**
     * Сохранение файла символов
     *
     * @param actionEvent
     */
    public void SaveData(ActionEvent actionEvent) {
        tFile file = new tFile();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Сохранение данных");
        alert.setHeaderText(null);

        if (file.SaveToFile(WorkingCrypt.getStringChars()))  //Сохранение в файл
            alert.setContentText("Файл успешно сохранён!");
        else
            alert.setContentText("Операция отменена, файл не сохранён!");

        alert.showAndWait();
    }

    /**
     * Открытие файла символов
     *
     * @param actionEvent
     */
    public void LoadData(ActionEvent actionEvent) {

        tFile file = new tFile();
        String readStr = file.ReadFile();//Открытие файла
        String[] mStrs;

        if (readStr != null) {
            mStrs = readStr.split("[\\r\\n]+");//сплит по знаку строки
            WorkingCrypt.setStringChars(mStrs); //Запись в класс

            UpdateTable();
            UpdateModeText(true, true);
        }

    }

    public void UpdateData(ActionEvent actionEvent) {
        WorkingCrypt.updateModifiedText();
        UpdateModeText(false, true); //Обновление окон
    }

    /**
     * Открытие файла с шифротекстом
     *
     * @param actionEvent
     */
    public void OpenProject(ActionEvent actionEvent) {

        tFile file = new tFile();
        String readStr = file.ReadFile();

        if (readStr != null) {    //Открытие файла
            WorkingCrypt.setOriginalText(readStr);
        }
        if (fxSplitter.getItems().size() == 0) //Создание блоков текста, если отсутствуют
            TextsSplit(true, true);

        UpdateModeText(true, true);
        UpdateTable();

    }

    /**
     * Сохранение результата из текстового блока
     *
     * @param actionEvent
     */
    public void SaveProject(ActionEvent actionEvent) {

        tFile file = new tFile();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Сохранение текста");
        alert.setHeaderText(null);

        if (file.SaveToFile(WorkingCrypt.getToPrintText()))  //Сохранение в файл
            alert.setContentText("Файл успешно сохранён!");
        else
            alert.setContentText("Операция отменена, файл не сохранён!");

        alert.showAndWait();

    }


    @FXML
    private TableView<CryptRowType> fxTableData;
    @FXML
    private TableColumn<CryptRowType, String> fxColumnOld;
    @FXML
    private TableColumn<CryptRowType, String> fxColumnNew;
    @FXML
    private TableColumn<CryptRowType, Boolean> fxColumnOn;
    @FXML
    private TableColumn<CryptRowType, Integer> fxColumnCount;


    public void initialize() {
        CryptRowType test = new CryptRowType("A", "B", true, 0);
        fxColumnOld.setCellValueFactory(cellData -> cellData.getValue().symbolProperty());
        fxColumnCount.setCellValueFactory(cellData -> cellData.getValue().countProperty().asObject());

        fxColumnNew.setCellValueFactory(cellData -> cellData.getValue().replaceProperty());
        fxColumnNew.setCellFactory(TextFieldTableCell.forTableColumn());
        fxColumnNew.setEditable(true);


        fxColumnOn.setCellValueFactory(new PropertyValueFactory<CryptRowType, Boolean>("active"));
        fxColumnOn.setCellFactory(CheckBoxTableCell.forTableColumn(fxColumnOn));
        fxColumnOn.setEditable(true);

        //заполняем таблицу данными
        fxTableData.setItems(cryptRows);
    }

    public void EndReplaceEdit(TableColumn.CellEditEvent<CryptRowType,String> cryptRowTypeStringCellEditEvent) {
        cryptRowTypeStringCellEditEvent.getRowValue();
    }
}