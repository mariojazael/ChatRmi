package Controller;

import View.VentanPrincipalChat;

import java.io.Serial;
import java.io.Serializable;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClienteChat implements Serializable {
    @Serial
    private static final long serialVersionUID = -6044598747301230549L;

    public static void main(String[] args) throws UnknownHostException {
        ServidorMensajes servidorMensajes = ClienteChat.obtenerReferenciaObjetoRemoto(args);
        VentanPrincipalChat ventanPrincipalChat = new VentanPrincipalChat();
        VentanaPrincipalChatControlador ventanaPrincipalChatControlador = new VentanaPrincipalChatControlador(ventanPrincipalChat, servidorMensajes);
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
