package modelo.clases;

import java.util.ArrayList;
import modelo.bd.ProveedorBD;

public class Proveedor {
    private String id, nombre, apellido, direccion;

    public Proveedor(String id, String nombre, String apellido, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
    }

    public Proveedor() {
    }
    
//BD METHODS
    
    public static ArrayList getAll() throws Exception{
        return ProveedorBD.getAll();
    }
    
    public static Proveedor getByCod(Proveedor p) throws Exception{
        return ProveedorBD.getByCod(p);
    }
    
    public static ArrayList getByCodLike(Proveedor p) throws Exception{
        return ProveedorBD.getByCodLike(p);
    }
    
    public static Proveedor getByNombre(Proveedor p) throws Exception{
        return ProveedorBD.getByNombre(p);
    }
    
    public static ArrayList getByNombreLike(Proveedor p) throws Exception{
        return ProveedorBD.getByNombreLike(p);
    }
    
    public static ArrayList getByDireccionLike(Proveedor p) throws Exception{
        return ProveedorBD.getByDireccionLike(p);
    }
    
    public static Proveedor getByDireccion(Proveedor p) throws Exception{
        return ProveedorBD.getByDireccion(p);
    }
    
    public static boolean insert(Proveedor p) throws Exception{
        return ProveedorBD.insert(p);
    }
    
    public static boolean delete(Proveedor p) throws Exception{
        return ProveedorBD.delete(p);
    }
    
    public static boolean update(Proveedor p) throws Exception{
        return ProveedorBD.update(p);
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    
}
