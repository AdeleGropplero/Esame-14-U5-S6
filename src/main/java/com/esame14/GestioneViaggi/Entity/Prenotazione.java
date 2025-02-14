package com.esame14.GestioneViaggi.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "data_di_richiesta")
    private LocalDate dataDiRichiesta;
    private String note;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "viaggio_id")
    private Viaggio viaggio;

    public Prenotazione(String note, Viaggio viaggio) {
        this.dataDiRichiesta = LocalDate.now();
        this.note = note;
        this.viaggio = viaggio;
    }

    public Prenotazione(LocalDate dataDiRichiesta, String note, Viaggio viaggio) {
        this.dataDiRichiesta = dataDiRichiesta;
        this.note = note;
        this.viaggio = viaggio;
    }
}
