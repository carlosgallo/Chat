
package messenger.server.entities;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

/**
 *
 * @author smoranbl
 */
public class Connection extends Thread{
    private TCP_Service server;
    private int idClient;    
    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream ouputStream;
    
    public Connection(TCP_Service server, Socket socket){
        this.server = server;
        //this.idClient = idClient;
        this.socket = socket;
        this.createStreams(socket);
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
    
    @Override
    public void run() {
        while(true){
            String text = inString();
            if(text != null){                
                server.sendMenssage(text);
            }
        } 
    }
}
