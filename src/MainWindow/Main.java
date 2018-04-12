package MainWindow;
/*Выполнено*/
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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

        //Информирование контроллера о списке строк
        mwController contrl = loader.getController();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
