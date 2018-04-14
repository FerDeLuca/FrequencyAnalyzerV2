package Functionality;
/*Выполнено*/

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class tFile{
    public Stage stage;
    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
            "Текстовый файл (*.txt)",
            "*.txt");

    public tFile(){
        stage=new Stage();
    }

    /**
     * Выбор файла и считвание
     * @return  строка
     */
    public String ReadFile() {

        FileChooser fileChooser = new FileChooser();//Экземпляр
        fileChooser.setTitle("Выбор текстового документа");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(stage);



        if (file != null) {
            try {
                String s=  Files.readAllLines(Paths.get(file.getPath()), StandardCharsets.UTF_8).toString();
                s=s.replaceAll("[\\[.\\]+]", "");
                s=s.replace(", ", "\r\n");
                return s;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }




        return null;
    }

    /**
     * Сохранение объекта в файл
     * @param obj   объект для сохранения
     * @return  результат сохранения
     */
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

    /**
     * Сохранение текста в файл
     * @param content   Текст
     * @param file      Путь к файлу
     * @return          Результат сохранения
     */
    private boolean saveTextToFile(String content, File file) {
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(content);
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }
}
