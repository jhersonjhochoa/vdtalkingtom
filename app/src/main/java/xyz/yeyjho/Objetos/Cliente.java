package xyz.yeyjho.Objetos;

public class Cliente {
    public String nombre;
    public String documento;
    private int edad;

    public Cliente(){};

    public Cliente(String nombre, String documento){
        this.nombre = nombre;
        this.documento = documento;
    };

    public Cliente(String nombre, String documento, int edad){
        this.nombre = nombre;
        this.documento = documento;
        this.edad = edad;
    };

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}
