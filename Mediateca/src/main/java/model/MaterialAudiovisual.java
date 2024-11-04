package model;

public abstract class MaterialAudiovisual extends Material {
    private String formato;

    public MaterialAudiovisual(String titulo, String formato, int unidadesDisponibles) {
        super(titulo, unidadesDisponibles);
        this.formato = formato;
    }

    // Getters y setters
    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }
}

