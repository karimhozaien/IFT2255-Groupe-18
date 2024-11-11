package com.maville.model;

public class Intervenant extends User {
    protected int identifier;
    protected CompanyType companyType;

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

    private Intervenant(Builder builder) {
        super.name = builder.name;
        super.email = builder.email;
        super.password = builder.password;
        this.identifier = builder.identifier;
        this.companyType = builder.companyType;
    }

    public static class Builder extends User.Builder {
        private int identifier;
        private CompanyType companyType;

        public Builder() {
            this.identifier = build().getIdentifier();
            this.companyType = build().getCompanyType();
        }

        @Override
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        @Override
        public Builder email(String email) {
            this.email = email;
            return this;
        }

        @Override
        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder identifier(String identifier) {
            this.identifier = Integer.parseInt(identifier);
            return this;
        }

        public Builder companyType(int choice) {
            this.companyType = CompanyType.stringToType(choice);
            return this;
        }

        @Override
        public Intervenant build() {
            return new Intervenant(this);
        }
    }

    // Getters
    public int getIdentifier() { return this.identifier; }
    public CompanyType getCompanyType() { return this.companyType; }
}