package diti4.jee_to_spring.mapper;

import diti4.jee_to_spring.DTO.ProductDTO;
import diti4.jee_to_spring.DTO.TypeProduitDTO;
import diti4.jee_to_spring.entity.Produit;
import diti4.jee_to_spring.entity.TypeProduit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO toDTO(Produit produit);

    TypeProduitDTO toDTO(TypeProduit typeProduit);

    TypeProduit toEntity(TypeProduitDTO dto);

    Produit toEntity(ProductDTO dto);

//    @Mapping(target = "typeProduit")
//    Produit toEntity(ProductDTO dto);
}