class Intervenant {
    private final String nom;
    private final String email;
    private final String mdp;
    private final int identifiant;
    private final TypeEntreprise typeEntreprise; 

    public enum TypeEntreprise {
        PRIVEE(1, "Privée"),
        PUBLIQUE(2, "Publique"),
        PARTICULIER(3, "Particulier");

        private final int valeur;
        private final String affichageType;

        TypeEntreprise(int valeur, String affichageType) {
            this.valeur = valeur;
            this.affichageType = affichageType;
        }

        public String getAffichageType() {
            return affichageType;
        }

        public int getValeur() {
            return valeur;
        }

        public static TypeEntreprise stringAType(int choix) {
            for (TypeEntreprise typeEntreprise : TypeEntreprise.values()) {
                if (typeEntreprise.getValeur() == choix) {
                    return typeEntreprise;
                }
            }
            throw new IllegalArgumentException("Ce type d'entreprise d'existe pas.");
        }
    }
    
    public Intervenant(IntervenantBuilder builder) {
        this.nom = builder.nom;
        this.email = builder.email;
        this.mdp = builder.mdp;
        this.identifiant = builder.identifiant;
        this.typeEntreprise = builder.typeEntreprise;
    }

    public String getNom() { return this.nom; }
    public String getEmail() { return this.email; }
    public String getMdp() { return this.mdp; }
    public int getIdentifiant() { return this.identifiant; }
    public TypeEntreprise getTypeEntreprise() { return this.typeEntreprise; }

    public static class IntervenantBuilder {
        private String nom;
        private String email;
        private String mdp;
        private int identifiant;
        private TypeEntreprise typeEntreprise; 

        public IntervenantBuilder nom(String nom) {
            this.nom = nom;
            return this;
        }

        public IntervenantBuilder email(String email) {
            this.email = email;
            return this;
        }

        public IntervenantBuilder mdp(String mdp) {
            this.mdp = mdp;
            return this;
        }

        public IntervenantBuilder identifiant(String identifiant) {
            this.identifiant = Integer.parseInt(identifiant);
            return this;
        }

        public IntervenantBuilder typeEntreprise(int choix) {
            this.typeEntreprise = TypeEntreprise.stringAType(choix);
            return this;
        }

        public Intervenant build() throws Exception {
            if (this.nom == null || this.nom.trim().isEmpty()) {
                throw new Exception("Le nom ne peut pas être vide. Veuillez réessayer.");
            }
            if (this.email == null || this.email.trim().isEmpty()) {
                throw new Exception("L'email ne peut pas être vide. Veuillez réessayer.");
            }
            // Lancement d'erreurs
            return new Intervenant(this); // Retourne l'instanciation de l'intervenant
        }
    }
}