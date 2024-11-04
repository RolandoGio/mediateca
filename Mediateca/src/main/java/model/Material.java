package model;

public abstract class Material {
    private String titulo;
    private int unidadesDisponibles;

    public Material(String titulo, int unidadesDisponibles) {
        this.titulo = titulo;
        this.unidadesDisponibles = unidadesDisponibles;
    }

    // Getters y setters
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getUnidadesDisponibles() {
        return unidadesDisponibles;
    }

    public void setUnidadesDisponibles(int unidadesDisponibles) {
        this.unidadesDisponibles = unidadesDisponibles;
    }
}


