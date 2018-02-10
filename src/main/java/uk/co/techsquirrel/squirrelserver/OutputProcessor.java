package uk.co.techsquirrel.squirrelserver;

import uk.co.techsquirrel.squirrelserver.repos.MessageLog;
import uk.co.techsquirrel.squirrelserver.repos.MessageLogRepository;
import uk.co.techsquirrel.squirrelserver.objects.Endpoint;
import uk.co.techsquirrel.squirrelserver.objects.Message;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import org.springframework.web.client.RestTemplate;

public class OutputProcessor extends Thread {

    private LinkedBlockingQueue<Message> outputMessageQueue;
    private ArrayList<Endpoint> endpoints;
    private MessageLogRepository messageLogRepository;
    
    public OutputProcessor(LinkedBlockingQueue<Message> p_outputMessageQueue, ArrayList<Endpoint> p_endpoints, MessageLogRepository p_messageLogRepository) {
        outputMessageQueue = p_outputMessageQueue;
        endpoints = p_endpoints;
        messageLogRepository = p_messageLogRepository;
    }

    @Override
    public void run() {
        while (true) {
            try{
                Message outputMessage = outputMessageQueue.take();
                try {
                    System.out.println("Send output (" + outputMessage.getName()+ ") with value (" + outputMessage.getValue() + ")");
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
                        MessageLog messageLog = new MessageLog("OUT", outputMessage.getName(), outputMessage.getValue(), "Success");
                        messageLogRepository.save(messageLog);
                    }
                } catch (Exception e) {
                    MessageLog messageLog = new MessageLog("OUT", outputMessage.getName(), outputMessage.getValue(), "Failed");
                    messageLogRepository.save(messageLog);
                    System.err.println("Failed to take send output message to endpoint.");
                    e.printStackTrace();
                }
            }catch (Exception e){
                System.err.println("Failed to take output message off queue.");
                e.printStackTrace();
            }
        }
    }
}
