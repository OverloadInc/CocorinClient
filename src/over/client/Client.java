package over.client;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import javax.swing.JOptionPane;

/**
 * Client class controls the user's operations for establishing a correct communication trough the chat application.
 */
public class Client extends Thread {

    /**
     * Socket to server communication.
     */
    private Socket socket;

    /**
     * Stream to send objects to the server.
     */
    private ObjectOutputStream objectOutputStream;

    /**
     * Stream to receive objects from the server.
     */
    private ObjectInputStream objectInputStream;

    /**
     * Chat application GUI.
     */
    private final Chat chat;

    /**
     * Unique client id.
     */
    private String clientId;

    /**
     * Indicates if a thread is listening a specific client.
     */
    private boolean isListening;

    /**
     * Server host IP.
     */
    private final String host;

    /**
     * Server port to receive connections.
     */
    private final int port;

    /**
     * Client class constructor.
     * @param chat the target application.
     * @param host the target server host.
     * @param port the server port.
     * @param clientId the unique client Id.
     */
    Client(Chat chat, String host, Integer port, String clientId) {
        this.chat = chat;
        this.host = host;
        this.port = port;
        this.clientId = clientId;
        this.isListening = true;

        this.start();
    }

    /**
     * Executes the client-side thread.
     */
    public void run(){
        try {
            socket = new Socket(host, port);

            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());

            System.out.println("Successful connection");

            this.sendConnectionRequest(clientId);
            this.listen();
        }
        catch (UnknownHostException ex) {
            JOptionPane.showMessageDialog(null, "Connection refused. Server not found.\n"
                    + "Please, verify your IP address or star the server");

            System.exit(0);
        }
        catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Connection refused. Input/Output error."
                    + "Please, verify your IP address or star the server");

            System.exit(0);
        }
    }

    /**
     * Closes the current socket communication.
     */
    public void disconnect(){
        try {
            objectOutputStream.close();
            objectInputStream.close();
            socket.close();

            isListening = false;
        }
        catch (Exception e) {
            System.err.println(" Error on closing client's communication elements.");
        }
    }

    /**
     * Sends a specific message to the server.
     * @param receiver the message receiver.
     * @param message the client's message.
     */
    public void sendMessage(String receiver, String message){
        LinkedList<String> dataList = new LinkedList<>();

        dataList.add("MESSAGE");
        dataList.add(clientId);
        dataList.add(receiver);
        dataList.add(message);

        try {
            objectOutputStream.writeObject(dataList);
        }
        catch (IOException ex) {
            System.out.println("Input/Reading error on sending message to the server.");
        }
    }

    /*
     * Listen to the current server.
     */
    public void listen() {
        try {
            while (isListening) {
                Object object = objectInputStream.readObject();

                if (object != null) {
                    if (object instanceof LinkedList)
                        execute((LinkedList<String>)object);
                    else
                        System.err.println("Unknown object received through the socket.");
                }
                else
                    System.err.println("NULL received through the socket");
            }
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Server communication lost.");

            System.exit(0);
        }
    }

    /**
     * Performs specific tasks depending on the message received for the user from the server.
     * @param dataList the set of data to the server.
     */
    public void execute(LinkedList<String> dataList){
        String data = dataList.get(0);

        switch (data) {
            case "ACCEPTED_CONNECTION":
                clientId = dataList.get(1);

                chat.initSession(clientId);

                for(int i = 2; i < dataList.size(); i++)
                    chat.addConnectedUser(dataList.get(i));
                break;
            case "NEW_USER_CONNECTED":
                chat.addConnectedUser(dataList.get(1));
                break;
            case "USER_DISCONNECTED":
                chat.removeUser(dataList.get(1));
                break;
            case "MESSAGE":
                chat.addMessage(dataList.get(1), dataList.get(3));
                break;
            default:
                break;
        }
    }

    /**
     * Sends a request connection to be added to connected user's list.
     * @param id the client who request the connection.
     */
    private void sendConnectionRequest(String id) {
        LinkedList<String> dataList = new LinkedList<>();

        dataList.add("CONNECTION_REQUEST");
        dataList.add(id);

        try {
            objectOutputStream.writeObject(dataList);
        }
        catch (IOException ex) {
            System.out.println("Input/Reading error on sending message to the server.");
        }
    }

    /**
     * Notifies to the server that a user is disconnected every time the client closes the chat application. Then, the
     * server updates each connected user's list.
     */
    public void acceptDisconnection() {
        LinkedList<String> dataList = new LinkedList<>();

        dataList.add("DISCONNECTION_REQUEST");
        dataList.add(clientId);

        try {
            objectOutputStream.writeObject(dataList);
        }
        catch (IOException ex) {
            System.out.println("Input/Reading error on sending message to the server.");
        }
    }

    /**
     * Returns the unique client Id.
     * @return the client Id.
     */
    String getClientId() {
        return clientId;
    }
}