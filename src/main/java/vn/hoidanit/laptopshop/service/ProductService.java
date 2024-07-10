package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.CartDetailRepository;
import vn.hoidanit.laptopshop.repository.CartRepository;
import vn.hoidanit.laptopshop.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CartDetailRepository cartDetailRepository;
    private final CartRepository cartRepository;
    private final UserService userService;

    public ProductService(ProductRepository productRepository, CartDetailRepository cartDetailRepository,
            CartRepository cartRepository, UserService userService) {
        this.productRepository = productRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.cartRepository = cartRepository;
        this.userService = userService;
    }

    public Product handleSaveProduct(Product product) {

        return this.productRepository.save(product);
    }

    public List<Product> getAllProduct() {

        return this.productRepository.findAll();
    }

    public Optional<Product> getfindByIdProduct(long id) {
        return this.productRepository.findById(id);
    }
    // public Product getfindByIdProduct (long id){
    // return this.productRepository.findById(id);
    // }

    public Product handleDeleteProduct(long id) {
        return this.productRepository.deleteById(id);
    }

    public void handleAddProductCart(String email, long prodctId) {
        User user = this.userService.getUserByEmail(email);
        if (user != null) {
            // check xem đã có Cart hay chưa
            Cart cart = this.cartRepository.findByUser(user);

            if (cart == null) {
                // tạo mới Cart
                Cart otheCart = new Cart();

                otheCart.setUser(user);
                // Set cứngc cho nó bằng 1 rồi fix sau
                otheCart.setSum(1);

                cart = this.cartRepository.save(otheCart);
            }
            // tìm product
            Optional<Product> productOptional = this.productRepository.findById(prodctId);
            if (productOptional.isPresent()) {
                Product otherProduct = productOptional.get();

                // save cart detail
                CartDetail cartDetail = new CartDetail();

                cartDetail.setCart(cart);
                cartDetail.setProduct(otherProduct);
                cartDetail.setPrice(otherProduct.getPrice());
                cartDetail.setQuantity(1);

                this.cartDetailRepository.save(cartDetail);
            }

        }
    }
}
