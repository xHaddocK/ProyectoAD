package modelo.clases;

import java.util.ArrayList;
import modelo.bd.PiezaBD;

public class Pieza {
    private String id, nombre, descripcion;
    private double precio;

    public Pieza(String id, String nombre, double precio, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }
    
    public Pieza() {
    }
    
//BD METHODS

    public static ArrayList getAll() throws Exception{
        return PiezaBD.getAll();
    }
    
    public static Pieza getByCod(Pieza p) throws Exception{
        return PiezaBD.getByCod(p);
    }
    
    public static ArrayList getByNombre(Pieza p) throws Exception{
        return PiezaBD.getByNombre(p);
    }
    
    public static boolean insert(Pieza p) throws Exception{
        return PiezaBD.insert(p);
    }
    
    public static boolean delete(Pieza p) throws Exception{
        return PiezaBD.delete(p);
    }
    
//GETTER AND SETTER
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    
}
