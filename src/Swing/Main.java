package Swing;

import Basic.CryptType;

public class Main {
    public static CryptType cryptType;

    public static void main(String[] args) {
        cryptType= new CryptType();
        SwingWindow mainWindow = new SwingWindow();
        mainWindow.pack();
        mainWindow.setVisible(true);
    }
}