
package messenger.server.entities;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author smoranbl
 */
public class TCP_Service extends Thread{
    private ServerSocket socketServidor;
    private Socket clientSocket;
    private ArrayList<Connection> storeConnections = new <Connection>ArrayList();
    
    public TCP_Service(){
        this.createSocketServidor();        
    }
    
    private void createSocketServidor(){
        try {
            socketServidor = new ServerSocket(5000);
        } catch (IOException ex) {
            Logger.getLogger(TCP_Service.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void newClient() throws IOException{
        System.out.println("Esperando conexion");
        clientSocket = socketServidor.accept();
        Connection newClient = new Connection(this, clientSocket);
        storeConnections.add(newClient);
        storeConnections.get(storeConnections.size() - 1).start();
        System.out.println("Conectado");        
    }
    
    public void sendMenssage(String menssage){
        for(int i=0; i<storeConnections.size(); i++)
            storeConnections.get(i).outString(menssage);
    }
    
    @Override
    public void run() {
        while(true){
            try {
                newClient();
            } catch (IOException ex) {
                Logger.getLogger(TCP_Service.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }    
}
