/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package poireauchatclient;

import poireauchatclient.Views.ChatMainWindows;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Gaëtan
 */
public class PoireauChat {
        final static private String HOSTNAME = "127.0.0.1";
        final static private int PORT = 5005;
        final static private String PSEUDOBEGIN = "pseudo : ";
        private Socket sockClient = new Socket(PoireauChat.HOSTNAME,PoireauChat.PORT); ;
        BufferedWriter bw;
        BufferedReader br;
    public PoireauChat() throws IOException {
        bw = new BufferedWriter(new OutputStreamWriter(sockClient.getOutputStream()));
        br = new BufferedReader(new InputStreamReader(sockClient.getInputStream()));  
        final ChatMainWindows chat = new ChatMainWindows("Poireau Chat");
        String pseudo = "pseudo : " + JOptionPane.showInputDialog("Pseudo", "Anonymous");
        sendMessage(pseudo);
        chat.capturerEntree(new KeyAdapter(){
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    try {
                        sendMessage(chat.getMessage());
                        chat.effacerChampEnvoi();
                    } catch (IOException ex) {
                        Logger.getLogger(PoireauChat.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        for(String line = br.readLine(); line != null; line = br.readLine()){
            chat.afficherMessage(line);
        }
    }
    
    public void sendMessage(String message) throws IOException{
        bw.write(message);
        bw.newLine();
        bw.flush();
    }
    
    public static void main(String[] args) {
        try {
            PoireauChat poireauChat = new PoireauChat();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }   
}
