package modelo.clases;

import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.table.TableModel;
import modelo.bd.GestionBD;

public class Gestion {
    private Proveedor prov;
    private Pieza pieza;
    private Proyecto proy;
    private int cantidad;

    public Gestion(Proveedor prov, Pieza pieza, Proyecto proy, int cantidad) {
        this.prov = prov;
        this.pieza = pieza;
        this.proy = proy;
        this.cantidad = cantidad;
    }

    public Gestion() {
    }

//BD METHODS
    
    public static ArrayList getAll() throws Exception{
        return GestionBD.getAll();
    }
    
    public static TableModel getAllTableModel() throws Exception{
        return GestionBD.getAllTableModel();
    }
    
    public static boolean insert(Gestion g) throws Exception{
        return GestionBD.insert(g);
    }
    
    public static Gestion getGestion(Gestion g) throws Exception{
        return GestionBD.getGestion(g);
    }
    
    public static boolean update(Gestion g) throws Exception{
        return GestionBD.update(g);
    }
    
    public static boolean delete(Gestion g) throws Exception{
        return GestionBD.delete(g);
    }
    
    //[0] piezas, [1] proyectos
    public static int[] PiezasProyectosByProv(Proveedor p) throws Exception{
        return GestionBD.PiezasProyectosByProv(p);
    }
    
    //[0] proyectos, [1] proveedores y [2] Cantidad
    public static int[] ProyProvCantByPieza(Pieza p) throws Exception{
        return GestionBD.ProyProvCantByPieza(p);
    }
    
    //MANDA TABLEMODEL, PORQUE NO SE VA A HACER NINGUNA OTRA GESTION
    public static TableModel piezasByProv(Proveedor p) throws Exception{
        return GestionBD.piezasByProv(p);
    }

    //Obtener las piezas suministradas a proyectos
    public static TableModel piezasToProy() throws Exception{
        return GestionBD.piezasToProy();
    }
    
    //RESUMENES ESTADISTICOS VV
    //Nº PIEZAS Y CANTIDAD DE PIEZAS SUMINISTRADAS EN PROYECTOS
        //Devuelve un array de dos posiciones, en la 0 está el número de piezas que han sido suministradas a proyectos y en la 1 está la cantidad total de piezas 
        //{-1,-1} si hay algún error
    public static int[] numCantPiezasToProy() throws Exception{
        return GestionBD.numCantPiezasToProy();
    }
    
    //Nº PIEZAS Y CANTIDAD DE PIEZAS SUMINISTRADAS POR PROVEEDOR
    public static int[] numCantPiezasByProv(Proveedor p) throws Exception{
        return GestionBD.numCantPiezasByProv(p);
    }
    
    //PIEZA DE LA QUE SE HA SUMINISTRADO LA MAYOR CANTIDAD
    public static Pieza maxCantPieza() throws Exception{
        return GestionBD.maxCantPieza();
    }
    
    //PIEZA QUE SE HA SUMINISTRADO A MÁS PROYECTOS
    public static Pieza piezaMaxProy() throws Exception{
        return GestionBD.piezaMaxProy();
    }
    
    //PROVEEDOR QUE HA SUMINISTRADO MÁS CANTIDAD DE PIEZAS
    public static Proveedor provMaxCantPiezas() throws Exception{
        return GestionBD.provMaxCantPiezas();
    }
    
    //PROVEEDOR QUE HA SUMINISTRADO A MÁS PROYECTOS, Y NÚMERO DE PROYECTOS 
        //Array de Object, 0 es un proveedor, el 1 un objeto Integer
    public static Object[] provMaxProyYCant() throws Exception{
        return GestionBD.provMaxProyYCant();
    }
    
    //PROVEEDOR QUE HA SUMINISTRADO MAS PIEZAS Y NÚMERO DE PIEZAS
        //Arraylist posicion 0 el proveedor, posicion 1 un int con la CANTIDAD de piezas
    public static Object[] provMaxPiezasYCant() throws Exception{
        return GestionBD.provMaxPiezasYCant();
    }
    
    
//GETTER AND SETTER
    public Proveedor getProv() {
        return prov;
    }

    public void setProv(Proveedor prov) {
        this.prov = prov;
    }

    public Pieza getPieza() {
        return pieza;
    }

    public void setPieza(Pieza pieza) {
        this.pieza = pieza;
    }

    public Proyecto getProy() {
        return proy;
    }

    public void setProy(Proyecto proy) {
        this.proy = proy;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
