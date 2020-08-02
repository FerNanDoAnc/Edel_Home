package com.example.fera1999.edelh.clases;

public class UsuarioHabitacion {

    private int idhabitacion;
    private int idusuario;
    private String usuario;

    public UsuarioHabitacion(){

    }

    public UsuarioHabitacion(int idhabitacion, int idusuario, String usuario) {
        this.idhabitacion = idhabitacion;
        this.idusuario = idusuario;
        this.usuario = usuario;
    }

    public int getIdhabitacion() {
        return idhabitacion;
    }

    public void setIdhabitacion(int idhabitacion) {
        this.idhabitacion = idhabitacion;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
