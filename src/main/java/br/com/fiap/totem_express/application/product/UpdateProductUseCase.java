package br.com.fiap.totem_express.application.product;

import java.util.Optional;

import br.com.fiap.totem_express.application.product.input.UpdateProductInput;
import br.com.fiap.totem_express.application.product.output.ProductView;

public interface UpdateProductUseCase {
    Optional<ProductView> update(UpdateProductInput input);
}
