package ChartsWindow;

import Functionality.WorkingCrypt;
import MainWindow.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SubWindow {
    public SubWindow() throws Exception
    {

        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("ChartsWindow.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Гистограммы");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
