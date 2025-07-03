package fr.esgi.color_run.repository;

import fr.esgi.color_run.business.Course;

import java.sql.SQLException;
import java.util.List;

public interface CourseRepository {
    List<Course> findAll() throws SQLException;

    List<Course> findByNomContainingIgnoreCase(String nom) throws SQLException;

    Course findById(Long id) throws SQLException;

    void save(Course course) throws SQLException;

    void update(Course course) throws SQLException;

}
