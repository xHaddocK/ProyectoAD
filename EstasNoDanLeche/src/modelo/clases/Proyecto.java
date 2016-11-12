package modelo.clases;

import java.util.ArrayList;
import modelo.bd.ProyectoBD;

public class Proyecto {
    private String id, nombre, ciudad;

    public Proyecto(String id, String nombre, String ciudad) {
        this.id = id;
        this.nombre = nombre;
        this.ciudad = ciudad;
    }

    public Proyecto() {
    }

//BD METHODS
            
    public static ArrayList getAll() throws Exception{
        return ProyectoBD.getAll();
    }
    
    public static Proyecto getByCod(Proyecto p) throws Exception{
        return ProyectoBD.getByCod(p);
    }
    
    public static ArrayList getByNombre(Proyecto p) throws Exception{
        return ProyectoBD.getByNombre(p);
    }
    
    public static boolean insert(Proyecto p) throws Exception{
        return ProyectoBD.insert(p);
    }
    
    public static boolean delete(Proyecto p) throws Exception{
        return ProyectoBD.delete(p);
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

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
    
    
}
