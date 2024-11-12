package com.maville.model;

import com.maville.view.MenuView;

public class Intervenant extends User {
    private final int identifier;
    private final CompanyType companyType;

    // Enum for Company Types
    public enum CompanyType {
        PRIVATE(1, "Private"),
        PUBLIC(2, "Public"),
        INDIVIDUAL(3, "Individual");

        private final int value;
        private final String displayType;

        CompanyType(int value, String displayType) {
            this.value = value;
            this.displayType = displayType;
        }

        public String getDisplayType() {
            return displayType;
        }

        public int getValue() {
            return value;
        }

        public static CompanyType stringToType(int choice) {
            for (CompanyType companyType : CompanyType.values()) {
                if (companyType.getValue() == choice) {
                    return companyType;
                }
            }
            throw new IllegalArgumentException("This company type does not exist.");
        }
    }

    // Constructor
    private Intervenant(IntervenantBuilder builder) {
        super(builder);
        this.identifier = builder.identifier;
        this.companyType = builder.companyType;
    }

    // Builder Class for Intervenant
    public static class IntervenantBuilder extends User.Builder<IntervenantBuilder> {
        private int identifier;
        private CompanyType companyType;

        // Methods to set builder fields
        public IntervenantBuilder identifier(String identifier) {
            this.identifier = Integer.parseInt(identifier);
            return this;
        }

        public IntervenantBuilder companyType(int choice) {
            this.companyType = CompanyType.stringToType(choice);
            return this;
        }

        @Override
        protected IntervenantBuilder self() {
            return this;
        }

        @Override
        public Intervenant build() {
            return new Intervenant(this);
        }
    }

    // Getters
    public int getIdentifier() {
        return this.identifier;
    }

    public CompanyType getCompanyType() {
        return this.companyType;
    }

    @Override
    public void print() {
        super.print();
        MenuView.printMessage("Identifier: " + this.identifier);
        MenuView.printMessage("Company Type: " + this.companyType.getDisplayType());
    }
}
