package uk.co.squirrel.squirrelserver;

import java.util.concurrent.LinkedBlockingQueue;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import uk.co.squirrel.objects.Message;
import uk.co.squirrel.objects.OutputMessage;

public class OutputProcessor implements Runnable {

    private LinkedBlockingQueue<OutputMessage> outputMessageQueue;

    public OutputProcessor(LinkedBlockingQueue<OutputMessage> p_outputMessageQueue) {
        outputMessageQueue = p_outputMessageQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                OutputMessage outputMessage = outputMessageQueue.take();
                System.out.println("Send message to endpoint (" + outputMessage.getEndpointUrl() + ")");
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.postForObject(outputMessage.getEndpointUrl(), outputMessage.getMessage(), Message.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
