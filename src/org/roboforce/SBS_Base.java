/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.roboforce;

import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import com.sun.spot.io.j2me.radiogram.RadiogramConnection;
import java.io.IOException;
import javax.microedition.io.Connector;
import javax.microedition.io.Datagram;

/**
 *
 * @author shane
 */
public class SBS_Base extends MIDlet {
    private static final int HOST_PORT = 49;
    private static final String BOT_IEEE = "broadcast";
    private static final int DATAGRAM_LENGTH = 50;
    
    RadiogramConnection comCon = null;
    Datagram dg = null;

    protected void startApp() throws MIDletStateChangeException {
        try {
            comCon = (RadiogramConnection) Connector.open("radiogram://" + BOT_IEEE + ":" + HOST_PORT);
            dg = comCon.newDatagram(DATAGRAM_LENGTH);
        } catch (Exception e) {
            System.err.println("Caught " + e + " in connection initialization.");
        }
        for(;;){
            dg.setLength(DATAGRAM_LENGTH);
            try {
                comCon.receive(dg);
                System.out.println("index:" + dg.readLong());
                System.out.println("Accel Angle:" + dg.readDouble());
                System.out.println("Angle:" + dg.readDouble());
                System.out.println("GyroRate:" + dg.readDouble());
                System.out.println("Speed:" + dg.readInt());
                
                
                
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
    }
    
    

    protected void pauseApp() {
    }

    protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {
    }
    
}
