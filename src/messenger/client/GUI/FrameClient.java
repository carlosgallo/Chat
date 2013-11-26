
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
    
    
    public FrameClient(Client client){
        this.client = client;
        this.configureContentPane();
        this.createAndShowGUI();
    }
    
    private void createAndShowGUI(){                
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                
        this.getContentPane().setBackground(Color.GRAY);        
        this.pack();                
        this.setSize(700, 600);
        this.setLocation(50, 50);        
        this.setVisible(true);        
    }
    
    private void configureContentPane(){
        this.getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        
        constraints.gridx = 0;
        constraints.gridy = 0;        
        constraints.gridwidth = 2;
        constraints.gridheight = 1;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        this.getContentPane().add(friendDisplay, constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;        
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        ownDisplay.addKeyListener(send);
        this.getContentPane().add(ownDisplay, constraints);                
        
        constraints.gridx = 2;
        constraints.gridy = 0;        
        constraints.gridwidth = 1;
        constraints.gridheight = 2;        
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        listPane.setPreferredSize(new Dimension(150, 450));
        this.getContentPane().add(listPane, constraints);       
    }   

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
    
    public void resetOwnDisplay(){
        ownDisplay.setText("");
    }
    
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
