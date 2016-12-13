package modelo.bd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.table.TableModel;
import modelo.clases.*;
import net.proteanit.sql.DbUtils;

public class GestionBD extends ConexionBD{
    
    private static Statement sentenciaSin; //Consultas sin parametros introducidos
    private static PreparedStatement sentenciaCon; //Consultas con parametros introducidos
    private static ResultSet rs;
    
    public static ArrayList getAll() throws Exception{
        connect();
        
        sentenciaSin = getConnection().createStatement();
        rs = sentenciaSin.executeQuery("SELECT * FROM GESTION");
        
        ArrayList lista = convertirAArray(rs, "GESTION");
        
        disconnect();
        return lista;
    }
    
    public static TableModel getAllTableModel() throws Exception{
        connect();
        
        sentenciaSin = getConnection().createStatement();
        rs = sentenciaSin.executeQuery("SELECT * FROM GESTION");
        TableModel tm= DbUtils.resultSetToTableModel(rs);
        
        disconnect();
        return tm;
    }
    
    public static boolean insert(Gestion g) throws Exception{
        connect();
        
        String query = "INSERT INTO `proyectoad`.`gestion` (`COD_PROV`, `COD_PIE`, `COD_PROY`, `CANTIDAD`) VALUES (?, ?, ?, ?);";
        sentenciaCon = getConnection().prepareStatement(query);
        sentenciaCon.setString(1, g.getProv().getId());
        sentenciaCon.setString(2, g.getPieza().getId());
        sentenciaCon.setString(3, g.getProy().getId());
        sentenciaCon.setInt(4, g.getCantidad());
        
        if(sentenciaCon.executeUpdate() > 0){
            disconnect();
            return true;
        }
        else{
            disconnect();
            return false;
        }
    }
    
    public static Gestion getGestion(Gestion g) throws Exception{
        connect();
        
        String query = "SELECT * FROM GESTION WHERE COD_PROV = ? AND COD_PIE = ? AND COD_PROY = ?;";
        sentenciaCon = getConnection().prepareStatement(query);
        sentenciaCon.setString(1, g.getProv().getId());
        sentenciaCon.setString(2, g.getPieza().getId());
        sentenciaCon.setString(3, g.getProy().getId());
        
        rs = sentenciaCon.executeQuery();
        
        if(rs.next()){
            //Hago la gestion
            g.setCantidad(rs.getInt(4));
            disconnect();
            return g;
        }
        else{
            //Mando gestion vacía
            g = null;
            disconnect();
            return g;
        }           
    }
    
    public static boolean update(Gestion g) throws Exception{
        connect();
        
        String query = "UPDATE GESTION SET CANTIDAD = ? WHERE COD_PROV = ? AND COD_PIE = ? AND COD_PROY = ?;";
        sentenciaCon = getConnection().prepareStatement(query);
        sentenciaCon.setInt(1, g.getCantidad());
        sentenciaCon.setString(2, g.getProv().getId());
        sentenciaCon.setString(3, g.getPieza().getId());
        sentenciaCon.setString(4, g.getProy().getId());
        
        if(sentenciaCon.executeUpdate() >= 1){
            disconnect();
            return true;
        }
        else{
            disconnect();
            return false;
        }
    }
    
    public static boolean delete(Gestion g) throws Exception{
        connect();
        
        String query = "DELETE FROM GESTION WHERE COD_PROV = ? AND COD_PIE = ? AND COD_PROY = ?;";
        sentenciaCon = getConnection().prepareStatement(query);
        sentenciaCon.setString(1, g.getProv().getId());
        sentenciaCon.setString(2, g.getPieza().getId());
        sentenciaCon.setString(3, g.getProy().getId());
        
        if(sentenciaCon.executeUpdate() >= 1){
            disconnect();
            return true;
        }
        else{
            disconnect();
            return false;
        }
    }
    
    public static int[] PiezasProyectosByProv(Proveedor p) throws Exception{
        connect();
        
        String query = "SELECT COUNT(DISTINCT(COD_PIE)), COUNT(DISTINCT(COD_PROY))\n" +
                        "FROM GESTION\n" +
                        "WHERE COD_PROV = ?;";
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
    
    public static int[] ProyProvCantByPieza(Pieza p) throws Exception{
        connect();
        
        String query = "SELECT COUNT(DISTINCT(COD_PROY)), COUNT(DISTINCT(COD_PROV)), SUM(CANTIDAD)\n" +
                        "FROM GESTION\n" +
                        "WHERE COD_PIE = ?;";
        
        sentenciaCon = getConnection().prepareStatement(query);
        sentenciaCon.setString(1, p.getId());
        rs = sentenciaCon.executeQuery();
        
        if(rs.next()){
            int[]ret = {rs.getInt(1), rs.getInt(2), rs.getInt(3)};
            disconnect();
            return ret;
        }
        else{
            int[]ret = {-1,-1,-1};
            disconnect();
            return ret;
        }
    }
    
    public static TableModel piezasByProv(Proveedor p) throws Exception{
        connect();
        
        String query = "SELECT A.COD_PIE, A.NOMBRE, A.PRECIO, A.DESCRIPCION\n" +
                        "FROM PIEZA A, GESTION B\n" +
                        "WHERE B.COD_PIE = A.COD_PIE\n" +
                        "AND B.COD_PROV = ?;";
        sentenciaCon = getConnection().prepareStatement(query);
        sentenciaCon.setString(1, p.getId());
        
        rs = sentenciaCon.executeQuery();
        
        TableModel tm= DbUtils.resultSetToTableModel(rs);
        
        disconnect();
        return tm;
    }
    
    public static TableModel piezasToProy() throws Exception{
        connect();
        
        String query = "SELECT *\n" +
                        "FROM PIEZA\n" +
                        "WHERE COD_PIE IN(\n" +
                            "SELECT COD_PIE\n" +
                            "FROM GESTION\n" +
                        ");";
        sentenciaSin = getConnection().createStatement();
        rs = sentenciaSin.executeQuery(query);
        
        TableModel tm= DbUtils.resultSetToTableModel(rs);
        
        disconnect();
        return tm;
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
    
    public static Object[] provMaxProyYCant() throws Exception{
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
            
            Object[]list = new Object[2];
            list[0] = p;
            list[1] = rs.getInt(5);
            
            disconnect();
            return list;
        }
        else{
            Object[]list = new Object[2];
            list[0] = new Proveedor();
            list[1] = -1;
            
            disconnect();
            return list;
        }
    }
    
    //Esto de momento está to mal niggi
    public static Object[] provMaxPiezasYCant() throws Exception{
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
            Object[]lista = new Object[2];
            lista[0] = p;
            lista[1] = num;
            
            disconnect();
            return lista;
        }
        else{
            Object[]lista = new Object[2];
            lista[0] = new Proveedor();
            lista[1] = -1;
            
            disconnect();
            return null;
        }
    }
}
