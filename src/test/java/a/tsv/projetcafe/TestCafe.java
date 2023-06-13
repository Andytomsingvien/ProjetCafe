package a.tsv.projetcafe;

import a.tsv.projetcafe.Entity.Commande;
import a.tsv.projetcafe.Entity.Produit;
import a.tsv.projetcafe.service.CommandeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestCafe {
    Produit produit = new Produit();
    String username = "andy";
    @Autowired
    CommandeService commandeService;
    @Test
    public void TestGetCmdByEtats(){
        produit.setId(3);
        produit.setNom("Arabica");
        produit.setOrigine("Maroc");
        produit.setDescription("Très bon café");
        produit.setPrix(15.90);
        produit.setImage("arabica.jpg");
        LocalDateTime dateCommande = LocalDateTime.now();
        LocalDateTime dateReception = dateCommande.plusMonths(1);
        Double montant = 15.90;
        Commande commande1 = new Commande("Andy", dateReception, 1, 15.90);
        Commande commande2 = new Commande("Theo", dateReception, 1, 18.90);
        Commande commande3 = new Commande("Andy", dateReception, 2, 10);
        List<Commande> allCommande = new ArrayList<>();
        allCommande.add(commande1);
        allCommande.add(commande2);
        allCommande.add(commande3);

        List<Commande> trueList = new ArrayList<>();
        trueList.add(commande1);
        trueList.add(commande2);
        List<Commande> testedList = commandeService.findCommandeByEtats(allCommande,1);
        assertEquals(trueList.get(1),testedList.get(1));
    }

    @Test
    public void TestGetCmdByUser(){
        LocalDateTime dateCommande = LocalDateTime.now();
        LocalDateTime dateReception = dateCommande.plusMonths(1);
        Double montant = 15.90;
        Commande commande1 = new Commande("Andy", dateReception, 1, 15.90);
        Commande commande2 = new Commande("Theo", dateReception, 1, 18.90);
        Commande commande3 = new Commande("Andy", dateReception, 2, 10);
        List<Commande> allCommande = new ArrayList<>();
        allCommande.add(commande1);
        allCommande.add(commande2);
        allCommande.add(commande3);

        List<Commande> trueList = new ArrayList<>();
        trueList.add(commande1);
        trueList.add(commande3);
        List<Commande> testedList = commandeService.getCommandesByUsername("Andy", allCommande);
        assertEquals(testedList,trueList,"Les usernames ne sont pas identiques");
    }

    }
