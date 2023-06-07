package a.tsv.projetcafe.service;

import a.tsv.projetcafe.Entity.Panier;
import a.tsv.projetcafe.Entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface PanierService extends JpaRepository<Panier, Long> {

     default List findPanierByUser(String username, List<Panier> allPaniers){
         List<Panier> paniers = new ArrayList<>();
        for (Panier panier : allPaniers) {
            if (panier.getUsername().equals(username)) {
                paniers.add(panier);
            }
        }
        return paniers;
    }

    default Panier gestionPanier(List<Panier> paniers, Long id, String username, Produit produit){
         Panier panierAretourner = null;
        for (Panier panier : paniers){
            if (panier.getUsername().equals(username)&& panier.getProduit().getId() == id){
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
