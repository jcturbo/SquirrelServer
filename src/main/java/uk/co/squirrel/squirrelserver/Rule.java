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
    private String inputName;
    private String comparator;
    private int inputValue;
    private String outputName;
    private int outputValue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getInputName() {
        return inputName;
    }

    public void setInputName(String inputName) {
        this.inputName = inputName;
    }

    public String getComparator() {
        return comparator;
    }

    public void setComparator(String comparator) {
        this.comparator = comparator;
    }

    public int getInputValue() {
        return inputValue;
    }

    public void setInputValue(int inputValue) {
        this.inputValue = inputValue;
    }

    public String getOutputName() {
        return outputName;
    }

    public void setOutputName(String outputName) {
        this.outputName = outputName;
    }

    public int getOutputValue() {
        return outputValue;
    }

    public void setOutputValue(int outputValue) {
        this.outputValue = outputValue;
    }
    
}
