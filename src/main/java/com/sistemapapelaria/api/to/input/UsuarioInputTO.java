package com.sistemapapelaria.api.to.input;

public class UsuarioInputTO {

    private Integer id;
    private String nome;
    private String email;
    private String senha;
    private String perfil;

    public UsuarioInputTO() {}

    public UsuarioInputTO(Integer id, String nome, String email, String senha, String perfil) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.perfil = perfil;
    }

    public Integer getId() { 
        return id; 
    }
    public void setId(Integer id) { 
        this.id = id; 
    }

    public String getNome() { 
        return nome; 
    }
    public void setNome(String nome) { 
        this.nome = nome; 
    }

    public String getEmail() { 
        return email; 
    }
    public void setEmail(String email) { 
        this.email = email; 
    }

    public String getSenha() { 
        return senha; 
    }
    public void setSenha(String senha) { 
        this.senha = senha; 
    }

    public String getPerfil() { 
        return perfil; 
    }
    public void setPerfil(String perfil) { 
        this.perfil = perfil; 
    }

    @Override
    public String toString() {
        return "UsuarioInputTO{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", perfil='" + perfil + '\'' +
                '}';
    }
}
