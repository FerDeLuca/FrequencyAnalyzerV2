package MainWindow;

import Basic.CryptType;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("MainWindow.fxml"));
        primaryStage.setTitle("Частотный анализ");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(400);

//        // Даём контроллеру доступ к главному приложению.
//        Controller controller = loader.getController();
//        controller.sew
    }

    private ObservableList<SymbRow> symbRows = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }
}
