package vn.hoidanit.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hoidanit.laptopshop.domain.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role,Long>{
    //được gọi bên UserService
    Role findByName(String name);

}
