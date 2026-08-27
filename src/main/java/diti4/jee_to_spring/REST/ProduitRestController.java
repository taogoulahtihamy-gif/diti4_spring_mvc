package diti4.jee_to_spring.REST;

import diti4.jee_to_spring.DTO.ProductDTO;
import diti4.jee_to_spring.exception.ProductNotFoundException;
import diti4.jee_to_spring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.web.PageableDefault;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springdoc.core.annotations.ParameterObject;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/produits")
@Tag(
        name = "Produits",
        description = "API de gestion des produits"
)
public class ProduitRestController {

    @Autowired
    private ProductService productService;

    @Operation(
            summary = "Lister les produits",
            description = "Retourne la liste paginée des produits."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Liste des produits récupérée avec succès"
            )
    })
    @GetMapping
    public ResponseEntity<Page<ProductDTO>> getList(
            @ParameterObject
            @PageableDefault(size = 10, sort = "id") Pageable pageable){
            return ResponseEntity.ok(
                    productService.findAll(pageable)
            );
    }

    @Operation(
            summary = "Créer un produit",
            description = "Crée un nouveau produit."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Produit créé avec succès",
                    content = @Content(
                            schema = @Schema(implementation = ProductDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Données invalides",
                    content = @Content
            )
    })
    @PostMapping
    public ResponseEntity<ProductDTO> save(@Valid @RequestBody ProductDTO produit){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.save(produit));
    }

    @Operation(
            summary = "Supprimer un produit",
            description = "Supprime un produit à partir de son identifiant."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Produit supprimé avec succès"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Produit introuvable"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){

        productService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Rechercher un produit",
            description = "Retourne un produit à partir de son identifiant."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Produit trouvé",
                    content = @Content(
                            schema = @Schema(implementation = ProductDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Produit introuvable"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getById(@PathVariable Long id){

//        return productService.findById(id)
//                .map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());

        ProductDTO produit = productService.findById(id);

        return ResponseEntity.ok(produit);
    }

    @Operation(
            summary = "Modifier un produit",
            description = "Modifie un produit existant."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Produit modifié avec succès",
                    content = @Content(
                            schema = @Schema(implementation = ProductDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Produit introuvable"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Données invalides"
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> edit(@PathVariable Long id,
                                           @Valid @RequestBody ProductDTO produit){

        return ResponseEntity.ok(productService.edit(id, produit));
    }

}