package vn.hoidanit.laptopshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vn.hoidanit.laptopshop.domain.Product;

import java.util.List;
import java.util.Optional;



@Repository
public interface ProductRepository extends JpaRepository<Product ,Long>, JpaSpecificationExecutor<Product> {
    Product save(Product product);
    Optional<Product> findById(long id);
    Product deleteById(long id);
    Page<Product> findAll(Pageable pageable);
    Page<Product> findAll(Specification<Product> specification, Pageable pageable);
    List<Product> findTop8ByOrderByIdAsc();
}
