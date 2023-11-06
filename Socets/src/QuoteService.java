

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QuoteService implements Runnable {

    private Socket clientSocket = null;
    private String quote;

    public QuoteService(String frase, Socket client) {
        clientSocket = client;
        quote = frase;
    }

    @Override
    public void run() {
        try {
            PrintWriter out
                    = new PrintWriter(clientSocket.getOutputStream(), true);

            out.println(quote);

            clientSocket.close();
        } catch (IOException ex) {
            System.out.println("Error en la conexion");
        }
    }

}
