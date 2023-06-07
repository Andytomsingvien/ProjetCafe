package a.tsv.projetcafe.Entity;

import jakarta.persistence.*;

@Entity
@Table(name="commande_produit")
public class CommandeProduit {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="quantite")
    private int quantite;

    @ManyToOne
    @JoinColumn(name="id_commande", nullable=false)
    private Commande commande;

    @ManyToOne
    @JoinColumn(name="id_produit", nullable=false)
    private Produit produit;

    public CommandeProduit(int quantite, Commande commande, Produit produit) {
        this.quantite = quantite;
        this.commande = commande;
        this.produit = produit;
    }

    public CommandeProduit() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }
}

