/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.roboforce;

import com.sun.spot.sensorboard.EDemoBoard;
import com.sun.spot.sensorboard.peripheral.Servo;

/**
 *
 * @author peter
 */
public class PWMMotorController {
    
    private int minPWM;
    private int maxPWM;
    private int speed = 0;
    private int pulseWidth;
    private int increment;
    private int offset;
    
    private Servo servo;
    
    public PWMMotorController(int max, int min, int pin){
        servo = new Servo(EDemoBoard.getInstance().getOutputPins()[pin]);
        minPWM = min;
        maxPWM = max;
        
        increment = (maxPWM - minPWM) / 200;
        offset = ((maxPWM - minPWM) / 2) + minPWM;         
    }
    
    public  int getSpeed(){
        return speed;
    }
        
    public void setSpeed(int speed){
        if(speed > 100||speed < -100){
            System.out.println("PWMMotorController Error: Input out of bounds");
            if(speed > 100){
                speed = 100;
            }else{
                speed = -100;
            }
        }
        this.speed = speed;
        pulseWidth = this.speed * increment + offset;
        servo.setValue(pulseWidth);
        System.out.println("Pulse Width = " + pulseWidth);
    }
    
    
    
}
