package com.esame14.GestioneViaggi.Repository;

import com.esame14.GestioneViaggi.Entity.Viaggio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViaggioRepository extends JpaRepository<Viaggio, Long> {
}
