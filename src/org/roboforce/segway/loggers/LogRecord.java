/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.roboforce.segway.loggers;

/**
 *
 * @author peter
 */
public class LogRecord{
    private long index;
    private double gyroRateY;
    private double accelAngleY;
    private double calculatedAngleY;
    private int motorSpeed;

    public void setMotorSpeed(int motorSpeed) {
        this.motorSpeed = motorSpeed;
    }

    public int getMotorSpeed() {
        return motorSpeed;
    }

    public double getAccelAngleY() {
        return accelAngleY;
    }

    public double getCalculatedAngleY() {
        return calculatedAngleY;
    }

    public double getGyroRateY() {
        return gyroRateY;
    }

    public long getIndex() {
        return index;
    }

    public void setAccelAngleY(double accelAngleY) {
        this.accelAngleY = accelAngleY;
    }

    public void setCalculatedAngleY(double calculatedAngleY) {
        this.calculatedAngleY = calculatedAngleY;
    }

    public void setGyroRateY(double gyroRateY) {
        this.gyroRateY = gyroRateY;
    }

    public void setIndex(long index) {
        this.index = index;
    }
}
