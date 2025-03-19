package fr.esgi.color_run.service;


import fr.esgi.color_run.business.Parcours;

import java.util.List;
import java.util.Optional;

// Interface ParcoursService
interface ParcoursService {
    void ajouterParcours(Parcours parcours);
    Optional<Parcours> obtenirParcoursParId(Long id);
    List<Parcours> obtenirTousLesParcours();
    void supprimerParcours(Long id);
}
