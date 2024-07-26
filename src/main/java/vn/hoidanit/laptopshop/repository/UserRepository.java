package vn.hoidanit.laptopshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hoidanit.laptopshop.domain.Role;
import vn.hoidanit.laptopshop.domain.User;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save(User hodanit);

    List<User> findOneByEmail(String email);

    User findById(long id);

    User deleteById(long id);

    boolean existsByEmail(String email);

    User findByEmail(String email);

    long countByRole(Role role);

    Page<User> findAll(Pageable pageable);
}
