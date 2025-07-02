package fr.esgi.color_run.dao;

import java.sql.SQLException;
import java.util.List;

import fr.esgi.color_run.business.User;

public interface UserDao {

    // CRUD : Create Read Update Delete

    // Pour créer
    User create(User user) throws SQLException;

    // Pour rechercher par ID
    User findOne(Long id) throws SQLException;

    // Pour rechercher par Email (pour login)
    User findByEmail(String email) throws SQLException;

    // Pour tout afficher
    List<User> findAll() throws SQLException;

}
