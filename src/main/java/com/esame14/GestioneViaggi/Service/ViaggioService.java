package com.esame14.GestioneViaggi.Service;

import com.esame14.GestioneViaggi.Entity.Viaggio;
import com.esame14.GestioneViaggi.Exception.OggettoNulloException;
import com.esame14.GestioneViaggi.Repository.ViaggioRepository;
import com.esame14.GestioneViaggi.payload.ViaggioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ViaggioService {
    @Autowired
    ViaggioRepository viaggioRepository;

    //Post
    public String insertViaggio(ViaggioDTO newViaggioDTO) {
        Viaggio viaggioEntity = dto_entity(newViaggioDTO);
        viaggioRepository.save(viaggioEntity);
        return "Viaggio con destinazione: " + newViaggioDTO.getDestinazione() +
                " in data " + newViaggioDTO.getDataViaggio() + " inserito correttamente!";
    }

    //Post Multipli
    public String insertViaggiMultipli(List<ViaggioDTO> viaggi){
        List<Viaggio> viaggiEntity = viaggi.stream().map(v -> dto_entity(v)).toList();
        viaggioRepository.saveAll(viaggiEntity);
        return "Inseriti " + viaggiEntity.size() + " viaggi" ;
    }

    //GetAll
    public Page<ViaggioDTO> getAllViaggi(Pageable pageable) {
        //implemento la paginazione
        //Recupero una pagina di entità viaggio dal DB
        Page<Viaggio> viaggi = viaggioRepository.findAll(pageable);
        return viaggi.map(v -> entity_dto(v));
    }

    //Get
    public ViaggioDTO getViaggioById(Long id){
        Viaggio viaggio = viaggioRepository.findById(id).orElseThrow(
                          () -> new OggettoNulloException("Non risulta un viaggio con id: " + id)
                          ); // Cerca il viaggio con l'ID specificato e lancia un'eccezione se non viene trovato.
           return entity_dto(viaggio);
    }

    //Patch
    public String patchViaggio (Long id, ViaggioDTO viaggioDTO){
        Viaggio viaggio = viaggioRepository.findById(id).orElseThrow(
                () -> new OggettoNulloException("Non risulta un viaggio con id: " + id)
                ); // Cerca il viaggio con l'ID specificato e lancia un'eccezione se non viene trovato.
        //
        if (viaggioDTO.getDestinazione() != null){
            viaggio.setDestinazione(viaggioDTO.getDestinazione());
        }
        if (viaggioDTO.getStatoViaggio() != null){
            viaggio.setStatoViaggio(viaggioDTO.getStatoViaggio());
        }
        if (viaggioDTO.getDataViaggio() != null){
            viaggio.setDataViaggio(viaggioDTO.getDataViaggio());
        }

        viaggioRepository.save(viaggio);
        return "Viaggio  con id: " + id + " aggiornato.";
    }

    //Delete
    public String deleteViaggio(Long id){
        Viaggio viaggio = viaggioRepository.findById(id).orElseThrow(
                () -> new OggettoNulloException("Non risulta un viaggio con id: " + id)
                ); // Cerca il viaggio con l'ID specificato e lancia un'eccezione se non viene trovato.

        viaggioRepository.delete(viaggio);
        return "Viaggio  con id: " + id + " eliminato.";
    }

    //METODI TRAVASO
    public Viaggio dto_entity(ViaggioDTO viaggioDTO) {
        if (viaggioDTO != null) {
            Viaggio viaggio = new Viaggio();
            viaggio.setDataViaggio(viaggioDTO.getDataViaggio());
            viaggio.setStatoViaggio(viaggioDTO.getStatoViaggio());
            viaggio.setDestinazione(viaggioDTO.getDestinazione());
            return viaggio;
        } else {
            throw new OggettoNulloException("L'oggetto in entrata risulta nullo");
        }
    }

    public ViaggioDTO entity_dto(Viaggio viaggio) {
        if (viaggio != null) {
            ViaggioDTO viaggioDTO = new ViaggioDTO();
            viaggioDTO.setDataViaggio(viaggio.getDataViaggio());
            viaggioDTO.setStatoViaggio(viaggio.getStatoViaggio());
            viaggioDTO.setDestinazione(viaggio.getDestinazione());
            return viaggioDTO;
        } else {
            throw new OggettoNulloException("L'oggetto in uscita risulta nullo");
        }
    }
}
