package com.maville.model;

public class User {
    protected String name;
    protected String email;
    protected String password;

    // Getter methods
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }

    // Builder class for building Utilisateur instances
    public static class Builder {
        protected String name;
        protected String email;
        protected String password;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }


        // Build method to create a Utilisateur instance
        public User build() {
            return new User();
        }
    }
}
