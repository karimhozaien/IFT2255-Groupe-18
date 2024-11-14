package com.maville.model;

import com.maville.view.MenuView;
import java.util.UUID;

public class User {
    protected final String ID;
    protected String name;
    protected String email;
    protected String password;

    protected User(Builder<?> builder) {
        this.ID = builder.id;
        this.name = builder.name;
        this.email = builder.email;
        this.password = builder.password;
    }

    public String getID() { return ID; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }

    // Fonction helper
    public void print() {
        MenuView.printMessage("Name: " + this.name);
        MenuView.printMessage("Email: " + this.email);
        MenuView.printMessage("Password: " + this.password);
    }

    // Generic Builder to support subclass builders
    public static abstract class Builder<T extends Builder<T>> {
        protected String id;
        protected String name;
        protected String email;
        protected String password;

        public T name(String name) {
            this.name = name;
            return self();
        }

        public T email(String email) {
            this.email = email;
            return self();
        }

        public T password(String password) {
            this.password = password;
            return self();
        }

        public T id() {
            this.id = UUID.randomUUID().toString();
            return self();
        }

        protected abstract T self();

        public abstract User build();
    }
}
