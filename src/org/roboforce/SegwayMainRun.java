/*
 * SegwayMainRun.java
 *
 * Created on Feb 21, 2012 11:20:56 PM;
 * 
 * 
 * Let's add:
 * Reporting for:
 * min and max gyro and accell readings
 * loop speed
 */

package org.roboforce;


import org.roboforce.segway.sensors.Gyro;
import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.ITriColorLED;
import com.sun.spot.resources.transducers.ITriColorLEDArray;
import com.sun.spot.resources.transducers.LEDColor;
import com.sun.spot.resources.transducers.IAnalogInput;
import com.sun.spot.resources.transducers.IOutputPin;
import com.sun.spot.resources.transducers.ISwitch;
import com.sun.spot.resources.transducers.ITemperatureInput;
import com.sun.spot.sensorboard.EDemoBoard;
import com.sun.spot.sensorboard.peripheral.IAccelerometer3D;
import com.sun.spot.util.Utils;
import java.io.IOException;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import org.roboforce.segway.loggers.LogRecord;


public class SegwayMainRun extends MIDlet {
    
    public static final double RADIANS_TO_DEGREES = 57.2957795;
    public static final double ACCEL_WEIGHT_FACTOR = 0.0; //This and GYRO WEIGHT will sum up to 1
    public static final double GYRO_WEIGHT_FACTOR = 1.0 - ACCEL_WEIGHT_FACTOR;
    public static final int PWM_MIN = 670;
    public static final int PWM_MAX = 2330;
    public static final double SAMPLE_DURATION_SEC = 0.04;
    public static final double CUTOFF_ANGLE = 25.0;
    
    private ITriColorLEDArray leds       = (ITriColorLEDArray) Resources.lookup(ITriColorLEDArray.class);

    protected void startApp() throws MIDletStateChangeException {
        leds.getLED(4).setOn();
        leds.getLED(4).setColor(LEDColor.YELLOW);
        leds.getLED(3).setOn();
        leds.getLED(3).setColor(LEDColor.YELLOW);
        leds.getLED(2).setOn();
        leds.getLED(2).setColor(LEDColor.YELLOW);
        
        
        ISwitch sw1 = (ISwitch) Resources.lookup(ISwitch.class, "SW1");
        ISwitch sw2 = (ISwitch) Resources.lookup(ISwitch.class, "SW2");
        
        Gyro gyro = new Gyro(0, 0);
        gyro.setPowerOn(true);
        Utils.sleep(700);
        leds.getLED(2).setColor(LEDColor.GREEN);
        ITemperatureInput tempInput = EDemoBoard.getInstance().getADCTemperature();
        IAccelerometer3D accel = EDemoBoard.getInstance().getAccelerometer();
        double offset = 1.23; //zero point at 25 degrees celsius
        double zeroTempDelta = 0.00005; 
        double nominalTemp = 25; 
        double sensitivity = 2.5; 
        double sensitivityTempDelta = 0.00925;
        double tempDelta = 0.0;
        double rateOfChange;
        double temp = 0.0;
        int loopcount1 = 100;
        for(int i = 0;i<loopcount1;i++){ 
            try {
            temp += tempInput.getCelsius();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
        }
        Utils.sleep(700);
        leds.getLED(3).setColor(LEDColor.GREEN);
        temp = (temp/loopcount1);
        tempDelta = temp - nominalTemp;
        offset += zeroTempDelta * tempDelta;
        sensitivity += sensitivityTempDelta * tempDelta;
        gyro.setScalingFactor(sensitivity);
        gyro.setOffset(offset);
        double angle = 0;
        int wait = 9;
        double accelTilt =0.0;
        PWMMotorController port = new PWMMotorController(PWM_MIN,PWM_MAX,1); 
        PWMMotorController starboard = new PWMMotorController(PWM_MIN,PWM_MAX,2); 
        int speed = 0;
        Utils.sleep(700);
        LogRecord logRecord = new LogRecord();
        
        
        
        long countedMillis=System.currentTimeMillis();
        int loops=0;
        leds.getLED(4).setColor(LEDColor.GREEN);
        int broadcastCount=0;
        
        while(true){
        
            if (sw1.isClosed()){
                angle = 0; 
        
        
        
        
            }
            long now = System.currentTimeMillis();
            rateOfChange = gyro.getRateOfChange();
            try {
                accelTilt = accel.getTiltY()*RADIANS_TO_DEGREES;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
            
            angle = ((angle + rateOfChange * SAMPLE_DURATION_SEC) * GYRO_WEIGHT_FACTOR) + (ACCEL_WEIGHT_FACTOR * accelTilt); 
            speed = (int) (angle*angle*angle*2);
            
            if(speed > 100){
                speed = 100;
            } 
            
            if(speed < -100){
                speed = -100;
            }
           
            
            port.setSpeed(speed);
            starboard.setSpeed(speed*-1);
            
            if (sw1.isClosed()&&sw2.isClosed()){
                break;
            }
            
            loops++;
            broadcastCount++;
            
            if (broadcastCount==10){
                
                broadcastCount=0;
            }
            
            logRecord.setAccelAngleY(accelTilt);
            logRecord.setCalculatedAngleY(angle);
            logRecord.setMotorSpeed(speed);
            logRecord.setGyroRateY(rateOfChange);
            
            Utils.sleep((long)(wait-(System.currentTimeMillis()-now)));
            
        }
        
        
        
        

        long endtime = System.currentTimeMillis();
        System.out.println(endtime - countedMillis);
        for(int i = 0; i < leds.size(); i++){
            leds.getLED(i).setOn();                    // Enable this LED
            leds.getLED(i).setColor(LEDColor.RED);              // Set it to black
        }
    }

    protected void pauseApp() {
    }

    protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {
    }

}
