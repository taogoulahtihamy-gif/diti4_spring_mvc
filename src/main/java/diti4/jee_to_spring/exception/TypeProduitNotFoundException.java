package diti4.jee_to_spring.exception;

public class TypeProduitNotFoundException extends RuntimeException {

    public TypeProduitNotFoundException(Long id) {
        super("Type produit avec l'id " + id + " introuvable");
    }
}