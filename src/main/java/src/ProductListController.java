package src;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductListController {
    private final ProductListServices services;

    public ProductListController(ProductListServices services) {
        this.services = services;
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


}
