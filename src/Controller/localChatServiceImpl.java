package Controller;

import View.VentanPrincipalChat;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class localChatServiceImpl extends UnicastRemoteObject implements localChatService, Serializable {
    List<String> mensajes = new ArrayList<>();
    @Serial
    private static final long serialVersionUID = -6044598747301230548L;
    protected localChatServiceImpl() throws RemoteException {
    }

    @Override
    public void sendMessage(String message, String destinationIp, String port, VentanPrincipalChat ventanPrincipalChat) throws RemoteException {
        Objects.requireNonNull(obtenerReferenciaObjetoRemoto(destinationIp, port)).sendMessageStraightForward(message, ventanPrincipalChat);
    }

    @Override
    public void sendMessageStraightForward(String message, VentanPrincipalChat ventanPrincipalChat) throws RemoteException {
        ventanPrincipalChat.txtAreaChatGeneral1.setText(ventanPrincipalChat.txtAreaChatGeneral1.getText() +
                "\n" + message);
        deleteMessages();
    }

    @Override
    public boolean listenMessages() throws RemoteException {
        return mensajes.isEmpty();
    }

    @Override
    public Object[] getMessages() throws RemoteException {
        return mensajes.toArray();
    }

    @Override
    public void deleteMessages(){
        mensajes.clear();
    }

    public static localChatService obtenerReferenciaObjetoRemoto(String ip, String port){
        try {
            localChatService localChatService = (localChatService) Naming.lookup("//" +
                    ip + ":" + port + "/PruebaRMI");
            return localChatService;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
