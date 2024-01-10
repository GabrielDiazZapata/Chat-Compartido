package client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientApp {  public static void main(String[] args) {
    try {
        Socket socket = new Socket("localhost", 55000);
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        String usermensaje = "";
        Scanner sc = new Scanner(System.in);

        System.out.println("¿Cómo te llamas?");
        String username = sc.nextLine();
        dataOutputStream.writeUTF(username);
        boolean x = true;
        while (x){
            System.out.println("Envía un mensaje al servidor (bye para salir): ");
            usermensaje = sc.nextLine();
            if (usermensaje.equals("bye")) {
                x = false;
            }

            dataOutputStream.writeUTF(usermensaje);


        }

        sc.close();
        socket.close();
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}
}