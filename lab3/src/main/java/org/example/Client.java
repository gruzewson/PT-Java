package org.example;
import java.net.*;
import java.io.*;



public class Client {


    public static void main(String[] args) throws IOException {
        try {
        Socket clientSocket = new Socket("127.0.0.1", 1111);
        ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

            if ("ready".equals((String) in.readObject())) {
            System.out.println("Server ready");
        }

            int n = Integer.parseInt(input.readLine());
            //System.out.println(n);
           out.writeObject(n);
            if ("ready for messages".equals((String) in.readObject())) {
                System.out.println("Server ready for messages");
            }
            for(int i = 0; i < n; i++)
            {
                String receivedMessage = input.readLine();
                Message message = new Message();
                message.setContent(receivedMessage);
                message.setNumber(i);
                System.out.println(message.getNumber() + ". message: " + message.getContent());
                out.writeObject(message);
            }

            if ("finished".equals((String) in.readObject())) {
                System.out.println("finished");
            }
            in.close();
            out.close();
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
