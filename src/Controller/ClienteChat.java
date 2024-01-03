package Controller;

import View.VentanPrincipalChat;

import java.io.Serial;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClienteChat implements Serializable {
    @Serial
    private static final long serialVersionUID = -6044598747301230549L;

    public static void main(String[] args) throws UnknownHostException, RemoteException, MalformedURLException {
        ServidorMensajes servidorMensajes = ClienteChat.obtenerReferenciaObjetoRemoto(args);

        Registry registry = LocateRegistry.createRegistry(1234);

        localChatServiceImpl localChatService = new localChatServiceImpl();

        java.rmi.Naming.rebind("//" +
                java.net.InetAddress.getLocalHost().getHostAddress() +
                ":" + "1234" + "/PruebaRMI", localChatService);

        VentanPrincipalChat ventanPrincipalChat = new VentanPrincipalChat();
        VentanaPrincipalChatControlador ventanaPrincipalChatControlador = new VentanaPrincipalChatControlador(ventanPrincipalChat, localChatService);
        ventanPrincipalChat.setVisible(true);
    }

    public static ServidorMensajes obtenerReferenciaObjetoRemoto(String[] args){
        try {
            ServidorMensajes mir =
                    (ServidorMensajes) Naming.lookup("//" +
                            args[0] + ":" + args[1] + "/PruebaRMI");
            return mir;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
