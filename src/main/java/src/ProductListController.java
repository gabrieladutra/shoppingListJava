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
    public ResponseEntity<String> createList(@RequestBody ProductListCreationRequest productListCreationRequest) {
        try {
            Validator.validateStringNullOrBlank(productListCreationRequest.name);
        } catch (NullOrEmptyArgumentException exception) {
            return ResponseEntity.badRequest().body("The list name is null or empty");
        }
        services.addProductList(productListCreationRequest.name);
        return ResponseEntity.ok("The list was successfully created!");
    }

    @DeleteMapping("/list/{id}")
    public ResponseEntity<String> deleteList(@PathVariable("id") String id) {
        try {
            Validator.validateStringNullOrBlank(id);
        } catch (NullOrEmptyArgumentException exception) {
            return ResponseEntity.badRequest().body("The list id is null or empty");
        }
        repository.deleteProductList(id);
        return ResponseEntity.ok("The list was successfully deleted!");
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<ProductList> getList(
            @PathVariable("id") String id) {
        try {
            Validator.validateStringNullOrBlank(id);
        } catch (NullOrEmptyArgumentException exception) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(repository.getProductList(id));

    }

    @PatchMapping("/list/{id}/name")
    public ResponseEntity<String> changeListName(@PathVariable("id") String id, @RequestBody ProductListEditNameRequest body) {
        try {
            Validator.validateStringNullOrBlank(id);
            Validator.validateStringNullOrBlank(body.name);
        } catch (NullOrEmptyArgumentException exception) {
            return ResponseEntity.badRequest().body("The list name or id is null or empty");
        }
        services.alterProductListName(id, body.name);
        return ResponseEntity.ok("Product list name was updated to " + body.name);
    }

    @PostMapping("/list/{id}/product")
    public ResponseEntity<Product> addProduct(@PathVariable("id") String id, @RequestBody Product product) {
        try {
            Validator.validateStringNullOrBlank(id);
            if (product == null) {
                return ResponseEntity.badRequest().build();
            }
        } catch (NullOrEmptyArgumentException exception) {
            return ResponseEntity.badRequest().build();
        }
        services.addProduct(id, product);
        return ResponseEntity.ok(product);

    }

    @DeleteMapping("/list/{id}/product/{name}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") String id, @PathVariable("name") String name) {
        try {
            Validator.validateStringNullOrBlank(id);
            Validator.validateStringNullOrBlank(name);
        } catch (NullOrEmptyArgumentException exception) {
            return ResponseEntity.badRequest().body("The list name or id is null or empty");
        }
        services.deleteProduct(id, name);
        return ResponseEntity.ok(name + " was successfully deleted");
    }

    @PutMapping("/list/{id}/product/{name}")
    public ResponseEntity<Product> editProduct(@PathVariable("id") String id,
                                               @PathVariable("name") String name,
                                               @RequestBody Product product) {
        try {
            Validator.validateStringNullOrBlank(id);
            Validator.validateStringNullOrBlank(name);
            if (product == null) {
                return ResponseEntity.badRequest().build();
            }
        } catch (NullOrEmptyArgumentException exception) {
            return ResponseEntity.badRequest().build();
        }
        services.editProduct(id, name, product);
        return ResponseEntity.ok(product);

    }


}

    




