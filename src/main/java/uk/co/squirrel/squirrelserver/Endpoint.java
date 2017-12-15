package uk.co.squirrel.squirrelserver;

public class Endpoint {

    private String endpointName;
    private String endpointUrl;

    public Endpoint(){
    }
    
    public Endpoint(String name, String url){
        endpointName = name;
        endpointUrl = url;
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
    
}
