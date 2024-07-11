package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
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

    public void handleAddProductCart(String email, long prodctId,HttpSession session) {
        User user = this.userService.getUserByEmail(email);
        if (user != null) {
            // check xem đã có Cart hay chưa
            Cart cart = this.cartRepository.findByUser(user);

            if (cart == null) {
                // tạo mới Cart
                Cart otheCart = new Cart();

                otheCart.setUser(user);
                otheCart.setSum(0);

                cart = this.cartRepository.save(otheCart);
            }
            // tìm product
            Optional<Product> productOptional = this.productRepository.findById(prodctId);
            if (productOptional.isPresent()) {
                Product otherProduct = productOptional.get();

                // Check sp đã tưnnfg có trong giỏ hàng chưa
                CartDetail oldDetail = this.cartDetailRepository.findByCartAndProduct(cart, otherProduct);

                if (oldDetail == null) {
                    CartDetail cartDetail = new CartDetail();

                    cartDetail.setCart(cart);
                    cartDetail.setProduct(otherProduct);
                    cartDetail.setPrice(otherProduct.getPrice());
                    cartDetail.setQuantity(1);

                    // save cart detail
                    this.cartDetailRepository.save(cartDetail);

                    // update Cart
                    int sum = cart.getSum() + 1;
                    cart.setSum(sum);
                    this.cartRepository.save(cart);
                    session.setAttribute("sum",sum);
                } else {
                    oldDetail.setQuantity(oldDetail.getQuantity() + 1);

                    this.cartDetailRepository.save(oldDetail);
                }

            }

        }
    }
}
