package a.tsv.projetcafe.service;

import a.tsv.projetcafe.Entity.Panier;
import a.tsv.projetcafe.Entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface du service de gestion des paniers. Cette interface étend JpaRepository pour fournir
 * des opérations de base sur les données de la table Panier.
 */
public interface PanierService extends JpaRepository<Panier, Long> {

    /**
     * Obtient les paniers d'un utilisateur spécifique.
     *
     * @param username le nom de l'utilisateur
     * @param allPaniers la liste de tous les paniers
     * @return la liste des paniers de l'utilisateur spécifié
     */
    default List<Panier> findPanierByUser(String username, List<Panier> allPaniers){
        List<Panier> paniers = new ArrayList<>();
        for (Panier panier : allPaniers) {
            if (panier.getUsername().equals(username)) {
                paniers.add(panier);
            }
        }
        return paniers;
    }

    /**
     * Vérifie qu'un panier existe, si il existe quantité +1, sinon il est créé.
     *
     * @param paniers la liste de tous les paniers
     * @param id l'id du produit
     * @param username le nom de l'utilisateur
     * @param produit le produit à ajouter
     * @return le panier géré
     */
    default Panier gestionPanier(List<Panier> paniers, Long id, String username, Produit produit){
        Panier panierAretourner = null;
        for (Panier panier : paniers){
            if (panier.getUsername().equals(username) && panier.getProduit().getId() == id){
                panier.setQuantite(panier.getQuantite()+1);
                panierAretourner = panier;
                break;
            }
            else {
                Panier paniero = new Panier(1,username,produit);
                panierAretourner = paniero;
            }
        }
        if (paniers.isEmpty()){
            Panier paniera = new Panier(1,username,produit);
            panierAretourner = paniera;
        }
        return panierAretourner;
    }

    /**
     * Trouve les paniers d'un utilisateur spécifique.
     *
     * @param username le nom de l'utilisateur
     * @param allPanier la liste de tous les paniers
     * @return la liste des paniers de l'utilisateur spécifié
     */
    default List<Panier> findPaniersByUser(String username,List<Panier> allPanier){
        List<Panier> panierARetourner = new ArrayList<>();
        for(Panier panier : allPanier){
            if (panier.getUsername().equals(username)){
                panierARetourner.add(panier);
            }
        }
        return panierARetourner;
    }
}