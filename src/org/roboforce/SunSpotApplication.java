/*
 * SunSpotApplication.java
 *
 * Created on Feb 21, 2012 11:20:56 PM;
 */

package org.roboforce;


import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.IOutputPin;
import com.sun.spot.resources.transducers.ISwitch;
import com.sun.spot.resources.transducers.ITemperatureInput;
import com.sun.spot.sensorboard.EDemoBoard;
import com.sun.spot.sensorboard.peripheral.IAccelerometer3D;
import com.sun.spot.util.Utils;
import java.io.IOException;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;


public class SunSpotApplication extends MIDlet {

    protected void startApp() throws MIDletStateChangeException {
        
        PWMMotorController mc = new PWMMotorController(670,2330,0); 
        
        int speed = 0;
        
        IOutputPin power = EDemoBoard.getInstance().getOutputPins()[1];
        power.setHigh();
        
        ISwitch sw1 = (ISwitch) Resources.lookup(ISwitch.class, "SW1");
        ISwitch sw2 = (ISwitch) Resources.lookup(ISwitch.class, "SW2");
        
        while(true){
            if (sw1.isClosed()){   
                if(speed<100){
                    speed+=1;
                }
            }
            if (sw2.isClosed()){   
                if(speed>-100){
                    speed+=-1;
                }
            }
            mc.setSpeed(speed);
            System.out.println(speed);
            Utils.sleep(50);
        }

        
//Start of Gyro Test        
//        Gyro gyro = new Gyro(0, 0);
//        gyro.setPowerOn(true);
//        Utils.sleep(1000);
//        ITemperatureInput temp = EDemoBoard.getInstance().getADCTemperature();
//        IAccelerometer3D accel = EDemoBoard.getInstance().getAccelerometer();
//        
//        
//        double highest = 0;
//        double lowest = 0;
//        double rateOfChange;
//        double offset = 0.0;
//        for(int i = 0;i<100;i++){
//            offset+= gyro.getVoltage();
//            Utils.sleep(10);
//        }
//        offset = offset/100.0;
//        System.out.println("offset = " + offset);
//        gyro.setOffset(offset);
//        double angle = 0;
//        double wait = 500;
//        double[] buffer = {0.0,0.0,0.0,0.0,0.0};
//        double accelTilt =0.0;
//        
//        
//        
//        for(int i = 0;i<5000;i++){
//            double now = System.currentTimeMillis();
//            rateOfChange = gyro.getRateOfChange();
//            try {
//                accelTilt = accel.getAccelY()*(180/Math.PI);
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//            for(int j = 0; j < buffer.length; j++ ) {
//                if (j +1 < buffer.length) {
//                    buffer[j] = buffer[j + 1];
//                } else {
//                    buffer[j] = rateOfChange;
//                }
//            }
//            
//            System.out.println("buffer = " + buffer[0] + "," + buffer[4]);
//            
//            angle = ((((buffer[0] + buffer[1] + buffer[2] + buffer[3] + buffer[4])/5)/2.0)+angle)*.98 + accelTilt*-.02;
//            System.out.println("accelTilt = " + accelTilt);
//            System.out.println("angle = " + angle);
//            try {
//                System.out.println("temp = " + temp.getCelsius());
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
////            System.out.println("rateOfChange = " + rateOfChange);
////            if(rateOfChange > highest){
////                highest = rateOfChange;
////            }
////            if(rateOfChange < lowest){
////                lowest = rateOfChange;
////            }
//            Utils.sleep((long)(wait-(System.currentTimeMillis()-now)));
//        }
//        System.out.println("Lowest "+ lowest);
//        System.out.println("Highest "+ highest);
//End of Gyro Test        
    }

    protected void pauseApp() {
    }

    protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {
    }

}
