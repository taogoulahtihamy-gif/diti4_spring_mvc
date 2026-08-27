package diti4.jee_to_spring.service;


import diti4.jee_to_spring.DTO.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    ProductDTO save(ProductDTO dto);

    Page<ProductDTO> findAll(Pageable pageable);

    ProductDTO findById(Long id);

    void delete(Long id);

    ProductDTO edit(Long id, ProductDTO dto);
}