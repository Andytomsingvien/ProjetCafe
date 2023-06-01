package a.tsv.projetcafe.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="pannier")

public class Panier {
        @Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        @Column(name="id")
        private int id;

        @Column(name="quantite")
        private int quantite;

        @Column(nullable = false)
        private String username;

        @ManyToOne
        @JoinColumn(name = "id_produit", referencedColumnName = "id", nullable = false)
        private Produit produit;

        public Panier() {
        }

        public Panier(int quantite, String user, Produit produit) {
                this.quantite = quantite;
                this.username = user;
                this.produit = produit;
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

        public String getUsername() {
                return username;
        }

        public void setUsername(String user) {
                this.username = user;
        }

        public Produit getProduit() {
                return produit;
        }

        public void setProduit(Produit produit) {
                this.produit = produit;
        }

        public List findPannierByUser(String username, List<Panier> allPaniers, List<Panier> paniers){
                for (Panier panier : allPaniers) {
                        if (panier.getUsername().equals(username)) {
                                paniers.add(panier);
                        }
                }
                return paniers;
        }
}
