/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.roboforce;

/**
 *
 * @author shane
 */




public class LogRecord {
    double accelAngleY=0.0;
    double calcAngleY=0.0;
    double gyroRateY=0.0;
    double motorSpeed=0.0;
    long recordNum=0;
    
    public void setAccelAngleY(double angle){
        accelAngleY=angle;
    } 
    
    public double getAccelAngleY() {
        return accelAngleY;
    }
    
    public void setCalcAngleY(double calcAngle){
        calcAngleY = calcAngle;
    }
    
    public double getCalcAngleY(){
        return accelAngleY;
    }
    
    public void SETyGyroRate(double yGyro){
        gyroRateY = yGyro;
    }
    
    public double getGyroRateY(){
        return gyroRateY;
    }
    
    public void setMotorSpeed(double outMotorSpeed){
        motorSpeed=outMotorSpeed;
    }
    
    public double getMotorSpeed(){
        return motorSpeed;
    }

    public long getRecordNum() {
        return recordNum;
    }

    public void setRecordNum(long recordNum) {
        this.recordNum = recordNum;
    }
    
    public void incrementRecordNum(){
        recordNum++;
    }
    
}
