package client;

import client.thread.ListenerThread;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientApp {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 55000);
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

            String usermensaje = "";
            Scanner sc = new Scanner(System.in);

            System.out.println("¿Cómo te llamas?");
            String username = sc.nextLine();
            dataOutputStream.writeUTF(username);


            ListenerThread hilo = new ListenerThread(dataInputStream);
            hilo.start();


            boolean x = true;
            while (x) {
                System.out.println("Envía un mensaje al servidor (bye para salir): ");
                usermensaje = sc.nextLine();
                dataOutputStream.writeUTF(usermensaje);
                dataOutputStream.flush();

                if (usermensaje.equals("bye")) {
                    x = false;
                }
            }


            hilo.join();

            sc.close();
            socket.close();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
