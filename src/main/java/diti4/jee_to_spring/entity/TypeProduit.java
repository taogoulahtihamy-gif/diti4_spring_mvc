package diti4.jee_to_spring.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "type_produits")
public class TypeProduit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le libellé du type produit est obligatoire")
    private String libelle;

    @JsonIgnore
    @OneToMany(mappedBy = "typeProduit")
    private List<Produit> produits = new ArrayList<>();

    public TypeProduit() {
    }

    public TypeProduit(Long id, String libelle) {
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

    public List<Produit> getProduits() {
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }

    @Override
    public String toString() {
        return "TypeProduit{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
