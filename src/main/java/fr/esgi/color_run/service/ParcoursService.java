package fr.esgi.color_run.service;

import fr.esgi.color_run.business.Parcours;

import java.util.List;
import java.util.Optional;

public interface ParcoursService {

    // Ajouter un nouveau parcours
    void ajouterParcours(Parcours parcours);

    // Obtenir un parcours par son identifiant
    Optional<Parcours> obtenirParcoursParId(Long id);

    // Obtenir la liste de tous les parcours
    List<Parcours> obtenirTousLesParcours();

    // Supprimer un parcours via son identifiant
    void supprimerParcours(Long id);
}
