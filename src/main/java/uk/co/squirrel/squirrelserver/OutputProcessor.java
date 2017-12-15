package uk.co.squirrel.squirrelserver;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import org.springframework.web.client.RestTemplate;

public class OutputProcessor extends Thread {

    private LinkedBlockingQueue<Message> outputMessageQueue;
    private ArrayList<Endpoint> endpoints;
    
    public OutputProcessor(LinkedBlockingQueue<Message> p_outputMessageQueue, ArrayList<Endpoint> p_endpoints) {
        outputMessageQueue = p_outputMessageQueue;
        endpoints = p_endpoints;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Message outputMessage = outputMessageQueue.take();
                System.out.println("Send message to endpoint (" + outputMessage.getName()+ ")");
                String endpointUrl = null;
                for(Endpoint endpoint : endpoints){
                    if(endpoint.getEndpointName().equalsIgnoreCase(outputMessage.getName())){
                        endpointUrl = endpoint.getEndpointUrl();
                        break;
                    }
                }
                if(endpointUrl != null){
                    RestTemplate restTemplate = new RestTemplate();
                    restTemplate.postForObject(endpointUrl, outputMessage, Message.class);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
