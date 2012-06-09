/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.roboforce.segway.loggers;

import com.sun.spot.io.j2me.radiogram.RadiogramConnection;
import javax.microedition.io.Connector;
import javax.microedition.io.Datagram;

/**
 *
 * @author peter
 */
public class BroadcastLogger implements Logger{
    private static final int HOST_PORT = 49;
    private static final String BOT_IEEE = "broadcast";
    private static final int DATAGRAM_LENGTH = 50;
    
    RadiogramConnection comCon = null;
    Datagram dg = null;
    
    BroadcastLogger (){
        try {
            comCon = (RadiogramConnection) Connector.open("radiogram://" + BOT_IEEE + ":" + HOST_PORT);
            dg = comCon.newDatagram(DATAGRAM_LENGTH);
        } catch (Exception e) {
            System.err.println("Caught " + e + " in connection initialization.");
        }

    }

    public void log(LogRecord lr) {
        try{
            dg.reset();
            dg.writeLong(lr.getIndex());
            dg.writeDouble(lr.getAccelAngleY());
            dg.writeDouble(lr.getCalculatedAngleY());
            dg.writeDouble(lr.getGyroRateY());
            dg.writeInt(lr.getMotorSpeed());
            comCon.send(dg);
        } catch (Exception e) {
            System.err.println("Caught " + e + " while collecting/sending accelerometer sample.");
        }
    }
    
}
