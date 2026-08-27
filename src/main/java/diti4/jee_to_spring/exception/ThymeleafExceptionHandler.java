package diti4.jee_to_spring.exception;

import diti4.jee_to_spring.controller.ProduitController;
import diti4.jee_to_spring.controller.TypeProduitController;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = {
        ProduitController.class,
        TypeProduitController.class
})
public class ThymeleafExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public String handleProductNotFound(
            ProductNotFoundException ex,
            Model model) {

        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("status", 404);

        return "error";
    }

    @ExceptionHandler(TypeProduitNotFoundException.class)
    public String handleTypeProduitNotFound(
            TypeProduitNotFoundException ex,
            Model model) {

        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("status", 404);

        return "error";
    }
}