package server.thread;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
public class ClientHandler extends Thread{
    private Socket clientSocket;

    public ClientHandler(Socket socket){
        this.clientSocket = socket;
    }

     DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss" );
     Date date = new Date();

    @Override
    public void run() {
        try {
            DataInputStream dataInputStream = new DataInputStream(this.clientSocket.getInputStream());
            final String username = dataInputStream.readUTF();
            System.out.println("<"+username+">");
            String mensajeRecibido ="";
            while (!mensajeRecibido.equals("bye")){
                mensajeRecibido  =dataInputStream.readUTF();
                
                System.out.println( dateFormat.format(date ) +" "+   username+":" + '<' + mensajeRecibido + '>');
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
