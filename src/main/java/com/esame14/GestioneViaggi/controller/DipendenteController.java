package com.esame14.GestioneViaggi.controller;

import com.esame14.GestioneViaggi.Service.DipendenteService;
import com.esame14.GestioneViaggi.Service.ViaggioService;
import com.esame14.GestioneViaggi.payload.DipendenteDTO;
import com.esame14.GestioneViaggi.payload.ViaggioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    // POSTMAN --> http://localhost:8080/dipendenti
    @GetMapping
    public ResponseEntity<Page<DipendenteDTO>> getAllDipendenti(Pageable pageable) {
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

    //---------------------------------------------------------------------------------------------
    //






}
