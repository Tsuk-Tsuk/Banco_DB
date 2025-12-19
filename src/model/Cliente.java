package model;

public class Cliente {

    private String usuario;
    private String nombre;
    private double saldo;

    public Cliente(String usuario, String nombre, double saldo) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.saldo = saldo;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}
