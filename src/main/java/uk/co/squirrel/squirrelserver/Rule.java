package uk.co.squirrel.squirrelserver;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class Rule {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    
    private String ruleName;
    private String requestName;
    private String comparator;
    private int requestValue;
    private String responseName;
    private int responseValue;
    private String outMessageEndpointURL;

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRequestName() {
        return requestName;
    }

    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }

    public int getRequestValue() {
        return requestValue;
    }

    public void setRequestValue(int requestValue) {
        this.requestValue = requestValue;
    }

    public String getResponseName() {
        return responseName;
    }

    public void setResponseName(String responseName) {
        this.responseName = responseName;
    }

    public int getResponseValue() {
        return responseValue;
    }

    public void setResponseValue(int responseValue) {
        this.responseValue = responseValue;
    }

    public String getOutMessageEndpointURL() {
        return outMessageEndpointURL;
    }

    public void setOutMessageEndpointURL(String outMessageEndpointURL) {
        this.outMessageEndpointURL = outMessageEndpointURL;
    }

    public String getComparator() {
        return comparator;
    }

    public void setComparator(String comparator) {
        this.comparator = comparator;
    }
    
}
