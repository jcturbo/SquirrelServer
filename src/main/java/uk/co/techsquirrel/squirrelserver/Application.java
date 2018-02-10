package uk.co.techsquirrel.squirrelserver;

import uk.co.techsquirrel.squirrelserver.repos.MessageLogRepository;
import uk.co.techsquirrel.squirrelserver.repos.Rule;
import uk.co.techsquirrel.squirrelserver.repos.RuleRepository;
import uk.co.techsquirrel.squirrelserver.objects.Endpoint;
import uk.co.techsquirrel.squirrelserver.objects.Message;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "uk.co.techsquirrel.**.*")
public class Application {

    @Autowired
    private RuleRepository ruleRepository;
    
    @Autowired
    private MessageLogRepository messageLogRepository;
    
    private ArrayList<Endpoint> endpoints;
    
    private final LinkedBlockingQueue<Message> inputMessageQueue;
    private final LinkedBlockingQueue<Message> outputMessageQueue;
    private Thread outputProcessor;
    private MessageProcessor messageProcessor;
    private final Alarm alarm;
            
    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }
    
    public Application() {
        inputMessageQueue = new LinkedBlockingQueue<>();
        outputMessageQueue = new LinkedBlockingQueue<>();
        outputProcessor = null;
        messageProcessor = null;
        endpoints = new ArrayList<>();
        alarm = new Alarm();
    }
    
    @PostConstruct
    public void runProcessors(){
        outputProcessor = new OutputProcessor(outputMessageQueue, endpoints, messageLogRepository);
        outputProcessor.start();
        messageProcessor = new MessageProcessor(inputMessageQueue, outputMessageQueue, endpoints, ruleRepository);
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
    
    public ArrayList<Endpoint> getEndpoints(){
        return endpoints;
    }
    
    public void addEndpoint(Endpoint newEndpoint){
        boolean endpointAlreadyExists = false;
        for(Endpoint endpoint : endpoints){
            if(endpoint.getEndpointName().equalsIgnoreCase(newEndpoint.getEndpointName())){
                endpointAlreadyExists = true;
                break;
            }
        }
        if(!endpointAlreadyExists){
            endpoints.add(newEndpoint);
        }
    }
    
}
