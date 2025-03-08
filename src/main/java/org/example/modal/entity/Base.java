package org.example.modal.entity;

import java.io.Serializable;

abstract class Base implements Serializable {
    protected long id;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}

