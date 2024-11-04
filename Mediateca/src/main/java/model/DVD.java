package model;

import java.sql.Connection;
import java.sql.SQLException;

public class DVD extends MaterialAudiovisual {
    private String dvdID;
    private String director;
    private String genero;
    private int duracion;

    public DVD(Connection connection, String titulo, String formato, String director, String genero, int duracion, int unidadesDisponibles) throws SQLException {
        super(titulo, formato, unidadesDisponibles);
        this.dvdID = IdentificadorGenerator.generarIdentificador(connection, "DVD");
        this.director = director;
        this.genero = genero;
        this.duracion = duracion;
    }

    // Getters y setters
    public String getDvdID() {
        return dvdID;
    }

    public void setDvdID(String dvdID) {
        this.dvdID = dvdID;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }
}
