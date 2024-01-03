package Controller;

import View.VentanPrincipalChat;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class localChatServiceImpl extends UnicastRemoteObject implements localChatService, Serializable {
    List<String> mensajes = new ArrayList<>();
    @Serial
    private static final long serialVersionUID = -6044598747301230548L;
    protected localChatServiceImpl() throws RemoteException {
    }

    @Override
    public void sendMessage(String message, String destinationIp, String port) throws RemoteException {
        localChatServiceImpl localChatService = (localChatServiceImpl) obtenerReferenciaObjetoRemoto(destinationIp, port);
        assert localChatService != null;
        localChatService.mensajes.add(message);
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
