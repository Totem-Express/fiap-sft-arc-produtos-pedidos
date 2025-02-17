package br.com.fiap.totem_express.application.product;

import br.com.fiap.totem_express.application.product.input.UpdateProductInput;
import br.com.fiap.totem_express.application.product.output.ProductView;

import java.util.Optional;

public interface UpdateProductUseCase {
    Optional<ProductView> update(UpdateProductInput input);
}
