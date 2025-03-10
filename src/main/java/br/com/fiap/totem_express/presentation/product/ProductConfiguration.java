package br.com.fiap.totem_express.presentation.product;

import br.com.fiap.totem_express.application.product.*;
import br.com.fiap.totem_express.application.product.impl.CreateProductUseCaseImpl;
import br.com.fiap.totem_express.application.product.impl.DeleteProductUseCaseImpl;
import br.com.fiap.totem_express.application.product.impl.FindProductsByCategoryUseCaseImpl;
import br.com.fiap.totem_express.application.product.impl.UpdateProductUseCaseImpl;
import br.com.fiap.totem_express.infrastructure.product.ProductGatewayImpl;
import br.com.fiap.totem_express.infrastructure.product.ProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductConfiguration {
    
    private final ProductRepository repository;

    public ProductConfiguration(ProductRepository repository) {
        this.repository = repository;
    }

    @Bean
    public ProductGateway productGateway() {
        return new ProductGatewayImpl(repository);
    }

    @Bean
    public CreateProductUseCase createProductUseCase() {
        return new CreateProductUseCaseImpl(productGateway());
    }

    @Bean
    public DeleteProductUseCase deleteProductUseCase() {
        return new DeleteProductUseCaseImpl(productGateway());
    }

    @Bean
    public UpdateProductUseCase updateProductUseCase() {
        return new UpdateProductUseCaseImpl(productGateway());
    }

    @Bean
    public FindProductsByCategoryUseCase findProductsByCategoryUseCase() {
        return new FindProductsByCategoryUseCaseImpl(productGateway());
    }
}
