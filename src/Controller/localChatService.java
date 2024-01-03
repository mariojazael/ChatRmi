package Controller;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface localChatService extends Remote {
    void sendMessage(String message, String destinationIp, String port) throws RemoteException;
    void sendMessageStraightForward(String message) throws RemoteException;
    boolean listenMessages() throws RemoteException;
    Object[] getMessages() throws RemoteException;
}
