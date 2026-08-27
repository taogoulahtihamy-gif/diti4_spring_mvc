package diti4.jee_to_spring.service.impl;

import diti4.jee_to_spring.DTO.TypeProduitDTO;
import diti4.jee_to_spring.entity.TypeProduit;
import diti4.jee_to_spring.exception.TypeProduitNotFoundException;
import diti4.jee_to_spring.mapper.ProductMapper;
import diti4.jee_to_spring.repository.TypeProduitRepository;
import diti4.jee_to_spring.service.TypeProduitService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeProduitServiceImpl implements TypeProduitService {

    private final TypeProduitRepository repository;
    private final ProductMapper productMapper;

    public TypeProduitServiceImpl(
            TypeProduitRepository repository,
            ProductMapper productMapper) {

        this.repository = repository;
        this.productMapper = productMapper;
    }

    @Override
    public TypeProduitDTO save(TypeProduitDTO dto) {

        TypeProduit typeProduit = productMapper.toEntity(dto);

        typeProduit.setId(null);

        TypeProduit saved = repository.save(typeProduit);

        return productMapper.toDTO(saved);
    }

    @Override
    public Page<TypeProduitDTO> findAll(Pageable pageable) {

        return repository.findAll(pageable)
                .map(productMapper::toDTO);
    }

    @Override
    public List<TypeProduitDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(productMapper::toDTO)
                .toList();
    }

    @Override
    public TypeProduitDTO findById(Long id) {

        TypeProduit typeProduit = repository.findById(id)
                .orElseThrow(() ->
                        new TypeProduitNotFoundException(id)
                );

        return productMapper.toDTO(typeProduit);
    }

    @Override
    public void delete(Long id) {

        if (!repository.existsById(id)) {
            throw new TypeProduitNotFoundException(id);
        }

        repository.deleteById(id);
    }

    @Override
    public TypeProduitDTO edit(Long id, TypeProduitDTO dto) {

        TypeProduit typeProduit = repository.findById(id)
                .orElseThrow(() ->
                        new TypeProduitNotFoundException(id)
                );

        typeProduit.setLibelle(dto.getLibelle());

        TypeProduit updated = repository.save(typeProduit);

        return productMapper.toDTO(updated);
    }
}