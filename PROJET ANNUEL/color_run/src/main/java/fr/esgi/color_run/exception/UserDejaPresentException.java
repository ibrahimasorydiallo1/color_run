package fr.esgi.color_run.exception;

/** Lancée lorsqu’un utilisateur avec le même email existe déjà. */
public class UserDejaPresentException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UserDejaPresentException(String message) {
        super(message);
    }
}
