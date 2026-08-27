package diti4.jee_to_spring.controller;

import diti4.jee_to_spring.DTO.ProductDTO;
import diti4.jee_to_spring.DTO.TypeProduitDTO;
import diti4.jee_to_spring.entity.TypeProduit;
import diti4.jee_to_spring.exception.ProductNotFoundException;
import diti4.jee_to_spring.exception.TypeProduitNotFoundException;
import diti4.jee_to_spring.service.TypeProduitService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import diti4.jee_to_spring.DTO.TypeProduitDTO;

@Controller
@RequestMapping("/type-produit")
public class TypeProduitController {

    private final TypeProduitService typeProduitService;

    public TypeProduitController(
            TypeProduitService typeProduitService) {

        this.typeProduitService = typeProduitService;
    }


    @GetMapping
    public String getList(
            @PageableDefault(size = 10, sort = "id") Pageable pageable,
            Model model) {

        Page<TypeProduitDTO> typeProduits =
                typeProduitService.findAll(pageable);

        model.addAttribute("typeProduits", typeProduits);

        return "type-produit";
    }


    // FORMULAIRE DE CREATION
    @GetMapping("/new")
    public String form(Model model) {

        model.addAttribute("typeProduit",  new TypeProduit());

        return "form-type-produit";
    }

    // CREATION
    @PostMapping
    public String save(
            @Valid @ModelAttribute("typeProduit") TypeProduitDTO typeProduit,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            return "type-product";
        }

        typeProduitService.save(typeProduit);

        return "redirect:/type-produit";
    }


    // FORMULAIRE DE MODIFICATION
    @GetMapping("/edit/{id}")
    public String edit(
            @PathVariable Long id,
            Model model) {

        try {
            TypeProduitDTO typeProduit = typeProduitService.findById(id);

            model.addAttribute("typeProduit", typeProduit);

            return "form-type-produit";

        } catch (ProductNotFoundException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "error";
        }
    }

    // MODIFICATION
    @PostMapping("/edit/{id}")
    public String update(
            @PathVariable Long id,
            @Valid @ModelAttribute("typeProduit") TypeProduitDTO typeProduit,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            return "form-type-produit";
        }

        try {
            typeProduitService.edit(id, typeProduit);
        } catch (TypeProduitNotFoundException ex) {
            model.addAttribute("serviceError", ex.getMessage());

            return "form-type-produit";
        }

        return "redirect:/type-produit";
    }

    // SUPPRESSION
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

        typeProduitService.delete(id);

        return "redirect:/type-produit";
    }

}