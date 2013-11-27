
package messenger.server.entities;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;
import messenger.utils.Flow;

/**
 *
 * @author smoranbl
 */
public class Connection extends Thread{
    private final TCP_Service server;
    private int idClient;    
    private String clientName;
    private final Socket socket;
    private Flow flow;
    
    //Constructor de la conexion, se le pasa el servidor que la inicializa y el socket del cliente.
    //Inicializa los flujos de entrada y salida de la conexion.
    public Connection(TCP_Service server, Socket socket){
        this.server = server;
        //this.idClient = idClient;        
        this.socket = socket;
        createStreams(socket);
    }
    
    //Ccrea los flujos de entrada y salida de la conexion.
    private void createStreams(Socket socket){        
        try {
            flow = new Flow(socket);
        } catch (IOException ex) {
            Logger.getLogger(ex.getMessage());
        }
    }  
       
    public String getClientName(){
        return clientName;
    }
    public Flow getFlow(){
        return flow;
    }
    
    public void setClientName(String clientName){
        this.clientName = clientName;
    }
    
    //Lanza un hilo en el que la conexion esta constatnemente a la espera de un flujo de entrada.
    @Override
    public void run() {
        while(true){
            String text = flow.inString();
            if(text != null){                
                server.sendMenssage("<" + this.getClientName()+ "> : " + text);
            }
        } 
    }
}
