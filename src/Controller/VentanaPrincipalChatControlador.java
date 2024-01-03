package Controller;

import View.VentanPrincipalChat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VentanaPrincipalChatControlador implements ActionListener {
    VentanPrincipalChat ventanPrincipalChat;
    ServidorMensajes servidorMensajes;
    localChatService localChatService;
    ExecutorService executorService = Executors.newFixedThreadPool(1);
    Runnable buscarMensajesGenerales = () -> {
        while(true){
            try {
                String[] mensajes = servidorMensajes.obtenerMensajes();
                for(String mensaje: mensajes){
                    if(mensaje != null && !ventanPrincipalChat.txtAreaChatGeneral.getText().contains(mensaje)) ventanPrincipalChat.txtAreaChatGeneral.setText(ventanPrincipalChat.txtAreaChatGeneral.getText() + "\n" + mensaje);
                }
                Thread.sleep(1000);
            } catch (RemoteException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    };

    Runnable listeningTask = () -> {
        while(true){
            try {
                if(!localChatService.listenMessages()){
                    ventanPrincipalChat.txtAreaChatGeneral1.setText(ventanPrincipalChat.txtAreaChatGeneral1.getText() +
                            "\n" + Arrays.toString(localChatService.getMessages()));
                    localChatService.deleteMessages();
                }
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    };

    public VentanaPrincipalChatControlador(VentanPrincipalChat ventanPrincipalChat,
                                           localChatService localChatService) throws UnknownHostException {
        this.ventanPrincipalChat = ventanPrincipalChat;
        this.localChatService = localChatService;
        ventanPrincipalChat.btnChatPrivadoEnviar.addActionListener(this);
        ventanPrincipalChat.btnEnviar.addActionListener(this);
        ventanPrincipalChat.btnEstablecerConexion.addActionListener(this);
        ventanPrincipalChat.jLabel4.setText("tu ip: " + java.net.InetAddress.getLocalHost().getHostAddress());
        executorService.submit(listeningTask);
    }
    public VentanaPrincipalChatControlador(VentanPrincipalChat ventanPrincipalChat,
                                           ServidorMensajes servidorMensajes) throws UnknownHostException {
        this.ventanPrincipalChat = ventanPrincipalChat;
        this.servidorMensajes = servidorMensajes;
        ventanPrincipalChat.btnChatPrivadoEnviar.addActionListener(this);
        ventanPrincipalChat.btnEnviar.addActionListener(this);
        ventanPrincipalChat.btnEstablecerConexion.addActionListener(this);
        ventanPrincipalChat.jLabel4.setText("tu ip: " + java.net.InetAddress.getLocalHost().getHostAddress());
        // executorService.submit(buscarMensajesGenerales);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == ventanPrincipalChat.btnEnviar){
            try {
                servidorMensajes.enviarMensaje(java.net.InetAddress.getLocalHost().getHostAddress() + ": " +  ventanPrincipalChat.txtFieldMensajeChatGeneral.getText());
            } catch (RemoteException | UnknownHostException ex) {
                throw new RuntimeException(ex);
            }
        }
        else if(e.getSource() == ventanPrincipalChat.btnEstablecerConexion){
            try {
                ventanPrincipalChat.txtAreaChatGeneral1.setText("");
                String[] mensajes = servidorMensajes.obtenerMensajes(ventanPrincipalChat.txtFldIpDestino.getText(), java.net.InetAddress.getLocalHost().getHostAddress());
                for(String mensaje : mensajes){
                    if(mensaje != null) ventanPrincipalChat.txtAreaChatGeneral1.setText((ventanPrincipalChat.txtAreaChatGeneral1.getText() + "\n" + mensaje + "\n"));
                }
            } catch (RemoteException | UnknownHostException ex) {
                throw new RuntimeException(ex);
            }
        }
        else if(e.getSource() == ventanPrincipalChat.btnChatPrivadoEnviar){
            // String [] ips;
            try {
                localChatService.sendMessage(ventanPrincipalChat.txtFldChatPrivado.getText(),
                        ventanPrincipalChat.txtFldIpDestino.getText(),
                        "1234",
                        this.ventanPrincipalChat);
                // ips = new String[]{ventanPrincipalChat.txtFldIpDestino.getText(), java.net.InetAddress.getLocalHost().getHostAddress()};
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
            /*Map.Entry<String[], String> entry =  new AbstractMap.SimpleEntry<>(ips, ips[1] + " " + LocalDate.now() + ": " + ventanPrincipalChat.txtFldChatPrivado.getText());
            try {
                servidorMensajes.enviarMensajePrivado(entry);
                ventanPrincipalChat.txtAreaChatGeneral1.setText("");
                String[] mensajes = servidorMensajes.obtenerMensajes(ventanPrincipalChat.txtFldIpDestino.getText(), java.net.InetAddress.getLocalHost().getHostAddress());
                for(String mensaje : mensajes){
                    if(mensaje != null) ventanPrincipalChat.txtAreaChatGeneral1.setText(ventanPrincipalChat.txtAreaChatGeneral1.getText() + "\n" + mensaje + "\n");
                }
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            } catch (UnknownHostException ex) {
                throw new RuntimeException(ex);
            }
        */}
    }
}
