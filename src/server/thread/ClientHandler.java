package server.thread;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler extends Thread{
    private Socket clientSocket;

    public ClientHandler(Socket socket){
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            DataInputStream dataInputStream = new DataInputStream(this.clientSocket.getInputStream());
            final String username = dataInputStream.readUTF();
            System.out.println("Etoy aquiii "+ username);
            String mensajeRecibido ="";
            while (!mensajeRecibido.equals("bye")){
                mensajeRecibido  =dataInputStream.readUTF();

                System.out.println(username + " envia " + mensajeRecibido);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
