package fr.esgi.color_run.service;

import fr.esgi.color_run.business.Course;
import fr.esgi.color_run.repository.CourseRepository;
import fr.esgi.color_run.service.impl.CourseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Tests unitaires de CourseServiceImpl en mockant CourseRepository.
 */
@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository repo;

    @InjectMocks
    private CourseServiceImpl service;

    private Course c1;
    private Course c2;

    @BeforeEach
    void setUp() {
        // Si tu n'utilises pas @ExtendWith, dé-commente la ligne suivante :
        // MockitoAnnotations.openMocks(this);

        c1 = new Course(
                1L, "Color Run Paris", "Fun", LocalDateTime.of(2025, 7, 5, 10, 0),
                "Paris", 2500, 25.0f
        );
        c2 = new Course(
                2L, "Color Run Lyon", "Encore plus fun", LocalDateTime.of(2025, 8, 2, 10, 0),
                "Lyon", 2000, 20.0f
        );
    }

    @Test
    @DisplayName("recupererCourses() retourne la liste renvoyée par le repo")
    void recupererCourses_returnsListFromRepo() throws Exception {
        when(repo.findAll()).thenReturn(List.of(c1, c2));

        var result = service.recupererCourses();

        assertThat(result).containsExactly(c1, c2);
        verify(repo).findAll();
        verifyNoMoreInteractions(repo);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t"})
    @DisplayName("recupererCourses(nom) → findAll() si nom est null/blanc")
    void recupererCourses_filterBlank_callsFindAll(String filtre) throws Exception {
        when(repo.findAll()).thenReturn(List.of(c1));

        var result = service.recupererCourses(filtre);

        assertThat(result).containsExactly(c1);
        verify(repo).findAll();
        verifyNoMoreInteractions(repo);
    }

    @Test
    @DisplayName("recupererCourses(nom) → findByNomContainingIgnoreCase(nom) sinon")
    void recupererCourses_withFilter_callsFindByNomContaining() throws Exception {
        when(repo.findByNomContainingIgnoreCase("par")).thenReturn(List.of(c1));

        var result = service.recupererCourses("par");

        assertThat(result).containsExactly(c1);
        verify(repo).findByNomContainingIgnoreCase("par");
        verifyNoMoreInteractions(repo);
    }

    @Test
    @DisplayName("trouverParId(id) retourne la course si trouvée")
    void trouverParId_found() throws Exception {
        when(repo.findById(1L)).thenReturn(c1); // plus de Optional

        var found = service.trouverParId(1L);

        assertThat(found).isEqualTo(c1);
        verify(repo).findById(1L);
        verifyNoMoreInteractions(repo);
    }

    @Test
    @DisplayName("trouverParId(id) retourne null si absente")
    void trouverParId_notFound() throws Exception {
        when(repo.findById(99L)).thenReturn(null); // repo renvoie null

        var found = service.trouverParId(99L);

        assertThat(found).isNull();
        verify(repo).findById(99L);
        verifyNoMoreInteractions(repo);
    }

    @Test
    @DisplayName("propagation d'une SQLException du repo")
    void repoThrowsSQLException_isPropagated() throws Exception {
        when(repo.findAll()).thenThrow(new SQLException("boom"));

        assertThrows(SQLException.class, () -> service.recupererCourses());
        verify(repo).findAll();
        verifyNoMoreInteractions(repo);
    }
}
