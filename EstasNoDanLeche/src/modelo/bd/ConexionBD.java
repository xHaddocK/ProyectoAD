package modelo.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modelo.clases.*;

public class ConexionBD {
	
    private static Connection c;
    
    protected static void connect() throws Exception{ 
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        String url="jdbc:mysql://localhost:3306/"+"proyectoad";
        c = DriverManager.getConnection(url,"root","");
    }
	
    protected static boolean disconnect(){
        try{
            c.close();
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
	
    protected static Connection getConnection(){
        return c;
    }
	
    protected static ArrayList convertirAArray(ResultSet rs, String clase) throws SQLException{
        ArrayList lista = new ArrayList();
        
        switch(clase){
            case "PIEZA":
                while(rs.next()){
                    lista.add(new Pieza(rs.getString(1), rs.getString(2), rs.getFloat(3),rs.getString(4)));
                }
                break;
            case "PROVEEDOR":
                while(rs.next()){
                    lista.add(new Proveedor(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
                }
                break;
            case "PROYECTO":
                while(rs.next()){
                    lista.add(new Proyecto(rs.getString(1), rs.getString(2), rs.getString(3)));
                }
                break;
            default:
                System.out.println("Introduce el nombre de la clase correctamente en el codigo");
        }
        
        return lista;
    }
    
    protected static Object convertirAObjeto(ResultSet rs, String clase) throws SQLException{
        
        Object objeto = null;
        
        switch(clase){
            case "PIEZA":
                if(rs.next())
                    objeto = new Pieza(rs.getString(1), rs.getString(2), rs.getFloat(3),rs.getString(4));
                else
                    objeto = new Pieza();
                break;
            case "PROVEEDOR":
                if(rs.next())
                    objeto = new Proveedor(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
                else
                    objeto = new Proveedor();
                break;
            case "PROYECTO":
                if(rs.next())
                    objeto = new Proyecto(rs.getString(1), rs.getString(2), rs.getString(3));
                else
                    objeto = new Proyecto();
                break;
            default:
                System.out.println("Escribe correctamente la clase en el codigo");
        }
        
        return objeto;
    }
	
}