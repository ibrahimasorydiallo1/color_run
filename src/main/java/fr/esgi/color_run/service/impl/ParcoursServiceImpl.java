package fr.esgi.color_run.service.impl;

import fr.esgi.color_run.business.Parcours;
import fr.esgi.color_run.service.ParcoursService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Implémentation du service
public class ParcoursServiceImpl implements ParcoursService {
    private final List<Parcours> parcoursList = new ArrayList<>();

    @Override
    public void ajouterParcours(Parcours parcours) {
        parcoursList.add(parcours);
    }

    @Override
    public Optional<Parcours> obtenirParcoursParId(Long id) {
        return parcoursList.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    @Override
    public List<Parcours> obtenirTousLesParcours() {
        return new ArrayList<>(parcoursList);
    }

    @Override
    public void supprimerParcours(Long id) {
        parcoursList.removeIf(p -> p.getId().equals(id));
    }
}
