package org.example;
import java.net.*;
import java.io.*;

public class Server {
    public static void main(String[] args) throws IOException{
        ServerSocket serverSocket = new ServerSocket(1111);

        try {
            System.out.println("Server started");
            int client_counter = 0;
            while(true)
            {
                Socket clientSocket = serverSocket.accept();
                client_counter++;
                new Thread(new ClientHandler(clientSocket, client_counter)).start();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*public void start() throws IOException, ClassNotFoundException {

    }*/

}
