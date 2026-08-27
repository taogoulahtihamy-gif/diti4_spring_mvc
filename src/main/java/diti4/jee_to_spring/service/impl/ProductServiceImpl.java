package diti4.jee_to_spring.service.impl;

import diti4.jee_to_spring.DTO.ProductDTO;
import diti4.jee_to_spring.entity.Produit;
import diti4.jee_to_spring.entity.TypeProduit;
import diti4.jee_to_spring.exception.ProductNotFoundException;
import diti4.jee_to_spring.exception.TypeProduitNotFoundException;
import diti4.jee_to_spring.mapper.ProductMapper;
import diti4.jee_to_spring.repository.ProductRepository;
import diti4.jee_to_spring.repository.TypeProduitRepository;
import diti4.jee_to_spring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository repository;
    private ProductMapper productMapper;
    private TypeProduitRepository typeProduitRepository;


    public ProductServiceImpl(TypeProduitRepository  typeProduitRepository,
    ProductMapper productMapper, ProductRepository repository) {
        this.typeProduitRepository = typeProduitRepository;
        this.productMapper = productMapper;
        this.repository = repository;
    }


    @Override
    public ProductDTO save(ProductDTO dto) {

        Produit produit = productMapper.toEntity(dto);

        produit.setId(null);

        if (dto.getTypeProduit() == null ||
                dto.getTypeProduit().getId() == null) {

            throw new TypeProduitNotFoundException(null);
        }

        Long typeId = dto.getTypeProduit().getId();

        TypeProduit typeProduit =
                typeProduitRepository.findById(typeId)
                        .orElseThrow(() ->
                                new TypeProduitNotFoundException(typeId)
                        );

        produit.setTypeProduit(typeProduit);

        Produit saved = repository.save(produit);

        return productMapper.toDTO(saved);
    }

    @Override
    public Page<ProductDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(productMapper::toDTO);
    }

    @Override
    public ProductDTO findById(Long id) {

        return repository.findById(id)
                .map(productMapper::toDTO)
                .orElseThrow(() ->
                        new ProductNotFoundException(id)
                );
    }

    @Override
    public void delete(Long id) {

        repository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException(id)
                );

        repository.deleteById(id);
    }

    @Override
    public ProductDTO edit(Long id, ProductDTO dto) {

        Produit produit = repository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException(id)
                );

        produit.setId(id);
        produit.setLibelle(dto.getLibelle());
        produit.setPrix(dto.getPrix());

        if (dto.getTypeProduit() == null ||
                dto.getTypeProduit().getId() == null) {

            throw new TypeProduitNotFoundException(null);
        }

        TypeProduit typeProduit =
                typeProduitRepository
                        .findById(dto.getTypeProduit().getId())
                        .orElseThrow(() ->
                                new TypeProduitNotFoundException(
                                        dto.getTypeProduit().getId()
                                )
                        );

        produit.setTypeProduit(typeProduit);

        Produit updated = repository.save(produit);

        return productMapper.toDTO(updated);
    }

//    private ProductDTO toDTO(Produit produit){
//        ProductDTO dto=new ProductDTO();
//
//        dto.setId(produit.getId());
//        dto.setLibelle(produit.getLibelle());
//        dto.setPrix(produit.getPrix());
//
//        if(produit.getTypeProduit()!=null){
//            TypeProduitDTO typeDTO=new TypeProduitDTO();
//            typeDTO.setId(produit.getTypeProduit().getId());
//            typeDTO.setLibelle(produit.getTypeProduit().getLibelle());
//            dto.setTypeProduit(typeDTO);
//        }
//
//        return dto;
//    }
//
//    private Produit toEntity(ProductDTO dto){
//        Produit produit=new Produit();
//
//        produit.setId(dto.getId());
//        produit.setLibelle(dto.getLibelle());
//        produit.setPrix(dto.getPrix());
//
//        if(dto.getTypeProduit()!=null && dto.getTypeProduit().getId()!=null){
//            TypeProduit type=typeProduitRepository
//                    .findById(dto.getTypeProduit().getId())
//                    .orElseThrow(()->new RuntimeException("Type produit introuvable"));
//
//            produit.setTypeProduit(type);
//        }
//
//        return produit;
//    }

}