package org.example.m09juego;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static final String HOST = "localhost";
    private static final int PORT = 8001;

    public static void main(String[] args) throws IOException {
        Socket clientSocket = new Socket(HOST, PORT);
        System.out.println("Cliente conectado al servidor: " + clientSocket.getInetAddress());

        InputStream inputStream = clientSocket.getInputStream();
        OutputStream outputStream = clientSocket.getOutputStream();
        Scanner scanner = new Scanner(System.in);

        // Recibir mensaje de bienvenida del servidor
        byte[] welcomeBytes = new byte[1024];
        int bytesRead = inputStream.read(welcomeBytes);
        String welcomeMessage = new String(welcomeBytes, 0, bytesRead).trim();
        System.out.println(welcomeMessage);

        while (true) {
            // Introducir la suposición del jugador
            System.out.print("Introduce tu suposición: ");
            String guessString = scanner.nextLine();

            // Enviar la suposición al servidor
            outputStream.write(guessString.getBytes());

            // Recibir la respuesta del servidor
            byte[] responseBytes = new byte[1024];
            bytesRead = inputStream.read(responseBytes);
            String response = new String(responseBytes, 0, bytesRead).trim();
            System.out.println(response);

            // Si la suposición es correcta, finalizar el juego
            if (response.startsWith("¡Felicidades")) {
                break;
            }
        }

        clientSocket.close();
    }
}
