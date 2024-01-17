package server.thread;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ClientHandler extends Thread{
    private Socket clientSocket;
    private List<String> sharedMessages;

    public ClientHandler(Socket socket, List<String> sharedMessages){
        this.clientSocket = socket;
        this.sharedMessages = sharedMessages;
    }

    DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss" );
    Date date = new Date();

    @Override
    public void run() {
        try {
            DataInputStream dataInputStream = new DataInputStream(this.clientSocket.getInputStream());
            final String username = dataInputStream.readUTF();
            System.out.println("<" + username + ">");

            String mensajeRecibido = "";
            while (!mensajeRecibido.equals("bye")) {
                mensajeRecibido = dataInputStream.readUTF();

                String mensajeTotal = dateFormat.format(date) + " " + username + ":" + '<' + mensajeRecibido + '>';
                System.out.println(mensajeTotal);


                synchronized (sharedMessages) {
                    sharedMessages.add(mensajeTotal);
                }


                for (String message : sharedMessages) {
                    DataOutputStream clientOutputStream = new DataOutputStream(clientSocket.getOutputStream());
                    clientOutputStream.writeUTF(message);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}