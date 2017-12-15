package uk.co.squirrel.squirrelserver;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import uk.co.squirrel.objects.Message;
import uk.co.squirrel.objects.OutputMessage;

public class MessageProcessor extends Thread {

    private RuleRepository ruleRepository;
    private ArrayList<Rule> rules;
    private LinkedBlockingQueue<Message> inputMessageQueue;
    private LinkedBlockingQueue<OutputMessage> outputMessageQueue;
            
    public MessageProcessor(LinkedBlockingQueue<Message> p_inputMessageQueue, LinkedBlockingQueue<OutputMessage> p_outputMessageQueue, RuleRepository p_ruleRepository){
        inputMessageQueue = p_inputMessageQueue;
        outputMessageQueue = p_outputMessageQueue;
        ruleRepository = p_ruleRepository;
        rules = new ArrayList<>();
        loadRules();
    }
    
    @Override
    public void run() {
        while(true){
            try{
                Message message = inputMessageQueue.take();
                for(Rule rule : rules){
                    boolean conditionMet = false;
                    if(message.getName().equalsIgnoreCase(rule.getRequestName())){
                        switch(rule.getComparator()){
                            case "eq" :
                                if(message.getValue() == rule.getRequestValue()) conditionMet = true; break;
                            case "ne" :
                                if(message.getValue() != rule.getRequestValue()) conditionMet = true; break;
                            case "gt" :
                                if(message.getValue() > rule.getRequestValue()) conditionMet = true; break;
                            case "ge" :
                                if(message.getValue() >= rule.getRequestValue()) conditionMet = true; break;
                            case "lt" :
                                if(message.getValue() < rule.getRequestValue()) conditionMet = true; break;
                            case "le" :
                                if(message.getValue() <= rule.getRequestValue()) conditionMet = true; break;
                            default :
                                break;
                        }
                    }
                    outputMessageQueue.add(new OutputMessage(rule.getOutMessageEndpoint(), new Message(rule.getResponseName(), rule.getResponseValue())));
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public void loadRules(){
        try{
            for(Rule rule : ruleRepository.findAll()){
                System.out.println("Rule: " + rule.getRuleName());
                rules.add(rule);
            }
        } catch (Exception e){
            e.printStackTrace();
        }  
    }
    
    public ArrayList<Rule> getRules(){
        return rules;
    }
    
    public ArrayList<Rule> setRules(ArrayList<Rule> newRules){
        ruleRepository.deleteAll();
        for(Rule rule : newRules){
            ruleRepository.save(rule);
        }
        rules = newRules;
        return rules;
    }
}
