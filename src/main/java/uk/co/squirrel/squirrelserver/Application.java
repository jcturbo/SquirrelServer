package uk.co.squirrel.squirrelserver;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import uk.co.squirrel.objects.Message;
import uk.co.squirrel.objects.OutputMessage;

@SpringBootApplication
public class Application {

    @Autowired
    private RuleRepository ruleRepository;
    
    private final LinkedBlockingQueue<Message> inputMessageQueue;
    private final LinkedBlockingQueue<OutputMessage> outputMessageQueue;
    private final Thread outputProcessor;
    private MessageProcessor messageProcessor;
            
    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }
    
    public Application() {
        inputMessageQueue = new LinkedBlockingQueue<>();
        outputMessageQueue = new LinkedBlockingQueue<>();
        outputProcessor = new Thread(new OutputProcessor(outputMessageQueue));
        messageProcessor = null;
    }
    
    @PostConstruct
    public void runProcessors(){
        outputProcessor.start();
        messageProcessor = new MessageProcessor(inputMessageQueue, outputMessageQueue, ruleRepository);
        messageProcessor.start();
    }
    
    public boolean addMessageToQueue(Message message){
        System.out.println(message.getName() + " : " + message.getValue());
        inputMessageQueue.add(message);
        return true;
    }
    
    public ArrayList<Rule> getRules(){
        return messageProcessor.getRules();
    }
    
    public ArrayList<Rule> setRules(ArrayList<Rule> newRules){
        return messageProcessor.setRules(newRules);
    }
}
