
package messenger;

import messenger.client.entities.Client;
import messenger.server.entities.TCP_Service;

/**
 *
 * @author smoranbl
 */
public class Messenger {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TCP_Service server = new TCP_Service();        
        server.start();        

        Client client2 = new Client("Milo", "localhost");
        client2.start();
        
        Client client3 = new Client("Darwin", "localhost");
        client3.start();
    }
}
