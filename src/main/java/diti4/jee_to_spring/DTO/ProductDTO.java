package diti4.jee_to_spring.DTO;


import diti4.jee_to_spring.entity.TypeProduit;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ProductDTO {

    private Long id;

    @NotBlank(message = "Le libellé est obligatoire")
    private String libelle;

    @NotNull(message = "Le prix est obligatoire")
    @Positive(message = "Le prix doit être positif")
    private Double prix;

    @JoinColumn(name = "type_produit")
    @NotNull(message = "Le type produit est obligatoire")
    private TypeProduitDTO typeProduit;


    public ProductDTO() {
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getLibelle() {
        return libelle;
    }


    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }


    public Double getPrix() {
        return prix;
    }


    public void setPrix(Double prix) {
        this.prix = prix;
    }


    public TypeProduitDTO getTypeProduit() {
        return typeProduit;
    }


    public void setTypeProduit(TypeProduitDTO typeProduit) {
        this.typeProduit = typeProduit;
    }
}