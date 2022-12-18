package ru.chat;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientApplication {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8081;

    private static BufferedReader input;
    private static BufferedWriter output;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
            System.out.println("Соеденение с сервером установлено");

            try {
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                while (true) {
                    System.out.println("Ведите Сообщение.");
                    Scanner scanner = new Scanner(System.in);
                    String message = scanner.nextLine();

                    output.write(message + "\n");
                    output.flush();

                    String responseFromServer = input.readLine();
                    System.out.printf(responseFromServer);
                    System.out.println();
                    if (responseFromServer.equals("Спасибо, Досвидание")){
                        break;
                    } else if (responseFromServer.equals("Сервер выключается")){
                        break;
                    }
                }
            } finally {
                input.close();
                output.close();
            }
        } catch (IOException e){
            System.out.printf("Connection problem with host %s had %s", SERVER_HOST, SERVER_PORT);
        }
    }
}
