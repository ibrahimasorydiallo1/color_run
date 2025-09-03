package fr.esgi.color_run.repository.impl;

import fr.esgi.color_run.business.Parcours;
import fr.esgi.color_run.repository.ParcoursRepository;
import fr.esgi.color_run.service.ParcoursService;

import java.util.List;
import java.util.Optional;

public class ParcoursServiceImpl implements ParcoursService {
    private final ParcoursRepository repo;

    public ParcoursServiceImpl(ParcoursRepository repo) {
        this.repo = repo;
    }

    @Override
    public void ajouterParcours(Parcours parcours) { repo.save(parcours); }

    @Override
    public Optional<Parcours> obtenirParcoursParId(Long id) { return repo.findById(id); }

    @Override
    public List<Parcours> obtenirTousLesParcours() { return repo.findAll(); }

    @Override
    public void supprimerParcours(Long id) { repo.deleteById(id); }
}


