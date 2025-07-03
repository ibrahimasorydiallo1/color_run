package fr.esgi.color_run.service.impl;

import fr.esgi.color_run.business.Course;
import fr.esgi.color_run.exception.CourseDejaPresentException;
import fr.esgi.color_run.repository.CourseRepository;
import fr.esgi.color_run.repository.impl.CourseRepositoryImpl;
import fr.esgi.color_run.service.CourseService;

import java.sql.SQLException;
import java.util.List;

/**
 * Couche métier : validations / règles d’intégrité avant d’appeler le repository.
 */
public class CourseServiceImpl implements CourseService {

    private final CourseRepository repo;

    /** Injection par constructeur : new CourseServiceImpl(new CourseRepositoryImpl()) */
    public CourseServiceImpl(CourseRepository repo) {
        this.repo = repo;
    }

    /* ---------- Récupérations ---------- */

    @Override
    public List<Course> recupererCourses() throws SQLException {
        return repo.findAll();
    }

    @Override
    public List<Course> recupererCourses(String nom) throws SQLException {
        if (nom == null || nom.isBlank()) {
            return repo.findAll();
        }
        return repo.findByNomContainingIgnoreCase(nom);
    }

    @Override
    public Course trouverParId(Long id) throws SQLException {
        return repo.findById(id);
    }


    /* ---------- Ajout ---------- */

    @Override
    public void ajouterCourse(Course course) throws SQLException {
        if (course == null) {
            throw new IllegalArgumentException("Course null");
        }
        // Règle : nom unique (insensible à la casse)
        if (!repo.findByNomContainingIgnoreCase(course.getNom()).isEmpty()) {
            throw new CourseDejaPresentException(
                    "Une course appelée '" + course.getNom() + "' existe déjà");
        }
        repo.save(course);
    }

    /* ---------- Modification ---------- */

    @Override
    public void modifierCourse(Course course) throws SQLException {
        if (course == null || course.getId() == null) {
            throw new IllegalArgumentException("Course ou id null");
        }
        // Même règle de nom unique (on ignore la course elle-même)
        boolean doublon = repo.findByNomContainingIgnoreCase(course.getNom()).stream()
                .anyMatch(c -> !c.getId().equals(course.getId()));
        if (doublon) {
            throw new CourseDejaPresentException(
                    "Le nom '" + course.getNom() + "' est déjà utilisé par une autre course");
        }
        repo.update(course);
    }
}

