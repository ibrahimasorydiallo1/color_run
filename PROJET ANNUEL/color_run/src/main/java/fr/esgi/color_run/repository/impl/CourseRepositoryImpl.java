package fr.esgi.color_run.repository.impl;

import fr.esgi.color_run.business.Course;
import fr.esgi.color_run.business.User;
import fr.esgi.color_run.configuration.DatabaseManager;
import fr.esgi.color_run.repository.CourseRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation JDBC simple du repository Course (H2).
 */
public class CourseRepositoryImpl implements CourseRepository {

    /* ╔════════════════════════════════════════════════════════════╗
       ║           Création automatique de la table « course »      ║
       ╚════════════════════════════════════════════════════════════╝ */
    public CourseRepositoryImpl() {
        String sql = """
            CREATE TABLE IF NOT EXISTS course (
                id IDENTITY PRIMARY KEY,
                nom VARCHAR(255)             NOT NULL,
                description VARCHAR(255),
                date TIMESTAMP,
                lieu VARCHAR(255),
                nombreMaxParticipants INT,
                prix FLOAT
            )
            """;
        try (Connection conn = DatabaseManager.getConnection();
             Statement st = conn.createStatement()) {
            st.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* ===== findAll ===== */
    @Override
    public List<Course> findAll() throws SQLException {
        List<Course> list = new ArrayList<>();
        String sql = """
        SELECT ID, NOM, description, date, lieu,
               NOMBREMAXPARTICIPANTS, prix
        FROM course
        ORDER BY id
        """;

        try (Connection c = DatabaseManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(map(rs));
        }
        return list;
    }



    /* ╔════════════════════════════════════════════════════════════╗
       ║             FIND BY NOM (insensible à la casse)            ║
       ╚════════════════════════════════════════════════════════════╝ */
    @Override
    public List<Course> findByNomContainingIgnoreCase(String nom) throws SQLException {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT * FROM course WHERE LOWER(nom) LIKE ?";

        try (Connection c = DatabaseManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, "%" + nom.toLowerCase() + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    @Override
    public Course findById(Long id) throws SQLException {
        String sql = """
        SELECT id, nom, description, date, lieu, nombreMaxParticipants, prix
          FROM course
         WHERE id = ?
        """;

        try (Connection c = DatabaseManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Timestamp ts = rs.getTimestamp("date");
                    return new Course(
                            rs.getLong("id"),
                            rs.getString("nom"),
                            rs.getString("description"),
                            ts != null ? ts.toLocalDateTime() : null,
                            rs.getString("lieu"),
                            rs.getInt("nombreMaxParticipants"),
                            rs.getFloat("prix")
                    );
                }
            }
        }
        return null;   // pas trouvé
    }


    /* ╔════════════════════════════════════════════════════════════╗
       ║                           SAVE                            ║
       ╚════════════════════════════════════════════════════════════╝ */
    @Override
    public void save(Course c) throws SQLException {

        String sql = """
        INSERT INTO course
        (nom, description, date, lieu, nombreMaxParticipants, prix)
        VALUES (?,?,?,?,?,?)
        """;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            bindCourse(ps, c);
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) c.setId(keys.getLong(1));
            }
        }
    }


    /* ╔════════════════════════════════════════════════════════════╗
       ║                          UPDATE                           ║
       ╚════════════════════════════════════════════════════════════╝ */
    @Override
    public void update(Course c) throws SQLException {
        if (c.getId() == null) {
            throw new IllegalArgumentException("id null : impossible de mettre à jour");
        }

        String sql = """
            UPDATE course
            SET nom = ?, description = ?, date = ?, lieu = ?,
                nombreMaxParticipants = ?, prix = ?
            WHERE id = ?
            """;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            bindCourse(ps, c);
            ps.setLong(7, c.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* ╔════════════════════════════════════════════════════════════╗
       ║            Méthode de binding commune (INSERT / UPDATE)    ║
       ╚════════════════════════════════════════════════════════════╝ */
    /** Remplit les 6 paramètres INSERT/UPDATE dans le bon ordre. */
    private void bindCourse(PreparedStatement ps, Course c) throws SQLException {

        ps.setString(1, c.getNom());

        ps.setString(2, c.getDescription());

        // 3) DATE
        if (c.getDate() != null) {
            ps.setTimestamp(3, Timestamp.valueOf(c.getDate()));
        } else {
            ps.setTimestamp(3, null);          // acceptera NULL
        }

        ps.setString(4, c.getLieu());

        ps.setInt   (5, c.getNombreMaxParticipants());

        ps.setFloat (6, c.getPrix());          // attention : 6ᵉ paramètre
    }


    /* ╔════════════════════════════════════════════════════════════╗
       ║             Mapping ResultSet  →  Entité Course            ║
       ╚════════════════════════════════════════════════════════════╝ */
    private Course map(ResultSet rs) throws SQLException {
        LocalDateTime date = null;
        Timestamp ts = rs.getTimestamp("date");
        if (ts != null) date = ts.toLocalDateTime();

        return new Course(
                rs.getLong("id"),
                rs.getString("nom"),
                rs.getString("description"),
                date,
                rs.getString("lieu"),
                rs.getInt("nombreMaxParticipants"),
                rs.getFloat("prix")
        );
    }
}
