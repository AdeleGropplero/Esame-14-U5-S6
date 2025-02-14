package com.esame14.GestioneViaggi.Repository;

import com.esame14.GestioneViaggi.Entity.Dipendente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DipendenteRepository extends JpaRepository<Dipendente, Long> {
}
