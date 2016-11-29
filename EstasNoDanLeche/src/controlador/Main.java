/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import jdk.nashorn.internal.scripts.JO;
import modelo.clases.*;

/**
 *
 * @author Marcos
 */
public class Main {
    
    public static void main(String[]args) throws Exception{
        Pieza p;
        
        do{
            String id = JOptionPane.showInputDialog("ID DE LA PIEZA A MODIFICAR");
            p = new Pieza();
            p.setId(id);
            p = Pieza.getByCod(p);
        }
        while(p.getId() == null);
        
        JOptionPane.showMessageDialog(null, p.getId() + "\n" + p.getNombre() + "\n" + p.getDescripcion() + "\n" + p.getPrecio());
        
        String nom = JOptionPane.showInputDialog("Nuevo nombre");
        String des = JOptionPane.showInputDialog("Nueva descripcion");
        double pre = Double.parseDouble(JOptionPane.showInputDialog("Nuevo precio"));
        
        p.setNombre(nom);
        p.setDescripcion(des);
        p.setPrecio(pre);
        
        if(Pieza.update(p))
            JOptionPane.showMessageDialog(null, "UPDATEADO CORRECTAMENTE");
        else
            JOptionPane.showMessageDialog(null, "NO UPDATEADO");
        
    }
    
}
