package fr.esgi.color_run.repository.impl;

import fr.esgi.color_run.business.Course;
import fr.esgi.color_run.repository.CourseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CourseRepositoryImpl implements CourseRepository {

    private static final List<Course> MOCK_COURSES = new ArrayList<>();

    static {
        MOCK_COURSES.add(new Course("Color Run Paris"));
        MOCK_COURSES.add(new Course("Color Run Lyon"));
        MOCK_COURSES.add(new Course("Color Run Marseille"));
    }

    @Override
    public List<Course> findAll() {
        return new ArrayList<>(MOCK_COURSES);
    }

    @Override
    public List<Course> findByNomContainingIgnoreCase(String nom) {
        return MOCK_COURSES.stream()
                .filter(course -> course.getNom().toLowerCase().contains(nom.toLowerCase()))
                .collect(Collectors.toList());
    }
}
