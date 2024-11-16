package com.example.ScreenSoundMusicas.model;

public enum Tipo {
    SOLO("Solo"),
    DUPLA("Dupla"),
    BANDA("Banda");

    public String descricao;

    Tipo(String descricao) {
        this.descricao = descricao;
    }

    public static Tipo fromString(String descricao) {
        for (Tipo tipo : Tipo.values()) {
            if (tipo.descricao.equalsIgnoreCase(descricao)) {
                return tipo;
            }
        }

        throw new RuntimeException("Tipo não encontrado");
    }
}
