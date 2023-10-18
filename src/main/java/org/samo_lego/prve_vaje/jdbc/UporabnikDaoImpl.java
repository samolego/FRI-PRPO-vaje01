package org.samo_lego.prve_vaje.jdbc;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class UporabnikDaoImpl implements BaseDao<Uporabnik> {

    private static final UporabnikDaoImpl instance = new UporabnikDaoImpl();
    private static final Logger           log = Logger.getLogger(UporabnikDaoImpl.class.getName());

    private final Connection connection;

    public static UporabnikDaoImpl getInstance() {
        return instance;
    }

    public UporabnikDaoImpl() {
        this.getConnection();
        this.connection = getConnection().orElse(null);
    }

    @Override
    public Optional<Connection> getConnection() {
        try {
            InitialContext initCtx = new InitialContext();
            DataSource ds = (DataSource) initCtx.lookup("jdbc/SimpleJdbcDS");

            return Optional.of(ds.getConnection());
        }
        catch (Exception e) {
            log.severe("Cannot get connection: " + e.toString());
        }

        return Optional.empty();
    }

    @Override
    public Optional<Uporabnik> vrni(int id) {
        final String sql = "SELECT * FROM uporabnik WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return Optional.of(getUporabnikFromRS(rs));
            } else {
                log.info("Uporabnik ne obstaja");
            }

        } catch (SQLException e) {
            log.severe(e.toString());
        }

        return Optional.empty();
    }

    @Override
    public void vstavi(Uporabnik ent) {
        //programska koda za vstavljanje uporabnikov

    }

    @Override
    public void odstrani(int id) {
        //programska koda za odstranjevanje uporabnikov
    }

    @Override
    public void posodobi(Uporabnik ent) {
        //programska koda za posodabljanje uporabnikov

    }

    @Override
    public List<Uporabnik> vrniVse() {
        final String sql = "SELECT * FROM uporabnik";
        final List<Uporabnik> entitete = new ArrayList<>();

        try (final Statement st = connection.createStatement()) {

            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                entitete.add(getUporabnikFromRS(rs));
            }

        }
        catch (SQLException e) {
            log.severe(e.toString());
        }

        return entitete;
    }

    private static Uporabnik getUporabnikFromRS(ResultSet rs) throws SQLException {
        String ime = rs.getString("ime");
        String priimek = rs.getString("priimek");
        String uporabniskoIme = rs.getString("uporabniskoime");

        return new Uporabnik(ime, priimek, uporabniskoIme);

    }
}
