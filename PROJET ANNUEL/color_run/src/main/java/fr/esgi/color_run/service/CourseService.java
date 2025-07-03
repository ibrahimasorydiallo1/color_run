package fr.esgi.color_run.service;

import fr.esgi.color_run.business.Course;

import java.sql.SQLException;
import java.util.List;

public interface CourseService {

    List<Course> recupererCourses() throws SQLException;

    List<Course> recupererCourses(String nom) throws SQLException;

    Course trouverParId(Long id) throws SQLException;

    void ajouterCourse(Course course) throws SQLException;

    void modifierCourse(Course course) throws SQLException;

}
