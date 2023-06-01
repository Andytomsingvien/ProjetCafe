package a.tsv.projetcafe.service;

import a.tsv.projetcafe.Entity.CommandeProduit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeProduitService extends JpaRepository<CommandeProduit, Long> {
}
