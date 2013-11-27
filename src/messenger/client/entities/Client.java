
package messenger.client.entities;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import messenger.client.GUI.FrameClient;
import messenger.utils.Flow;

/**
 *
 * @author smoranbl
 */
public class Client extends Thread {
    private int id;
    private String name;
    private Socket socket;  
    private Flow flow;
    
    private final FrameClient frame;
    
    //Constructor del Client, se le pasa la id del cliente, el nombre y el host al que se conecta.
    public Client(String name, String host){        
        this.name = name;
        this.createSocket(host);
        this.createStreams(this.socket);
        this.sendName();
        
        frame = new FrameClient(this);
    }
    
    //Crea el socket del cliente, se le pasa el nombre del host al que se conecta y entra siempre al puerto 5000.
    private void createSocket(String host){
        try {
            socket = new Socket(host, 5000);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }
    
    //Crea los flujos de entrada y salida para el cliente.
    private void createStreams(Socket socket){        
        try {
            flow = new Flow(socket);
        } catch (IOException ex) {
            Logger.getLogger(ex.getMessage());
        }
    }        
    
    //Metodo para enviar el nombre del cliente al servidor.
    private void sendName(){
        this.flow.outString(this.name);
    }
    
    //Metodos GET de la id del cliente y el nombre del mismo.
    public int getClientId() {
        return id;
    }
    public String getClientName() {
        return name;
    }        
    public Flow getFlow(){
        return flow;
    }
    
    //Metodos SET de la id y el nombre del cliente.
    public void setIdClient(int id){
        this.id = id;
    }
    public void setNameClient(String name){
        this.name = name;
    }
    
    //Lanza un hilo en el que el cliente esta constatnemente a la espera de un flujo de entrada.
    @Override
    public void run() {
        while(true){
            String text = flow.inString();
            if(text != null){                
                frame.getFriendDisplay().append(text);
            }
        } 
    }    
}
