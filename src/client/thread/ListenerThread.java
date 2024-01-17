package client.thread;

import java.io.DataInputStream;
import java.io.IOException;

public class ListenerThread extends Thread{
    private final DataInputStream dataInputStream;

    public ListenerThread(DataInputStream dataInputStream) {
        this.dataInputStream = dataInputStream;
    }

    @Override
    public void run() {
        try {
            String mensajeRecibido;
            while (!(mensajeRecibido = dataInputStream.readUTF()).equals("bye")) {
                System.out.println(mensajeRecibido);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        }
    }

