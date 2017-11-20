package uk.co.squirrel.objects;

public class OutputMessage {

    private Message message;
    private String endpointUrl;
    
    public OutputMessage(String p_endpointUrl, Message p_message){
        endpointUrl = p_endpointUrl;
        message = p_message;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getEndpointUrl() {
        return endpointUrl;
    }

    public void setEndpointUrl(String endpointUrl) {
        this.endpointUrl = endpointUrl;
    }
    
}
