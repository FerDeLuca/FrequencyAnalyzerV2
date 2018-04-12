package MainWindow;

import Basic.CryptType;
import javafx.beans.property.*;

public class SymbRow{
    private SimpleIntegerProperty count;
    private SimpleStringProperty name;
    private SimpleStringProperty renew;
    private SimpleBooleanProperty active;

    public SymbRow(){
        this(" "," ",false,0);
    }

    public SymbRow(String name,String renew,boolean active,int count){
        this.name = new SimpleStringProperty(String.valueOf(name.charAt(0)));
        this.renew = new SimpleStringProperty(String.valueOf(renew.charAt(0)));
        this.active = new SimpleBooleanProperty(active);
        this.count = new SimpleIntegerProperty(count);
    }

    //Count
    public Integer getCount() {
        return count.get();
    }

    public void setCount(int count) {
        this.count.set(count);
    }

    public SimpleIntegerProperty countProperty() {
        return count;
    }

    //Active
    public Boolean getActive() {
        return active.get();
    }

    public void setActive(Boolean active) {
        this.active.set(active);
    }

    public SimpleBooleanProperty activeProperty() {
        return active;
    }

    //Renew
    public String getRenew() {
        if (renew!=null)
            return renew.get().trim();
        return renew.get();
    }

    public void setRenew(String renew) {
        if(renew!=null)
            this.renew.set(String.valueOf(renew.charAt(0)));
    }

    public SimpleStringProperty renewProperty() {
        return renew;
    }

    //Name
    public String getName() {
        if (name!=null)
            return name.get().trim();
        return name.get();
    }

    public void setName(String name) {
        if(name!=null)
            this.name.set(String.valueOf(name.charAt(0)));
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public static CryptType.StatChar toStatChar (SymbRow sr){
        return new CryptType.StatChar(sr.getName().charAt(0),sr.getRenew().charAt(0),sr.getActive(),sr.getCount());
    }

}
