package vn.hoidanit.laptopshop.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.service.ProductService;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class ItemController {

    private final ProductService productService;

    public ItemController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product/{id}")
    public String getProductPage(Model model, @PathVariable long id) {
        model.addAttribute("id",id);
        Product product = this.productService.getfindByIdProduct(id).get();
        model.addAttribute("productDetail", product);
        return "client/product/detail";
    }

    @PostMapping("/add-product-to-cart/{id}")
    public String postProductAddToCart(@PathVariable long id,HttpServletRequest request ) {
        HttpSession session = request.getSession(false);

        String email =(String) session.getAttribute("email");
        long productId = id;

            this.productService.handleAddProductCart(email, productId);
        
        return "redirect:/";
    }
    
}
