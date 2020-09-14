package src;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class ProductListControllerIntegrationTest {
    @Autowired
    private  ProductListRepository repository;

    @Autowired
    private ProductListServices services;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    public String getAddress(String path){
        return "http://localhost:" + port + path;
    }
    @Test
    public void createProductList(){
        var creationRequest = new ProductListCreationRequest();
        creationRequest.name = "minha lista";
        var response = restTemplate.postForEntity( getAddress("/list"), creationRequest, Map.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        var id =  (String) response.getBody().get("id");
        Assertions.assertNotNull(repository.getProductList(id));
    }

    @Test
    public void deleteProductList(){
        var productList = new ProductList("minha lista");
        repository.add(productList);

        restTemplate.delete(getAddress("/list/"+ productList.getId()));
        Assertions.assertThrows(IllegalArgumentException.class, () -> repository.getProductList(productList.getId()));
    }

    @Test
    public void editProductListName(){

    }

}
