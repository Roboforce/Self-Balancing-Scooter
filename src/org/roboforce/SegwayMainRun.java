/*
 * SegwayMainRun.java
 *
 * Created on Feb 21, 2012 11:20:56 PM;
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


public class SegwayMainRun extends MIDlet {
    
    public static final double RADIANS_TO_DEGREES = 57.2957795;
    public static final double ACCEL_WEIGHT_FACTOR = 0.02; //Make sure this and GYRO WEIGHT sum up to 1
    public static final double GYRO_WEIGHT_FACTOR = 1.0 - ACCEL_WEIGHT_FACTOR;
    public static final int PWM_MIN = 670;
    public static final int PWM_MAX = 2330;
    public static final double SAMPLE_DURATION_SEC = 0.04;
    public static final double CUTOFF_ANGLE = 25.0;
    
    private ITriColorLEDArray leds       = (ITriColorLEDArray) Resources.lookup(ITriColorLEDArray.class);

    protected void startApp() throws MIDletStateChangeException {
        

//start motorcontroller test        
//        PWMMotorController mc = new PWMMotorController(670,2330,0); 
//        
//        int speed = 0;
//        
//        IOutputPin power = EDemoBoard.getInstance().getOutputPins()[1];
//        power.setHigh();
//        
      ISwitch sw1 = (ISwitch) Resources.lookup(ISwitch.class, "SW1");
//        ISwitch sw2 = (ISwitch) Resources.lookup(ISwitch.class, "SW2");
//        
//        while(true){
//            if (sw1.isClosed()){   
//                if(speed<100){
//                    speed+=1;670
//                }
//            }
//            if (sw2.isClosed()){   
//                if(speed>-100){
//                    speed+=-1;
//                }
//            }
//            mc.setSpeed(speed);
//            System.out.println(speed);
//            Utils.sleep(50);
//        }

        
//Start of Gyro Test        
        Gyro gyro = new Gyro(0, 0);
        gyro.setPowerOn(true);
        Utils.sleep(1000);
        ITemperatureInput tempInput = EDemoBoard.getInstance().getADCTemperature();
        IAccelerometer3D accel = EDemoBoard.getInstance().getAccelerometer();
        
        
        
        
        double offset = 1.23; //zero point at 25 degrees celsius
        double zeroTempDelta = 0.00005; //v/degrees celcius
        double nominalTemp = 25; //degrees celcius
        double sensitivity = 2.5; //mv/degrees/second
        double sensitivityTempDelta = 0.00925;//mv/degree celsius
        
        double tempDelta = 0.0;
        
        double rateOfChange;
        double temp = 0.0;
        
        int loopcount1 = 100;
        for(int i = 0;i<loopcount1;i++){ //average temp input
            try {
            temp += tempInput.getCelsius();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Utils.sleep(10);
        }
        temp = (temp/loopcount1);
        
        tempDelta = temp - nominalTemp;
        
        offset += zeroTempDelta * tempDelta;
        sensitivity += sensitivityTempDelta * tempDelta;
        gyro.setScalingFactor(sensitivity);
        
        

        gyro.setOffset(offset);
        double angle = 0;
        double wait = 40;
        double accelTilt =0.0;
        
        
        PWMMotorController port = new PWMMotorController(PWM_MIN,PWM_MAX,1); 
        PWMMotorController starboard = new PWMMotorController(PWM_MIN,PWM_MAX,2); 
        
        int speed = 0;
        
        
        while(true){
//            System.out.println("TempDelta = " + tempDelta);
//            System.out.println("Sensitivity = " + sensitivity);
//            try{ 
//                System.out.println("Ref = " + ref.getVoltage());
//                System.out.println("Temp = " + tempInput.getCelsius());
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//            System.out.println("Offset = " + offset);
            if (sw1.isClosed()){
                angle = 0; //
            }
            double now = System.currentTimeMillis();
            rateOfChange = gyro.getRateOfChange();
            try {
                accelTilt = accel.getTiltY()*RADIANS_TO_DEGREES;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
            //System.out.println("RateOfChange = " + rateOfChange);
            //System.out.println("AccelTilt = " + accelTilt);
            angle = ((angle + rateOfChange * SAMPLE_DURATION_SEC) * GYRO_WEIGHT_FACTOR) + (ACCEL_WEIGHT_FACTOR * accelTilt); 
//            angle += rateOfChange *  0.04;
            System.out.println("angle = " + angle +", RateOfChange = " + rateOfChange + ", AccelTilt = " + accelTilt);
            
            speed = (int) (angle*angle/2);
            
            if(speed > 100){
                speed = 100;
            } 
            
            if(speed < -100){
                speed = -100;
            }
            
            //Use this bit to cut the program if things get out of hand
            if(angle > CUTOFF_ANGLE){
               
                speed = 0; 
                port.setSpeed(0); //Make it stop doing things!
                break;
            }
            
            if(angle < CUTOFF_ANGLE * -1){
                //MIDl2
                //et.notifyDestroyed();
                speed = 0;
                port.setSpeed(0); // Stop making it do stuff even when negative!
                break;
            }
            
            port.setSpeed(speed);
            starboard.setSpeed(speed*-1);
            
            Utils.sleep((long)(wait-(System.currentTimeMillis()-now)));
        }
//End of Gyro Test      
        leds.getLED(0).setColor(LEDColor.BLUE);
    }

    protected void pauseApp() {
    }

    protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {
    }

}
