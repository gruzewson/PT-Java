package org.example;
import java.net.*;
import java.io.*;



public class Client {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        in = new ObjectInputStream(clientSocket.getInputStream());
    }


    public String sendMessage(String msg) throws IOException, ClassNotFoundException {
        out.writeObject(msg);
        //out.flush();
        String resp = (String) in.readObject();
        return resp;
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.startConnection("127.0.0.1", 1111);
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        try {
            if ("ready".equals((String) client.in.readObject())) {
            System.out.println("Server ready");
        }

            int n = Integer.parseInt(input.readLine());
            //System.out.println(n);
            client.out.writeObject(n);
            if ("ready for messages".equals((String) client.in.readObject())) {
                System.out.println("Server ready for messages");
            }
            for(int i = 0; i < n; i++)
            {
                String receivedMessage = input.readLine();
                Message message = new Message();
                message.setContent(receivedMessage);
                message.setNumber(i);
                System.out.println(message.getNumber() + ". message: " + message.getContent());
                client.out.writeObject(message);
            }

            if ("finished".equals((String) client.in.readObject())) {
                System.out.println("finished");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
