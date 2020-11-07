package chatServer;

import chatProtocol.User;
import chatProtocol.IService;
import java.util.HashMap;
import java.util.Map;
import chatServer.data.UsuarioDao;
import java.util.List;

public class Service implements IService{
    private static IService theInstance;
    public static IService instance(){
        if (theInstance==null){ 
            theInstance=new Service();
        }
        return theInstance;
    }
    
    private UsuarioDao usuarioDao;
    Server srv;
    Map<String,User> users;

    public Service() {        
        usuarioDao = new UsuarioDao();
        users = usuarioDao.findAll();
        users.put("jperez", new User("jperez","111","Juan"));
        users.put("mreyes", new User("mreyes","222","Maria"));
       // users.put("parias", new User("parias","333","Pedro"));             
    }
    
      public User get(User o) throws Exception{
        return usuarioDao.read(o.getId());
    }  
      
     public void add(User o)throws Exception{
        usuarioDao.create(o);
    }

    public void update(User o)throws Exception{
        usuarioDao.update(o);
        User stored=this.get(o);
    }
    
    public List<User> search(User o){
        return usuarioDao.findByNombre(o); 
    }
    
    public void setSever(Server srv){
        this.srv=srv;
    }
    
    public void post(String m, String idEmisor, String idReceptor){
        srv.deliver(m,idEmisor,idReceptor);
        // TODO if the receiver is not active, store it temporarily
    }
    public boolean checkStatus(String m, String idEmisor, String idReceptor)
    {
       return srv.checkStatus(m,idEmisor,idReceptor);
    }
    
    public User login(User u) throws Exception{
        User result=users.get(u.getId());
        if(result==null)  throw new Exception("User does not exist");
        if(!result.getClave().equals(u.getClave()))throw new Exception("User does not exist");
        return result;
    } 

    public void logout(User p) throws Exception{
        srv.remove(p);
    }    
}
