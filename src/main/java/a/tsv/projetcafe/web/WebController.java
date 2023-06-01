package a.tsv.projetcafe.web;

import a.tsv.projetcafe.Entity.Panier;
import a.tsv.projetcafe.Entity.Produit;
import a.tsv.projetcafe.service.PanierService;
import a.tsv.projetcafe.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
public class WebController {
    @Autowired
    private ProduitService produitService;
    @Autowired
    private PanierService panierService;
    @GetMapping(path ={"/","/index"})
    public String addAllProduits(Model model) {

        List<Produit> produits = produitService.findAll();
        model.addAttribute("produits", produits);
        return "index";
    }

    @GetMapping(path = "/produit/{id}")
    public String getProduitById(@PathVariable Long id,Authentication authentication, Model model){
        Optional<Produit> optionalProduit = produitService.findById(id);
        Produit produit = optionalProduit.orElseThrow(() -> new IllegalArgumentException("Produit non trouvé pour l'ID: " + id));
        model.addAttribute("produit", produit);
        model.addAttribute("username", authentication.getName());

        return "produit";
    }

    @GetMapping(path = "/panier/{username}/{id}")
    public String createPanierWithUsernameAndProduit(@PathVariable Long id, Authentication authentication, Model model){
        String username = authentication.getName();
        Optional<Produit> optionalProduit = produitService.findById(id);

        Produit produit = optionalProduit.orElseThrow(() -> new NoSuchElementException("Produit introuvable"));
        List<Panier> paniers = panierService.findAll();
        Panier panierACreer = panierService.gestionPanier(paniers,id,username,produit);
        panierService.save(panierACreer);
        model.addAttribute("username", username);

        return "redirect:/index";  // changement ici
    }

    @GetMapping(path = "/panier/{username}")
    public String printPanier(Authentication authentication, Model model) {
        String username = authentication.getName();
        List<Panier> allPaniers = panierService.findAll();
        List<Panier> paniers = panierService.findPanierByUser(username, allPaniers);
        model.addAttribute("panier", paniers);
        model.addAttribute("username", username);

        return "panier";
    }

    @GetMapping(path = "/panier/augmenterQuantite/{id}")

    public String augmenterQuantite(@PathVariable Long id, Model model,Authentication authentication){
        Panier panierConcerne = panierService.getReferenceById(id);
        panierConcerne.setQuantite(panierConcerne.getQuantite()+1);
        panierService.save(panierConcerne);
        return "redirect:/panier/" + authentication.getName();
  }

    @GetMapping(path = "/panier/diminuerQuantite/{id}")

    public String diminuerQuantite(@PathVariable Long id, Model model,Authentication authentication){
        Panier panierConcerne = panierService.getReferenceById(id);
        if (panierConcerne.getQuantite()>1){
            panierConcerne.setQuantite(panierConcerne.getQuantite()-1);
            panierService.save(panierConcerne);

        }
        else {
            panierService.delete(panierConcerne);
        }
        return "redirect:/panier/" + authentication.getName();
            }
}
