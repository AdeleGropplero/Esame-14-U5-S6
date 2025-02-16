package com.esame14.GestioneViaggi.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Dipendente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;
    private String nome;
    private String cognome;

    @Column (unique = true)
    private String email;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "dipendente_id")
    private List<Prenotazione> prenotazioni;

    private String imageUrl;

    public Dipendente(String nome, String cognome, String email) {
        this.username = nome + "_" + cognome;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
    }
}
