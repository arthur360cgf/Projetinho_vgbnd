package com.vgbnd.auth_service.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "google_sub", unique = true, nullable = false)
    private String googleSub;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true, nullable = false)
    private String email;

    private String foto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Papel papel = Papel.USER;

    @Column(nullable = false)
    private boolean bloqueado = false;

    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();

    @Column(name = "atualizado_em", nullable = false)
    private LocalDateTime atualizadoEm = LocalDateTime.now();

    // Construtores
    public Usuario() {}

    public Usuario(String googleSub, String nome, String email, String foto) {
        this.googleSub = googleSub;
        this.nome = nome;
        this.email = email;
        this.foto = foto;
    }

    // Getters e Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getGoogleSub() { return googleSub; }
    public void setGoogleSub(String googleSub) { this.googleSub = googleSub; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }

    public Papel getPapel() { return papel; }
    public void setPapel(Papel papel) { this.papel = papel; }

    public boolean isBloqueado() { return bloqueado; }
    public void setBloqueado(boolean bloqueado) { this.bloqueado = bloqueado; }

    public LocalDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(LocalDateTime criadoEm) { this.criadoEm = criadoEm; }

    public LocalDateTime getAtualizadoEm() { return atualizadoEm; }
    public void setAtualizadoEm(LocalDateTime atualizadoEm) { this.atualizadoEm = atualizadoEm; }
}