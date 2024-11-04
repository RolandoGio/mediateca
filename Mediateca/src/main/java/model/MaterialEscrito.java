package model;

public abstract class MaterialEscrito extends Material {
    private String editorial;

    public MaterialEscrito(String titulo, String editorial, int unidadesDisponibles) {
        super(titulo, unidadesDisponibles);
        this.editorial = editorial;
    }

    // Getters y setters
    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }
}
