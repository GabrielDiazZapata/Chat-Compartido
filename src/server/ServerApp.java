package server;

import server.thread.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp {
    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(55000);
            while (true){
                System.out.println("Esperando conexion...");
                Socket clientsocket = serverSocket.accept();
                System.out.println("Conexion establecida");
                ClientHandler clientHandler = new ClientHandler(clientsocket);
                clientHandler.start();

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

