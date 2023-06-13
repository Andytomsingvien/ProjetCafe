package a.tsv.projetcafe.service;

import a.tsv.projetcafe.Entity.Commande;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface du service de gestion des commandes. Cette interface étend JpaRepository pour fournir
 * des opérations de base sur les données de la table Commande.
 */
public interface CommandeService extends JpaRepository<Commande, Long> {

    /**
     * Obtient les commandes pour un utilisateur spécifique.
     *
     * @param username le nom de l'utilisateur
     * @param allCommande la liste de toutes les commandes
     * @return la liste des commandes de l'utilisateur spécifié
     */
    default List<Commande> getCommandesByUsername(String username, List<Commande> allCommande) {
        List<Commande> commandesUser = new ArrayList<>();
        for (Commande commande : allCommande) {
            if (commande.getUsername().equals(username)) {
                commandesUser.add(commande);
            }
        }
        return commandesUser;
    }
    /**
     * Trouve les commandes selon leur état.
     *
     * @param commandeList la liste de toutes les commandes
     * @param etats l'état des commandes à trouver
     * @return la liste des commandes avec l'état spécifié
     */
    default List<Commande> findCommandeByEtats(List<Commande> commandeList, int etats){
        List<Commande> cmdARetourner = new ArrayList<>();
        for (Commande commande : commandeList){
            if (commande.getEtats()==etats){
                cmdARetourner.add(commande);
            }
        }
        return cmdARetourner;
    }
}
