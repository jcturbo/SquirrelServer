package uk.co.squirrel.squirrelserver;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import org.springframework.web.client.RestTemplate;
import uk.co.squirrel.objects.Endpoint;
import uk.co.squirrel.objects.Message;
import uk.co.squirrel.objects.OutputMessage;

public class OutputProcessor extends Thread {

    private LinkedBlockingQueue<OutputMessage> outputMessageQueue;
    private ArrayList<Endpoint> endpoints;
    
    public OutputProcessor(LinkedBlockingQueue<OutputMessage> p_outputMessageQueue, ArrayList<Endpoint> p_endpoints) {
        outputMessageQueue = p_outputMessageQueue;
        endpoints = p_endpoints;
    }

    @Override
    public void run() {
        while (true) {
            try {
                OutputMessage outputMessage = outputMessageQueue.take();
                System.out.println("Send message to endpoint (" + outputMessage.getEndpointName() + ")");
                String endpointUrl = null;
                for(Endpoint endpoint : endpoints){
                    if(endpoint.getEndpointName().equalsIgnoreCase(outputMessage.getEndpointName())){
                        endpointUrl = endpoint.getEndpointUrl();
                        break;
                    }
                }
                if(endpointUrl != null){
                    RestTemplate restTemplate = new RestTemplate();
                    restTemplate.postForObject(endpointUrl, outputMessage.getMessage(), Message.class);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
