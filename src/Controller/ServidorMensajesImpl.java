package Controller;

import View.VentanPrincipalChat;

import java.io.Serial;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class ServidorMensajesImpl extends UnicastRemoteObject implements ServidorMensajes, Serializable {
    List<Map.Entry<String[], String>> listaMensajes = new ArrayList<>();
    String[] mensajesGenerales = new String [10000];
    AtomicInteger atomicContador = new AtomicInteger(0);

    @Serial
    private static final long serialVersionUID = -6044598747301230549L;

    protected ServidorMensajesImpl() throws RemoteException{ // Los mensajes privados tienen forma de: map <[ipOrigen,ipDestino] : "Hola cliente 2"> y generales son "strings"
    }

    @Override
    public void prueba() throws RemoteException, InterruptedException {
        Thread.sleep(5000);
        listaMensajes.remove(0);
    }

    @Override
    public boolean buscarMensajesBuzon(String ip) { // Comprobar si existe algún mensaje
        AtomicReference<Boolean> condicion = new AtomicReference<>(false);

        listaMensajes.forEach((x) -> {
            if (Arrays.asList(x.getKey()).contains(ip)) {
                condicion.set(true);
            }
        });

        return condicion.get();
    }

    @Override
    public String[] obtenerMensajes(String ipDestino, String ipOrigen) { //Obtener mensajes destinados a mi cliente)

        String [] mensajes = new String[10];

        AtomicInteger i = new AtomicInteger(0);

        listaMensajes.forEach((x) -> {
            if (Arrays.asList(x.getKey()).contains(ipDestino) && Arrays.asList(x.getKey()).contains(ipOrigen)) {
                mensajes[i.get()] = x.getValue();
                i.getAndIncrement();
            }
        });

        return mensajes;
    }

    @Override
    public String[] obtenerMensajes() {
        return mensajesGenerales;
    }

    @Override
    public void eliminarMensajes(String ipDestino, String ipOrigen) throws RemoteException { // Eliminar mensajes una vez se impriman en mi cliente
        listaMensajes.forEach((x) -> {
            if (Arrays.asList(x.getKey()).contains(ipDestino) && Arrays.asList(x.getKey()).contains(ipOrigen)) {
                listaMensajes.remove(x);
            }
        });
    }

    @Override
    public void enviarMensaje(String mensaje) {
        mensajesGenerales[atomicContador.getAndIncrement()] = mensaje;
    }

    @Override
    public void enviarMensajePrivado(Map.Entry<String[], String> mapa) {
        listaMensajes.add(mapa);
    }

    public static void main(String[] args) throws UnknownHostException, MalformedURLException, RemoteException, InterruptedException {
        Registry registry = LocateRegistry.createRegistry(1234);

        ServidorMensajesImpl mir = new ServidorMensajesImpl();

        java.rmi.Naming.rebind("//" +
                java.net.InetAddress.getLocalHost().getHostAddress() +
                ":" + "1234" + "/PruebaRMI", mir);

        VentanPrincipalChat ventanPrincipalChat = new VentanPrincipalChat();
        VentanaPrincipalChatControlador ventanaPrincipalChatControlador = new VentanaPrincipalChatControlador(ventanPrincipalChat, mir);
        ventanPrincipalChat.setVisible(true);
    }
}
