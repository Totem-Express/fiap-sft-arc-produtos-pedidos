package br.com.fiap.totem_express.application.product;

import br.com.fiap.totem_express.application.product.output.ProductView;
import br.com.fiap.totem_express.domain.product.Category;

import java.util.List;

public interface FindProductsByCategoryUseCase {
    List<ProductView> findAllByCategory(Category category);
}
