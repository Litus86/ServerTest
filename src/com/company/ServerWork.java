package com.company;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerWork implements Runnable {
    private static Socket clientDialog;

    public ServerWork(Socket client) {
        ServerWork.clientDialog = client;
    }

    @Override
    public void run() {
        processingWork();
    }

    public synchronized void processingWork() {
        try {
            Object lock = new Object();

            DataOutputStream out = new DataOutputStream(clientDialog.getOutputStream());
            DataInputStream in = new DataInputStream(clientDialog.getInputStream());

            while (!clientDialog.isClosed()) {
                String message = in.readUTF();

                if (message.equalsIgnoreCase("q")) {

                    synchronized (lock) {
                        System.out.println("Client initialize connections suicide");
                        out.writeUTF("Server reply - " + message + " - OK");
                        Thread.sleep(3000);
                    }
                    break;
                }

                synchronized (lock) {
                    out.writeUTF("Server reply - " + message + " - OK");
                    out.flush();
                    System.out.println("Closing connections & channels.");

                    in.close();
                    out.close();
                    clientDialog.close();
                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
