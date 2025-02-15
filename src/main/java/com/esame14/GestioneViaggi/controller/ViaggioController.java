package com.esame14.GestioneViaggi.controller;

import com.esame14.GestioneViaggi.Service.ViaggioService;
import com.esame14.GestioneViaggi.payload.ViaggioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    // POSTMAN --> http://localhost:8080/viaggi/multipli
    @PostMapping("/multipli")
    public ResponseEntity<String> createViaggiMultipli(@RequestBody @Validated List<ViaggioDTO> viaggiDTO, BindingResult validation) {
        if (validation.hasErrors()) {
            String messaggioErrore = "ERRORI DI VALIDAZIONE \n";
            for (ObjectError error : validation.getAllErrors()) {
                messaggioErrore += error.getDefaultMessage() + ",\n";
            }
            return new ResponseEntity<>(messaggioErrore, HttpStatus.NOT_ACCEPTABLE);
        } else {
            String messaggio = viaggioService.insertViaggiMultipli(viaggiDTO);
            return new ResponseEntity<>(messaggio, HttpStatus.CREATED);
        }
    }

    // POSTMAN --> http://localhost:8080/viaggi
    @GetMapping
    public ResponseEntity<Page<ViaggioDTO>> getAllViaggi(Pageable pageable) {
        return ResponseEntity.ok(viaggioService.getAllViaggi(pageable));
    }

    // POSTMAN --> http://localhost:8080/viaggi/id
    @GetMapping("/{id}")
    public ResponseEntity<ViaggioDTO> getViaggioById(@PathVariable Long id) {
        return ResponseEntity.ok(viaggioService.getViaggioById(id));
    }

    // POSTMAN --> http://localhost:8080/viaggi/update/id
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateViaggio(@PathVariable Long id, @RequestBody @Validated ViaggioDTO viaggioDTO, BindingResult validation) {
        if (validation.hasErrors()) {
            String messaggioErrore = "ERRORI DI VALIDAZIONE \n";
            for (ObjectError error : validation.getAllErrors()) {
                messaggioErrore += error.getDefaultMessage() + ", \n";
            }
            return new ResponseEntity<>(messaggioErrore, HttpStatus.NOT_ACCEPTABLE);
        } else {
            viaggioService.patchViaggio( id, viaggioDTO);
            return new ResponseEntity<>("Il post è stato modificato correttamente", HttpStatus.OK);
        }
    }

    // POSTMAN --> http://localhost:8080/viaggi/delete/id
    @DeleteMapping("/delete/{id}")
    public String cancellaViaggio(@PathVariable Long id) {
        return viaggioService.deleteViaggio(id);
    }


}

