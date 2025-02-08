package br.com.fiap.totem_express.application.product;

import br.com.fiap.totem_express.domain.product.Category;
import br.com.fiap.totem_express.domain.product.Product;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductGateway {

    Product save(Product product);

    void delete(Long id);

    Product update(Product product);

    Optional<Product> findById(Long id);

    List<Product> findAllByCategory(Category category);

    List<Product> findAllByIds(Set<Long> order);
}
