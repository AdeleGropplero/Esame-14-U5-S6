package com.esame14.GestioneViaggi.payload;

import com.esame14.GestioneViaggi.Entity.Prenotazione;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DipendenteDTO {
    @NotNull
    @Size(min = 3, max=20, message = "I riferimenti personali devono avere una lunghezza compresa tra i 3 e i 20 caratteri")
    private String nome;
    @NotNull
    @Size(min = 3, max=20, message = "I riferimenti personali devono avere una lunghezza compresa tra i 3 e i 20 caratteri")
    private String cognome;
    @NotBlank(message = "Il contenuto risulta vuoto")
    private String email;
    private Long prenotazione_id;

    public DipendenteDTO(String email, String cognome, String nome) {
        this.email = email;
        this.cognome = cognome;
        this.nome = nome;
    }

}
