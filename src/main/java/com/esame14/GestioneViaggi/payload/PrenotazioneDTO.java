package com.esame14.GestioneViaggi.payload;

import com.esame14.GestioneViaggi.Entity.Viaggio;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PrenotazioneDTO {
    @NotNull(message = "Inserire Data prenotazione")
    private LocalDate dataDiRichiesta;
    private String note;
    @NotNull(message = "Specificare il viaggio")
    private Viaggio viaggio;
}
