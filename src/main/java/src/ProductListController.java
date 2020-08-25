package src;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductListController {
    private final ProductListServices services;
    private final ProductListRepository repository;

    public ProductListController(ProductListServices services, ProductListRepository repository) {
        this.services = services;
        this.repository = repository;
    }



    @PostMapping("/list")
    public ResponseEntity<String> createList(@RequestBody ProductListCreationRequest productList) {
        try {
            Validator.validateStringNullOrBlank(productList.name);
        } catch (NullOrEmptyArgumentException exception) {
            return ResponseEntity.badRequest().body("The list name is null or empty");
        }
        services.addProductList(productList.name);
        return ResponseEntity.ok("The list was successfully created!");
    }

    @DeleteMapping("/list/{id}")
    public ResponseEntity<String> deleteList(@PathVariable("id") String id){
        try {
            Validator.validateStringNullOrBlank(id);
        } catch (NullOrEmptyArgumentException exception) {
            return ResponseEntity.badRequest().body("The list name is null or empty");
        }
        repository.deleteProductList(id);
        return ResponseEntity.ok("The list was successfully deleted!");
    }


}
