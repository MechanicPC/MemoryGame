package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Klasa NetSocket - klasa ta stworzona do obsluzenia polaczenia pomiedzy dwoma
 * komputerami graczy
 * 
 * @author Paweł Cielma
 * @version Final
 *
 */
public class NetSocket {
	/**
	 * Podstawowe parametry potrzebne do obsługi połaczenie miedzy dwoma graczami
	 */
	// private String ip = "localhost";
	// private int port = 22222;
	private Socket socket;
	private ServerSocket serverSocket;
	private ObjectOutputStream outputObject;
	private ObjectInputStream inputObject;
	private DataOutputStream outputData;
	private DataInputStream inputData;

	public ObjectOutputStream getOutputObject() {return outputObject;}
	public ObjectInputStream getInputObject() {return inputObject;}
	public DataOutputStream getOutputData() {return outputData;}
	public DataInputStream getInputData() {return inputData;}

	/**
	 * Metoda initializeServer - inicjalizacja serwera z określonym portem,
	 * długoscia kolejki przychodzącej i lokalnym adresem IP
	 * 
	 * @return serverSocket - gniazdo serwera czekajace na żadanie
	 */
	public ServerSocket initializeServer() {
		try {
			serverSocket = new ServerSocket(Data.port, 8, InetAddress.getByName(Data.ip));
			System.out.println("Server started");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serverSocket;
	}

	/**
	 * Metoda connectToServer - metoda inicjalizujaca polaczenie z serwerem typu
	 * boolean zwracajaca dwa rezultaty:
	 * 
	 * @return polaczanie z serwerem lub brak polaczenia z serwerem
	 */
	public boolean connectToServer() {
		try {
			socket = new Socket(Data.ip, Data.port);

			outputData = new DataOutputStream(socket.getOutputStream());
			inputData = new DataInputStream(socket.getInputStream());
			outputObject = new ObjectOutputStream(socket.getOutputStream());
			inputObject = new ObjectInputStream(socket.getInputStream());

		} catch (IOException e) {
			System.out.println("Unable to connect to the address: " + Data.ip + ":" + Data.port);
			return false;
		}
		System.out.println("Successfully connected to the server.");
		return true;
	}

	/**
	 * Metoda listenForServerRequest - metoda nasłuchujaca żadania do serwera -
	 * klient może wyslac prosbę o podlaczenie a serwer ja wtedy akceptuje
	 */
	public void listenForServerRequest() {
		Socket socket = null;
		try {
			socket = serverSocket.accept();

			outputData = new DataOutputStream(socket.getOutputStream());
			inputData = new DataInputStream(socket.getInputStream());
			outputObject = new ObjectOutputStream(socket.getOutputStream());
			inputObject = new ObjectInputStream(socket.getInputStream());

			System.out.println("CLIENT HAS REQUESTED TO JOIN, AND WE HAVE ACCEPTED");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metoda closeServer - metoda odpowiadajaca na zamkniecie serwera -
	 * wykorzystywana przy zamknieciu programu
	 */
	public void closeServer() {
		try {
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}