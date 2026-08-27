package diti4.jee_to_spring.REST;

import diti4.jee_to_spring.DTO.TypeProduitDTO;
import diti4.jee_to_spring.service.TypeProduitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/type-produits")
@Tag(
        name = "Types de produits",
        description = "API de gestion des types de produits"
)
public class TypeProduitRestController {

    private final TypeProduitService typeProduitService;

    public TypeProduitRestController(
            TypeProduitService typeProduitService) {

        this.typeProduitService = typeProduitService;
    }

    @Operation(
            summary = "Lister les types de produits",
            description = "Retourne la liste paginée des types de produits."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Liste des types de produits récupérée avec succès"
            )
    })
    @GetMapping
    public ResponseEntity<Page<TypeProduitDTO>> getList(
            @ParameterObject
            @PageableDefault(size = 10, sort = "id")
            Pageable pageable) {

        return ResponseEntity.ok(
                typeProduitService.findAll(pageable)
        );
    }

    @Operation(
            summary = "Créer un type de produit",
            description = "Crée un nouveau type de produit."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Type de produit créé avec succès",
                    content = @Content(
                            schema = @Schema(implementation = TypeProduitDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Données invalides",
                    content = @Content
            )
    })
    @PostMapping
    public ResponseEntity<TypeProduitDTO> save(
            @Valid @RequestBody TypeProduitDTO dto) {

        TypeProduitDTO saved =
                typeProduitService.save(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    }

    @Operation(
            summary = "Rechercher un type de produit",
            description = "Retourne un type de produit à partir de son identifiant."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Type de produit trouvé",
                    content = @Content(
                            schema = @Schema(implementation = TypeProduitDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Type de produit introuvable"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<TypeProduitDTO> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                typeProduitService.findById(id)
        );
    }

    @Operation(
            summary = "Modifier un type de produit",
            description = "Modifie un type de produit existant."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Type de produit modifié avec succès",
                    content = @Content(
                            schema = @Schema(implementation = TypeProduitDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Type de produit introuvable"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Données invalides"
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<TypeProduitDTO> edit(
            @PathVariable Long id,
            @Valid @RequestBody TypeProduitDTO dto) {

        return ResponseEntity.ok(
                typeProduitService.edit(id, dto)
        );
    }

    @Operation(
            summary = "Supprimer un type de produit",
            description = "Supprime un type de produit à partir de son identifiant."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Type de produit supprimé avec succès"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Type de produit introuvable"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        typeProduitService.delete(id);

        return ResponseEntity.noContent().build();
    }
}