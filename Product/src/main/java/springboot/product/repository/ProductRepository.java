package springboot.product.repository;

import org.springframework.data.repository.CrudRepository;
import springboot.product.model.Product;

import java.util.List;


public interface ProductRepository extends CrudRepository<Product, Long> {
    public List<Product> findAllByCreditIdIn(List<Integer> creditNumberList);
}
