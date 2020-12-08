
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

// SERVER : Single Server                       
// TIPE : One-Way Communication (Client to Server)
// DESCRIPTION : 
// A simple server that will accept a single client connection and display everything the client says on the screen. 
// If the client user types "exit", the client and the server will both quit.
public class Server {

    private int port = 8081;
    private Socket socket = null;
    private ServerSocket serverSocket = null;
    private BufferedInputStream bis = null;
    private DataInputStream dis = null;
    private DataOutputStream dos=null;

    public Server() {
        try {
        	Spectacle s=new Spectacle();
        	s.init();
        	//Done Cr�er une socket pour �couter les clients
            serverSocket = new ServerSocket(port);
          //Done Afficher le port local du serveur
            System.out.println("Server started on port " + serverSocket.getLocalPort() + "...");
            System.out.println("Waiting for client...");

          //Done Le serveur accepte une demande de connexion
            socket = serverSocket.accept();
            System.out.println("Client " + socket.getRemoteSocketAddress() + " connected to server...");

            //Done Obtenir le flux d'entr�e pour obtenir les informations parvenues de la socket
            bis = new BufferedInputStream(socket.getInputStream());
            //Done Lire le data input stream
            dis = new DataInputStream(bis);

            while (true) {
                try {
                    String messageFromClient = dis.readUTF();
                    if (messageFromClient.equals("exit")) {
                        break;
                    }
                    //Done afficher l'adresse du client et le message envoyer par le client
                    System.out.println("Client [" + socket.getRemoteSocketAddress() + "] : " + messageFromClient);
                  //Done obtenir le flux d'�criture pour envoyer le message
                    dos = new DataOutputStream(socket.getOutputStream());
                    dos.writeUTF(s.toString());
                } catch (IOException e) {
                    break;
                }
            }
          
            dis.close();
            dos.close();
            socket.close();
            System.out.println("Client " + socket.getRemoteSocketAddress() + " disconnect from server...");
        } catch (IOException e) {
            System.out.println("Error : " + e);
        }
    }

    public static void main(String args[]) {
        Server server = new Server();
    }
}