package ru.chat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApplication {

    private static final int SERVER_PORT = 8081;
    private static Socket clientSocket;
    private static ServerSocket serverSocket;
    private static BufferedReader input;
    private static BufferedWriter writer;

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(SERVER_PORT);
            System.out.println("Сервер запушен.");
            clientSocket = serverSocket.accept();
            System.out.println("Клиент подключился по порту: " + clientSocket.getPort());

            try {
                input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                while(true) {
                    String massage = input.readLine();
                    System.out.printf("Client send massage %s \n", massage);

                    if (massage.equals("exit")){
                        writer.write(String.format("Спасибо, Досвидание" + "\n"));
                        writer.flush();
                    } else if (massage.equals("shutdown")){
                        writer.write(String.format("Сервер выключается" + "\n"));
                        writer.flush();
                        break;
                    } else {
                        writer.write(String.format("Спасибо за сообщение %s", massage + "\n"));
                        writer.flush();
                    }
                }
            } finally {
                clientSocket.close();
                input.close();
                writer.close();
            }

        } catch (IOException e) {
            System.out.printf("IOException with create server application in port %s", SERVER_PORT);
        }
    }
}