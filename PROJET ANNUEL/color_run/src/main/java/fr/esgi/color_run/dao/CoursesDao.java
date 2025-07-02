package fr.esgi.color_run.dao;

import fr.esgi.color_run.business.Course;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Accès BDD pour l’entité Course.
 */
public interface CoursesDao {

    /* --------- Create --------- */
    Course create(Course course) throws SQLException;

    /* --------- Read --------- */
    Course findById(Long id) throws SQLException;
    List<Course> findAll() throws SQLException;

    /**
     * Recherche en pleine insensibilité à la casse sur le nom.
     * @param nomPartiel texte à rechercher (% ajouté dans l’implémentation)
     */
    List<Course> searchByNom(String nomPartiel) throws SQLException;

    /* --------- Update --------- */
    Course update(Course course) throws SQLException;

    /* --------- Delete --------- */
    boolean delete(Long id) throws SQLException;
}
