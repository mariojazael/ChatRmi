package Controller;

import View.VentanPrincipalChat;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface localChatService extends Remote {
    void sendMessage(String message, String destinationIp, String port) throws RemoteException;
    boolean listenMessages() throws RemoteException;
    void addMessageToMailBox(String message) throws RemoteException;
    Object[] getMessages() throws RemoteException;
    void deleteMessages() throws RemoteException;
}
