package a.tsv.projetcafe.service;

import a.tsv.projetcafe.Entity.Commande;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeService extends JpaRepository<Commande, Long> {
}
