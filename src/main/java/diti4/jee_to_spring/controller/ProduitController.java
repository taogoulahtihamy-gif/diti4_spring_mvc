package diti4.jee_to_spring.controller;

import diti4.jee_to_spring.DTO.ProductDTO;
import diti4.jee_to_spring.exception.ProductNotFoundException;
import diti4.jee_to_spring.exception.TypeProduitNotFoundException;
import diti4.jee_to_spring.service.ProductService;
import diti4.jee_to_spring.service.TypeProduitService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/produit")
public class ProduitController {

    private final ProductService productService;
    private final TypeProduitService typeProduitService;

    public ProduitController(
            ProductService productService,
            TypeProduitService typeProduitService) {

        this.productService = productService;
        this.typeProduitService = typeProduitService;
    }

    @GetMapping
    public String getList(
            @PageableDefault(size = 10, sort = "id") Pageable pageable,
            Model model) {

        Page<ProductDTO> produits =
                productService.findAll(pageable);

        model.addAttribute("produits", produits);

        return "produit";
    }

    // FORMULAIRE DE CREATION
    @GetMapping("/new")
    public String form(Model model) {

        model.addAttribute("produit", new ProductDTO());

        model.addAttribute(
                "typeProduits",
                typeProduitService.findAll()
        );

        return "form-product";
    }


    // CREATION
    @PostMapping
    public String save(
            @Valid @ModelAttribute("produit") ProductDTO produit,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {

            model.addAttribute(
                    "typeProduits",
                    typeProduitService.findAll()
            );

            return "form-product";
        }

        productService.save(produit);

        return "redirect:/produit";
    }

    // FORMULAIRE DE MODIFICATION
    @GetMapping("/edit/{id}")
    public String edit(
            @PathVariable Long id,
            Model model) {

        try {
            ProductDTO produit = productService.findById(id);

            model.addAttribute("produit", produit);
            model.addAttribute("typeProduits", typeProduitService.findAll());

            return "form-product";

        } catch (ProductNotFoundException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "error";
        }
    }

    // MODIFICATION
    @PostMapping("/edit/{id}")
    public String update(
            @PathVariable Long id,
            @Valid @ModelAttribute("produit") ProductDTO produit,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {

            model.addAttribute(
                    "typeProduits",
                    typeProduitService.findAll()
            );

            return "form-product";
        }

        try {
            productService.edit(id, produit);
        } catch (TypeProduitNotFoundException ex) {
            model.addAttribute("serviceError", ex.getMessage());
            model.addAttribute("typeProduits", typeProduitService.findAll());

            return "form-product";
        }

        return "redirect:/produit";
    }

    // SUPPRESSION
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

        productService.delete(id);

        return "redirect:/produit";
    }
}