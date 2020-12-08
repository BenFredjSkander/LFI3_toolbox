import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;




public class UDPServer {

	DatagramSocket socket;
	int port ;


	int				pacSize;
	byte[]			pacData;
	DatagramPacket 	packet;
	int	recvPort;

	public void runServer() {
		recvPort=8081;
		
		try {
			//Done initialiser le serveur pour recevoir les donn�es
			socket = new DatagramSocket(port);
			System.out.println("Ready to receive...");


			//Receive request from client
			pacSize = 5000;
			pacData = new byte[5000];

			//Done Cr�er un datagrame
			packet = new DatagramPacket(pacData, pacSize);
			try {
				//Done Laisser le serveur attendre pendant une dur�e de 30000 millisecondes la r�ception d'un datagramme
				socket.setSoTimeout(30000);
				//Done le serveur re�oit le datagramme
				socket.receive(packet);


			} catch (IOException e) {
				System.out.println("Error IO exception receiving packet.");
				System.out.println("Could be due to timeout.");
				System.out.println("Now closing server...");
				System.exit(-1);
			}
			processMessage(packet.getData());
		} catch (SocketException e1) {
			e1.printStackTrace();
		}

	}

	public void processMessage(byte[] data) {

		String msg="";		

		// Use the data to construct a new MessageInfo object
		ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
		ObjectInputStream is;

		try {
			//Done cr�er le buffer dans un ObjectInputStream o� je vais mettre l'objet re�u
			is = new ObjectInputStream(new BufferedInputStream(byteStream));
			//Done Lire l'objet re�u
			msg = (String) is.readObject();
			is.close();
			System.out.print(" Le message re�u"+msg);
		} catch (ClassNotFoundException e) {
			System.out.println("Error: Could not find class match for transmitted message.");
		} catch (IOException e) {
			System.out.println("Error: IO exception creating ObjectInputStream.");
		}




	}


	public static void main(String args[]) {
		UDPServer udpsrver=new UDPServer();
		udpsrver.runServer();


	}

}	