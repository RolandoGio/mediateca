package model;

import java.sql.Connection;
import java.sql.SQLException;

public class Revista extends MaterialEscrito {
    private String revistaID;
    private String periodicidad;
    private String fechaPublicacion;

    public Revista(Connection connection, String titulo, String editorial, String periodicidad, String fechaPublicacion, int unidadesDisponibles) throws SQLException {
        super(titulo, editorial, unidadesDisponibles);
        this.revistaID = IdentificadorGenerator.generarIdentificador(connection, "REV");
        this.periodicidad = periodicidad;
        this.fechaPublicacion = fechaPublicacion;
    }

    // Getters y setters
    public String getRevistaID() {
        return revistaID;
    }

    public void setRevistaID(String revistaID) {
        this.revistaID = revistaID;
    }

    public String getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(String periodicidad) {
        this.periodicidad = periodicidad;
    }

    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }
}
