package br.com.fiap.totem_express.application.product;

import br.com.fiap.totem_express.application.product.input.NewProductInput;
import br.com.fiap.totem_express.application.product.output.ProductView;

public interface CreateProductUseCase {
    ProductView create(NewProductInput input);
}
