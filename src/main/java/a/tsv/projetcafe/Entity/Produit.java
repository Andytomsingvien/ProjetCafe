package a.tsv.projetcafe.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="produit")
public class Produit {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="nom")
    private String nom;

    @Column(name="origine")
    private String origine;

    @Column(name="description")
    private String description;

    @Column(name="prix")
    private double prix;

    @Column(name="image")
    private String image;

    @OneToMany(mappedBy="produit")
    private List<CommandeProduit> commandeProduits;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getOrigine() {
        return origine;
    }

    public void setOrigine(String origine) {
        this.origine = origine;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public List<CommandeProduit> getCommandeProduits() {
        return commandeProduits;
    }

    public void setCommandeProduits(List<CommandeProduit> commandeProduits) {
        this.commandeProduits = commandeProduits;
    }
public Produit(){

}
    public Produit(String nom, String origine, String description, double prix, String image) {
        this.nom = nom;
        this.origine = origine;
        this.description = description;
        this.prix = prix;
        this.image = image;
    }

    // Getters et setters...
}

