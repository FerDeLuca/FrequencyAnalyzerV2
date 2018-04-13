package Functionality;
/*Завершено*/
import javafx.beans.property.*;

public class CryptRowType {
    private SimpleCharacterProperty symbol;
    private SimpleCharacterProperty replace;
    private SimpleBooleanProperty active;
    private SimpleIntegerProperty count;

    public CryptRowType(){
        this(" "," ",false,0);
    }
    public CryptRowType(String symbol, int count){
        this(symbol," ",false,count);
    }

    public CryptRowType(String symbol, String replace, boolean active, int count){
        this.symbol = new SimpleCharacterProperty(symbol);
        this.replace = new SimpleCharacterProperty(replace);
        this.active = new SimpleBooleanProperty(active);
        this.count = new SimpleIntegerProperty(count);
    }

    public SimpleCharacterProperty symbolProperty() { return symbol; }
    public SimpleCharacterProperty replaceProperty() { return replace; }
    public SimpleIntegerProperty countProperty() { return count; }
    public SimpleBooleanProperty activeProperty() { return active; }


    //Count
    public Integer getCount() {
        return count.get();
    }
    public void setCount(int count) {
        this.count.set(count);
    }

    //Active
    public Boolean getActive() {
        return active.get();
    }
    public void setActive(Boolean active) {
        this.active.set(active);
    }

    //replace
    public String getReplace() {
        if (replace==null || replace.get()==" ")
            return " ";
        else
            return replace.get();
    }
    public void setReplace(String replace) {
        this.replace.set(replace);
    }

    //replace
    public String getSymbol() {
        if (symbol==null || symbol.get()==" ")
            return " ";
        else
            return symbol.get();
    }
    public void setSymbol(String replace) {
        this.symbol.set(replace);
    }

    /**
     * Получение данных столбца по id
     * @param column    идентификатор столбца
     * @return  объект колонки
     */
    public Object getData(int column){
        Object resObj;
        switch (column){
            case 0:
                resObj = symbolProperty().getValue();
                break;
            case 1:
                resObj = replaceProperty().getValue();
                break;
            case 2:
                resObj = activeProperty().getValue();
                break;
            case 3:
                resObj = countProperty().getValue();
                break;
            default:
                resObj = new Object();
        }
        return resObj;
    }

    /**
     * Запись данных в столбец
     * @param column идентификатор столбца
     * @param obj объект
     */
    public void setData(int column, Object obj) {
        try {
            switch (column) {
                case 0:
                    this.symbolProperty().set((String)obj);
                    break;
                case 1:
                    this.replaceProperty().set((String)obj);
                    break;
                case 2:
                    this.activeProperty().set((Boolean)obj);
                    break;
                case 3:
                    this.countProperty().set((Integer) obj);
                break;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
