package uk.co.squirrel.squirrelserver;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageProcessor extends Thread {

    private RuleRepository ruleRepository;
    private ArrayList<Rule> rules;
    private LinkedBlockingQueue<Message> inputMessageQueue;
    private LinkedBlockingQueue<Message> outputMessageQueue;
            
    public MessageProcessor(LinkedBlockingQueue<Message> p_inputMessageQueue, LinkedBlockingQueue<Message> p_outputMessageQueue, RuleRepository p_ruleRepository){
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
                    if(message.getName().equalsIgnoreCase(rule.getInputName())){
                        switch(rule.getComparator()){
                            case "eq" :
                                if(message.getValue() == rule.getInputValue()) conditionMet = true; break;
                            case "ne" :
                                if(message.getValue() != rule.getInputValue()) conditionMet = true; break;
                            case "gt" :
                                if(message.getValue() > rule.getInputValue()) conditionMet = true; break;
                            case "ge" :
                                if(message.getValue() >= rule.getInputValue()) conditionMet = true; break;
                            case "lt" :
                                if(message.getValue() < rule.getInputValue()) conditionMet = true; break;
                            case "le" :
                                if(message.getValue() <= rule.getInputValue()) conditionMet = true; break;
                            default :
                                break;
                        }
                    }
                    if(conditionMet){
                        outputMessageQueue.add(new Message(rule.getOutputName(), rule.getOutputValue()));
                    }
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
