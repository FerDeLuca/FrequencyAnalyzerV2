package Swing;

import Basic.CryptType;
import Basic.tFile;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.*;
import javax.swing.text.DefaultCaret;
import java.io.IOException;
import java.util.StringJoiner;

public class SwingWindow extends JFrame {
    private JPanel mainPanel;
    private JButton mAnalyzer;
    private JButton mSave;
    private JButton mReport;
    private JButton mOpen;
    private JRadioButton View1;
    private JRadioButton View2;
    private JRadioButton View3;
    private JTextPane tOriginal;
    private JTextPane tResult;
    private JPanel pView;
    private JPanel pViewText;
    private JButton bSymSave;
    private JButton bSymLoad;
    private JTable bTable;
    private JScrollPane bScrollPane;
    private JButton bUpdateData;
    private JCheckBox mCheckReg;
    private JPanel pResult;
    private JPanel pOriginal;
    private JProgressBar bBar;
    private JCheckBox mCheckColor;
    private JSplitPane tSplit;
    private JScrollPane scrollOriginal;
    private JScrollPane scrollResult;
    private ButtonGroup ViewOptional;

    public SwingWindow() {
        $$$setupUI$$$();
        this.getContentPane().add(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(mainPanel.getMinimumSize());

        //---------События---------------
        this.View1.addActionListener(new VisibleText());
        this.View2.addActionListener(new VisibleText());
        this.View3.addActionListener(new VisibleText());

        // Событие при нажатии клавиши mOpen
        this.mOpen.addActionListener(new ActionListener() {
            @Override //Переопределение метода при нажатии клавиши
            public void actionPerformed(ActionEvent actionEvent) {
                tFile file = new tFile();
                String readStr = file.ReadFile();
                if (readStr != null) {    //Открытие файла
                    CryptType.setOriginalText(readStr);
                    UpdateTextBlock(true);
                }
            }
        });

        //Кнопка обновления для текста (результат)
        bUpdateData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CryptType.updateModifiedText();
                UpdateTextBlock(false);
            }
        });

        //CheckBox активация перевода регистра
        mCheckReg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CryptType.toLow = mCheckReg.isSelected();
                CryptType.UpdateReplChars();
                UpdateTextBlock(true);
            }
        });

        //CheckBox цветовой подсветки
        mCheckColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CryptType.colorText = mCheckColor.isSelected();
                CryptType.updateModifiedText();
                UpdateTextBlock(false);
            }
        });

        //Сохранение текста результата
        mSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tFile file = new tFile();
                String msg;
                if (file.SaveToFile(CryptType.getToPrintText()))  //Сохранение в файл
                    msg = "Файл успешно сохранён!";
                else
                    msg = "Произошла ошибка, файл не сохранён!";

                JOptionPane.showMessageDialog(null, msg, "Оповещение", JOptionPane.INFORMATION_MESSAGE);

            }
        });

        //Сохранение данных (символы)
        bSymSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                               tFile file = new tFile();
                String msg;
                if (file.SaveToFile(CryptType.getStringChars(false)))  //Сохранение в файл
                    msg = "Файл успешно сохранён!";
                else
                    msg = "Произошла ошибка, файл не сохранён!";
                JOptionPane.showMessageDialog(null, msg, "Оповещение", JOptionPane.INFORMATION_MESSAGE);

            }
        });

        //Считывание данных (символы)
        bSymLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tFile file = new tFile();
                String readStr = file.ReadFile();//Открытие файла
                String[] mStrs;

                if (readStr != null) {
                    mStrs = readStr.split("[\\r\\n]+");//сплит по знаку строки
                    CryptType.setStringChars(mStrs); //Запись в класс
                    UpdateTextBlock(true);
                }
            }
        });
    }

    private void createUIComponents() {

        //Создание таблицы
        BasicTableModel model = new BasicTableModel(true);
        bTable = new JTable(model);

        //Центрирование
        bTable.setDefaultRenderer(Character.class, new MyRenderer());
        bTable.setDefaultRenderer(String.class, new MyRenderer());
        bTable.setDefaultRenderer(Integer.class, new MyRenderer());
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        mainPanel = new JPanel();
        mainPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.setMinimumSize(new Dimension(600, 400));
        mainPanel.setPreferredSize(new Dimension(800, 600));
        final JToolBar toolBar1 = new JToolBar();
        toolBar1.setBackground(new Color(-592138));
        toolBar1.setFloatable(false);
        mainPanel.add(toolBar1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 20), null, 0, false));
        toolBar1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.ABOVE_TOP));
        mOpen = new JButton();
        mOpen.setText("Открыть");
        toolBar1.add(mOpen);
        mSave = new JButton();
        mSave.setText("Сохранить");
        toolBar1.add(mSave);
        mAnalyzer = new JButton();
        mAnalyzer.setText("Анализ частот");
        toolBar1.add(mAnalyzer);
        mReport = new JButton();
        mReport.setText("Отчёт");
        toolBar1.add(mReport);
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        toolBar1.add(spacer1);
        mCheckColor = new JCheckBox();
        mCheckColor.setSelected(true);
        mCheckColor.setText("Подсветка");
        toolBar1.add(mCheckColor);
        mCheckReg = new JCheckBox();
        mCheckReg.setSelected(true);
        mCheckReg.setText("Смена регистра");
        mCheckReg.setToolTipText("Перевод всего текста в регистр малых букв");
        toolBar1.add(mCheckReg);
        bSymSave = new JButton();
        bSymSave.setText("Сохранить");
        toolBar1.add(bSymSave);
        bSymLoad = new JButton();
        bSymLoad.setText("Загрузить");
        toolBar1.add(bSymLoad);
        pView = new JPanel();
        pView.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 4, new Insets(0, 0, 0, 0), -1, -1));
        pView.setEnabled(true);
        mainPanel.add(pView, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(318, 224), null, 0, false));
        View1 = new JRadioButton();
        View1.setSelected(true);
        View1.setText("Шифр/Результат");
        View1.putClientProperty("hideActionText", Boolean.FALSE);
        pView.add(View1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTHWEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        View2 = new JRadioButton();
        View2.setText("Оригинал");
        pView.add(View2, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTHWEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        View3 = new JRadioButton();
        View3.setText("Результат");
        pView.add(View3, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTHWEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        pViewText = new JPanel();
        pViewText.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        pView.add(pViewText, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        tSplit = new JSplitPane();
        tSplit.setDividerLocation(300);
        tSplit.setDividerSize(2);
        tSplit.setOrientation(1);
        pViewText.add(tSplit, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        pOriginal = new JPanel();
        pOriginal.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tSplit.setLeftComponent(pOriginal);
        scrollOriginal = new JScrollPane();
        scrollOriginal.setHorizontalScrollBarPolicy(31);
        scrollOriginal.setRequestFocusEnabled(false);
        pOriginal.add(scrollOriginal, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        tOriginal = new JTextPane();
        tOriginal.setContentType("text/html");
        tOriginal.setEditable(false);
        tOriginal.setMargin(new Insets(0, 0, 0, 0));
        scrollOriginal.setViewportView(tOriginal);
        pResult = new JPanel();
        pResult.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tSplit.setRightComponent(pResult);
        scrollResult = new JScrollPane();
        scrollResult.setHorizontalScrollBarPolicy(31);
        scrollResult.setVerticalScrollBarPolicy(20);
        pResult.add(scrollResult, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tResult = new JTextPane();
        tResult.setContentType("text/html");
        tResult.setEditable(false);
        tResult.setMargin(new Insets(0, 0, 0, 0));
        scrollResult.setViewportView(tResult);
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        pView.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(210, -1), new Dimension(210, -1), new Dimension(210, -1), 0, false));
        bScrollPane = new JScrollPane();
        bScrollPane.setVerticalScrollBarPolicy(22);
        panel1.add(bScrollPane, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        bTable.setAutoscrolls(true);
        bTable.setColumnSelectionAllowed(false);
        bTable.setPreferredScrollableViewportSize(new Dimension(450, 400));
        bTable.setShowHorizontalLines(true);
        bTable.setShowVerticalLines(false);
        bTable.setUpdateSelectionOnSort(true);
        bScrollPane.setViewportView(bTable);
        bUpdateData = new JButton();
        bUpdateData.setMargin(new Insets(2, 14, 4, 14));
        bUpdateData.setText("Обновить данные");
        bUpdateData.setVerticalAlignment(1);
        bUpdateData.setVerticalTextPosition(1);
        panel1.add(bUpdateData, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTH, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(-1, 16), new Dimension(-1, 30), new Dimension(-1, 30), 0, false));
        bBar = new JProgressBar();
        bBar.setValue(0);
        panel1.add(bBar, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ViewOptional = new ButtonGroup();
        ViewOptional.add(View1);
        ViewOptional.add(View2);
        ViewOptional.add(View3);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

    //Форматирование колонок по центру
    public class MyRenderer extends DefaultTableCellRenderer {

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setHorizontalAlignment(SwingConstants.CENTER);
            return this;
        }
    }

    // Переключение между областями отображения
    private class VisibleText implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            pResult.setVisible(true);
            pOriginal.setVisible(true);
            if (View1.isSelected()) {
                tSplit.setDividerLocation(0.5);
            }
            if (View2.isSelected()) {
                pResult.setVisible(false);
            }
            if (View3.isSelected()) {
                pOriginal.setVisible(false);
            }
        }
    }

    //Отрисовка блоков текста
    private void UpdateTextBlock(boolean updateOrigText) {
        //Обновление блоков отображения текста
        if (updateOrigText) {
            tOriginal.setText(CryptType.getOriginalText());
        }
        tResult.setText(CryptType.getModifiedText());
        bTable.setModel(new BasicTableModel(true));

        DefaultCaret caret = (DefaultCaret) tResult.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);


    }

    public void Progress(double value) {
        this.bBar.setValue((int) Math.round(value));
    }
}
