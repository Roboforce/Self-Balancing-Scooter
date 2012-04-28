/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.roboforce.segway.sensors;

import com.sun.spot.resources.transducers.IAnalogInput;
import com.sun.spot.resources.transducers.IOutputPin;
import com.sun.spot.sensorboard.EDemoBoard;
import com.sun.spot.sensorboard.io.IScalarInput;
import java.io.IOException;

/**
 *
 * @author peter
 */
public class Gyro {
    private IAnalogInput gyro;
    private double voltage;
    private IOutputPin power;
//    private double rateOfChange;
    private double offset = 2.4;
    private double scalingFactor = 1.0/7;
    private boolean powerOn = false;
    
    
    public Gyro(int signalPin,int powerPin){
        gyro = EDemoBoard.getInstance().getAnalogInputs()[signalPin];
        power = EDemoBoard.getInstance().getOutputPins()[powerPin];
    }
    

    public double getRateOfChange(){
        try {
            voltage = gyro.getVoltage();
//            System.out.println("voltage" + voltage );
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return ((voltage - offset)*1000.0)/scalingFactor;
//        System.out.println("voltage = " + voltage);
//        double offsetVoltage = voltage-offset;
//        System.out.println("offsetVoltage = " + offsetVoltage);
//        double voltageInMillivolts = offsetVoltage*1000.0;
//        System.out.println("voltageInMillivolts =" + voltageInMillivolts);
//        double rateOfChange = voltageInMillivolts/scalingFactor;
//        System.out.println("rateOfChange = " + rateOfChange);
//        return rateOfChange;
    }
    
    public void setOffset(double offset){
        this.offset = offset;
    }
    
    public double getOffset(){
        return offset;
    }
    
    public void setScalingFactor(double scalingFactor){
        this.scalingFactor = scalingFactor;
    }
    
    public double getScalingFactor(){
        return scalingFactor;
    }
    
    public double getVoltage(){
        try {
            return gyro.getVoltage();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return 0;
    }
    
    public boolean getPowerOn() {
        return powerOn;
    }
    
    public void setPowerOn(boolean on) {
        if (on){
            power.setHigh();
        } else {
            power.setLow();
        }
    }
        
}
