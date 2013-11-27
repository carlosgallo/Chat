
package messenger.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

/**
 *
 * @author smoranbl
 */
public class Flow {
    private final DataInputStream inputStream;
    private final DataOutputStream ouputStream;
    
    public Flow (Socket socket) throws IOException{
        this.inputStream = new DataInputStream(socket.getInputStream());
        this.ouputStream = new DataOutputStream(socket.getOutputStream());                    
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
}
