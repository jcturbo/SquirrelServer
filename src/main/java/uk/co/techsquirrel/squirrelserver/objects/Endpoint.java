package uk.co.techsquirrel.squirrelserver.objects;

public class Endpoint {

    private String endpointName;
    private String endpointUrl;
    private int maxValue; // Assume min is 0 and step is 1

    public Endpoint(){
    }
        
    public Endpoint(String name, String url){
        endpointName = name;
        endpointUrl = url;
        maxValue = 1;
    }
    
    public Endpoint(String name, String url, int max){
        endpointName = name;
        endpointUrl = url;
        maxValue = max;
    }
    
    public String getEndpointName() {
        return endpointName;
    }

    public void setEndpointName(String endpointName) {
        this.endpointName = endpointName;
    }

    public String getEndpointUrl() {
        return endpointUrl;
    }

    public void setEndpointUrl(String endpointUrl) {
        this.endpointUrl = endpointUrl;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }
    
}
