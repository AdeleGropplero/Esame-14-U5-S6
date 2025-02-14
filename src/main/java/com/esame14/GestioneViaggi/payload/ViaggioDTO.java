package com.esame14.GestioneViaggi.payload;

import com.esame14.GestioneViaggi.Enum.StatoViaggio;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViaggioDTO {
    @NotNull(message = "La destinazione del viaggio è obbligatoria")
    @Size(min = 3, max = 20, message = "La destinazione deve avere una lunghezza compresa tra i 3 e i 20 caratteri")
    private String destinazione;
    @NotNull(message = "La data del viaggio è obbligatoria")
    private LocalDate dataViaggio;
    private StatoViaggio statoViaggio;
}
