
package messenger.client.entities;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import messenger.client.GUI.FrameClient;

/**
 *
 * @author smoranbl
 */
public class Client extends Thread {
    private int id;
    private String name;
    private Socket socket;  
    private DataInputStream inputStream;
    private DataOutputStream ouputStream;
    
    private FrameClient frame;
    
    public Client(int id, String name, String host){
        this.id = id;
        this.name = name;
        this.createSocket(host);
        this.createStreams(this.socket);
        
        frame = new FrameClient(this);
    }
    
    private void createSocket(String host){
        try {
            socket = new Socket(host, 5000);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }
    
    private void createStreams(Socket socket){        
        try {
            inputStream = new DataInputStream(socket.getInputStream());
            ouputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(ex.getMessage());
        }
    }        
    
    public String inString(){
        String cadenaRecibe = null;
        
        try {            
            cadenaRecibe = inputStream.readUTF();            
        } catch (IOException ex) {
            Logger.getLogger(ex.getMessage());
        }
        
        return cadenaRecibe;
    }
    
    public void outString(String text){
        try {            
            ouputStream.writeUTF(text);
        } catch (IOException ex) {
            Logger.getLogger(ex.getMessage());
        }
    }

    public int getIdClient() {
        return id;
    }
    public String getNameClient() {
        return name;
    }        
    
    @Override
    public void run() {
        while(true){
            String text = inString();
            if(text != null){                
                frame.getFriendDisplay().append(text);
            }
        } 
    }    
}
