package modelo.bd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import modelo.clases.Pieza;

public class PiezaBD extends ConexionBD{
    
    private static Statement sentenciaSin; //Consultas sin parametros introducidos
    private static PreparedStatement sentenciaCon; //Consultas con parametros introducidos
    private static ResultSet rs;
    
    public static ArrayList getAll() throws Exception{
        connect();
        
        sentenciaSin = getConnection().createStatement();
        rs = sentenciaSin.executeQuery("SELECT * FROM PIEZA;");
        
        ArrayList lista = convertirAArray(rs, "PIEZA");
        
        disconnect();
        return lista;
    }
    
    public static Pieza getByCod(Pieza p) throws SQLException, Exception{
        connect();
        
        String query = "SELECT * FROM PIEZA WHERE COD_PIE = ?;";
        sentenciaCon = getConnection().prepareStatement(query);
        sentenciaCon.setString(1, p.getId());
        
        rs = sentenciaCon.executeQuery();
        
        Pieza p2 = (Pieza) convertirAObjeto(rs, "PIEZA");
        
        disconnect();
        return p2;
    }
    
    public static ArrayList getByCodLike(Pieza p) throws Exception{
        connect();
        
        String query = "SELECT * FROM PIEZA WHERE COD_PIE LIKE ?";
        sentenciaCon = getConnection().prepareStatement(query);
        sentenciaCon.setString(1,"%" + p.getId() + "%");
        
        rs = sentenciaCon.executeQuery();
        
        ArrayList<Pieza>lista = convertirAArray(rs, "PIEZA");
        
        disconnect();
        return lista;
    }
    
    public static ArrayList getByNombreLike(Pieza p) throws SQLException, Exception{
        //Puede haber dos nombres iguales por lo que retorno una lista 
        //con todos los que tengan ese nombre
        connect();
        
        String query = "SELECT * FROM PIEZA WHERE NOMBRE LIKE ?;";
        sentenciaCon = getConnection().prepareStatement(query);
        sentenciaCon.setString(1, "%" + p.getNombre() + "%");
        
        rs = sentenciaCon.executeQuery();
        
        ArrayList lista = convertirAArray(rs, "PIEZA");
        
        disconnect();
        return lista;
    }
    
    public static boolean insert(Pieza p) throws Exception{
        //Check que no hay otra pieza con la misma id
        if(getByCod(p).getId() == null){
            //No existe, puedo introducir
            connect();
            
            String query = "INSERT INTO PIEZA VALUES(?,?,?,?)";
            sentenciaCon = getConnection().prepareStatement(query);
            sentenciaCon.setString(1, p.getId());
            sentenciaCon.setString(2, p.getNombre());
            sentenciaCon.setFloat(3, Float.parseFloat(String.valueOf(p.getPrecio())));
            sentenciaCon.setString(4, p.getDescripcion());
            
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
    
    public static boolean delete(Pieza p) throws Exception{
        Pieza rPieza = getByCod(p);
        
        if(rPieza.getId() != null){
            //Existe, se puede borrar
            connect();
            
            String query = "DELETE FROM PIEZA WHERE COD_PIE = ?";
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
    
    public static boolean update(Pieza p) throws Exception{
        connect();
        
        String query = "UPDATE PIEZA SET NOMBRE = ?, PRECIO = ?, DESCRIPCION = ? WHERE COD_PIE = ?";
        sentenciaCon = getConnection().prepareStatement(query);
        sentenciaCon.setString(1, p.getNombre());
        sentenciaCon.setString(2, String.valueOf(p.getPrecio()));
        sentenciaCon.setString(3, p.getDescripcion());
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
