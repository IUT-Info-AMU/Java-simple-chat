/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package PoireauChat.Server.Application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;
/**
 *
 * @author Gaëtan
 */
public class ThreadServer extends Thread {
    final private OutputStream os;
    final private InputStream is;
    private String ligne;
    private Socket client;
    private Collection<ThreadServer> clientList;
    private String pseudo;
    final static private String PSEUDOBEGIN = "pseudo : ";
    public ThreadServer(Socket client,Collection<ThreadServer> clientList) throws IOException{
        this.client = client;
        this.clientList = clientList;
        this.os = this.client.getOutputStream();
        this.is = this.client.getInputStream();
    }
    public void run(){
        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(os));
            String date = new GregorianCalendar().getTime().toString();
            out.write(date);
            out.newLine();
            out.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(is));	
            while((this.ligne = in.readLine())!=null){
                if(this.ligne.startsWith(PSEUDOBEGIN)){
                    this.pseudo= this.ligne.substring(PSEUDOBEGIN.length());
                }else{
                    sendToAll(this.ligne);
                }
            }
            in.close();
            this.client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendToOne(String message) throws IOException{
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(this.os));
        out.write(this.pseudo + " : " + message);
        out.newLine();
        out.flush();
    }
    
   public void sendToAll(String message) throws IOException{
       Iterator it = this.clientList.iterator();
       while(it.hasNext()){
           ThreadServer thread = (ThreadServer) it.next();
           BufferedWriter out = new BufferedWriter(new OutputStreamWriter(thread.os));
           out.write(this.pseudo + " : " + message);
           out.newLine();
           out.flush();
       }
   }
               
}
