package com.esame14.GestioneViaggi.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Viaggio viaggio;  // Aggiungo la relazione ManyToOne con Viaggio

    @ManyToOne
    @JoinColumn(name = "dipendente_id", referencedColumnName = "id")
    private Dipendente dipendente;

    public Prenotazione(String note) {
        this.dataDiRichiesta = LocalDate.now();
        this.note = note;
    }

    public Prenotazione(LocalDate dataDiRichiesta, String note) {
        this.dataDiRichiesta = dataDiRichiesta;
        this.note = note;

    }

    @Override
    public String toString() {
        return "Prenotazione{" +
                "id=" + id +
                ", note='" + note + '\'' +
                '}';
    }

}
