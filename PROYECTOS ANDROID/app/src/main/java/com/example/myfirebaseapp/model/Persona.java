package com.example.myfirebaseapp.model;

public class Persona {

    private String uid;
    private String Nombre;
    private String Apellidos;
    private String Correo;
    private String Password;

    public Persona() {


    }

    public Persona(String uid,String nombre,String apellidos,String Correo,String Password) {
this.uid=uid;
this.Nombre=nombre;
this.Apellidos=apellidos;
this.Correo=Correo;
this.Password=Password;
    }


//el metodo toString devuelve el resultado en el listado
    @Override
    public String toString() {
        return getNombre();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String apellidos) {
        Apellidos = apellidos;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }
}
