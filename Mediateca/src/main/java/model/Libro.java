package model;

import java.sql.Connection;
import java.sql.SQLException;

public class Libro extends MaterialEscrito {
    private String libroID;
    private String autor;
    private int numPaginas;
    private String isbn;
    private int anioPublicacion;

    public Libro(Connection connection, String titulo, String autor, int numPaginas, String editorial, String isbn, int anioPublicacion, int unidadesDisponibles) throws SQLException {
        super(titulo, editorial, unidadesDisponibles);
        this.libroID = IdentificadorGenerator.generarIdentificador(connection, "LIB");
        this.autor = autor;
        this.numPaginas = numPaginas;
        this.isbn = isbn;
        this.anioPublicacion = anioPublicacion;
    }

    // Getters y setters
    public String getLibroID() {
        return libroID;
    }

    public void setLibroID(String libroID) {
        this.libroID = libroID;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getNumPaginas() {
        return numPaginas;
    }

    public void setNumPaginas(int numPaginas) {
        this.numPaginas = numPaginas;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(int anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }
}
