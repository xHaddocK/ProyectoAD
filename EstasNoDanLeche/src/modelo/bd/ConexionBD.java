package modelo.bd;

import java.io.BufferedReader;
import java.io.FileReader;
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
    
    public static void createDatabase() throws Exception{
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        String url="jdbc:mysql://localhost:3306/";
        c = DriverManager.getConnection(url,"root","");
        
        //Ruta relativa YA
        ScriptRunner runner = new ScriptRunner(c, false, false);
        //runner.runScript(new BufferedReader(new FileReader("C:\\Users\\Marcos\\Documents\\GitHub\\ProyectoAD\\EstasNoDanLeche\\src\\resources\\scriptcreacion.sql")));
        runner.runScript(new BufferedReader(new FileReader("src\\resources\\scriptcreacion.sql")));
        
        disconnect();
    }
    
    public static void dropDatabase() throws Exception{
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        String url="jdbc:mysql://localhost:3306/";
        c = DriverManager.getConnection(url,"root","");
        
        //Ruta relativa YA
        ScriptRunner runner = new ScriptRunner(c, false, false);
        //runner.runScript(new BufferedReader(new FileReader("C:\\Users\\Marcos\\Documents\\GitHub\\ProyectoAD\\EstasNoDanLeche\\src\\resources\\scriptborrar.sql")));
        runner.runScript(new BufferedReader(new FileReader("src\\resources\\scriptborrar.sql")));
        
        disconnect();
    }
	
    public static Connection getConnection(){
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
            case "GESTION":
                while(rs.next()){
                    Proveedor prov = new Proveedor(rs.getString(1), null, null, null);
                    Pieza pie = new Pieza(rs.getString(2), null, 0.0, null);
                    Proyecto proy = new Proyecto(rs.getString(3), null, null);
                    lista.add(new Gestion(prov, pie, proy, rs.getInt(4)));
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