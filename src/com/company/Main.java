package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main {
    static ExecutorService executeIt = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws InterruptedException {

        boolean flag = true;

        while (flag) {
            try (ServerSocket server = new ServerSocket(5040);
                 //BufferedReader buff = new BufferedReader(new InputStreamReader(System.in))
                 BufferedReader buff = new BufferedReader(new StringReader("q1"))

            ) {

                while (!server.isClosed()) {
                    if (buff.ready()) {
                        String serverCommand = buff.readLine();
                        if (serverCommand.equalsIgnoreCase("q")) {
                            System.out.println("exit");
                            server.close();
                            flag = false;
                            break;
                        }
                    }

                    Socket client = server.accept();
                    executeIt.execute(new ServerWork(client));
                }

                executeIt.shutdown();
            } catch (IOException e) {
                e.printStackTrace();
                flag = false;
            }
        }

    }

}

