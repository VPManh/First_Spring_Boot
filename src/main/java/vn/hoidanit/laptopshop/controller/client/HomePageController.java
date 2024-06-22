package vn.hoidanit.laptopshop.controller.client;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.service.ProductService;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HomePageController {
    
    private final ProductService productService;
    
    public HomePageController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/")
    public String getHomepage(Model model) {
        List<Product> products = this.productService.getAllProduct();
        model.addAttribute("products", products);   
        return "client/homepage/show";
    }

    // @GetMapping("/product/{id}")
    // public String getMethodName(Model model, @PathVariable long id) {
    //     model.addAttribute("id",id);
    //     Product product = this.productService.getfindByIdProduct(id).get();
    //     model.addAttribute("productDetail", product);
    //     return "client/product/detail";
    // }
    
}
