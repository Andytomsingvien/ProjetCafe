package a.tsv.projetcafe.service;

import a.tsv.projetcafe.Entity.CommandeProduit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface CommandeProduitService extends JpaRepository<CommandeProduit, Long> {
    default List<CommandeProduit> findByIdCommande(List<CommandeProduit> allCommandeProduit,long id){
        List<CommandeProduit> listARetourner = new ArrayList<>();
        for(CommandeProduit commandeProduit:allCommandeProduit){
            if (commandeProduit.getCommande().getId()==id){
                listARetourner.add(commandeProduit);
            }
        }
        return listARetourner;
    }
}
