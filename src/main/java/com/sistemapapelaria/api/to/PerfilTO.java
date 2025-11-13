package com.sistemapapelaria.api.to;

public class PerfilTO {

    private String perfil;

    public PerfilTO() {}

    public PerfilTO(String perfil) {
        this.perfil = perfil;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    @Override
    public String toString() {
        return perfil;
    }
}
