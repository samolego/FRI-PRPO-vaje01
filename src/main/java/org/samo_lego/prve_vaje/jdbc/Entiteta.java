package org.samo_lego.prve_vaje.jdbc;

import java.io.Serializable;

public abstract class Entiteta implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}