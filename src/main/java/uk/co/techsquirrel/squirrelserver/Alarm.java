package uk.co.techsquirrel.squirrelserver;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.co.techsquirrel.squirrelserver.enums.AlarmState;
import uk.co.techsquirrel.squirrelserver.objects.Message;

@RestController
@RequestMapping("/alarm")
public class Alarm {

    @Autowired
    private Application application;
    
    @Autowired
    private AlarmBell alarmBell;

    private final int secondSetAlarmCountDown = 10;

    private AlarmState alarmState;
    private Calendar alarmActivationDateTime;

    public Alarm() {
        System.out.println("Starting...");
        alarmState = AlarmState.OFF;
        alarmActivationDateTime = Calendar.getInstance();
    }

    private void setAlarmState(AlarmState state) {
        System.out.println("Set Alarm State: " + state);
        alarmState = state;
        updateState();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/deactivate")
    public void deactivateAlarm() {
        setAlarmState(AlarmState.OFF);
        alarmActivationDateTime = Calendar.getInstance();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/activate")
    public void activateAlarm(@RequestParam("level") AlarmState newAlarmState) {

        if (alarmState == AlarmState.OFF) {
            setAlarmState(AlarmState.ARMING);
            alarmActivationDateTime = Calendar.getInstance();
            alarmActivationDateTime.add(Calendar.SECOND, secondSetAlarmCountDown);
            new Thread() {
                @Override
                public void run() {
                    Calendar currentTime = Calendar.getInstance();
                    while (currentTime.before(alarmActivationDateTime)) {
                        System.out.println("Arming: " + currentTime.getTimeInMillis() + " | " + alarmActivationDateTime.getTimeInMillis());
                        try {
                            sleep(1000);
                        } catch (InterruptedException ex) {
                            System.err.println("Error in countdown to set alarm");
                        }
                        currentTime = Calendar.getInstance();
                    }
                    if (alarmState == AlarmState.ARMING) {
                        setAlarmState(newAlarmState);
                    }
                }
            }.start();
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/sensor")
    public void sensorEvent(@RequestParam("name") String sensorName) {

        System.out.println("Sensor: " + sensorName);

        application.addMessageToQueue(new Message("Alarm:"+sensorName, 1));
        
        if (alarmState == AlarmState.FULL) {
            setAlarmState(AlarmState.ACTIVE);
            System.out.println("Armed to Active, Reaction to sensor: " + sensorName);
        }

        updateState();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/storePicture")
    public void storePicture(@RequestBody byte[] imageInByte) {
        try {
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageInByte));
            String fileName = Calendar.getInstance().getTime().getTime() + ".png";
            ImageIO.write(bufferedImage, "PNG", new File(fileName));
        } catch (IOException ex) {
            Logger.getLogger(Alarm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void updateState() {
        if (alarmState == AlarmState.ACTIVE) {
            alarmBell.activate();
        } else {
            alarmBell.deactivate();
        }
    }

}
