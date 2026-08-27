package diti4.jee_to_spring.repository;

import diti4.jee_to_spring.entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Produit, Long> {

}
