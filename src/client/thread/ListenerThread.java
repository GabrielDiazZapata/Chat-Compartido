package client.thread;

import java.io.DataInputStream;
import java.io.IOException;

public class ListenerThread extends Thread {

    private final DataInputStream dataInputStream;

    // Constructor que recibe un objeto DataInputStream
    public ListenerThread(DataInputStream dataInputStream) {
        this.dataInputStream = dataInputStream;
    }

    // Método run() que se ejecutará cuando el hilo sea iniciado
    @Override
    public void run() {
        try {
            String mensajeRecibido;
            
            // El bucle se ejecuta mientras el mensaje recibido no sea "bye"
            while (!(mensajeRecibido = dataInputStream.readUTF()).equals("bye")) {
                System.out.println(mensajeRecibido);  // Muestra el mensaje recibido en la consola
            }
        } catch (IOException e) {
            e.printStackTrace();  // Maneja posibles excepciones de entrada/salida mostrando el rastreo de la pila
        }
    }
}
