package fr.esgi.color_run.service;

import fr.esgi.color_run.business.Parcours;
import fr.esgi.color_run.repository.ParcoursRepository;
import fr.esgi.color_run.service.impl.ParcoursServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParcoursServiceTest {

    private ParcoursRepository repo;
    private ParcoursService service;

    private Parcours p1;
    private Parcours p2;

    @BeforeEach
    void setup() {
        repo = mock(ParcoursRepository.class);
        service = new ParcoursServiceImpl(repo);

        p1 = new Parcours(1L, "Quais de Seine",
                "Parcours au bord de l’eau", 5.0, 5);
        p2 = new Parcours(2L, "Parc Tête d’Or",
                "Boucle dans le parc", 10.0, 8);
    }

    @Test
    @DisplayName("ajouterParcours() délègue à repo.save()")
    void ajouterParcours_callsSave() {
        service.ajouterParcours(p1);
        verify(repo).save(p1);
        verifyNoMoreInteractions(repo);
    }

    @Test
    @DisplayName("obtenirParcoursParId() renvoie l'Optional du repo")
    void obtenirParcoursParId_returnsOptional() {
        when(repo.findById(1L)).thenReturn(Optional.of(p1));

        Optional<Parcours> opt = service.obtenirParcoursParId(1L);

        assertTrue(opt.isPresent());
        assertEquals("Quais de Seine", opt.get().getNom());
        verify(repo).findById(1L);
        verifyNoMoreInteractions(repo);
    }

    @Test
    @DisplayName("obtenirParcoursParId() renvoie empty si le repo ne trouve rien")
    void obtenirParcoursParId_returnsEmpty() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        Optional<Parcours> opt = service.obtenirParcoursParId(99L);

        assertTrue(opt.isEmpty());
        verify(repo).findById(99L);
        verifyNoMoreInteractions(repo);
    }

    @Test
    @DisplayName("obtenirTousLesParcours() délègue à repo.findAll()")
    void obtenirTousLesParcours_returnsList() {
        when(repo.findAll()).thenReturn(List.of(p1, p2));

        List<Parcours> list = service.obtenirTousLesParcours();

        assertEquals(2, list.size());
        assertEquals(10.0, list.get(1).getDistanceKm());
        verify(repo).findAll();
        verifyNoMoreInteractions(repo);
    }

    @Test
    @DisplayName("supprimerParcours() délègue à repo.deleteById()")
    void supprimerParcours_callsDelete() {
        service.supprimerParcours(2L);

        InOrder order = inOrder(repo);
        order.verify(repo).deleteById(2L);
        verifyNoMoreInteractions(repo);
    }
}
