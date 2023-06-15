# Café OSE

Café OSE est un site de réservation de café destiné aux entreprises et aux clients. Ce fichier README fournit des informations sur l'installation, l'utilisation et les fonctionnalités de l'application.

## Guide d'installation

Le système Café OSE nécessite une base de données pour fonctionner. Créez un nouveau schéma dans votre base de données préférée, puis indiquez les informations appropriées dans le fichier `application.properties`.

Ensuite, vous pouvez lancer l'application à partir de votre environnement de développement (IDE) en exécutant le fichier `ProjetCafeApplication`.

## Guide d'utilisation

### Créer un compte

Pour créer un compte, cliquez sur le lien "S'inscrire" dans la barre de navigation supérieure. Cela vous dirigera vers un formulaire où vous pourrez choisir un nom d'utilisateur et un mot de passe.

### Se connecter

Pour vous connecter, cliquez sur le lien "Se connecter" dans la barre de navigation supérieure. Vous serez redirigé vers une page de connexion où vous devrez saisir votre nom d'utilisateur et votre mot de passe, puis cliquer sur le bouton "Se connecter".

### Ajouter un produit au panier

Pour ajouter un produit à votre panier, vous devez être connecté. Il vous suffit de cliquer sur le bouton "Ajouter au panier" situé sous le prix du produit que vous souhaitez acheter.

### Accéder à votre panier

Pour accéder à votre panier, vous devez être connecté. Cliquez sur le lien "Panier" dans la barre de navigation supérieure. Vous pourrez alors afficher votre panier et ajuster les quantités des produits en utilisant les boutons "+" et "-".

### Voir mes commandes

Vous pouvez accéder à deux types de commandes : les commandes prêtes à être récupérées en magasin et les commandes en attente. Pour accéder à ces informations, cliquez sur les liens appropriés dans la barre de navigation.

### Voir mon historique

Pour accéder à votre historique de commandes, vous devez être connecté. Cliquez sur le lien "Panier" dans la barre de navigation. Cela affichera l'historique de vos commandes précédentes.

## Guide du propriétaire

### Se connecter

La procédure de connexion est la même que celle décrite dans le guide d'utilisation. Cependant, vous devez vous connecter avec un compte d'administrateur (ADMIN).

### Modifier un produit

Pour modifier un produit, vous devez être connecté en tant qu'administrateur. Il vous suffit de cliquer sur le lien "Modifier le produit" situé sous le prix du produit que vous souhaitez modifier.

### Voir les commandes

Vous pouvez accéder aux commandes prêtes à être récupérées en magasin et aux commandes en attente. Pour accéder à ces informations, cliquez sur les liens appropriés dans la barre de navigation.

### Changer l'état des commandes

Une fois sur la page affichant la liste des commandes, vous pouvez effectuer les actions suivantes :
- Lorsqu'une commande est prête à être récupérée en magasin, vous pouvez la marquer comme "Commande prête".
- Lorsqu'une commande a été récupérée par un client, vous pouvez la marquer comme "Commande livrée".

### Historique

Pour accéder à l'historique des commandes, vous devez être connecté en tant qu'administrateur. Cliquez sur le lien "Panier" dans la barre de navigation. Cela affichera l'historique des commandes livrées à tous les clients, triées par ordre décroissant de date de livraison.

## Développeurs

L'application a été développée en utilisant le langage de programmation Java. Le code est entièrement commenté à l'aide de Javadoc. L'application utilise le framework Spring et suit le modèle MVC (Modèle-Vue-Contrôleur).

Technologies utilisées :
- Thymeleaf
- Validation
- Spring REST Docs
- Spring Web
- Spring Data JPA
- Bootstrap
- Spring Security

Tests unitaires :
IMPORTANT : Pour exécuter les tests unitaires, assurez-vous d'utiliser une base de données distincte en modifiant le fichier `application.properties`. Sinon, des données de test pourraient être ajoutées à votre base de données de production.

---

Veuillez noter que cette documentation décrit l'application Café OSE. Si vous avez des questions supplémentaires ou avez besoin d'une assistance supplémentaire, n'hésitez pas à nous contacter.
envoi