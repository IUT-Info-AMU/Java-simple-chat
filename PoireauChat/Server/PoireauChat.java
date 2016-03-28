/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package PoireauChat.Server;

import java.io.IOException;
import PoireauChat.Server.Application.ServerChat;

/**
 *
 * @author Gaëtan
 */
public class PoireauChat {
    
    public static void main(String[] args) {
        try {
            ServerChat server = new ServerChat();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    
}
