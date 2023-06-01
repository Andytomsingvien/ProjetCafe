package a.tsv.projetcafe.service;

import a.tsv.projetcafe.Entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitService extends JpaRepository<Produit, Long> {
}
