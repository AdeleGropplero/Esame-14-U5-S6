package com.esame14.GestioneViaggi.controller;

import com.esame14.GestioneViaggi.Entity.Prenotazione;
import com.esame14.GestioneViaggi.Service.DipendenteService;
import com.esame14.GestioneViaggi.Service.ViaggioService;
import com.esame14.GestioneViaggi.payload.DipendenteDTO;
import com.esame14.GestioneViaggi.payload.ViaggioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/dipendenti")
public class DipendenteController {
    @Autowired
    private DipendenteService dipendenteService;


    // POSTMAN --> http://localhost:8080/dipendenti
    //Create
    @PostMapping
    public ResponseEntity<String> createDipendente(@RequestBody @Validated DipendenteDTO dipendenteDTO, BindingResult validation) {
        if (validation.hasErrors()) {
            String messaggioErrore = "ERRORI DI VALIDAZIONE \n";
            for (ObjectError error : validation.getAllErrors()) {
                messaggioErrore += error.getDefaultMessage() + ", \n";
            }
            return new ResponseEntity<>(messaggioErrore, HttpStatus.NOT_ACCEPTABLE);
        } else {
            String messaggio = dipendenteService.insertDipendente(dipendenteDTO);
            return new ResponseEntity<>(messaggio, HttpStatus.CREATED);
        }
    }

    // POSTMAN --> http://localhost:8080/dipendenti/multipli
    @PostMapping("/multipli")
    public ResponseEntity<String> createDipendentiMultipli(@RequestBody @Validated List<DipendenteDTO> dipendentiDTO, BindingResult validation) {
        if (validation.hasErrors()) {
            String messaggioErrore = "ERRORI DI VALIDAZIONE \n";
            for (ObjectError error : validation.getAllErrors()) {
                messaggioErrore += error.getDefaultMessage() + ",\n";
            }
            return new ResponseEntity<>(messaggioErrore, HttpStatus.NOT_ACCEPTABLE);
        } else {
            String messaggio = dipendenteService.insertDipendentiMultipli(dipendentiDTO);
            return new ResponseEntity<>(messaggio, HttpStatus.CREATED);
        }
    }

    // POSTMAN --> http://localhost:8080/dipendenti?page=2&size=10
    @GetMapping
    public ResponseEntity<Page<DipendenteDTO>> getAllDipendenti(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(dipendenteService.getAllDipendenti(pageable));
    }

    // POSTMAN --> http://localhost:8080/dipendenti/id
    @GetMapping("/{id}")
    public ResponseEntity<DipendenteDTO> getDipendenteById(@PathVariable Long id) {
        return ResponseEntity.ok(dipendenteService.getDipendenteById(id));
    }

    // POSTMAN --> http://localhost:8080/dipendenti/update/id
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateDipendente(@PathVariable Long id, @RequestBody @Validated DipendenteDTO dipendenteDTO, BindingResult validation) {
        if (validation.hasErrors()) {
            String messaggioErrore = "ERRORI DI VALIDAZIONE \n";
            for (ObjectError error : validation.getAllErrors()) {
                messaggioErrore += error.getDefaultMessage() + ", \n";
            }
            return new ResponseEntity<>(messaggioErrore, HttpStatus.NOT_ACCEPTABLE);
        } else {
            dipendenteService.patchDipendente( id, dipendenteDTO);
            return new ResponseEntity<>("Il post è stato modificato correttamente", HttpStatus.OK);
        }
    }

    // POSTMAN --> http://localhost:8080/dipendenti/delete/id
    @DeleteMapping("/delete/{id}")
    public String cancellaDipendente(@PathVariable Long id) {
        return dipendenteService.deleteDipendente(id);
    }

    // POSTMAN --> http://localhost:8080/dipendenti/idDipendente/uploadImage
    @PostMapping("/{idDipendente}/uploadImage")
    public String uploadImage(@PathVariable Long idDipendente, @RequestParam("file") MultipartFile file) {
        return dipendenteService.uploadImmagine(idDipendente, file);
    }


    //---------------------------------------------------------------------------------------------
    // Endpoint per prenotazioni

    //POSTMAN --> http://localhost:8080/dipendenti/prenotazioni?idDipendente=n&idViaggio=n
    //Post
    @PostMapping("/prenotazioni")
    public ResponseEntity<String> createPrenotazione(@RequestParam Long idDipendente,
                                     @RequestParam Long idViaggio,
                                     @RequestBody(required = false) String note){
        String response = dipendenteService.inserisciPrenotazione(idDipendente, idViaggio, note);
        return ResponseEntity.ok(response);
    }

    //POSTMAN --> http://localhost:8080/dipendenti/prenotazioni
    //Get all
    @GetMapping("/prenotazioni")
    public ResponseEntity<String> getAllPrenotazioni(){
        String response = dipendenteService.getAllPrenotazioni();
        return ResponseEntity.ok(response);
    }

/*    //POSTMAN --> http://localhost:8080/dipendenti/prenotazioni/id
    //Get By id
    @GetMapping("/prenotazioni/{idUtente}")
    public ResponseEntity<List<Prenotazione>> getPrenotazioniByIdUtente(@PathVariable Long idUtente){
        List<Prenotazione> prenotazioniUtente = dipendenteService.getAllPrenotazioniById(idUtente);
        return ResponseEntity.ok(prenotazioniUtente);
    }*/




}
/*
Lista Dipendenti per Postman:
[

    {
        "username": "Paolo_Ferrari",
        "nome": "Paolo",
        "cognome": "Ferrari",
        "email": "paolo.ferrari@example.com"
    },
    {
        "username": "Giulia_Simone",
        "nome": "Giulia",
        "cognome": "Simone",
        "email": "giulia.simone@example.com"
    },
    {
        "username": "Francesca_Alessandro",
        "nome": "Francesca",
        "cognome": "Alessandro",
        "email": "francesca.alessandro@example.com"
    },
    {
        "username": "Giorgio_Parisi",
        "nome": "Giorgio",
        "cognome": "Parisi",
        "email": "giorgio.parisi@example.com"
    },
    {
        "username": "Elena_Vecchi",
        "nome": "Elena",
        "cognome": "Vecchi",
        "email": "elena.vecchi@example.com"
    },
    {
        "username": "Francesco_Martini",
        "nome": "Francesco",
        "cognome": "Martini",
        "email": "francesco.martini@example.com"
    },
    {
        "username": "Sofia_Neri",
        "nome": "Sofia",
        "cognome": "Neri",
        "email": "sofia.neri@example.com"
    },
    {
        "username": "Simone_Santoro",
        "nome": "Simone",
        "cognome": "Santoro",
        "email": "simone.santoro@example.com"
    },
    {
        "username": "Stefano_Tavoli",
        "nome": "Stefano",
        "cognome": "Tavoli",
        "email": "stefano.tavoli@example.com"
    },
    {
        "username": "Alessandra_Serrano",
        "nome": "Alessandra",
        "cognome": "Serrano",
        "email": "alessandra.serrano@example.com"
    },
    {
        "username": "Emanuele_Mancini",
        "nome": "Emanuele",
        "cognome": "Mancini",
        "email": "emanuele.mancini@example.com"
    },
    {
        "username": "Martina_Bello",
        "nome": "Martina",
        "cognome": "Bello",
        "email": "martina.bello@example.com"
    },
    {
        "username": "Tommaso_Romano",
        "nome": "Tommaso",
        "cognome": "Romano",
        "email": "tommaso.romano@example.com"
    },
    {
        "username": "Valentina_Moore",
        "nome": "Valentina",
        "cognome": "Moore",
        "email": "valentina.moore@example.com"
    },
    {
        "username": "Alessio_Fulvio",
        "nome": "Alessio",
        "cognome": "Fulvio",
        "email": "alessio.fulvio@example.com"
    },
    {
        "username": "Riccardo_Lombardi",
        "nome": "Riccardo",
        "cognome": "Lombardi",
        "email": "riccardo.lombardi@example.com"
    },
    {
        "username": "Gabriele_Gallo",
        "nome": "Gabriele",
        "cognome": "Gallo",
        "email": "gabriele.gallo@example.com"
    },
    {
        "username": "Marta_Valeri",
        "nome": "Marta",
        "cognome": "Valeri",
        "email": "marta.valeri@example.com"
    },
    {
        "username": "Antonio_Russo",
        "nome": "Antonio",
        "cognome": "Russo",
        "email": "antonio.russo@example.com"
    },
    {
        "username": "Chiara_Favero",
        "nome": "Chiara",
        "cognome": "Favero",
        "email": "chiara.favero@example.com"
    }
]
*/

/*
Lista Prenotazioni:

*/