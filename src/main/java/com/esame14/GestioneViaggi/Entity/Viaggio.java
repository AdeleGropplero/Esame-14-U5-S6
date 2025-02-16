package com.esame14.GestioneViaggi.Entity;

import com.esame14.GestioneViaggi.Enum.StatoViaggio;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Viaggio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String destinazione;
    @Column(name = "data_viaggio")
    private LocalDate dataViaggio;
    @Enumerated(EnumType.STRING)
    private StatoViaggio statoViaggio;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "viaggio_id")
    private List<Prenotazione> prenotazioni;

    public Viaggio(String destinazione, LocalDate dataViaggio, StatoViaggio statoViaggio) {
        this.destinazione = destinazione;
        this.dataViaggio = dataViaggio;
        this.statoViaggio = statoViaggio;
    }

    @Override
    public String toString() {
        return "Viaggio{" +
                "id=" + id +
                ", destinazione='" + destinazione + '\'' +
                ", dataViaggio=" + dataViaggio +
                '}';
    }
}
