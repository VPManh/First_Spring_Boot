package vn.hoidanit.laptopshop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import vn.hoidanit.laptopshop.domain.*;
import vn.hoidanit.laptopshop.repository.*;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CartDetailRepository cartDetailRepository;
    private final CartRepository cartRepository;
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public ProductService(ProductRepository productRepository, CartDetailRepository cartDetailRepository,
                          CartRepository cartRepository, UserService userService,
                          OrderRepository orderRepository, OrderDetailRepository orderDetailRepository) {
        this.productRepository = productRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.cartRepository = cartRepository;
        this.userService = userService;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
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

    public void handleAddProductCart(String email, long prodctId, HttpSession session, long quantity) {
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
                    cartDetail.setQuantity(quantity);

                    // save cart detail
                    this.cartDetailRepository.save(cartDetail);

                    // update Cart
                    int sum = cart.getSum() + 1;
                    cart.setSum(sum);
                    this.cartRepository.save(cart);
                    session.setAttribute("sum", sum);
                } else {
                    oldDetail.setQuantity(oldDetail.getQuantity() + quantity);

                    this.cartDetailRepository.save(oldDetail);
                }

            }

        }
    }

    public Cart fetchByUser(User user) {
        return this.cartRepository.findByUser(user);
    }

    public void handleDeleteCartToProduct(long cartDetailId, HttpSession session) {
        Optional<CartDetail> optional = this.cartDetailRepository.findById(cartDetailId);
        if (optional.isPresent()) {
            CartDetail cartDetail = optional.get();

            this.cartDetailRepository.deleteById(cartDetailId);

            Cart cart = cartDetail.getCart();

            if (cart.getSum() > 1) {
                int sum = cart.getSum() - 1;
                cart.setSum(sum);
                session.setAttribute("sum", sum);
                this.cartRepository.save(cart);
            } else {
                this.cartRepository.deleteById(cart.getId());
                session.setAttribute("sum", 0);
            }

        }
    }

    public void handleUpdateCartBeforeCheckout(List<CartDetail> cartDetails) {
        for (CartDetail cartDetail : cartDetails) {
            Optional<CartDetail> cdOptional = this.cartDetailRepository.findById(cartDetail.getId());
            if (cdOptional.isPresent()) {
                CartDetail currentCartDetail = cdOptional.get();
                currentCartDetail.setQuantity(cartDetail.getQuantity());
                this.cartDetailRepository.save(currentCartDetail);
            }
        }
    }

    public void handlePlaceOrder(User user, HttpSession session,
                                 String receiverName,
                                 String receiverAddress,
                                 String receiverPhone,
                                 String receiverNote) {

//        step 1 : get cart by user
        Cart cart = this.cartRepository.findByUser(user);
        if (cart != null){
            List<CartDetail> cartDetails = cart.getCartDetails();
            if (cartDetails != null){

//              create Order
                Order order = new Order();
                order.setUser(user);
                order.setReceiverName(receiverName);
                order.setReceiverAddress(receiverAddress);
                order.setReceiverPhone(receiverPhone);
                order.setReceiverNote(receiverNote);
                order.setStatus("PENDING");

                double sum = 0;
                for(CartDetail cartDetail : cartDetails) {
                    sum += cartDetail.getPrice();
                }
                order.setTotalPrice(sum);
                order = this.orderRepository.save(order);

//             create orderDetail
                for (CartDetail cds : cartDetails){
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrder(order);
                    orderDetail.setProduct(cds.getProduct());
                    orderDetail.setQuantity(cds.getQuantity());
                    orderDetail.setPrice((long)cds.getPrice());

                    this.orderDetailRepository.save(orderDetail);
                }
//            step 2: delete cartDetail and Cart
                for (CartDetail cds : cartDetails){
                    this.cartDetailRepository.deleteById(cds.getId());
                }

                this.cartRepository.deleteById(cart.getId());

//                step 3: update Session
                session.setAttribute("sum",0);

            }

        }
    }
}
