package model;

import java.sql.Connection;
import java.sql.SQLException;

public class Prestamo {
    private String idPrestamo;
    private String idCliente;
    private String cdID;
    private String dvdID;
    private String libroID;
    private String revistaID;
    private String fechaPrestamo;
    private String fechaDevolucion;

    public Prestamo(Connection connection, String idCliente, String cdID, String dvdID, String libroID, String revistaID, String fechaPrestamo, String fechaDevolucion) throws SQLException {
        this.idPrestamo = IdentificadorGenerator.generarIdentificador(connection, "PRE");
        this.idCliente = idCliente;
        this.cdID = cdID;
        this.dvdID = dvdID;
        this.libroID = libroID;
        this.revistaID = revistaID;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
    }

    // Getters y setters
    public String getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(String idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getCdID() {
        return cdID;
    }

    public void setCdID(String cdID) {
        this.cdID = cdID;
    }

    public String getDvdID() {
        return dvdID;
    }

    public void setDvdID(String dvdID) {
        this.dvdID = dvdID;
    }

    public String getLibroID() {
        return libroID;
    }

    public void setLibroID(String libroID) {
        this.libroID = libroID;
    }

    public String getRevistaID() {
        return revistaID;
    }

    public void setRevistaID(String revistaID) {
        this.revistaID = revistaID;
    }

    public String getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(String fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public String getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(String fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }
}
