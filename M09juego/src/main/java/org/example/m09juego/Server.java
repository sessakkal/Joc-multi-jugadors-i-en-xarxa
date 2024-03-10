package org.example.m09juego;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Server implements Runnable {

    private static final int PORT = 8001;
    private static final int MAX_NUMBER = 40;

    private Map<Socket, Integer> clientSecretNumbers;
    private ServerSocket serverSocket;
    private boolean running;

    public Server() throws IOException {
        serverSocket = new ServerSocket(PORT);
        clientSecretNumbers = new HashMap<>();
        System.out.println("Servidor iniciado en el puerto " + PORT);
        running = true;
    }

    public void run() {
        while (running) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress());
                new Thread(new ClientHandler(clientSocket)).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopServer() {
        running = false;
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        Thread serverThread = new Thread(server);
        serverThread.start();
    }

    private class ClientHandler implements Runnable {

        private Socket clientSocket;
        private InputStream inputStream;
        private OutputStream outputStream;

        public ClientHandler(Socket clientSocket) throws IOException {
            this.clientSocket = clientSocket;
            inputStream = clientSocket.getInputStream();
            outputStream = clientSocket.getOutputStream();
        }

        @Override
        public void run() {
            try {
                // Generar número secreto para el cliente
                int secretNumber = new Random().nextInt(MAX_NUMBER) + 1;
                clientSecretNumbers.put(clientSocket, secretNumber);

                // Enviar mensaje de bienvenida al cliente
                String welcomeMessage = "Bienvenido! Adivina un número entre 1 y " + MAX_NUMBER + ":\n";
                outputStream.write(welcomeMessage.getBytes());

                while (true) {
                    // Recibir la suposición del cliente
                    byte[] guessBytes = new byte[1024];
                    int bytesRead = inputStream.read(guessBytes);
                    String guessString = new String(guessBytes, 0, bytesRead).trim();

                    // Validar la suposición
                    if (guessString.isEmpty()) {
                        continue;
                    }

                    int guess = Integer.parseInt(guessString);
                    boolean correctGuess = guess == secretNumber;

                    // Enviar respuesta al cliente
                    String response;
                    if (correctGuess) {
                        response = "¡Felicidades! ¡Adivinaste el número!\n";
                        outputStream.write(response.getBytes());
                        clientSocket.close();
                        break;
                    } else {
                        response = "¡Fallaste! Intenta de nuevo:\n";
                        int difference = Math.abs(secretNumber - guess);
                        if (difference <= 5) {
                            response += "¡Muy cerca!La diferencia es <= 5\n";
                        } else if (difference <= 10) {
                            response += "¡Acercándote!La diferencia es  > 5 y <= 10\n";
                        } else {
                            response += "Lejos, sigue intentando!La diferencia es >10\n";
                        }
                        outputStream.write(response.getBytes());
                    }
                }
            } catch (IOException e){
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}