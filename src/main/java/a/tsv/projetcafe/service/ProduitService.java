package a.tsv.projetcafe.service;

import a.tsv.projetcafe.Entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface ProduitService extends JpaRepository<Produit, Long> {
    default List<Produit> findProduitAfficher(List<Produit> allProduit){
        List<Produit> produitARetouner = new ArrayList<>();
        String supprimer = "supprimer";
        for(Produit produit : allProduit){
            if (produit.getOrigine().equals(supprimer)){
            }
            else {
                produitARetouner.add(produit);
            }
        }
        return produitARetouner;
    }
}
