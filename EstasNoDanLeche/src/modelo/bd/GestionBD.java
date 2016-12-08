package modelo.bd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import modelo.clases.*;

public class GestionBD extends ConexionBD{
    
    private static Statement sentenciaSin; //Consultas sin parametros introducidos
    private static PreparedStatement sentenciaCon; //Consultas con parametros introducidos
    private static ResultSet rs;
    
    //Ejemplo
    public static ArrayList getAll() throws Exception{
        connect();
        
        sentenciaSin = getConnection().createStatement();
        rs = sentenciaSin.executeQuery("SELECT * FROM PIEZA;");
        
        ArrayList lista = convertirAArray(rs, "PIEZA");
        
        disconnect();
        return lista;
    }
    
    public static int piezasByProv(Proveedor p) throws Exception{
        connect();
        
        String query = "SELECT A.COD_PIE, A.NOMBRE, A.PRECIO, A.DESCRIPCION\n" +
                        "FROM PIEZA A, GESTION B\n" +
                        "WHERE B.COD_PIE = A.COD_PIE\n" +
                        "AND B.COD_PROV = ?;";
        sentenciaCon = getConnection().prepareStatement(query);
        sentenciaCon.setString(1, p.getId());
        
        rs = sentenciaCon.executeQuery();
        
        if(rs.next()){
            int num = Integer.parseInt(rs.getString(1));
            disconnect();
            return num;
        }
        else{
            disconnect();
            return -1;
        } 
    }
    
    public static ArrayList piezasToProv() throws Exception{
        connect();
        
        String query = "SELECT *\n" +
                        "FROM PIEZA\n" +
                        "WHERE COD_PIE IN(\n" +
                            "SELECT COD_PIE\n" +
                            "FROM GESTION\n" +
                        ");";
        sentenciaSin = getConnection().createStatement();
        rs = sentenciaSin.executeQuery(query);
        
        ArrayList lista = convertirAArray(rs, "PIEZA");
        
        disconnect();
        return lista;
    }
    
    public static int[] numCantPiezasToProy()throws Exception{
        connect();
        
        String query = "SELECT COUNT(DISTINCT(B.COD_PIE)) AS NUMERO_DE_PIEZAS, SUM(B.CANTIDAD) AS CANTIDAD_DE_PIEZAS\n" +
                        "FROM PIEZA A, GESTION B\n" +
                        "WHERE A.COD_PIE = B.COD_PIE\n" +
                        "AND A.COD_PIE IN(\n" +
                            "SELECT COD_PIE\n" +
                            "FROM GESTION\n" +
                        ");";
        
        sentenciaSin = getConnection().createStatement();
        rs = sentenciaSin.executeQuery(query);
        
        if(rs.next()){
            int[]ret = {rs.getInt(1), rs.getInt(2)};
            disconnect();
            return ret;
        }
        else{
            int[]ret = {-1,-1};
            disconnect();
            return ret;
        }
    }
    
    public static int[] numCantPiezasByProv(Proveedor p) throws Exception{
        connect();
        
        String query = "SELECT COUNT(DISTINCT(A.COD_PIE)) AS NUMERO_DE_PIEZAS, SUM(B.CANTIDAD) AS CANTIDAD_DE_PIEZAS\n" +
                        "FROM PIEZA A, GESTION B, PROVEEDOR C\n" +
                        "WHERE A.COD_PIE = B.COD_PIE\n" +
                        "AND B.COD_PROV = C.COD_PROV\n" +
                        "AND C.COD_PROV = ?;";
        sentenciaCon = getConnection().prepareStatement(query);
        sentenciaCon.setString(1, p.getId());
        
        rs = sentenciaCon.executeQuery();
        
        if(rs.next()){
            int[]ret = {rs.getInt(1), rs.getInt(2)};
            disconnect();
            return ret;
        }
        else{
            int[]ret = {-1,-1};
            disconnect();
            return ret;
        }
    }
    
    public static Pieza maxCantPieza() throws Exception{
        connect();
        
        String query = "SELECT A.COD_PIE, A.NOMBRE, A.PRECIO, A.DESCRIPCION\n" +
                        "FROM PIEZA A, GESTION B\n" +
                        "WHERE A.COD_PIE = B.COD_PIE\n" +
                        "GROUP BY A.COD_PIE\n" +
                        "HAVING SUM(B.CANTIDAD) = (\n" +
                            "SELECT MAX(T1.CONTADOR)\n" +
                            "FROM(\n" +
                                "SELECT SUM(CANTIDAD) CONTADOR\n" +
                                "FROM GESTION\n" +
                                "GROUP BY COD_PIE\n" +
                            ") AS T1\n" +
                        ");";
        
        sentenciaSin = getConnection().createStatement();
        rs = sentenciaSin.executeQuery(query);
        
        Pieza p = (Pieza) convertirAObjeto(rs, "PIEZA");
        
        disconnect();
        return p;
    }
    
    public static Pieza piezaMaxProy() throws Exception{
        connect();
        
        String query = "SELECT A.COD_PIE, A.NOMBRE, A.PRECIO, A.DESCRIPCION\n" +
                        "FROM PIEZA A, GESTION B\n" +
                        "WHERE A.COD_PIE = B.COD_PIE\n" +
                        "GROUP BY A.COD_PIE\n" +
                        "HAVING COUNT(DISTINCT(B.COD_PROY)) = (\n" +
                            "SELECT MAX(T1.CONTADOR)\n" +
                            "FROM (\n" +
                                "SELECT COUNT(DISTINCT(COD_PROY)) CONTADOR\n" +
                                "FROM GESTION\n" +
                                "GROUP BY COD_PIE\n" +
                            ") AS T1\n" +
                        ");";
        
        sentenciaSin = getConnection().createStatement();
        rs = sentenciaSin.executeQuery(query);
        
        Pieza p = (Pieza) convertirAObjeto(rs, "PIEZA");
        
        disconnect();
        return p;
    }
    
    public static Proveedor provMaxCantPiezas() throws Exception{
        connect();
        
        String query = "SELECT A.COD_PROV, A.NOMBRE, A.APELLIDOS, A.DIRECCION\n" +
                        "FROM PROVEEDOR A, GESTION B\n" +
                        "WHERE A.COD_PROV = B.COD_PROV\n" +
                        "GROUP BY A.COD_PROV\n" +
                        "HAVING SUM(B.CANTIDAD) = (\n" +
                            "SELECT MAX(T1.CONTADOR)\n" +
                            "FROM (\n" +
                                "SELECT SUM(CANTIDAD) CONTADOR\n" +
                                "FROM GESTION\n" +
                                "GROUP BY COD_PROV\n" +
                            ") AS T1\n" +
                        ");";
        
        sentenciaSin = getConnection().createStatement();
        rs = sentenciaSin.executeQuery(query);
        
        Proveedor p = (Proveedor) convertirAObjeto(rs, "PROVEEDOR");
        
        disconnect();
        return p;
    }
    
    public static ArrayList provMaxProyYCant() throws Exception{
        connect();
        
        String query = "SELECT A.COD_PROV, A.NOMBRE, A.APELLIDOS, A.DIRECCION, COUNT(DISTINCT(B.COD_PROY)) AS CONTADOR\n" +
                        "FROM PROVEEDOR A, GESTION B\n" +
                        "WHERE A.COD_PROV = B.COD_PROV\n" +
                        "GROUP BY A.COD_PROV\n" +
                        "HAVING COUNT(DISTINCT(B.COD_PROY)) = (\n" +
                            "SELECT MAX(T1.CONTADOR)\n" +
                            "FROM (\n" +
                                "SELECT COUNT(DISTINCT(COD_PROY)) CONTADOR\n" +
                                "FROM GESTION \n" +
                                "GROUP BY COD_PROV\n" +
                            ") AS T1\n" +
                        ");";

        sentenciaSin = getConnection().createStatement();
        rs = sentenciaSin.executeQuery(query);
        
        //Tratamiento de datos
        if(rs.next()){
            Proveedor p = new Proveedor(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
            int num = rs.getInt(5);
            ArrayList al = new ArrayList();
            al.add(p);
            al.add(num);
            
            disconnect();
            return al;
        }
        else{
            disconnect();
            return null;
        }
    }
    
    public static ArrayList provMaxPiezasYCant() throws Exception{
        connect();
        
        String query = "SELECT A.COD_PROV, A.NOMBRE, A.APELLIDOS, A.DIRECCION, SUM(B.CANTIDAD) AS CONTADOR\n" +
                        "FROM PROVEEDOR A, GESTION B\n" +
                        "WHERE A.COD_PROV = B.COD_PROV\n" +
                        "GROUP BY A.COD_PROV\n" +
                        "HAVING SUM(B.CANTIDAD) = (\n" +
                            "SELECT MAX(T1.CONTADOR)\n" +
                            "FROM (\n" +
                                "SELECT SUM(CANTIDAD) CONTADOR\n" +
                                "FROM GESTION\n" +
                                "GROUP BY COD_PROV\n" +
                            ")AS T1\n" +
                        ");";
        
        sentenciaSin = getConnection().createStatement();
        rs = sentenciaSin.executeQuery(query);
        
        //Tratamiento de datos
        if(rs.next()){
            Proveedor p = new Proveedor(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
            int num = rs.getInt(5);
            ArrayList al = new ArrayList();
            al.add(p);
            al.add(num);
            
            disconnect();
            return al;
        }
        else{
            disconnect();
            return null;
        }
    }
}