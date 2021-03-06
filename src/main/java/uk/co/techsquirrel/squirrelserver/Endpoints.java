package uk.co.techsquirrel.squirrelserver;

import uk.co.techsquirrel.squirrelserver.repos.MessageLog;
import uk.co.techsquirrel.squirrelserver.repos.MessageLogRepository;
import uk.co.techsquirrel.squirrelserver.repos.Rule;
import uk.co.techsquirrel.squirrelserver.objects.Endpoint;
import uk.co.techsquirrel.squirrelserver.objects.Message;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class Endpoints {
    
    @Autowired
    private Application application;

    @Autowired
    private MessageLogRepository messageLogRepository;
        
    // NotifyEndpoint is used to send server that an output endpoint is available
    @RequestMapping(method=RequestMethod.POST, path="/notifyEndpoint")
    public Endpoint notifyPresents(@RequestBody Endpoint endpoint){
        System.out.println("New Output Notified: " + endpoint.getEndpointName() + " : " + endpoint.getEndpointUrl());
        application.addEndpoint(endpoint);
        return endpoint;
    }
    
    // Return current known endpoints
    @RequestMapping(method=RequestMethod.GET, path="/endpoints")
    public ArrayList<Endpoint> getEndpoints() {
        return application.getEndpoints();
    }
    
    // Input for event messages
    @RequestMapping(method=RequestMethod.POST, path="/message")
    public void notifyEndpoint(@RequestBody Message message) {
        MessageLog messageLog = new MessageLog("IN", message.getName(), message.getValue());
        messageLogRepository.save(messageLog);
        application.addMessageToQueue(message);
    }
    
    // Return current rules
    @RequestMapping(method=RequestMethod.GET, path="/rules")
    public ArrayList<Rule> getRules() {
        return application.getRules();
    }
    
    // Overwrite rules with posted rules
    @RequestMapping(method=RequestMethod.POST, path="/rules")
    public ArrayList<Rule> setRules(ArrayList<Rule> newRules) {
        return application.setRules(newRules);
    }
    
}
