package diti4.jee_to_spring.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(Long id) {
        super("Produit avec l'id " + id + " introuvable");
    }
}
