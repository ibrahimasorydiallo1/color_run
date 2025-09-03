package fr.esgi.color_run.repository;

import fr.esgi.color_run.business.Parcours;

import java.util.List;
import java.util.Optional;

public interface ParcoursRepository {
    void save(Parcours p);
    Optional<Parcours> findById(Long id);
    List<Parcours> findAll();
    void deleteById(Long id);
}

