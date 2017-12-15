package uk.co.squirrel.squirrelserver;

import java.util.Calendar;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class MessageLog {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    
    private Date dateTime;
    private String direction;
    private String endpoint;
    private String name;
    private int value;
    private String result;

    public MessageLog(){}
    
    public MessageLog(String p_direction, String p_name, int p_value){
        dateTime = Calendar.getInstance().getTime();
        endpoint = "";
        result = "";
        direction = p_direction;
        name = p_name;
        value = p_value;
    }
    
    public MessageLog(String p_direction, String p_name, int p_value, String p_result){
        dateTime = Calendar.getInstance().getTime();
        endpoint = "";
        result = p_result;
        direction = p_direction;
        name = p_name;
        value = p_value;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
    
}
