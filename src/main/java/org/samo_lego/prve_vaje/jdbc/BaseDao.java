package org.samo_lego.prve_vaje.jdbc;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface BaseDao<E extends Entiteta> {

    Optional<Connection> getConnection();

    Optional<E> vrni(int id);

    void vstavi(E ent);

    void odstrani(int id);

    void posodobi(E ent);

    List<E> vrniVse();

}
