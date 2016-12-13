/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import jdk.nashorn.internal.scripts.JO;
import modelo.bd.ConexionBD;
import modelo.bd.GestionBD;
import modelo.clases.*;
import vista.Home;

/**
 *
 * @author Marcos
 */
public class Main {
    
    
    public static void main(String[]args) throws Exception{
        
        Home h = new Home();
        h.setVisible(true);
       
        
        
        
        
        /*ArrayList<Gestion>lista = Gestion.getAll();
        
        for(Gestion g : lista){
            System.out.println(Proveedor.getByCod(g.getProv()).getNombre() + " " + Pieza.getByCod(g.getPieza()).getNombre() + " " + Proyecto.getByCod(g.getProy()).getNombre() + " " + g.getCantidad());
        }
        
        Proveedor p = new Proveedor("B00001", null, null, null);
        Proyecto proy = new Proyecto("C00003", null, null);
        Pieza pie = new Pieza("A00001", null, 0.0, null);
        
        Gestion g = new Gestion(p, pie, proy, 15);
        
        if(Gestion.getGestion(g).getProv().getId() != null){
            if(Gestion.update(g)){
                System.out.println("Updateado");
            }
            else{
                System.out.println("no updateado");
            }
            
            if(Gestion.delete(g)){
                System.out.println("Borrado");
            }
            else{
                System.out.println("No borrado");
            }
            
            
            g.setCantidad(1);
            if(Gestion.insert(g)){
                System.out.println("insertado");
            }
            else{
                System.out.println("no insertado");
            }
        }
        else{
            System.out.println("no existe esa gestion");
        }
        
        Proveedor p = new Proveedor("B000", null, null, null);
        Proyecto proy = new Proyecto("C00", null, null);
        Pieza pie = new Pieza("A000", null, 0.0, null);
        
        ArrayList<Proveedor> l =  Proveedor.getByCodLike(p);
        ArrayList<Proyecto> l2 = Proyecto.getByCodLike(proy);
        ArrayList<Pieza> l3 = Pieza.getByCodLike(pie);
        
        for(Proveedor puti : l){
            System.out.println(puti.getId());
        }
        
        for(Proyecto puti2 : l2){
            System.out.println(puti2.getId());
        }
        
        for(Pieza puti3 : l3){
            System.out.println(puti3.getId());
        }
                
        Proveedor p = new Proveedor(null, null, null, "DIR");
        
        ArrayList<Proveedor>lista = Proveedor.getByDireccion(p);
        
        for(Proveedor p2 : lista){
            System.out.println(p2.getId());
        }
        
        Proveedor proveedor = new Proveedor("B00002", null, null, null);
        ArrayList<Pieza> l = GestionBD.piezasByProv(proveedor);
        System.out.println("Pieza por proveedor " + proveedor.getId());
        for(Pieza pie : l){
            System.out.println(pie.getId());
        }
        
        //No te olvides de poner el tipo al arraylist, hay que castearlo (<Pieza>)
        l = Gestion.piezasToProv();
        System.out.println("Piezas asignadas a proyectos");
        for(Pieza pie : l){
            System.out.println(pie.getId());
        }
        
        int[]nums = new int[2];
        //en [0] esta el numero de piezas, en [1] está la cantidad
        nums = Gestion.numCantPiezasToProy();
        System.out.println("NUMERO: " + nums[0] + " CANTIDAD: " + nums[1]);
        
        //en [0] esta el numero de piezas, en [1] está la cantidad
        nums = Gestion.numCantPiezasByProv(proveedor);
        System.out.println("NUMERO: " + nums[0] + " CANTIDAD: " + nums[1]);
        
        Pieza pieza = Gestion.maxCantPieza();
        System.out.println("PIEZA CON MAS CANTIDAD " + pieza.getId() + " " + pieza.getNombre());
        
        pieza = Gestion.piezaMaxProy();
        System.out.println("PIEZA EN MAS PROYECTOS " + pieza.getId() + " " + pieza.getNombre());
        
        proveedor = Gestion.provMaxCantPiezas();
        System.out.println("PROVEEDOR CON MAS CANTIDAD DE PIEZAS " + proveedor.getId() + " " + proveedor.getNombre());
        
        //Esto es un poco más especialito...
        //Primero create una lista para retornar lo del sql, luego IMPORTANTISIMO, tienes que crear una variable de proveedor parseando el indice 0
        //Luego ya el lista[1] en este caso está done.
        Object[]lista = Gestion.provMaxProyYCant();
        proveedor = (Proveedor) lista[0];
        System.out.println("PROVEEDOR QUE HA SUMINISTRADO A MAS PROYECTOS: " + proveedor.getNombre() + ". CON " + lista[1] + " PROYECTOS DIFERENTES ASIGNADOS.");
        
        //Igual para este ----  LOL LA FLECHICA OMG
        //                   |
        //                   |
        //                   v
        lista = Gestion.provMaxPiezasYCant();
        proveedor = (Proveedor) lista[0];
        System.out.println("PROVEEDOR QUE MAS PIEZAS HA SUMINISTRADO: " + proveedor.getNombre() + ". CON " + lista[1] + " PIEZAS ENTREGADAS.");
    
        //PROCESO PARA AÑADIR UNA GESTIÓN
        //CREAR 3 OBJETOS, PROV, PIE Y PROY, Y SUMARLE LA CANTIDAD, EN TU CASO SEGURAMENTE TENGAS QUE MIRAR QUE PROVEEDOR ESTÁ SELECCIONADO EN UNA COMBO BOX
        //O ALGO ASÍ, PERO EN MI CASO YO YA SE ID'S Y TAL PARA PODER INSERTARLO, AUNQUE YO CREO UN OBJETO CON SOLO LA ID, TU SEGURAMENTE TENGAS LA PIEZA ENTERA
        //AL HABER HECHO UN GETALL O UN GETBYID, ASIQUE DA IGUAL, PORQUE YO VOY A PETARLO A NULOS, PORQUE NO HACEN FALTA EL RESTO DE DATOS
        proveedor = new Proveedor("B00001", null, null, null);
        pieza = new Pieza("A00003", null, 0.0, null);
        Proyecto proyecto = new Proyecto("C00001", null, null);
        
        //Creas gestion
        Gestion g = new Gestion(proveedor, pieza, proyecto, 20);
        if(Gestion.insert(g)){
            System.out.println("Gestion insertada correctamente");
        }*/
    }
    
}
