package com.example.fera1999.edelh.clases;

public class Usuario {
private int id;
private String nombre;
private String email;
private String clave;
private int admin;
private int idgrupo;
private String lastlogin;


    public Usuario (){

    }

    public Usuario(int id, String nombre, String lastlogin){
        this.id = id;
        this.nombre = nombre;
        this.lastlogin = lastlogin;
    }

    public Usuario( String nombre, String lastlogin){
        this.nombre = nombre;
        this.lastlogin = lastlogin;
    }

    public Usuario(int id, String nombre, String email, String clave, int admin, int idgrupo, String lastlogin) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.clave = clave;
        this.admin = admin;
        this.idgrupo = idgrupo;
        this.lastlogin = lastlogin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    public int getIdgrupo() {
        return idgrupo;
    }

    public void setIdgrupo(int idgrupo) {
        this.idgrupo = idgrupo;
    }

    public String getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(String lastlogin) {
        this.lastlogin = lastlogin;
    }
}
