package com.esame14.GestioneViaggi.Repository;

import com.esame14.GestioneViaggi.Entity.Dipendente;
import com.esame14.GestioneViaggi.Entity.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DipendenteRepository extends JpaRepository<Dipendente, Long> {
    //Ora creo una Query personalizzata per gestire il controllo delle prenotazioni.
    //Se il dipendente ha già una prenotazione nella stessa data in cui sta cercando di prenotare
    //gli verrà dato un avviso e impedito di farlo nel Service.

    @Query ("SELECT COUNT(p) > 0 FROM Dipendente d JOIN d.prenotazioni p JOIN p.viaggio v" +
            "WHERE d.id = :dipendenteId AND v.dataViaggio = :dataViaggio")
    boolean esistePrenotazionePerData(@Param("dipendenteId") Long dipendenteId,
                                      @Param("dataViaggio") LocalDate dataViaggio);



/*    @Query ("SELECT *  FROM Dipendente d JOIN d.prenotazioni p WHERE d.id = :dipendenteId")
    List<Prenotazione> prenotazioni(@Param("dipendenteId") Long dipendenteId);*/
    //Inutile ho trovato un modo direttamente nel service

}
