package uk.co.techsquirrel.squirrelserver;

import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Component;

@Component
public class AlarmBell {

    private boolean sound;
    
    public AlarmBell(){
        sound = false;
        
        new Thread(){
            @Override
            public void run() {
             
                long bellLastSounded = 0;
                
                try {
                    while(true){
                        if(sound){
                            long currentTime = Calendar.getInstance().getTimeInMillis();
                            if(bellLastSounded + 2000 < Calendar.getInstance().getTimeInMillis()){
                                bellLastSounded = currentTime;
                                System.out.println("BELL...");
                            }
                        }
                        sleep(500);
                    }
                    
                } catch (InterruptedException ex) {
                    Logger.getLogger(AlarmBell.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();
    }
    
    public void activate(){
        sound = true;
    }
    
    public void deactivate(){
        sound = false;
    }
}
