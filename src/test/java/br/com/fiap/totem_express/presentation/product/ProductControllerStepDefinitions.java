package br.com.fiap.totem_express.presentation.product;

import br.com.fiap.totem_express.application.product.output.ProductView;
import br.com.fiap.totem_express.presentation.product.request.CreateProductRequest;
import br.com.fiap.totem_express.presentation.product.request.UpdateProductRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static br.com.fiap.totem_express.domain.product.Category.DISH;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ProductControllerStepDefinitions {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private MvcResult mvcResult;
    private CreateProductRequest createProductRequest;
    private UpdateProductRequest updateProductRequest;

    @Given("a valid product creation request")
    public void a_valid_product_creation_request() {
        createProductRequest = new CreateProductRequest(
                "Cheddar McMelt",
                "Um hamburguer (100% carne bovina) com molho cheddar.",
                "https://example.com/image.jpg",
                new BigDecimal("19.90"),
                DISH
        );
    }

    @When("the client sends a POST request to create a product")
    public void the_client_sends_a_post_request_to_create_a_product() throws Exception {
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createProductRequest)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Then("the response should contain the created product details")
    public void the_response_should_contain_the_created_product_details() throws Exception {
        String responseContent = mvcResult.getResponse().getContentAsString();
        ProductView.SimpleView productView = objectMapper.readValue(responseContent, ProductView.SimpleView.class);
        Assertions.assertEquals("Cheddar McMelt", productView.name());
        Assertions.assertEquals(new BigDecimal("19.90"), productView.price());
    }

    @Given("an invalid product creation request")
    public void an_invalid_product_creation_request() {
        createProductRequest = new CreateProductRequest("", "", "", null, null);
    }

    @When("the client sends an invalid POST request to create a product")
    public void the_client_sends_an_invalid_post_request_to_create_a_product() throws Exception {
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createProductRequest)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Then("the response should return a Bad Request status")
    public void the_response_should_return_a_bad_request_status() {
        Assertions.assertEquals(400, mvcResult.getResponse().getStatus());
    }
}
