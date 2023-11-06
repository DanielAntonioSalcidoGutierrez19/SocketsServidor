import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
public class UserManager {
    public static final String CLASS_NAME = UserManager.class.getSimpleName();
    public static final Logger LOGGER = Logger.getLogger(CLASS_NAME);
    private HashMap<String, Socket> userConnections;
    public UserManager() {
        super();
        userConnections = new HashMap<String, Socket>();
    }
    public boolean connect
            (String UsuarioConexion, Socket skt) {
        boolean Visible = true;
        if (userConnections.containsKey(UsuarioConexion)){
            Visible = false;
        }else {
            userConnections.put(UsuarioConexion,skt);
        }
        return Visible;}
    public boolean disconnect(String UsuarioConexion){
        boolean visible = true;
        if (userConnections.containsKey(UsuarioConexion)){
            userConnections.remove(UsuarioConexion); }
        else {
            visible = false; }
        return visible; }
    public String getUserList(){
        StringBuilder userList = new StringBuilder();
        for (String user: userConnections.keySet()){
            userList.append(user).append(" ");
        }return userList.toString().trim();}
    public void send(String UsuarioConexion, String msj) {
        Socket socket = userConnections.get(UsuarioConexion);
        if (socket!= null){
            try {
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
                output.println(msj); }catch (IOException e){
                LOGGER.severe(e.getMessage());
                e.printStackTrace(); }}}}
