package com.maville.model;

public class Resident extends User {
    protected String birthday;
    protected String phoneNumber;
    protected String address;

    private Resident(Builder builder) {
        super.name = builder.name;
        super.email = builder.email;
        super.password = builder.password;
        this.birthday = builder.birthday;
        this.phoneNumber = builder.phoneNumber;
        this.address = builder.address;
    }

    public static class Builder extends User.Builder {
        private String birthday;
        private String phoneNumber;
        private String address;

        public Builder() {
            this.birthday = build().getBirthday();
            this.phoneNumber = build().getPhoneNumber();
            this.address = build().getAddress();
        }

        @Override
        public Builder name(String name) {
            super.name(name);
            return this;  // Return the current Builder instance
        }

        @Override
        public Builder email(String email) {
            super.email(email);
            return this;  // Return the current Builder instance
        }

        @Override
        public Builder password(String password) {
            super.password(password);
            return this;  // Return the current Builder instance
        }

        public Builder birthday(String birthday) {
            this.birthday = birthday;
            return this;
        } // Added missing closing bracket

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        } // Added missing closing bracket

        public Builder address(String address) {
            this.address = address;
            return this;
        } // Added missing closing bracket

        @Override
        public Resident build() {
            return new Resident(this);
        }
    }

    // Getters
    public String getBirthday() { return this.birthday; }
    public String getPhoneNumber() { return this.phoneNumber; }
    public String getAddress() { return this.address; }
}
