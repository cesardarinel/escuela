/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cesardarinel.entidades;

import java.sql.Date;



/**
 *
 * @author cesar
 */
public class Profesor {
    String Cedula;
    String Nombre;
    String Apellidos;
    String FechadeIngreso;
    String Titulo;
    String CuentaBancaria;
    String EmpleadodeCarrera;
    String Asignadoa;
    
    
    public String getCedula() {
        return Cedula;
    }

    public void setCedula(String Cedula) {
        this.Cedula = Cedula;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String Apellidos) {
        this.Apellidos = Apellidos;
    }

    public String getFechadeIngreso() {
        return FechadeIngreso;
    }

    public void setFechadeIngreso(String FechadeIngreso) {
        this.FechadeIngreso = FechadeIngreso;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String Titulo) {
        this.Titulo = Titulo;
    }

    public String getCuentaBancaria() {
        return CuentaBancaria;
    }

    public void setCuentaBancaria(String CuentaBancaria) {
        this.CuentaBancaria = CuentaBancaria;
    }

    public String getEmpleadodeCarrera() {
        return EmpleadodeCarrera;
    }

    public void setEmpleadodeCarrera(String EmpleadodeCarrera) {
        this.EmpleadodeCarrera = EmpleadodeCarrera;
    }

    public String getAsignadoa() {
        return Asignadoa;
    }

    public void setAsignadoa(String Asignadoa) {
        this.Asignadoa = Asignadoa;
    }

    

}
