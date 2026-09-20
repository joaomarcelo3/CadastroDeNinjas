package dev.java10x.CadastroDeNinjas.Missoes;

public enum Dificuldade {
    A("rank A"),
    B("rank B"),
    C("rank C"),
    D("rank D"),
    E("rank E"),
    F("rank F");

    String descricao;
    int nivel;

    Dificuldade(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
