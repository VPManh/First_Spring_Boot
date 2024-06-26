package vn.hoidanit.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Role;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.domain.dto.RegisterDTO;
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

    public Role getRoleByName(String name) {
        return this.roleRepository.findByName(name);
    }

    // sử dụng dto (data transfer object) để lấy dữ liệu từ dto sang cho user
    public User registerDTOtoUser(RegisterDTO registerDTO){
         
        User user = new User();

        user.setFullName(registerDTO.getFirstName()+ " " +registerDTO.getLastName());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(registerDTO.getPassword());

        return user;
    }
    
}
