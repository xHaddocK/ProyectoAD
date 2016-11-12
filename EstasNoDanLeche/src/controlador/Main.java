/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.clases.*;

/**
 *
 * @author Marcos
 */
public class Main {
    
    public static void main(String[]args) throws Exception{
        ArrayList<Proyecto> list = Proyecto.getAll();
        
        Proyecto p = new Proyecto("C00005", "PROYECTOPRUEBA", "ELOJETEMORENO");
        
        if(Proyecto.insert(p)){
            System.out.println("Introducido");
        }
        else{
            System.out.println("No se ha podido introducir, que ecatombe");
        }
        
        System.out.println(list.size());
        
        list = Proyecto.getAll();
        
        System.out.println(list.size());
        
        while(true){
            Proyecto p2 = new Proyecto();
            p2.setId(JOptionPane.showInputDialog("Introduce el codigo del producto que quieras borrar"));
            
            if(Proyecto.delete(p2)){
                System.out.println("Borrao");
            }
            else{
                System.out.println("No borrao");
            }
        }
        
    }
    
}
