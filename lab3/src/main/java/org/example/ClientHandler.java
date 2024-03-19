package org.example;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private int client_counter;

    public ClientHandler(Socket clientSocket, int client_counter)
    {
        this.clientSocket = clientSocket;
        this.client_counter = client_counter;
    }
    
    @Override
    public void run() {
        try (
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
                ){

        System.out.println("Client nr: " + client_counter + " ready");
        out.writeObject("ready");

        int n = (int) in.readObject();
        //System.out.println("Client nr: " + client_counter + " ready for messages");
        out.writeObject("ready for messages");
        for(int i = 0; i < n; i++)
        {
            Message receivedMessage = (Message) in.readObject();
            System.out.println("Client nr: " + client_counter + " sent Message number: "+ receivedMessage.getNumber() + " which contains: " + receivedMessage.getContent());
        }
        System.out.println("Client nr: " + client_counter + " finished");
        out.writeObject("finished");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

