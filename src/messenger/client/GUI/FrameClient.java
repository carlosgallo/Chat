
package messenger.client.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.TextArea;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import messenger.client.entities.Client;

/**
 *
 * @author smoranbl
 */
public class FrameClient extends JFrame{
    private Client client;
    
    private TextArea friendDisplay = new TextArea();
    private TextArea ownDisplay = new TextArea();    
    private DefaultListModel listModel = new DefaultListModel();
    private JList userList = new JList(listModel);
    private JScrollPane listPane = new JScrollPane(userList);
    
    //Constructor del frame del cliente, se le pasa el cliente.
    public FrameClient(Client client){
        this.client = client;
        this.configureContentPane();
        this.createAndShowGUI();
    }
    
    //Crea y muestra el frame.
    private void createAndShowGUI(){                
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                
        this.getContentPane().setBackground(Color.GRAY);        
        this.pack();                
        this.setSize(700, 600);
        this.setLocation(50, 50);        
        this.setVisible(true);        
    }
    
    //Configura y añade los elementos del panel del frame.
    private void configureContentPane(){
        this.getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        
        //Configura y añade el TextArea donde se muestran los mensajes recibidos.
        constraints.gridx = 0;
        constraints.gridy = 0;        
        constraints.gridwidth = 2;
        constraints.gridheight = 1;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        this.getContentPane().add(friendDisplay, constraints);
        
        //Configura y añade el TextArea donde se escriben los mensajes que se quieren enviar.
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;        
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        ownDisplay.addKeyListener(send);
        this.getContentPane().add(ownDisplay, constraints);                
        
        //Configura y añade la lista donde se mostraran los clientes conectados al servidor.
        //TODO: lograr que se añada el nombre de los clientes conectados al servidor.
        constraints.gridx = 2;
        constraints.gridy = 0;        
        constraints.gridwidth = 1;
        constraints.gridheight = 2;        
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        listPane.setPreferredSize(new Dimension(150, 450));
        this.getContentPane().add(listPane, constraints);       
    }   
    
    //Metodos GET de los TextArea FriendDisplay y OwnDisplay, del listModel, userList y listPane.
    public TextArea getFriendDisplay() {
        return friendDisplay;
    }
    public TextArea getOwnDisplay() {
        return ownDisplay;
    }
    public DefaultListModel getListModel() {
        return listModel;
    }
    public JList getUserList() {
        return userList;
    }
    public JScrollPane getListPane() {
        return listPane;
    }
    
    //Reinicia el TextArea ownDisplay y lo deja vacio.
    public void resetOwnDisplay(){
        ownDisplay.setText("");
    }
    
    //KeyListener para enviar los mensajes escritos en el ownDisplay.
    //TODO: Lograr que no meta un salto de carro cuando se envia el mensaje.
    KeyListener send = new KeyListener(){             
        @Override
        public void keyTyped(KeyEvent ke) {                 
        }
        @Override
        public void keyPressed(KeyEvent ke) {
            if(ke.getKeyCode() == 10){                 
                client.outString("<" + client.getNameClient()+ "> : " + ownDisplay.getText() + "\n");                               
                resetOwnDisplay();
            } 
        }
        @Override
        public void keyReleased(KeyEvent ke) {            
        }
    };
}
