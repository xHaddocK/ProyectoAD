package modelo.bd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import modelo.clases.Proyecto;

public class ProyectoBD extends ConexionBD{
    
    private static Statement sentenciaSin;
    private static PreparedStatement sentenciaCon;
    private static ResultSet rs;
    
    public static ArrayList getAll() throws Exception{
        connect();
        
        sentenciaSin = getConnection().createStatement();
        rs = sentenciaSin.executeQuery("SELECT * FROM PROYECTO");
        
        ArrayList lista = convertirAArray(rs, "PROYECTO");
        
        disconnect();
        return lista;
    }
    
    public static Proyecto getByCod(Proyecto p) throws SQLException, Exception{
        connect();
        
        String query = "SELECT * FROM PROYECTO WHERE COD_PROY = ?;";
        sentenciaCon = getConnection().prepareStatement(query);
        sentenciaCon.setString(1, p.getId());
        
        rs = sentenciaCon.executeQuery();
        
        Proyecto p2 = (Proyecto) convertirAObjeto(rs, "PROYECTO");
        
        disconnect();
        return p2;
    }
    
    public static ArrayList getByNombre(Proyecto p) throws SQLException, Exception{
        //Puede haber dos nombres iguales por lo que retorno una lista
        //con todos los que tengan ese nombre
        connect();
        
        String query = "SELECT * FROM PROYECTO WHERE NOMBRE = ?;";
        sentenciaCon = getConnection().prepareStatement(query);
        sentenciaCon.setString(1, p.getNombre());
        
        rs = sentenciaCon.executeQuery();
        
        ArrayList lista = convertirAArray(rs, "PROYECTO");
        
        disconnect();
        return lista;
    }
    
    public static boolean insert(Proyecto p) throws Exception{
        //Check que no hay otro proyecto con la misma id
        if(getByCod(p).getId() == null){
            //no existe, puedo introducir
            connect();
            
            String query = "INSERT INTO PROYECTO VALUES(?,?,?)";
            sentenciaCon = getConnection().prepareStatement(query);
            sentenciaCon.setString(1, p.getId());
            sentenciaCon.setString(2, p.getNombre());
            sentenciaCon.setString(3, p.getCiudad());
            
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
    
    public static boolean delete(Proyecto p) throws Exception{
        Proyecto rProy = getByCod(p);
        
        if(rProy.getId() != null){
            //Existe, se puede borrar
            connect();
            
            String query = "DELETE FROM PROYECTO WHERE COD_PROY = ?";
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
}
