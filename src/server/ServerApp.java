package server;

import server.thread.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerApp {
    private static List<String> sharedMessages = new ArrayList<>();

    public static void main(String[] args) {
        try {
            // Se crea un ServerSocket en el puerto 55000
            ServerSocket serverSocket = new ServerSocket(55000);

            while (true) {
                System.out.println("Esperando conexión...");
                
                // Se espera a que un cliente se conecte y se acepta la conexión
                Socket clientSocket = serverSocket.accept();
                System.out.println("Conexión establecida");

                // Se crea una instancia de ClientHandler para manejar la comunicación con el cliente
                ClientHandler clientHandler = new ClientHandler(clientSocket, sharedMessages);
                
                // Se inicia un nuevo hilo para manejar la conexión con el cliente
                clientHandler.start();
            }
        } catch (IOException e) {
            // En caso de excepción de entrada/salida, se lanza una RuntimeException
            throw new RuntimeException(e);
        }
    }
}
