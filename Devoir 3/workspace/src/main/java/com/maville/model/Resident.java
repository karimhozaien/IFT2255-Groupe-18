package com.maville.model;

import com.maville.view.MenuView;

public class Resident extends User {
    private String birthday;
    private String phoneNumber;
    private String address;

    private Resident(ResidentBuilder builder) {
        super(builder);
        this.birthday = builder.birthday;
        this.phoneNumber = builder.phoneNumber;
        this.address = builder.address;
    }

    public static class ResidentBuilder extends User.Builder<ResidentBuilder> {
        private String birthday;
        private String phoneNumber;
        private String address;

        public ResidentBuilder birthday(String birthday) {
            this.birthday = birthday;
            return this;
        }

        public ResidentBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public ResidentBuilder address(String address) {
            this.address = address;
            return this;
        }

        @Override
        protected ResidentBuilder self() {
            return this;
        }

        @Override
        public Resident build() {
            return new Resident(this);
        }
    }

    // Getters
    public String getBirthday() {
        return this.birthday;
    }
    public String getPhoneNumber() {
        return this.phoneNumber;
    }
    public String getResidentialAddress() {
        return this.address;
    }

    @Override
    public void print() {
        super.print();
        MenuView.printMessage("Birthday: " + this.birthday);
        MenuView.printMessage("Phone number: " + this.phoneNumber);
        MenuView.printMessage("Address: " + this.address);
    }
}
