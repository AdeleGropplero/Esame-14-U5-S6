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

    @ManyToOne
    @JoinColumn(name = "viaggio_id", referencedColumnName = "id")
    private Viaggio viaggio;  // Aggiunta della relazione ManyToOne con Viaggio

    public Prenotazione(String note) {
        this.dataDiRichiesta = LocalDate.now();
        this.note = note;
    }

    public Prenotazione(LocalDate dataDiRichiesta, String note) {
        this.dataDiRichiesta = dataDiRichiesta;
        this.note = note;

    }
}
