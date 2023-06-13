package a.tsv.projetcafe.web;

import a.tsv.projetcafe.Entity.Commande;
import a.tsv.projetcafe.Entity.CommandeProduit;
import a.tsv.projetcafe.Entity.Panier;
import a.tsv.projetcafe.Entity.Produit;
import a.tsv.projetcafe.service.CommandeProduitService;
import a.tsv.projetcafe.service.CommandeService;
import a.tsv.projetcafe.service.PanierService;
import a.tsv.projetcafe.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Classe WebController.
 *
 * Cette classe est un controlleur Spring qui gère les différentes requêtes HTTP liées à la gestion des produits,
 * paniers, commandes et leurs états.
 */
@Controller
public class WebController {
    @Autowired
    private ProduitService produitService;
    @Autowired
    private PanierService panierService;
    @Autowired
    private CommandeService commandeService;
    @Autowired
    private CommandeProduitService commandeProduitService;

    /**
     * Méthode pour ajouter tous les produits au modèle et les retourner sous forme de page index.
     * Gère les exceptions en cas d'échec de l'opération.
     *
     * @param model Le modèle Spring MVC
     * @return La vue index
     * @throws RuntimeException si une erreur se produit lors de la récupération des produits
     */
    @GetMapping(path = {"/", "/index"})
    public String addAllProduits(Model model) {
        try {
            List<Produit> produits = produitService.findAll();
            List<Produit> produitAfficher = produitService.findProduitAficher(produits);
            produitAfficher.sort(Comparator.comparing(Produit::getImage));
            model.addAttribute("produits", produitAfficher);
            return "index";
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'ajout de tous les produits au modèle", e);
        }
    }

    /**
     * Méthode pour obtenir un produit spécifique par son ID et l'ajouter au modèle.
     * Gère les exceptions en cas d'échec de l'opération.
     *
     * @param id             L'ID du produit
     * @param authentication Authentification de l'utilisateur
     * @param model          Le modèle Spring MVC
     * @return La vue produit
     * @throws IllegalArgumentException si le produit avec l'ID spécifié n'est pas trouvé
     * @throws RuntimeException         si une autre erreur se produit lors de la récupération du produit
     */
    @GetMapping(path = "/produit/{id}")
    public String getProduitById(@PathVariable Long id, Authentication authentication, Model model) {
        try {
            Optional<Produit> optionalProduit = produitService.findById(id);
            Produit produit = optionalProduit.orElseThrow(() -> new IllegalArgumentException("Produit non trouvé pour l'ID: " + id));
            model.addAttribute("produit", produit);
            model.addAttribute("username", authentication.getName());

            return "produit";
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'obtention du produit par son ID", e);
        }
    }

    /**
     * Méthode pour créer un panier avec un nom d'utilisateur et un produit spécifiques.
     * Gère les exceptions en cas d'échec de l'opération.
     *
     * @param id             L'ID du produit
     * @param authentication Authentification de l'utilisateur
     * @param model          Le modèle Spring MVC
     * @return Redirige vers l'index en cas de succès, affiche une erreur sinon
     * @throws RuntimeException si une erreur se produit lors de la création du panier
     */
    @GetMapping(path = "/panier/{username}/{id}")
    public String createPanierWithUsernameAndProduit(@PathVariable Long id, Authentication authentication, Model model) {
        try {
            String username = authentication.getName();
            Optional<Produit> optionalProduit = produitService.findById(id);

            if (!optionalProduit.isPresent()) {
                model.addAttribute("message", "Produit introuvable");
                return "error";
            }

            Produit produit = optionalProduit.get();
            List<Panier> paniers = panierService.findAll();
            Panier panierACreer = panierService.gestionPanier(paniers, id, username, produit);

            if (panierACreer == null) {
                model.addAttribute("message", "Erreur dans la création du panier");
                return "error";
            }

            panierService.save(panierACreer);
            model.addAttribute("username", username);

            return "redirect:/index";
        } catch (Exception e) {
            throw new RuntimeException("Une erreur inattendue s'est produite lors de la création du panier", e);
        }
    }


    /**
     * Méthode pour afficher le panier d'un utilisateur.
     * Gère les exceptions en cas d'échec de l'opération.
     *
     * @param authentication Authentification de l'utilisateur
     * @param model          Le modèle Spring MVC
     * @return La vue panier
     * @throws RuntimeException si une erreur se produit lors de l'affichage du panier
     */
    @GetMapping(path = "/panier/{username}")
    public String printPanier(Authentication authentication, Model model) {
        try {
            String username = authentication.getName();
            List<Panier> allPaniers = panierService.findAll();
            List<Panier> paniers = panierService.findPanierByUser(username, allPaniers);
            model.addAttribute("panier", paniers);
            model.addAttribute("username", username);

            return "panier";
        } catch (Exception e) {
            throw new RuntimeException("Une erreur inattendue s'est produite lors de l'affichage du panier", e);
        }
    }


    /**
     * Méthode pour augmenter la quantité d'un produit dans le panier.
     * Gère les exceptions en cas d'échec de l'opération.
     *
     * @param id             L'ID du panier
     * @param authentication Authentification de l'utilisateur
     * @return Redirection vers la vue du panier de l'utilisateur
     * @throws RuntimeException si une erreur se produit lors de l'augmentation de la quantité
     */
    @GetMapping(path = "/panier/augmenterQuantite/{id}")
    public String augmenterQuantite(@PathVariable Long id, Authentication authentication) {
        try {
            Panier panierConcerne = panierService.getReferenceById(id);
            panierConcerne.setQuantite(panierConcerne.getQuantite() + 1);
            panierService.save(panierConcerne);
            return "redirect:/panier/" + authentication.getName();
        } catch (Exception e) {
            throw new RuntimeException("Une erreur inattendue s'est produite lors de l'augmentation de la quantité", e);
        }
    }

    /**
     * Méthode pour diminuer la quantité d'un produit dans le panier.
     * Gère les exceptions en cas d'échec de l'opération.
     *
     * @param id             L'ID du panier
     * @param authentication Authentification de l'utilisateur
     * @return Redirection vers la vue du panier de l'utilisateur
     * @throws RuntimeException si une erreur se produit lors de la diminution de la quantité
     */
    @GetMapping(path = "/panier/diminuerQuantite/{id}")
    public String diminuerQuantite(@PathVariable Long id, Authentication authentication) {
        try {
            Panier panierConcerne = panierService.getReferenceById(id);
            if (panierConcerne.getQuantite() > 1) {
                panierConcerne.setQuantite(panierConcerne.getQuantite() - 1);
                panierService.save(panierConcerne);
            } else {
                panierService.delete(panierConcerne);
            }
            return "redirect:/panier/" + authentication.getName();
        } catch (Exception e) {
            throw new RuntimeException("Une erreur inattendue s'est produite lors de la diminution de la quantité", e);
        }
    }

    /**
     * Méthode pour valider le panier d'un utilisateur.
     * Gère les exceptions en cas d'échec de l'opération.
     *
     * @param dateReception  La date de réception prévue
     * @param model          Le modèle Spring MVC
     * @param authentication Authentification de l'utilisateur
     * @return Redirection vers l'index
     * @throws RuntimeException si une erreur se produit lors de la validation du panier
     */
    @PostMapping(path = "/panier/valider")
    public String validationPanier(@RequestParam("dateReception") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime dateReception, Model model, Authentication authentication) {
        try {
            LocalDateTime currentDate = LocalDateTime.now();
            if (dateReception.isBefore(currentDate)) {
                String errorMessage = "La date de réception ne peut pas être antérieure à la date actuelle.";
                model.addAttribute("errorMessage", errorMessage);
                return "error";
            }
            String username = authentication.getName();
            List<Panier> listeAllPanier = panierService.findAll();
            List<Panier> paniersByUser = panierService.findPaniersByUser(username, listeAllPanier);
            double montant = 0;
            for (Panier panier : paniersByUser) {
                montant = montant + (panier.getQuantite() * panier.getProduit().getPrix());
            }
            Commande commande = new Commande(username, dateReception, 1, montant);
            commandeService.save(commande);
            for (Panier panier : paniersByUser) {
                Produit produitDuPanier = panier.getProduit();
                CommandeProduit commandeProduit = new CommandeProduit(panier.getQuantite(), commande, produitDuPanier);
                commandeProduitService.save(commandeProduit);
            }
            for (Panier panier : paniersByUser) {
                panierService.delete(panier);
            }
            return "redirect:/index";
        } catch (Exception e) {
            throw new RuntimeException("Une erreur inattendue s'est produite lors de la validation du panier", e);
        }
    }

    /**
     * Méthode pour afficher les commandes en cours d'un utilisateur.
     * Gère les exceptions en cas d'échec de l'opération.
     *
     * @param model          Le modèle Spring MVC
     * @param authentication Authentification de l'utilisateur
     * @return La vue des commandes en cours
     * @throws RuntimeException si une erreur se produit lors de la récupération des commandes en cours
     */
    @GetMapping(path = "/commandes/encours")
    public String afficherCommandesEnCours(Model model, Authentication authentication) {
        try {
            String username = authentication.getName();
            List<Commande> allCommande = commandeService.findAll();
            List<Commande> commandesDeUtilisateur = commandeService.getCommandesByUsername(username, allCommande);
            List<Commande> commandesByEtats = commandeService.findCommandeByEtats(commandesDeUtilisateur,1);
            model.addAttribute("commandes", commandesByEtats);
            return "commandeEnCours";
        } catch (Exception e) {
            throw new RuntimeException("Une erreur inattendue s'est produite lors de la récupération des commandes en cours", e);
        }
    }

    /**
     * Méthode pour afficher les commandes prêtes d'un utilisateur.
     * Gère les exceptions en cas d'échec de l'opération.
     *
     * @param model          Le modèle Spring MVC
     * @param authentication Authentification de l'utilisateur
     * @return La vue des commandes prêtes
     * @throws RuntimeException si une erreur se produit lors de la récupération des commandes prêtes
     */
    @GetMapping(path = "/commandes/pretes")
    public String afficherCommandesprêtes(Model model, Authentication authentication) {
        try {
            String username = authentication.getName();
            List<Commande> allCommande = commandeService.findAll();
            List<Commande> commandesDeUtilisateur = commandeService.getCommandesByUsername(username, allCommande);
            List<Commande> commandesByEtats = commandeService.findCommandeByEtats(commandesDeUtilisateur, 2);
            commandesByEtats.sort(Comparator.comparing(Commande::getDateCloture));

            model.addAttribute("commandes", commandesByEtats);
            return "commandesPretes";
        } catch (Exception e) {
            throw new RuntimeException("Une erreur inattendue s'est produite lors de la récupération des commandes prêtes", e);
        }
    }

    /**
     * Méthode pour afficher les commandes déjà livrée d'un utilisateur.
     * Gère les exceptions en cas d'échec de l'opération.
     *
     * @param model          Le modèle Spring MVC
     * @param authentication Authentification de l'utilisateur
     * @return La vue des commandes déjà livrée
     * @throws RuntimeException si une erreur se produit lors de la récupération des commandes déjà livrée
     */
    @GetMapping(path = "/commandes/historique")
    public String afficherCommandesHistorique(Model model, Authentication authentication) {
        try {
            String username = authentication.getName();
            List<Commande> allCommande = commandeService.findAll();
            List<Commande> commandesDeUtilisateur = commandeService.getCommandesByUsername(username, allCommande);
            List<Commande> commandesByEtats = commandeService.findCommandeByEtats(commandesDeUtilisateur, 3);
            commandesByEtats.sort(Comparator.comparing(Commande::getDateCloture));
            model.addAttribute("commandes", commandesByEtats);
            return "historiqueClient";
        } catch (Exception e) {
            throw new RuntimeException("Une erreur inattendue s'est produite lors de la récupération de votre historique", e);
        }
    }


    /*
    ********************************Partie Concernant l'ADMIN***********************************************************
     */

    /**
     * Méthode pour afficher les commandes de la première étape (lorsque l'utilisateur valide son panier (stage1)).
     * Gère les exceptions en cas d'échec de l'opération.
     *
     * @param model Le modèle Spring MVC
     * @return La vue des commandes de la première étape
     * @throws RuntimeException si une erreur se produit lors de la récupération des commandes de la première étape
     */
    @GetMapping(path = "/admin/commandes/stage1")
    public String afficherCommandeStage1(Model model) {
        try {
            List<Commande> allCommandes = commandeService.findAll();
            List<Commande> allCommandesStage1 = commandeService.findCommandeByEtats(allCommandes, 1);
            allCommandesStage1.sort(Comparator.comparing(Commande::getDateCloture));
            model.addAttribute("commandes", allCommandesStage1);
            return "commandeStage1";
        } catch (Exception e) {
            throw new RuntimeException("Une erreur inattendue s'est produite lors de la récupération des commandes de la première étape", e);
        }
    }

    /**
     * Méthode pour passer une commande de la première étape à la deuxième.
     * Gère les exceptions en cas d'échec de l'opération.
     *
     * @param id L'ID de la commande
     * @return Redirection vers la vue des commandes de la première étape
     * @throws NoSuchElementException si la commande n'est pas trouvée
     */
    @GetMapping(path = "/admin/augmenterEtatCommande1to2/{id}")
    public String changerEtatsCommande1to2(@PathVariable Long id) {
        try {
            Optional<Commande> optionalCommande = commandeService.findById(id);
            Commande commande = optionalCommande.orElseThrow(() -> new NoSuchElementException("Commande introuvable"));
            commande.setEtats(commande.getEtats() + 1);
            commandeService.save(commande);
            return "redirect:/admin/commandes/stage1";
        } catch (Exception e) {
            throw new RuntimeException("Une erreur inattendue s'est produite lors du passage de la commande à l'étape suivante", e);
        }
    }

    /**
     * Méthode pour afficher les commandes de la deuxième étape (lorsque la commande est prête à être réceptionnée (stage2)).
     * Gère les exceptions en cas d'échec de l'opération.
     *
     * @param model Le modèle Spring MVC
     * @return La vue des commandes de la deuxième étape
     * @throws RuntimeException si une erreur se produit lors de la récupération des commandes de la deuxième étape
     */
    @GetMapping(path = "/admin/commandes/stage2")
    public String afficherCommandeStage2(Model model) {
        try {
            List<Commande> allCommandes = commandeService.findAll();
            List<Commande> allCommandesStage2 = commandeService.findCommandeByEtats(allCommandes, 2);
            allCommandesStage2.sort(Comparator.comparing(Commande::getUsername));
            model.addAttribute("commandes", allCommandesStage2);
            return "commandeStage2";
        } catch (Exception e) {
            throw new RuntimeException("Une erreur inattendue s'est produite lors de la récupération des commandes de la deuxième étape", e);
        }
    }

    /**
     * Méthode pour passer une commande de la deuxième étape à la troisième.
     * Gère les exceptions en cas d'échec de l'opération.
     *
     * @param id L'ID de la commande
     * @return Redirection vers la vue des commandes de la deuxième étape
     * @throws NoSuchElementException si la commande n'est pas trouvée
     */
    @GetMapping(path = "/admin/augmenterEtatCommande2to3/{id}")
    public String changerEtatsCommande2to3(@PathVariable Long id) {
        try {
            Optional<Commande> optionalCommande = commandeService.findById(id);
            Commande commande = optionalCommande.orElseThrow(() -> new NoSuchElementException("Commande introuvable"));
            commande.setEtats(commande.getEtats() + 1);
            commande.setDateCloture(LocalDateTime.now());
            commandeService.save(commande);
            return "redirect:/admin/commandes/stage2";
        } catch (Exception e) {
            throw new RuntimeException("Une erreur inattendue s'est produite lors du passage de la commande à l'étape suivante", e);
        }
    }

    /**
     * Méthode pour afficher les commandes de la deuxième étape (lorsque la commande est réceptionnée par le client (stage3)).
     * Gère les exceptions en cas d'échec de l'opération.
     *
     * @param model Le modèle Spring MVC
     * @return La vue des commandes de la troisième étape
     * @throws RuntimeException si une erreur se produit lors de la récupération des commandes de la troisième étape
     */
    @GetMapping(path = "/admin/commandes/stage3")
    public String afficherCommandestage3(Model model) {
        try {
            List<Commande> allCommandes = commandeService.findAll();
            List<Commande> allCommandesStage3 = commandeService.findCommandeByEtats(allCommandes, 3);
            allCommandesStage3.sort(Comparator.comparing(Commande::getDateCloture));
            model.addAttribute("commandes", allCommandesStage3);
            return "commandeStage3";
        } catch (Exception e) {
            throw new RuntimeException("Une erreur inattendue s'est produite lors de la récupération des commandes de la troisième étape", e);
        }
    }

    /**
     * Méthode pour ajouter un nouveau produit.
     * Gère les exceptions en cas d'échec de l'opération.
     *
     * @param produit L'entité du produit à ajouter
     * @return Redirection vers la page d'accueil
     * @throws RuntimeException si une erreur se produit lors de l'ajout d'un produit
     */
    @GetMapping(path = "/admin/ajouter/produits")
    public String ajouterNouveauProduit(@ModelAttribute Produit produit) {
        try {
            produitService.save(produit);
            return "redirect:/index";
        } catch (Exception e) {
            throw new RuntimeException("Une erreur inattendue s'est produite lors de l'ajout du produit", e);
        }
    }
    /**
     * Méthode pour modifier un produit.
     * Gère les exceptions en cas d'échec de l'opération.
     *
     * @param id    L'identifiant du produit à modifier.
     * @param produit L'entité du produit à modifier
     * @return Redirection vers la page d'accueil
     * @throws RuntimeException si une erreur se produit lors de la modification d'un produit
     */
    @GetMapping(path = "/admin/modifier/produit/{id}")
    public String modifierProduit(@ModelAttribute Produit produit,@PathVariable long id) {
        try {
            Optional<Produit> optionalUpdate = produitService.findById(id);
            Produit update = optionalUpdate.orElseThrow(() -> new NoSuchElementException("Produit introuvable"));
            update.setNom(produit.getNom());
            update.setOrigine(produit.getOrigine());
            update.setPrix(produit.getPrix());
            update.setDescription(produit.getDescription());
            update.setImage(produit.getImage());
            produitService.save(update);
            return "redirect:/index";
        } catch (Exception e) {
            throw new RuntimeException("Une erreur inattendue s'est produite lors de l'ajout du produit", e);
        }
    }
    /**
     * Renvoie la page de formulaire pour modifier un produit.
     *
     * @param id    L'identifiant du produit à modifier.
     * @param model Le modèle utilisé pour transmettre des données à la vue.
     * @return La vue "formModifierProduit".
     * @throws NoSuchElementException Si le produit avec l'identifiant donné n'est pas trouvé.
     */
    @GetMapping(path = "/admin/modifierProduit/{id}")
    public String formulaireModifProduit(@PathVariable long id, Model model) {
        Optional<Produit> optionalProduit = produitService.findById(id);
        Produit produit = optionalProduit.orElseThrow(() -> new NoSuchElementException("Produit introuvable"));
        model.addAttribute("produit", produit);
        return "formModifierProduit";
    }

    /**
     * Supprime un produit.
     *
     * @param id L'identifiant du produit à supprimer.
     * @return La redirection vers la page d'accueil.
     * @throws RuntimeException Si une erreur inattendue se produit lors de la suppression du produit.
     */
    @GetMapping(path = "/admin/supprimer/produit/{id}")
    public String supprimerProduit(@PathVariable long id) {
        try {
            Optional<Produit> optionalProduit = produitService.findById(id);
            Produit produit = optionalProduit.orElseThrow(() -> new NoSuchElementException("Produit introuvable"));
            produit.setOrigine("supprimer");
            produitService.save(produit);
            return "redirect:/index";
        } catch (Exception e) {
            throw new RuntimeException("Une erreur inattendue s'est produite lors de la suppression du produit", e);
        }
    }

    /**
     * Supprime une commande.
     *
     * @param id L'identifiant de la commande à supprimer.
     * @return La redirection vers la page "stage2" des commandes d'administration.
     * @throws RuntimeException Si une erreur inattendue se produit lors de la suppression de la commande.
     */
    @GetMapping(path = "/admin/supprimerCommande/{id}")
    public String supprimerCommande(@PathVariable long id) {
        try {
            List<CommandeProduit> allCommandeProduit = commandeProduitService.findAll();
            List<CommandeProduit> commandeProduitDeLaCommande = commandeProduitService.findByIdCommande(allCommandeProduit, id);
            for (CommandeProduit commandeProduit : commandeProduitDeLaCommande) {
                commandeProduitService.delete(commandeProduit);
            }
            commandeService.deleteById(id);
            return "redirect:/admin/commandes/stage2";
        } catch (Exception e) {
            throw new RuntimeException("Une erreur inattendue s'est produite lors de la suppression de la commande", e);
        }
    }


    /**
     * Méthode pour accéder au formulaire d'ajout de produit.
     * Gère les exceptions en cas d'échec de l'opération.
     *
     * @return La vue du formulaire pour ajouter un produit
     * @throws RuntimeException si une erreur se produit lors de l'accès au formulaire d'ajout de produit
     */
    @GetMapping(path = "/admin/formProduit")
    public String formulaireProduit() {
        try {
            return "formAjouterProduit";
        } catch (Exception e) {
            throw new RuntimeException("Une erreur inattendue s'est produite lors de l'accès au formulaire d'ajout de produit", e);
        }
    }
}
