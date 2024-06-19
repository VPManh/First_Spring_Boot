package vn.hoidanit.laptopshop.service;

import java.util.List;

import org.eclipse.tags.shaded.org.apache.regexp.recompile;
import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Role;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.RoleRepository;
import vn.hoidanit.laptopshop.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    
    public UserService(UserRepository userRepository,RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<User> getFindAllUser(){
        return this.userRepository.findAll();
    }
    public List<User> getFindByEmailUser(String email){
        return this.userRepository.findByEmail(email);
    }

    public User getUserById (long id){
        return this.userRepository.findById(id);
    }

    public User handleSaveUser(User user){
        return this.userRepository.save(user);
    }

    public User handleDeleteUser(long id){
        return this.userRepository.deleteById(id);

    }
    public Role getHashPassWord(String name){
        return this.roleRepository.findByName(name);
    }
}
