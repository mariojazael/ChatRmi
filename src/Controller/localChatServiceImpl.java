package Controller;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class localChatServiceImpl extends UnicastRemoteObject implements localChatService, Serializable {
    List<String> mensajes = new ArrayList<>();
    VentanaPrincipalChatControlador ventanaPrincipalChatControlador = null;
    @Serial
    private static final long serialVersionUID = -6044598747301230548L;
    protected localChatServiceImpl() throws RemoteException {
    }

    @Override
    public void sendMessage(String message, String destinationIp, String port) throws RemoteException {
        localChatService localChatService = obtenerReferenciaObjetoRemoto(destinationIp, port);
        assert localChatService != null;
        localChatService.addMessageToMailBox(message);
    }

    @Override
    public boolean listenMessages() throws RemoteException {
        return mensajes.isEmpty();
    }

    @Override
    public void addMessageToMailBox(String message) throws RemoteException {
        // this.mensajes.add(message);
        this.ventanaPrincipalChatControlador.pintarGUI(message);
    }

    @Override
    public Object[] getMessages() throws RemoteException {
        return mensajes.toArray();
    }

    @Override
    public void deleteMessages(){
        mensajes.clear();
    }

    @Override
    public void addViewController(VentanaPrincipalChatControlador ventanaPrincipalChatControlador) throws RemoteException {
        this.ventanaPrincipalChatControlador = ventanaPrincipalChatControlador;
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
