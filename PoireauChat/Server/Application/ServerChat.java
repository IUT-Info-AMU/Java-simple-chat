/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package PoireauChat.Server.Application;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
/**
 *
 * @author Gaëtan
 */
public class ServerChat {
    private final static int PORT = 5005;
    private final Collection<ThreadServer> clientList = Collections.synchronizedCollection(new ArrayList());
    public ServerChat() throws IOException{
        System.out.println("Serveur lancé sur le port : " + ServerChat.PORT);
        run();
    }
    
    private void run() throws IOException{
        Socket client;
        ServerSocket server = new ServerSocket(ServerChat.PORT);
        for(;;){
            client = server.accept();
            ThreadServer thread = new ThreadServer(client, clientList);
            clientList.add(thread);
            thread.start();
        }
    }
}
