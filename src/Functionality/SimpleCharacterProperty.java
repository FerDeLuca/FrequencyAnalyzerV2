package Functionality;
/*Выполнено*/
import javafx.beans.property.StringPropertyBase;

public class SimpleCharacterProperty extends StringPropertyBase {

    private static final Object DEFAULT_BEAN = null;
    private static final String DEFAULT_NAME = "";

    private final Object bean;
    private final String name;

    @Override
    public Object getBean() {
        return bean;
    }

    @Override
    public String getName() {
        return name;
    }

    public SimpleCharacterProperty() {
        this(DEFAULT_BEAN, DEFAULT_NAME);
    }

    public SimpleCharacterProperty(String initialValue) {
        this(DEFAULT_BEAN, DEFAULT_NAME, initialValue);
    }

    public SimpleCharacterProperty(Object bean, String name) {
        this.bean = bean;
        this.name = (name == null) ? DEFAULT_NAME : name;
    }

    public SimpleCharacterProperty(Object bean, String name, String initialValue) {
        set(initialValue);
        this.bean = bean;
        this.name = (name == null) ? DEFAULT_NAME : name;
    }

    public void set(String newValue) {
        if (newValue != null && newValue.length()>0) {
            if (newValue.charAt(0) == ' ') {
                super.set(String.valueOf(newValue.trim().charAt(0)));
            } else {
                super.set(String.valueOf(newValue.charAt(0)));
            }
        }else {
            super.set(" ");
        }
    }
}