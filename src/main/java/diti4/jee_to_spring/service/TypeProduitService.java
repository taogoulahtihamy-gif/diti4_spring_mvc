package diti4.jee_to_spring.service;

import diti4.jee_to_spring.DTO.TypeProduitDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeProduitService {

    TypeProduitDTO save(TypeProduitDTO dto);

    Page<TypeProduitDTO> findAll(Pageable pageable);

    List<TypeProduitDTO> findAll();

    TypeProduitDTO findById(Long id);

    void delete(Long id);

    TypeProduitDTO edit(Long id, TypeProduitDTO dto);
}