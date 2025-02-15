package com.esame14.GestioneViaggi.controller;

import com.esame14.GestioneViaggi.Service.ViaggioService;
import com.esame14.GestioneViaggi.payload.ViaggioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/viaggi")
public class ViaggioController {
    @Autowired
    private ViaggioService viaggioService;

    // POSTMAN --> http://localhost:8080/viaggi
    //Create
    @PostMapping
    public ResponseEntity<String> createViaggio(@RequestBody @Validated ViaggioDTO viaggioDTO, BindingResult validation) {
        if (validation.hasErrors()) {
            String messaggioErrore = "ERRORI DI VALIDAZIONE \n";
            for (ObjectError error : validation.getAllErrors()) {
                messaggioErrore += error.getDefaultMessage() + ", \n";
            }
            return new ResponseEntity<>(messaggioErrore, HttpStatus.NOT_ACCEPTABLE);
        } else {
            String messaggio = viaggioService.insertViaggio(viaggioDTO);
            return new ResponseEntity<>(messaggio, HttpStatus.CREATED);
        }
    }

    @PostMapping("/multipli")
    public ResponseEntity<String> createViaggiMultipli(@RequestBody @Validated List<ViaggioDTO> viaggiDTO, BindingResult validation) {
        if (validation.hasErrors()) {
            String messaggioErrore = "ERRORI DI VALIDAZIONE \n";
            for (ObjectError error : validation.getAllErrors()) {
                messaggioErrore += error.getDefaultMessage() + ", \n";
            }
            return new ResponseEntity<>(messaggioErrore, HttpStatus.NOT_ACCEPTABLE);
        } else {
            String messaggio = viaggioService.insertViaggiMultipli(viaggiDTO);
            return new ResponseEntity<>(messaggio, HttpStatus.CREATED);
        }
    }

    @













}

