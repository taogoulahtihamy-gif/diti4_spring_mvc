//package diti4.jee_to_spring.exception;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import jakarta.validation.ConstraintViolationException;
//import java.util.HashMap;
//import java.util.Map;

//@RestControllerAdvice //(@ControllerAdvice et @ResponseBody)
////Sans @RestControllerAdvice on placerait sur chaque controller
//// @ExceptionHandler(ConstraintViolationException.class)
//// @ControllerAdvice Elle indique à Spring :"Cette classe contient des traitements
//// communs pour plusieurs contrôleurs."
//// Grâce à : @RestControllerAdvice Spring va chercher dans : ValidationExceptionHandler
//// pour savoir quoi faire.
//public class ValidationExceptionHandler {
//
//    @ExceptionHandler(ConstraintViolationException.class)
//    // "Cette méthode doit être exécutée lorsqu'une exception de type
//    // ConstraintViolationException apparaît."
//    public ResponseEntity<Map<String,String>> handleValidation(
//            ConstraintViolationException ex){
//
//        Map<String,String> errors = new HashMap<>();
//
//        ex.getConstraintViolations()
//                .forEach(error ->
//                        errors.put(
//                                error.getPropertyPath().toString(),
//                                error.getMessage()
//                        )
//                );
//
//        return ResponseEntity
//                .status(HttpStatus.BAD_REQUEST)
//                .body(errors);
//    }
//
//    @ExceptionHandler(ProductNotFoundException.class)
//    public ResponseEntity<String> handleProductNotFound(
//            ProductNotFoundException ex){
//
//        return ResponseEntity
//                .status(HttpStatus.NOT_FOUND)
//                .body(ex.getMessage());
//    }
//}


package diti4.jee_to_spring.exception;

import diti4.jee_to_spring.REST.ProduitRestController;
import diti4.jee_to_spring.REST.TypeProduitRestController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice(assignableTypes = {
        ProduitRestController.class,
        TypeProduitRestController.class
})//(@ControllerAdvice et @ResponseBody)
//Sans @RestControllerAdvice on placerait sur chaque controller
// @ExceptionHandler(ConstraintViolationException.class)
// @ControllerAdvice Elle indique à Spring :"Cette classe contient des traitements
// communs pour plusieurs contrôleurs."
// Grâce à : @RestControllerAdvice Spring va chercher dans : ValidationExceptionHandler
// pour savoir quoi faire.
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    // "Cette méthode doit être exécutée lorsqu'une exception de type
    // ConstraintViolationException apparaît."
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        System.out.println("HANDLE METHOD ARGUMENT NOT VALID");

        Map<String, String> errors = new LinkedHashMap<>();
//        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                "Erreur de validation",
                request.getRequestURI(),
                errors
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }


    /**
     * Erreurs de validation des paramètres.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(
            ConstraintViolationException ex,
            HttpServletRequest request) {

        System.out.println("HANDLE ConstraintViolation");
        Map<String, String> errors = new LinkedHashMap<>();

        ex.getConstraintViolations()
                .forEach(error ->
                        errors.put(
                                error.getPropertyPath().toString(),
                                error.getMessage()
                        )
                );

        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                "Erreur de validation",
                request.getRequestURI(),
                errors
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }


    /**
     * Produit introuvable.
     */
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFound(
            ProductNotFoundException ex,
            HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(TypeProduitNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTypeProduitNotFound(
            TypeProduitNotFoundException ex,
            HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleInvalidJson(
            HttpMessageNotReadableException ex,
            HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                "Le JSON envoyé est invalide.",
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }


    /**
     * Toutes les autres erreurs inattendues.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(
            Exception ex,
            HttpServletRequest request) {

        ex.printStackTrace();

        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}