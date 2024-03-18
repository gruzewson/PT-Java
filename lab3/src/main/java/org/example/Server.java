package org.example;
import java.net.*;
import java.io.*;

public class Server {

    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public static void main(String[] args) throws IOException{
        Server server = new Server();
        ServerSocket serverSocket = new ServerSocket(1111); // Define serverSocket as a local variable

        try {
            System.out.println("Server started");

            while(true)
            {
                Socket clientSocket = serverSocket.accept(); // Define clientSocket as a local variable
                server.clientSocket = clientSocket; // Assign clientSocket to the instance variable
                server.start(); // Start the server
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void start() throws IOException, ClassNotFoundException {
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        in = new ObjectInputStream(clientSocket.getInputStream());
        System.out.println("ready");
        out.writeObject("ready");

        int n = (int) in.readObject();
        System.out.println("ready for messages");
        out.writeObject("ready for messages");
        for(int i = 0; i < n; i++)
        {
            Message receivedMessage = (Message) in.readObject();
            System.out.println("Received Message number: "+ receivedMessage.getNumber() + " which contains: " + receivedMessage.getContent());
        }
        System.out.println("finished");
        out.writeObject("finished");
    }

}
