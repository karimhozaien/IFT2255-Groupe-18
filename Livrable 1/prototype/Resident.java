import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class Resident {
    private final String nom;
    private final LocalDate dateNaissance;
    private final String email;
    private final String mdp;
    private final String telephone;
    private final String adresse;

    public Resident(ResidentBuilder builder) {
        this.nom = builder.nom;
        this.dateNaissance = builder.dateNaissance;
        this.email = builder.email;
        this.mdp = builder.mdp;
        this.telephone = builder.telephone;
        this.adresse = builder.adresse;
    }

    public String getNom() { return this.nom; }
    public LocalDate getDateNaissance() { return this.dateNaissance; }
    public String getEmail() { return this.email; }
    public String getMdp() { return this.mdp; }
    public String getTelephone() { return this.telephone; }
    public String getAdresse() { return this.adresse; }

    public static class ResidentBuilder {
        private String nom;
        private LocalDate dateNaissance;
        private String email;
        private String mdp;
        private String telephone;
        private String adresse;

        public ResidentBuilder nom(String nom) {
            this.nom = nom;
            return this;
        }

        public ResidentBuilder dateNaissance(String naissance) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            this.dateNaissance = LocalDate.parse(naissance.trim(), formatter);
            return this;
        }

        public ResidentBuilder email(String email) {
            this.email = email;
            return this;
        }

        public ResidentBuilder mdp(String mdp) {
            this.mdp = mdp;
            return this;
        }

        public ResidentBuilder telephone(String telephone) {
            this.telephone = telephone;
            return this;
        }

        public ResidentBuilder adresse(String adresse) {
            this.adresse = adresse;
            return this;
        }

        public Resident build() {
            // Lancement d'erreurs
            return new Resident(this); // Retourne l'instanciation du résident
        }
    }
}
