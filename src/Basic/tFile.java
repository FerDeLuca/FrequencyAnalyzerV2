package Basic;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class tFile{
    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Текстовый файл (*.txt)", "*.txt");
    public Stage stage;
    public tFile(){
        stage=new Stage();
    }
    public String ReadFile() {

        FileChooser fileChooser = new FileChooser();//Экземпляр
        fileChooser.setTitle("Выбор текстового документа");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                return new String(Files.readAllBytes(file.toPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public boolean SaveToFile(Object obj){
        boolean comf=false; //Результат выполнения
        FileChooser fileChooser = new FileChooser();//Экземпляр
        fileChooser.setTitle("Выбор места для сохранения");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(stage);
        if (file != null)
            if(saveTextToFile(obj.toString(), file))
                comf=true;
        return comf;
    }

    private boolean saveTextToFile(String content, File file) {
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(content);
            writer.close();
        } catch (IOException ex) {
            System.out.println(ex);
            return false;
        }
        return true;
    }
}
