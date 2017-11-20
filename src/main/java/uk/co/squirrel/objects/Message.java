package uk.co.squirrel.objects;

public class Message {

    private String name;
    private int value;
    
    public Message(){
        name = "";
        value = 0;
    }
    
    public Message(String p_name, int p_value){
        name = p_name;
        value = p_value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
