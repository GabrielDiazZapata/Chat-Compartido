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
            // Se establece la conexión con el servidor en el puerto 55000 en localhost
            Socket socket = new Socket("localhost", 55000);

            // Se crean los flujos de entrada y salida para la comunicación con el servidor
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

            // Se solicita el nombre del usuario al cliente
            String usermensaje = "";
            Scanner sc = new Scanner(System.in);

            System.out.println("¿Cómo te llamas?");
            String username = sc.nextLine();

            // Se envía el nombre del usuario al servidor
            dataOutputStream.writeUTF(username);
            dataOutputStream.flush();

            // Se crea e inicia un hilo para escuchar mensajes del servidor
            ListenerThread listenerThread = new ListenerThread(dataInputStream);
            listenerThread.start();

            boolean exit = false;
            while (!exit) {
                // Se solicita al usuario que envíe un mensaje al servidor
                System.out.println("Envía un mensaje al servidor con msg (bye para salir): ");
                usermensaje = sc.nextLine();

                // Se envía el mensaje al servidor
                dataOutputStream.writeUTF(usermensaje);
                dataOutputStream.flush();

                // Si el usuario escribe "bye", se marca para salir del bucle
                if (usermensaje.equals("bye")) {
                    exit = true;
                }
            }

            // Se espera a que el hilo de escucha termine antes de cerrar la conexión
            listenerThread.join();

            // Se cierran los recursos
            sc.close();
            socket.close();
        } catch (IOException | InterruptedException e) {
            // Se lanza una RuntimeException en caso de excepción para simplificar el manejo
            throw new RuntimeException(e);
        }
    }
}
