package chatServer;

import chatProtocol.PaqueteDatos;
import chatProtocol.Protocol;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import chatProtocol.User;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;

public class Server {
    ServerSocket srv;
    List<Worker> workers; 
    
    public Server() {
        try {
            srv = new ServerSocket(Protocol.PORT);
            workers =  Collections.synchronizedList(new ArrayList<Worker>());
        } 
        catch (IOException ex) {}
    }
    
    public void run(){
        Service localService = (Service)(Service.instance());
        localService.setSever(this);
        boolean continuar = true;
        while (continuar) {
            try {
                Socket skt = srv.accept();
                ObjectInputStream in = new ObjectInputStream(skt.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(skt.getOutputStream() );
                try {
                    int method = in.readInt(); // should be Protocol.LOGIN                    
                    User user=(User)in.readObject();                          
                    try {
                        user=Service.instance().login(user);
                        out.writeInt(Protocol.ERROR_NO_ERROR);
                        out.writeObject(user);
                        out.flush();
                        Worker worker = new Worker(skt,in,out,user); 
                        workers.add(worker);                      
                        worker.start();                            
                    } catch (Exception ex) {
                       out.writeInt(Protocol.ERROR_LOGIN);
                       out.flush();
                    }                          
                } 
                catch (ClassNotFoundException ex) {}                

            } 
            catch (IOException ex) {}
        }
    }
    
    public void deliver(PaqueteDatos message, String idEmisor, String idRec){
        Worker emisor = null;
        Worker receptor = null;
        for(Worker wk:workers){
            if (wk.user.getId().equals(idEmisor)) {
                 //wk.deliver(message);    
                 emisor = wk;
            }     
            if (wk.user.getId().equals(idRec)) {
                receptor = wk;
            }
        }
        if (receptor == null) {
            message.setMensaje("Offline");
            emisor.deliver(message);
        }
        else
        {
         emisor.deliver(message);   
         receptor.deliver(message);
        } 
    } 
    
    public boolean checkStatus(String message, String idEmisor, String idRec){
        for(Worker wk:workers){
            if (wk.user.getId().equals(idRec) ) {
                return true;             
            }       
        }
        return false;
    } 
    
    public void remove(User u){
        for(Worker wk:workers) {
            if(wk.user.equals(u)){
                workers.remove(wk);
                try { wk.skt.close();} catch (IOException ex) {}
                break;
            }
        }
    }
    
}