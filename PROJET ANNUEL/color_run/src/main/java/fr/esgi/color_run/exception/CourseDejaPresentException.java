package fr.esgi.color_run.exception;

/**
 * Lancée lorsqu’une course avec le même nom et la même date existe déjà.
 */
public class CourseDejaPresentException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CourseDejaPresentException(String message) {
        super(message);
    }
}
