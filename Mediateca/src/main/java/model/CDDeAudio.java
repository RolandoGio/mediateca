package model;

import java.sql.Connection;
import java.sql.SQLException;

public class CDDeAudio extends MaterialAudiovisual {
    private String cdID;
    private String artista;
    private String genero;
    private int duracion;
    private int numeroCanciones;

    public CDDeAudio(Connection connection, String titulo, String formato, String artista, String genero, int duracion, int numeroCanciones, int unidadesDisponibles) throws SQLException {
        super(titulo, formato, unidadesDisponibles);
        this.cdID = IdentificadorGenerator.generarIdentificador(connection, "CDA");
        this.artista = artista;
        this.genero = genero;
        this.duracion = duracion;
        this.numeroCanciones = numeroCanciones;
    }

    // Getters y setters
    public String getCdID() {
        return cdID;
    }

    public void setCdID(String cdID) {
        this.cdID = cdID;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
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

    public int getNumeroCanciones() {
        return numeroCanciones;
    }

    public void setNumeroCanciones(int numeroCanciones) {
        this.numeroCanciones = numeroCanciones;
    }
}

