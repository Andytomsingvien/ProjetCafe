package a.tsv.projetcafe.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="commande")
public class Commande {
    public Commande(int id, String username, LocalDateTime dateCreation, LocalDateTime dateCloture, int etats, double montant, List<CommandeProduit> commandeProduits, String user) {
        this.id = id;
        this.username = username;
        this.dateCreation = dateCreation;
        this.dateCloture = dateCloture;
        this.etats = etats;
        this.montant = montant;
        this.commandeProduits = commandeProduits;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="username")
    private String username;

    @Column(name="date_creation")
    private LocalDateTime dateCreation;

    @Column(name="date_cloture")
    private LocalDateTime dateCloture;

    @Column(name="etats")
    private int etats;

    @Column(name="montant")
    private double montant;

    @OneToMany(mappedBy="commande")
    private List<CommandeProduit> commandeProduits;

    @Column(nullable = false)
    private String user;

    public Commande() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDateTime getDateCloture() {
        return dateCloture;
    }

    public void setDateCloture(LocalDateTime dateCloture) {
        this.dateCloture = dateCloture;
    }

    public int getEtats() {
        return etats;
    }

    public void setEtats(int etats) {
        this.etats = etats;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public List<CommandeProduit> getCommandeProduits() {
        return commandeProduits;
    }

    public void setCommandeProduits(List<CommandeProduit> commandeProduits) {
        this.commandeProduits = commandeProduits;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    // Getters et setters...
}
