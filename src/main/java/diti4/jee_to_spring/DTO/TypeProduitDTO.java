package diti4.jee_to_spring.DTO;

import jakarta.validation.constraints.NotBlank;

public class TypeProduitDTO {

    private Long id;

    @NotBlank(message = "Le libellé du type produit est obligatoire")
    private String libelle;


    public TypeProduitDTO() {
    }

    public TypeProduitDTO(Long id, String libelle) {
        this.id = id;
        this.libelle = libelle;
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

    @Override
    public String toString(){
        return libelle;
    }
}
