import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;
public class ConnectionHandler implements Runnable {
    public static final String CLASS_NAME = ConnectionHandler.class.getSimpleName();
    public static final Logger LOGGER = Logger.getLogger(CLASS_NAME);
    private UserManager users;
    private Socket clientSocket = null;
    private BufferedReader input;
    private PrintWriter output;
    public ConnectionHandler(UserManager Usuarios, Socket soket) {
        users = Usuarios;
        clientSocket = soket;
        try {
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
            e.printStackTrace();}}
    @Override
    public void run() {
        try{
            String bf = null ;
            while (true) {
                String userList = users.getUserList();
                bf = input.readLine();
                if (bf== null){ break;}
                String command = bf.trim();
                String[] RespuestaServer = command.split(" ");
                //SWITCH AND CASE PARA SABER LA SITUACION DE MI SERVIDOR DE UNA MANERA RAPIDA
                switch (RespuestaServer[0]){
                    //CONEXIONDELSERVIDOR
                    case "CONNECT":
                        String NombreUSUARIO = command.substring(command.indexOf(' ')).trim();
                        System.out.println(NombreUSUARIO);
                        users.connect(NombreUSUARIO,clientSocket);
                        boolean isConnected =  users.connect(NombreUSUARIO,clientSocket);
                        if (!isConnected){
                            output.println("CONEXION EXITOSA"); }else {
                            output.println("LA CONEXION ES TODO UN DESASTRE");}
                        break;
                        //DESCONCECCION DEL SERVIDOR
                    case "DISCONNECT":
                        String NombreUsuario1 = command.substring(command.indexOf(' ')+1).trim();
                        System.out.println(NombreUsuario1);
                        boolean disconnected = users.disconnect(NombreUsuario1);
                        if (disconnected){
                            System.out.println(userList);
                            output.println(NombreUsuario1+" USTED HA SIDO DESCONECTADO");
                        }else {
                            output.println("INTENTO DE DESCONEXCION FALLIDO");
                        }
                        break;
                    case "SEND":
                        if (RespuestaServer[1].charAt(0)== '#'){
                            String CadenaMensaje = command.substring(command.indexOf('#')+1,command.indexOf('@'));
                            System.out.println(CadenaMensaje);
                                    //Debe de enviar un parrafo de minimo 140 palabras
                            if (CadenaMensaje.length()<140){
                                String NombreUser3 = command.substring(command.indexOf('@')+1).trim();
                                ArrayList<String> ListaDeLosUsuarios = new ArrayList<>(Arrays.asList(userList.split(" ")));
                                if (ListaDeLosUsuarios.contains(NombreUser3)){
                                    users.send(NombreUser3, CadenaMensaje);
                                }else {
                                    output.println("No se reconoce el usuario ¿Lo escribiste bien?");
                                }}else {
                                output.println("Debes de escribir por lo menos 141 palabras");
                            } }else{
                            output.println("El mensaje debe empezar con '#' comienza de nuevo por favor");}
                        break;
                    case "LIST":
                        output.println(userList); break;
                    default: output.println("Revisa tu escritura en la sintaxis por favor"); break;
                }}}catch (IOException exception){
            LOGGER.severe(exception.getMessage()); exception.printStackTrace(); }}}