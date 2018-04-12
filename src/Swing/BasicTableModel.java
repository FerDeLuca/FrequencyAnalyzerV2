package Swing;

import Basic.CryptType;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BasicTableModel extends AbstractTableModel {

    // ---------------------------- FIELDS ------------------------------------
    //  https://javatalks.ru/topics/16294

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -485101156922142740L;

    /**
     * Имя скрытого столбца, используемого для хранения объекта.
     */
    public static final String OBJECT_COLUMN_NAME = "Object";

    /**
     * Номер скрытого столбца, используемого для хранения объекта.
     */
    public static final int OBJECT_COLUMN_NUMBER = 0;

    /**
     * Названия столбцов.
     */
    private String[] columnNames = {"Символ", "Замена", "Кол.", "Вкл."};

    /**
     * Типы столбцов.
     */
    private Class<?>[] columnTypes = {Character.class,String.class,Integer.class,Boolean.class};

    /**
     * Данные.
     */
    private CryptType.StatChar[] data = CryptType.replChars;

    /**
     * Возможность редактирования.
     */
    private boolean editable;

    /**
     * Редактируемость столбцов.
     */
    private Boolean[] columnEditable = {false,true,false,true};

    /**
     * Текущий отсортированный столбец.
     */
    private int currentSorted = -1;

    /**
     * Текущая сортировка - по возрастанию/убыванию.
     */
    private boolean currentAsc = false;

    // ------------------------------ METHODS ---------------------------------

    /**
     * Конструктор.
     *
     * @param editable - редактируемая таблица
     */
    public BasicTableModel(boolean editable) {
        this.editable = editable;
    }

    /**
     * Получить количество строк.
     *
     * @return количество строк
     */
    public int getRowCount() {
        synchronized (data) {
            return data.length;
        }
    }

    /**
     * Получить количество столбцов.
     *
     * @return количество столбцов
     */
    public int getColumnCount() {
        return columnNames.length;
    }

    /**
     * Тип данных столбца.
     *
     * @param column - номер столбца
     *
     * @return тип данных столбце
     */
    @Override
    public Class<?> getColumnClass(int column) {
        if (column >= 0 && column < columnNames.length) {
            return columnTypes[column];
        }
        return null;
    }

    /**
     * Тип данных столбцов.
     *
     * @return Список типов данных столбцов
     */
    public List<Class<?>> getColumnsClass() {
        List<Class<?>> telClass = new ArrayList<>();
        for(Class type : columnTypes)
            telClass.add(type);
        return telClass;
    }

    /**
     * Получить название столбца.
     *
     * @param column - номер столбца
     *
     * @return наименование столбца
     */
    @Override
    public String getColumnName(int column) {
        if (column >= 0 && column < columnNames.length) {
            return columnNames[column];
        }
        return "";
    }

    /**
     * Получить данные из ячейки.
     *
     * @param row - номер строки
     * @param column - номер сторбца
     *
     * @return значение в ячейке
     */
    public Object getValueAt(int row, int column) {
        if (row < data.length) {
            CryptType.StatChar dataColumns = data[row];
            if (dataColumns == null) {
                return null;
            }
            if (column >= 0 && column < 4) {
                synchronized (data) {
                    if(dataColumns.getData(column).equals(' '))
                        return "";
                    else
                        return dataColumns.getData(column);
                }
            }
        }
        return null;
    }

    /**
     * Редактируемость ячейки таблицы.
     *
     * @param row - номер строки
     * @param column - номер столбца
     *
     * @return редактируемый
     */
    public boolean isEditable(int row, int column) {
        return columnEditable[column];
    }

    /**
     * Установить значение в ячейку.
     *
     * @param value - значение
     * @param row - номер строки
     * @param column - номер столбца
     */
    @Override
    public void setValueAt(Object value, int row, int column) {
        if (row < data.length) {
            if (column == 1) {  //Заплатка для колонки с вводом char типа
                synchronized (data) {
                    if(value.equals(" ") || value.equals(""))
                        data[row].setData(column,' ');
                    else
                        data[row].setData(column, ((String) value).trim().charAt(0));

                    fireTableCellUpdated(row, column);
                }
            }
            else {
                checkValue(value, column);
                synchronized (data) {
                    data[row].setData(column, value);
                    fireTableCellUpdated(row, column);
                }
            }
        }
    }

    /**
     * Очистить данные.
     */
    public void removeAll() {
        synchronized (data) {
            data=null;
            fireTableRowsDeleted(data.length, data.length);
        }
    }

//    /**
//     * Удалить строку.
//     *
//     * @param row - номер строки
//     */
//    public void removeRow(int row) {
//        synchronized (data) {
//            data.remove(row);
//            this.fireTableRowsDeleted(row, row);
//        }
//
//    }

//    /**
//     * Удалить диапазон строк.
//     *
//     * @param rows - диапазон строк
//     */
//    public void removeRow(int[] rows) {
//        // Удалить интервал
//        for (int i = 0; i < rows.length; i++) {
//            int row = rows[i];
//
//            for (int j = 0; j < i; j++) {
//                if (rows[i] > rows[j]) {
//                    row--;
//                }
//            }
//
//            removeRow(row);
//        }
//    }

//    /**
//     * Добавить столбец.
//     *
//     * @param columnName - имя столбца
//     * @param columnType - тип столбца
//     */
//    public void addColumn(String columnName, Class<?> columnType) {
//        synchronized (this) {
//            columnNames.add(columnName);
//            columnTypes.add(columnType);
//            // Добавляем столбец в данные
//            for (int i = 0; i < data.size(); i++) {
//                data.get(i).add(null);
//            }
//            columnEditable.add(editable);
//        }
//        fireTableStructureChanged();
//    }

    /**
     * Является ли ячейка редактируемой.
     *
     * @param row - номер строки
     * @param col - номер столбца
     *
     * @return да/нет
     */
    @Override
    public boolean isCellEditable(int row, int col) {
        return isEditable(row, col);
    }

//    /**
//     * Указать редактируемость столбца.
//     *
//     * @param index - номер столбца
//     * @param editable - редактируемость
//     */
//    public void setColumnEditable(int index, boolean editable) {
//        columnEditable.set(index, editable);
//    }

//    /**
//     * Добавить строку.
//     *
//     * @param row - строка
//     */
//    public void add(List<Object> row) {
//        add(data.size(), row);
//        if (currentSorted >= 0) {
//            sortByColumn(currentSorted, currentAsc);
//        }
//    }

//    /**
//     * Добавить строку.
//     *
//     * @param row - на позицию
//     * @param values - строка
//     */
//    public void add(int row, List<Object> values) {
//        if (values.size() != columnNames.size()) {
//            throw new Error("Неверна длина добавляемой строки: "
//                    + values.size() + " (должно быть " + columnNames.size()
//                    + ")");
//        }
//        int i = 0;
//        for (Object object : values) {
//            checkValue(object, i++);
//        }
//
//        synchronized (data) {
//            data.add(row, values);
//        }
//
//        fireTableRowsInserted(row, 1);
//    }

    /**
     * Вернуть индекс столбца в модели таблицы.
     *
     * @param name - имя столбца
     *
     * @return индекс
     */
    public int getColumnModelIndex(String name) {
        int index = columnNames.length - 1;
        boolean found = false;

        while (!found && (index > 0)) {
            if (columnNames[index].equals(name)) {
                found = true;
            } else {
                index--;
            }
        }

        return index;
    }

    /**
     * Удалить все столбцы.
     */
    public void removeColumns() {
        removeAll();
        synchronized (columnNames) {
            synchronized (columnTypes) {
                columnTypes=null;
            }
            columnNames=null;
            fireTableStructureChanged();
        }
    }

    /**
     * Получить объекты строки.
     *
     * @param row строка
     * @return строка
     */
    public CryptType.StatChar getRow(int row) {
        if (row < data.length) {
            return data[row];
        }
        return null;
    }

//    /**
//     * Отсортировать по столбцу.
//     *
//     * @param column
//     * @param asc по возрастанию/по убыванию
//     */
//    public void sortByColumn(int column, boolean asc) {
//        if (column < 0 || column >= columnNames.size()) {
//            removeSort();
//            return;
//        }
//
//        currentSorted = column;
//        currentAsc = asc;
//
//        List<List<Object>> sorted = new ArrayList<List<Object>>(data);
//        // Сортировка пузырьком
//        for (int i = 0; i < data.size() - 1; i++) {
//            for (int j = 0; j <= data.size() - 2 - i; j++) {
//                if (compare(sorted.get(j).get(column), sorted.get(j + 1).get(
//                        column), asc) > 0) {
//                    List<Object> forChange = sorted.get(j);
//                    sorted.set(j, sorted.get(j + 1));
//                    sorted.set(j + 1, forChange);
//                }
//            }
//        }
//        removeAll();
//        for (int i = 0; i < sorted.size(); i++) {
//            add(i, sorted.get(i));
//        }
//    }
//
//    /**
//     * Сбросить сортировку.
//     */
//    public void removeSort() {
//        currentSorted = -1;
//        currentAsc = false;
//    }

//    /**
//     * Получить количество строк.
//     *
//     * @return количество строк
//     */
//    public List<List<Object>> getData() {
//        return ;
//    }

    /**
     * Получить количество строк.
     *
     * @return количество строк
     */
    public List<String> getColumnNames() {
        return new ArrayList<String>(Arrays.asList(columnNames));
    }

    // ----------------------------- LOCALS -----------------------------------

//    /**
//     * Сравнение значений в столбце разных строк (для сортировки).
//     *
//     * @param value1 первое значение
//     * @param value2 второе значение
//     * @param asc по возрастанию/убыванию
//     * @return результат сравнения (как в comparable)
//     *
//     */
//    @SuppressWarnings("unchecked")
//    private int compare(Object value1, Object value2, boolean asc) {
//        Comparable val1 = (Comparable) value1;
//        Comparable val2 = (Comparable) value2;
//        int compareResult;
//        if (val1 == null && val2 == null) {
//            compareResult = 0;
//        } else if (val1 == null && val2 != null) {
//            compareResult = asc ? -1 : 1;
//        } else if (val1 != null && val2 == null) {
//            compareResult = asc ? 1 : -1;
//        } else {
//            compareResult = asc ? val1.compareTo(val2) : val2.compareTo(val1);
//        }
//
//        return compareResult;
//    }

    /**
     * Проверить значение на соответсвие модели.
     *
     * @param value - проверяемое значение
     * @param column - столбец для значения
     */
    private void checkValue(Object value, int column) {
        if ((value != null) && !columnTypes[column].isInstance(value)) {
            throw new Error("Неверный тип данных столбца "
                    + columnNames[column] + ": "
                    + value.getClass().toString() + ", должен быть "
                    + columnTypes[column]);

        }

    }
}