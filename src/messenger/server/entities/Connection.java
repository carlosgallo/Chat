
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
    private String clientName;
    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream ouputStream;
    
    //Constructor de la conexion, se le pasa el servidor que la inicializa y el socket del cliente.
    //Inicializa los flujos de entrada y salida de la conexion.
    public Connection(TCP_Service server, Socket socket){
        this.server = server;
        //this.idClient = idClient;        
        this.socket = socket;
        this.createStreams(socket);
    }
    
    //Ccrea los flujos de entrada y salida de la conexion.
    private void createStreams(Socket socket){        
        try {
            inputStream = new DataInputStream(socket.getInputStream());
            ouputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(ex.getMessage());
        }
    }  
    
    //Metodo para recibir un flujo de entrada, devuelve el string recibido.
    public String inString(){
        String cadenaRecibe = null;
        
        try {            
            cadenaRecibe = inputStream.readUTF();            
        } catch (IOException ex) {
            Logger.getLogger(ex.getMessage());
        }
        
        return cadenaRecibe;
    }
    
    //Metodo para la salida de un flujo, se le pasa como parametro el texto que se quiere enviar.
    public void outString(String text){
        try {            
            ouputStream.writeUTF(text);
        } catch (IOException ex) {
            Logger.getLogger(ex.getMessage());
        }
    }        
    
    public String getClientName(){
        return this.clientName;
    }
    
    public void setClientName(String clientName){
        this.clientName = clientName;
    }
    
    //Lanza un hilo en el que la conexion esta constatnemente a la espera de un flujo de entrada.
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
