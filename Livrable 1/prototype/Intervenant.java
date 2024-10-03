class Intervenant {
    private final String nom;
    private final String email;
    private final String mdp;
    private final int identifiant;
    private final TypeEntreprise typeEntreprise; 

    public enum TypeEntreprise {
        PRIVATE("Private"),
        PUBLIC("Public"),
        PARTICULIER("Particulier");

        private final String affichageType;

        TypeEntreprise(String affichageType) {
            this.affichageType = affichageType;
        }

        public String getAffichageType() {
            return affichageType;
        }

        public static TypeEntreprise stringAType(String type) {
            for (TypeEntreprise typeEntreprise : TypeEntreprise.values()) {
                if (typeEntreprise.getAffichageType().equalsIgnoreCase(type)) {
                    return typeEntreprise;
                }
            }
            throw new IllegalArgumentException("Invalid type: " + type);
        }
    }
    
    public Intervenant(IntervenantBuilder builder) {
        this.nom = builder.nom;
        this.email = builder.email;
        this.mdp = builder.mdp;
        this.identifiant = builder.identifiant;
        this.typeEntreprise = builder.typeEntreprise;
    }

    public static class IntervenantBuilder {
        private String nom;
        private String email;
        private String mdp;
        private int identifiant;
        private TypeEntreprise typeEntreprise; 

        public IntervenantBuilder getNom(String nom) {
            this.nom = nom;
            return this;
        }

        public IntervenantBuilder getEmail(String email) {
            this.email = email;
            return this;
        }

        public IntervenantBuilder getMdp(String mdp) {
            this.mdp = mdp;
            return this;
        }

        public IntervenantBuilder getIdentifiant(String identifiant) {
            this.identifiant = Integer.parseInt(identifiant);
            return this;
        }

        public IntervenantBuilder getTypeEntreprise(String type) {
            this.typeEntreprise = TypeEntreprise.stringAType(type);
            return this;
        }

        public Intervenant build() {
            // Lancement d'erreurs
            return null; // Retourne l'instanciation de l'intervenant
        }
    }
}