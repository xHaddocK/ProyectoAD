package modelo.clases;

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
