package fr.esgi.color_run.repository.impl;

import fr.esgi.color_run.business.User;
import fr.esgi.color_run.configuration.DatabaseManager;
import fr.esgi.color_run.repository.UserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    /* ===== INSERT / UPDATE communs ===== */
    private static void bindUser(PreparedStatement ps, User u) throws SQLException {
        ps.setString(1, u.getPrenom());
        ps.setString(2, u.getNom());
        ps.setString(3, u.getEmail());
        ps.setString(4, u.getPassword());
        ps.setString(5, u.getRole());
    }

    @Override
    public User save(User u) throws SQLException {
        if (u.getId() == null) {                        // ---- INSERT ----
            String sql = """
                INSERT INTO utilisateur (prenom, nom, email, password, role)
                VALUES (?,?,?,?,?)
                """;
            try (Connection c = DatabaseManager.getConnection();
                 PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                bindUser(ps, u);
                ps.executeUpdate();

                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) u.setId(keys.getLong(1));
                }
            }
        } else {                                        // ---- UPDATE ----
            String sql = """
                UPDATE utilisateur
                   SET prenom = ?, nom = ?, email = ?, password = ?, role = ?
                 WHERE id = ?
                """;
            try (Connection c = DatabaseManager.getConnection();
                 PreparedStatement ps = c.prepareStatement(sql)) {

                bindUser(ps, u);
                ps.setLong(6, u.getId());
                ps.executeUpdate();
            }
        }
        return u;
    }

    /* variante admin_save : inutile => save() gère déjà le rôle */

    /* ===== findById ===== */
    @Override
    public User findById(Long id) throws SQLException {
        String sql = "SELECT id, prenom, nom, email, password, role " +
                "FROM utilisateur WHERE id = ?";
        try (Connection c = DatabaseManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        }
        return null;
    }

    /* ===== findByEmail ===== */
    @Override
    public User findByEmail(String email) throws SQLException {
        String sql = """
            SELECT id, prenom, nom, email, password, role
              FROM utilisateur
             WHERE LOWER(email) = LOWER(?)
            """;
        try (Connection c = DatabaseManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, email.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        }
        return null;
    }

    /* ===== findAll ===== */
    @Override
    public List<User> findAll() throws SQLException {
        List<User> list = new ArrayList<>();
        String sql = "SELECT id, prenom, nom, email, password, role FROM utilisateur";

        try (Connection c = DatabaseManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    /* ===== mapper privé ===== */
    private User map(ResultSet rs) throws SQLException {
        return new User(
                rs.getLong("id"),
                rs.getString("prenom"),
                rs.getString("nom"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("role")
        );
    }
}

