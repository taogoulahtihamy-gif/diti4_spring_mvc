package diti4.jee_to_spring.repository;

import diti4.jee_to_spring.entity.TypeProduit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypeProduitRepository extends JpaRepository<TypeProduit,Long> {

}
