import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Logger;

public class QouteServer {

    public static final String CLASS_NAME = QouteServer.class.getSimpleName();
    public static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

    public static final int PORT = 1717;

    public static void main(String args[]) {
        int portNumber;
        if (args.length != 1) {
            System.err.println("Usage: java QouteServer <port number>");
            portNumber = PORT;
            //System.exit(1);
        } else {
            portNumber = Integer.parseInt(args[0]);
        }

        ArrayList<String> quotes = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader("quotes.txt");
            BufferedReader input = new BufferedReader(fileReader );

            String quote;

            while ( (quote=input.readLine()) != null) {
                quotes.add(quote);
            }
            input.close();
        } catch (FileNotFoundException e) {
            LOGGER.severe( e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            LOGGER.severe( e.getMessage());
            e.printStackTrace();
        }

        try {
            ServerSocket serverSocket
                    = new ServerSocket( portNumber );

            while( quotes.size() > 0 ) {
                String frase = quotes.remove(0);
                Socket clientSocket = serverSocket.accept();

                LOGGER.info("CONNECTION: " +
                        clientSocket.getRemoteSocketAddress().toString());

                Thread serviceProcess = new Thread(new QuoteService(frase,
                                             clientSocket) );
                serviceProcess.start();
            }

        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }

    }
}
