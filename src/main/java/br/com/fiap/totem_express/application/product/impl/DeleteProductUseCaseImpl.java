package br.com.fiap.totem_express.application.product.impl;

import br.com.fiap.totem_express.application.product.DeleteProductUseCase;
import br.com.fiap.totem_express.application.product.ProductGateway;
import br.com.fiap.totem_express.domain.product.Product;


public class DeleteProductUseCaseImpl implements DeleteProductUseCase {

    private final ProductGateway gateway;

    public DeleteProductUseCaseImpl(ProductGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public void delete(Long id) {
        Product product = gateway.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product must exists invalid id " + id));

        gateway.delete(product.getId());
    }

}
