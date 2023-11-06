import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Logger;

public class QuoteClient {

    public static final String CLASS_NAME = QuoteClient.class.getSimpleName();
    public static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java QuoteClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try {
            // abrir la conexion con el servidor
            Socket theSocket = new Socket(hostName, portNumber);

            // Obtener flujo de entrada
            BufferedReader in
                    = new BufferedReader(
                    new InputStreamReader( theSocket.getInputStream() ));

            // leer la frase que envia el servidor
            String frase = in.readLine();

            //imprimir el valor en consola
            System.out.println(frase);

            theSocket.close();
        } catch (UnknownHostException e) {
            LOGGER.severe("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            LOGGER.severe("Couldn't get I/O for the connection to "
                    + hostName);
            System.exit(1);
        }
    }
}
