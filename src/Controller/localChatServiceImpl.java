package Controller;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class localChatServiceImpl extends UnicastRemoteObject implements localChatService, Serializable {
    private final List<String> mensajes;
    @Serial
    private static final long serialVersionUID = -6044598747301230548L;
    protected localChatServiceImpl() throws RemoteException {
        mensajes = new ArrayList<>();
    }

    @Override
    public void sendMessage(String message, String destinationIp, String port) throws RemoteException {
        localChatService localChatService = obtenerReferenciaObjetoRemoto(destinationIp, port);
        if(localChatService != null) localChatService.sendMessageStraightForward(message);
    }

    @Override
    public void sendMessageStraightForward(String message) throws RemoteException {
        mensajes.add(message);
    }

    @Override
    public boolean listenMessages() throws RemoteException {
        return !mensajes.isEmpty();
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
            return (localChatService) Naming.lookup("//" +
                    ip + ":" + port + "/PruebaRMI");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
