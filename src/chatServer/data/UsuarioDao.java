/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatServer.data;

/**
 *
 * @author David
 */
import chatProtocol.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsuarioDao {
     public void create(User o) throws Exception{
        String sql="insert into Usuario (id, nombre, clave) "+
                "values(?,?,?)";
        PreparedStatement stm = Database.instance().prepareStatement(sql);
        stm.setString(1, o.getId());
        stm.setString(2, o.getNombre());   
        stm.setString(3, o.getClave());  
        int count=Database.instance().executeUpdate(stm);
        if (count==0){
           throw new Exception("Usuario ya existe");
        }
    }
     
 public User read(String id) throws Exception{
        String sql="select * from Usuario where id=?";
        PreparedStatement stm = Database.instance().prepareStatement(sql);
        stm.setString(1, id);
        ResultSet rs =  Database.instance().executeQuery(stm);           
        if (rs.next()) {
            return from(rs);
        }
        else{
            throw new Exception ("Usuario no Existe");
        }
    }   
 
 public void update(User o) throws Exception{
        String sql="update Usuario set nombre=? "+
                "where id=?";
        PreparedStatement stm = Database.instance().prepareStatement(sql);
        stm.setString(1, o.getNombre());
        stm.setString(2, o.getId());        
        int count=Database.instance().executeUpdate(stm);
        if (count==0){
            throw new Exception("Usuario no existe");
        }
    }    
 
 public void delete(User o) throws Exception{
        String sql="delete from Usuario where id=?";
        PreparedStatement stm = Database.instance().prepareStatement(sql);
        stm.setString(1, o.getId());        
        int count=Database.instance().executeUpdate(stm);        
        if (count==0){
            throw new Exception("Usuario no existe");
        }
    }

 public Map<String,User> findAll(){
        Map<String,User> r =  new HashMap();
        String sql="select * from Usuario c";
        try {
            PreparedStatement stm = Database.instance().prepareStatement(sql);
            ResultSet rs =  Database.instance().executeQuery(stm);
            while (rs.next()) {
                User aux = from(rs);
                r.put(aux.getId(), aux);
            }
        } catch (SQLException ex) { }
        return r;
    }
   public List<User> findByNombre(User o){
        List<User> r= new ArrayList<>();
        String sql="select * from Usuario where nombre like ?";
        try {        
            PreparedStatement stm = Database.instance().prepareStatement(sql);
            stm.setString(1, "%"+o.getNombre()+"%");   
            ResultSet rs =  Database.instance().executeQuery(stm); 
            while (rs.next()) {
                r.add(from(rs));
            }
        } catch (SQLException ex) { }
        return r;
    } 
 
   public User from (ResultSet rs){
        try {
            User r= new User();
            r.setId(rs.getString("id"));
            r.setNombre(rs.getString("nombre"));
            r.setClave(rs.getString("clave"));
            return r;
        } catch (SQLException ex) {
            return null;
        }
    }
 public  void close(){
    }
}

