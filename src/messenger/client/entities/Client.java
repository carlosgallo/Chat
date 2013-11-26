
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
    
    //Constructor del Client, se le pasa la id del cliente, el nombre y el host al que se conecta.
    public Client(int id, String name, String host){
        this.id = id;
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
    
    //Metodo para enviar el nombre del cliente.
    private void sendName(){
        this.outString(this.name);
    }
    
    //Metodos GET de la id del cliente y el nombre del mismo.
    public int getIdClient() {
        return id;
    }
    public String getNameClient() {
        return name;
    }        
    
    //Lanza un hilo en el que el cliente esta constatnemente a la espera de un flujo de entrada.
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
