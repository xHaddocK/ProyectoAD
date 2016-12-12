package modelo.bd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import modelo.clases.Proveedor;

public class ProveedorBD extends ConexionBD{
    
    private static Statement sentenciaSin;
    private static PreparedStatement sentenciaCon;
    private static ResultSet rs;
    
    public static ArrayList getAll() throws Exception{
        connect();
        
        sentenciaSin = getConnection().createStatement();
        rs = sentenciaSin.executeQuery("SELECT * FROM PROVEEDOR");
        
        ArrayList lista = convertirAArray(rs, "PROVEEDOR");
        
        disconnect();
        return lista;
    }
    
    public static Proveedor getByCod(Proveedor p) throws SQLException, Exception{
        connect();
        
        String query = "SELECT * FROM PROVEEDOR WHERE COD_PROV = ?;";
        sentenciaCon = getConnection().prepareStatement(query);
        sentenciaCon.setString(1, p.getId());
        
        rs = sentenciaCon.executeQuery();
        
        Proveedor p2 = (Proveedor) convertirAObjeto(rs, "PROVEEDOR");
        
        disconnect();
        return p2;
    }
    
    public static ArrayList getByNombre(Proveedor p) throws SQLException, Exception{
        //Puede haber dos nombres iguales por lo que retorno una lista
        //con todos los que tengan ese nombre
        connect();
        
        String query = "SELECT * FROM PROVEEDOR WHERE NOMBRE = ?;";
        sentenciaCon = getConnection().prepareStatement(query);
        sentenciaCon.setString(1, p.getNombre());
        
        rs = sentenciaCon.executeQuery();
        ArrayList lista =  convertirAArray(rs, "PROVEEDOR");
       
        
        disconnect();
        return lista;
    }
    
    public static boolean insert(Proveedor p) throws Exception{
        //Check que no hay otro proveedor con la misma id
        if(getByCod(p).getId() == null){
            //No existe, puedo introducir
            connect();
            
            String query = "INSERT INTO PROVEEDOR VALUES(?,?,?,?)";
            sentenciaCon = getConnection().prepareStatement(query);
            sentenciaCon.setString(1, p.getId());
            sentenciaCon.setString(2, p.getNombre());
            sentenciaCon.setString(3, p.getApellido());
            sentenciaCon.setString(4, p.getDireccion());
            
            if(sentenciaCon.executeUpdate() > 0){
                disconnect();
                return true;
            }
            else{
                disconnect();
                return false;
            }
        }
        else{
            return false;
        }
    }
    
    public static boolean delete(Proveedor p) throws Exception{
        Proveedor rProv = getByCod(p);
        
        if(rProv.getId() != null){
            //Existe, se puede borrar
            connect();
            
            String query = "DELETE FROM PROVEEDOR WHERE COD_PROV = ?";
            sentenciaCon = getConnection().prepareStatement(query);
            sentenciaCon.setString(1, p.getId());
            
            if(sentenciaCon.executeUpdate() > 0){
                disconnect();
                return true;
            }
            else{
                disconnect();
                return false;
            }
        }
        else{
            return false;
        }
    }
    
    public static boolean update(Proveedor p) throws Exception{
        connect();
        
        String query = "UPDATE PROVEEDOR SET NOMBRE = ?, APELLIDOS = ?, DIRECCION = ? WHERE COD_PROV = ?";
        sentenciaCon = getConnection().prepareStatement(query);
        sentenciaCon.setString(1, p.getNombre());
        sentenciaCon.setString(2, p.getApellido());
        sentenciaCon.setString(3, p.getDireccion());
        sentenciaCon.setString(4, p.getId());
        
        if(sentenciaCon.executeUpdate() > 0){
            disconnect();
            return true;
        }
        else{
            disconnect();
            return false;
        }
    }
}
