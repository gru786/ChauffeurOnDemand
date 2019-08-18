package com.example.chauffeurondemand.com.beans;

import androidx.annotation.NonNull;

public class Driver {
    String id;

    @NonNull
    @Override
    public String toString() {
        return id+"             "+name;
    }

    public Driver() {
    }

    public Driver(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;
}
