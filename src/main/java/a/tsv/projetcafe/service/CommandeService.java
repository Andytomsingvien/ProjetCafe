package a.tsv.projetcafe.service;

import a.tsv.projetcafe.Entity.Commande;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface CommandeService extends JpaRepository<Commande, Long> {

    default List<Commande> getCommandesByUsername(String username, List<Commande> allCommande) {
        List<Commande> commandesUser = new ArrayList<>();
        for (Commande commande : allCommande) {
            if (commande.getUsername().equals(username)) {
                commandesUser.add(commande);
            }
        }
        return commandesUser;
    }

    default List<Commande> getCommandesEnCoursByUsername(List<Commande> commandesDeUtilisateur) {
        List<Commande> commandesUserEnCours = new ArrayList<>();
        for (Commande commande : commandesDeUtilisateur) {
            if (commande.getEtats() == 1) {
                commandesUserEnCours.add(commande);
            }
        }
        return commandesUserEnCours;
    }

    default List<Commande> getCommandespreteByUsername(List<Commande> commandesDeUtilisateur) {
        List<Commande> commandesUserPrete = new ArrayList<>();
        for (Commande commande : commandesDeUtilisateur) {
            if (commande.getEtats() == 2) {
                commandesUserPrete.add(commande);
            }
        }
        return commandesUserPrete;
    }
}
