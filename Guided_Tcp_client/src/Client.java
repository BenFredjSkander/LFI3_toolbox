
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

// Client for Server1, Server2, Server3
public class Client {
	//Done Ajouter le nom du serveur
	private String serverName="localhost";
    private int serverPort = 8081;
    private Socket socket = null;
    private DataOutputStream dos = null;

    public Client() {
        try {
        	//Done cr�er une nouvelle socket
        	socket = new Socket(serverName,serverPort);
            //Done r�cup�rer le num�ro de port du socket c�t� client
        	System.out.println("Client d�marr� sur le port "+socket.getLocalPort());
            //Done r�cup�rer le num�ro de port du socket c�t� serveur
        	System.out.println("Connect� au serveur"+socket.getRemoteSocketAddress());
                
            //Done obtenir le flux d'�criture pour envoyer le message
        	dos= new DataOutputStream(socket.getOutputStream());
        	
            Scanner scan=new Scanner(System.in);
            while (true) {
                try {
                    System.out.println("Message to server : ");
                    String messageToServer = scan.nextLine();
                    if(messageToServer.equals("exit")){
                        break;
                    }
                  //Done �crire le message � envoyer au serveur
                   dos.writeUTF(messageToServer);
                   dos.flush();
                } catch (IOException e) {
                    break;
                }
            }
          //Done fermer le flux d'�criture et le socket
           dos.close();
           socket.close();
        } catch (IOException e) {
            System.out.println("Error : " + e.getMessage());
        }
    }

    public static void main(String args[]) {
        Client client = new Client();
    }
}