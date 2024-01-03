package Controller;

import View.VentanPrincipalChat;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface localChatService extends Remote {
    void sendMessage(String message, String destinationIp, String port, VentanPrincipalChat ventanaPrincipalChat) throws RemoteException;
    void sendMessageStraightForward(String message, VentanPrincipalChat ventanaPrincipalChat) throws RemoteException;
    boolean listenMessages() throws RemoteException;
    Object[] getMessages() throws RemoteException;
    void deleteMessages() throws RemoteException;
}
