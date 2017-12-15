package uk.co.squirrel.squirrelserver;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.co.squirrel.objects.Endpoint;
import uk.co.squirrel.objects.Message;

@CrossOrigin
@RestController
public class Endpoints {
    
    @Autowired
    private Application application;
    
    @RequestMapping(method=RequestMethod.POST, path="/message")
    public void notifyEndpoint(Message message) {
        application.addMessageToQueue(message);
    }
    
    @RequestMapping(method=RequestMethod.GET, path="/rules")
    public ArrayList<Rule> getRules() {
        return application.getRules();
    }
    
    @RequestMapping(method=RequestMethod.POST, path="/rules")
    public ArrayList<Rule> getRules(ArrayList<Rule> newRules) {
        return application.getRules();
    }
    
    // NotifyEndpoint is used to send server that an output endpoint is available
    @RequestMapping(method=RequestMethod.POST, path="/notifyEndpoint")
    public void notifyPresents(Endpoint endpoint){
        System.out.println("Endpoint: " + endpoint.getEndpointName());
        application.addEndpoint(endpoint);
    }
    
}
