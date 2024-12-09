package com.maville.model;

import com.maville.view.MenuView;
import java.util.UUID;

/**
 * Classe représentant un utilisateur générique dans le système MaVille.
 */
public class User {
    protected final String ID;
    protected String name;
    protected String email;
    protected String password;

    /**
     * Constructeur protégé pour initialiser un utilisateur via un Builder.
     *
     * @param builder Le Builder contenant les données nécessaires pour créer l'utilisateur.
     */
    protected User(Builder<?> builder) {
        this.ID = builder.id;
        this.name = builder.name;
        this.email = builder.email;
        this.password = builder.password;
    }

    /**
     * Retourne l'identifiant unique de l'utilisateur.
     *
     * @return L'identifiant unique.
     */
    public String getID() {
        return ID;
    }

    /**
     * Retourne le nom de l'utilisateur.
     *
     * @return Le nom de l'utilisateur.
     */
    public String getName() {
        return name;
    }

    /**
     * Retourne l'adresse email de l'utilisateur.
     *
     * @return L'adresse email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Retourne le mot de passe de l'utilisateur.
     *
     * @return Le mot de passe.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Affiche les informations principales de l'utilisateur dans la console.
     */
    public void print() {
        MenuView.printMessage("Nom : " + this.name);
        MenuView.printMessage("Email : " + this.email);
        MenuView.printMessage("Mot de passe : " + this.password);
    }

    /**
     * Classe interne abstraite utilisée comme base pour construire des objets User.
     *
     * @param <T> Le type du Builder spécifique.
     */
    public static abstract class Builder<T extends Builder<T>> {
        protected String id;
        protected String name;
        protected String email;
        protected String password;

        /**
         * Définit le nom de l'utilisateur.
         *
         * @param name Le nom de l'utilisateur.
         * @return L'instance courante du Builder.
         */
        public T name(String name) {
            this.name = name;
            return self();
        }

        /**
         * Définit l'adresse email de l'utilisateur.
         *
         * @param email L'adresse email.
         * @return L'instance courante du Builder.
         */
        public T email(String email) {
            this.email = email;
            return self();
        }

        /**
         * Définit le mot de passe de l'utilisateur.
         *
         * @param password Le mot de passe.
         * @return L'instance courante du Builder.
         */
        public T password(String password) {
            this.password = password;
            return self();
        }

        /**
         * Définit un identifiant unique aléatoire pour l'utilisateur.
         *
         * @return L'instance courante du Builder.
         */
        public T id() {
            this.id = UUID.randomUUID().toString();
            return self();
        }

        /**
         * Méthode à implémenter pour retourner l'instance courante du Builder.
         *
         * @return L'instance courante du Builder.
         */
        protected abstract T self();

        /**
         * Construit l'objet User.
         *
         * @return Une instance de User.
         */
        public abstract User build();
    }
}