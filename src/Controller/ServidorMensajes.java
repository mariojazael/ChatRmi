package Controller;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public interface ServidorMensajes extends Remote {
    List<Map.Entry<String[], String>> listMensajes = null;
    boolean buscarMensajesBuzon(String ip) throws RemoteException;
    String[] obtenerMensajes(String ipDestino, String ipOrigen) throws RemoteException;
    String[] obtenerMensajes() throws RemoteException;
    void eliminarMensajes(String ipDestino, String ipOrigen) throws RemoteException;
    void enviarMensaje(String mensaje) throws RemoteException;
    void enviarMensajePrivado(Map.Entry<String[], String> mapa) throws RemoteException;
    void prueba() throws RemoteException, InterruptedException;
}

