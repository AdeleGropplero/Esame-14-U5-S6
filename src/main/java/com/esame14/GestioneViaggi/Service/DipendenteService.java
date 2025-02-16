package com.esame14.GestioneViaggi.Service;

import com.esame14.GestioneViaggi.Entity.Dipendente;
import com.esame14.GestioneViaggi.Entity.Prenotazione;
import com.esame14.GestioneViaggi.Entity.Viaggio;
import com.esame14.GestioneViaggi.Exception.OggettoNulloException;
import com.esame14.GestioneViaggi.Exception.PrenotazioneEsistenteException;
import com.esame14.GestioneViaggi.Repository.DipendenteRepository;
import com.esame14.GestioneViaggi.Repository.ViaggioRepository;
import com.esame14.GestioneViaggi.payload.DipendenteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DipendenteService {
    @Autowired
    private DipendenteRepository dipendenteRepository;

    @Autowired
    private ViaggioRepository viaggioRepository;

    //Post
    public String insertDipendente(DipendenteDTO newDipendenteDTO) {
        Dipendente dipendenteEntity = dto_entity(newDipendenteDTO);
        dipendenteRepository.save(dipendenteEntity);
        return "Dipendente: " + newDipendenteDTO.getNome() +
                " " + newDipendenteDTO.getCognome() + " inserito correttamente!";
    }

    //Post Multipli
    public String insertDipendentiMultipli(List<DipendenteDTO> dipendenti) {
        List<Dipendente> dipendentiEntity = dipendenti.stream().map(v -> dto_entity(v)).toList();
        dipendenteRepository.saveAll(dipendentiEntity);
        return "Inseriti " + dipendentiEntity.size() + " dipendenti";
    }

    //GetAll
    public Page<DipendenteDTO> getAllDipendenti(Pageable pageable) {
        //implemento la paginazione
        //Recupero una pagina di entità dipendente dal DB
        Page<Dipendente> dipendenti = dipendenteRepository.findAll(pageable);
        return dipendenti.map(v -> entity_dto(v));
    }

    //Get
    public DipendenteDTO getDipendenteById(Long id) {
        Dipendente dipendente = dipendenteRepository.findById(id).orElseThrow(
                () -> new OggettoNulloException("Non risulta un dipendente con id: " + id)
        ); // Cerca il dipendente con l'ID specificato e lancia un'eccezione se non viene trovato.
        return entity_dto(dipendente);
    }

    //Patch
    public String patchDipendente(Long id, DipendenteDTO dipendenteDTO) {
        Dipendente dipendente = dipendenteRepository.findById(id).orElseThrow(
                () -> new OggettoNulloException("Non risulta un dipendente con id: " + id)
        ); // Cerca il dipendente con l'ID specificato e lancia un'eccezione se non viene trovato.
        //
        if (dipendenteDTO.getNome() != null) {
            dipendente.setNome(dipendenteDTO.getNome());
        }
        if (dipendenteDTO.getCognome() != null) {
            dipendente.setCognome(dipendenteDTO.getCognome());
        }
        if (dipendenteDTO.getEmail() != null) {
            dipendente.setEmail(dipendenteDTO.getEmail());
        }

        dipendenteRepository.save(dipendente);
        return "Dipendente  con id: " + id + " aggiornato.";
    }

    //Delete
    public String deleteDipendente(Long id) {
        Dipendente dipendente = dipendenteRepository.findById(id).orElseThrow(
                () -> new OggettoNulloException("Non risulta un dipendente con id: " + id)
        ); // Cerca il dipendente con l'ID specificato e lancia un'eccezione se non viene trovato.

        dipendenteRepository.delete(dipendente);
        return "Dipendente  con id: " + id + " eliminato.";
    }

    //METODI TRAVASO
    //Da DTO ad Entity
    public Dipendente dto_entity(DipendenteDTO dipendenteDTO) {
        if (dipendenteDTO != null) {
            Dipendente dipendente = new Dipendente();
            dipendente.setNome(dipendenteDTO.getNome());
            dipendente.setCognome(dipendenteDTO.getCognome());
            dipendente.setEmail(dipendenteDTO.getEmail());
            return dipendente;
        } else {
            throw new OggettoNulloException("L'oggetto in entrata risulta nullo");
        }
    }

    //Da Entity ad DTO
    public DipendenteDTO entity_dto(Dipendente dipendente) {
        if (dipendente != null) {
            DipendenteDTO dipendenteDTO = new DipendenteDTO();
            dipendenteDTO.setNome(dipendente.getNome());
            dipendenteDTO.setCognome(dipendente.getCognome());
            dipendenteDTO.setEmail(dipendente.getEmail());
            return dipendenteDTO;
        } else {
            throw new OggettoNulloException("L'oggetto in uscita risulta nullo");
        }
    }

    //Metodi per CRUD Prenotazioni

    // Post
    public String inserisciPrenotazione(Long idDipendente, Long idViaggio, String note){
        // Recupero gli oggetti dal database per id
        Dipendente dipendente = dipendenteRepository.findById(idDipendente).orElseThrow(
                () -> new OggettoNulloException("Non risulta un dipendente con id: " + idDipendente));
        Viaggio viaggio = viaggioRepository.findById(idViaggio).orElseThrow(
                () -> new OggettoNulloException("Non risulta un viaggio con id: " + idViaggio));

        // Controllo se esiste già una prenotazione per questa data di viaggio
        boolean prenotazioneEsistente = dipendente.getPrenotazioni().stream()
                .anyMatch(p -> p.getViaggio().getDataViaggio().equals(viaggio.getDataViaggio()));

        // Se esiste una prenotazione per la stessa data, lancia eccezione
        if (prenotazioneEsistente) {
            throw new PrenotazioneEsistenteException("Il dipendente: " + dipendente.getUsername() +
                    " (id: " + idDipendente + ")" + " ha già un viaggio prenotato per questa data!");
        }

        // Istanzio un oggetto prenotazione
        Prenotazione prenotazione = new Prenotazione(note);

        // Aggiungo la prenotazione alle liste prenotazioni di dipendente e viaggio
        dipendente.getPrenotazioni().add(prenotazione);
        viaggio.getPrenotazioni().add(prenotazione);

        // Faccio l'update degli oggetti nel DB
        dipendenteRepository.save(dipendente); // Salvo o aggiorno il dipendente
        viaggioRepository.save(viaggio); // Salvo o aggiorno il viaggio

        // Restituisco un messaggio di conferma
        return "Prenotazione aggiunta per il dipendente: " + dipendente.getUsername() +
                " (id: " + idDipendente + ")" +
                " per il viaggio verso " + viaggio.getDestinazione() +
                " (id: " + idViaggio + ")";
    }


    //GetAllPrenotazioniPerIdDipendente
    public List<Prenotazione> getAllPrenotazioniById(Long id){
        //dipendenteRepository.prenotazioni(id);
        Dipendente dipendente = dipendenteRepository.findById(id).orElseThrow(
                () -> new OggettoNulloException("Non risulta un dipendente con id: " + id));
        return dipendente.getPrenotazioni();
    } //per fare una get di tutte le prenotazioni mi servirebbe un PrenotazioniRepository
      // oppure posso provare a fare il seguente metodo:

    public String getAllPrenotazioni(){
        List<Dipendente> dipendenti = dipendenteRepository.findAll();
        List<Prenotazione> prenotazioni = new ArrayList<>();
        dipendenti.forEach(dipendente -> prenotazioni.addAll(dipendente.getPrenotazioni()));
        return "Ecco una lista di tutte le prenotazioni: " +
                prenotazioni.stream()
                        .map(p -> "ID: " + p.getId() + ", Data: " + p.getDataDiRichiesta() + ", Note: " + p.getNote())
                        .collect(Collectors.joining(" \n "));
    }







}

/*   Ho dovuto cambiare il seguente cosice perchè per come era impostato precedentemente non poteva funzionare...

 //Post
    public String inserisciPrenotazione(Long idDipendente, Long idViaggio, String note){
        //Recupero gli oggetti dal dataBase per id
        Dipendente dipendente = dipendenteRepository.findById(idDipendente).orElseThrow(
                () -> new OggettoNulloException("Non risulta un dipendente con id: " + idDipendente));
        Viaggio viaggio = viaggioRepository.findById(idViaggio).orElseThrow(
                () -> new OggettoNulloException("Non risulta un viaggio con id: " + idViaggio));

        //Istanzio un oggetto prenotazione
        Prenotazione prenotazione = new Prenotazione(note);

        //aggiungo la prenotazione alle liste Prenotazione di dipendente e viaggio
        dipendente.getPrenotazioni().add(prenotazione);
        viaggio.getPrenotazioni().add(prenotazione);



        //Faccio l'upDate degli obj in DB viaggio e dipendente. E faccio il controllo richiesto tramite una
        // query nel repository.
        if (dipendenteRepository.esistePrenotazionePerData(idDipendente, viaggio.getDataViaggio())){
            throw new PrenotazioneEsistenteException("Il dipendente: " + dipendente.getUsername() +
                    " (id: " + idDipendente + ")" + "ha già un viaggio prenotato per questa data!");
        }else {
            dipendenteRepository.save(dipendente); //save() gestisce sia insert che update.
            viaggioRepository.save(viaggio); //Stessa cosa qui.
        }

        //Stampo informazioni utili in lettura
        return "Prenotazione aggiunta per il dipendente: " + dipendente.getUsername() +
                " (id: " + idDipendente + ")" +
                " per il viaggio" + viaggio.getDestinazione() +
                " (id: " + idViaggio + ")";
    }*/





