package server.thread;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    private List<String> sharedMessages;

    // Constructor que recibe el socket del cliente y una lista compartida para almacenar mensajes
    public ClientHandler(Socket socket, List<String> sharedMessages) {
        this.clientSocket = socket;
        this.sharedMessages = sharedMessages;
    }

    DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    Date date = new Date();

    @Override
    public void run() {
        try {
            // Se obtiene un flujo de entrada desde el cliente
            DataInputStream dataInputStream = new DataInputStream(this.clientSocket.getInputStream());
            
            // Se lee el nombre de usuario enviado por el cliente
            final String username = dataInputStream.readUTF();
            System.out.println("<" + username + ">");  // Se imprime el nombre del usuario en el servidor

            String mensajeRecibido = "";

            // Bucle principal que escucha los mensajes del cliente
            while (!mensajeRecibido.equals("bye")) {
                mensajeRecibido = dataInputStream.readUTF();

                if (mensajeRecibido.startsWith("msg:")) {
                    // Si el mensaje comienza con "msg:", se procesa y se agrega a la lista compartida
                    String mensajeTotal = dateFormat.format(date) + " " + username + ":" + '<' + mensajeRecibido + '>';
                    System.out.println(mensajeTotal);

                    // Se sincroniza el acceso a la lista compartida para evitar problemas de concurrencia
                    synchronized (sharedMessages) {
                        sharedMessages.add(mensajeTotal);
                    }

                    // Se envía el mensaje a todos los clientes conectados
                    for (String message : sharedMessages) {
                        DataOutputStream clientOutputStream = new DataOutputStream(clientSocket.getOutputStream());
                        clientOutputStream.writeUTF(message);
                        clientOutputStream.flush();
                    }

                } else if (mensajeRecibido.equals("bye")) {
                    // Si el cliente envía "bye", se cierra la conexión con ese cliente
                    clientSocket.close();
                } else {
                    // Si el mensaje no sigue el formato esperado, se imprime un mensaje de error
                    System.out.println("Error: Ingrese los prefijos indicados");
                }
            }
        } catch (IOException e) {
            // En caso de excepción de entrada/salida, se lanza una RuntimeException
            throw new RuntimeException(e);
        }
    }
}
