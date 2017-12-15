package uk.co.squirrel.objects;

public class OutputMessage {

    private Message message;
    private String endpointName;
    
    public OutputMessage(String p_endpointName, Message p_message){
        endpointName = p_endpointName;
        message = p_message;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getEndpointName() {
        return endpointName;
    }

    public void setEndpointName(String endpointName) {
        this.endpointName = endpointName;
    }
    
}
