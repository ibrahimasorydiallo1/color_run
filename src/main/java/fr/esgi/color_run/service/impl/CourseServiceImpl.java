package fr.esgi.color_run.service.impl;

import fr.esgi.color_run.business.Course;
import fr.esgi.color_run.repository.CourseRepository;
import fr.esgi.color_run.repository.impl.CourseRepositoryImpl;
import fr.esgi.color_run.service.CourseService;

import java.util.List;

public class CourseServiceImpl implements CourseService {

    private CourseRepository courseRepository = new CourseRepositoryImpl();

    @Override
    public List<Course> recupererCourses() {
        return courseRepository.findAll();
    }

    @Override
    public List<Course> recupererCourses(String nom) {
        return courseRepository.findByNomContainingIgnoreCase(nom);
    }
}
