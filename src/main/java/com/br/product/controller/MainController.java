package com.br.product.controller;

import com.br.product.service.ProductService;
import com.br.product.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/deliveryApp")
public class MainController {


    private static String UPLOADED_FOLDER = "/home/emerson/spring-boot-product-system-master/src/main/resources/static/img/";

    private ProductService productService;

    private static int currentPage = 1;
    private static int pageSize = 5;
    private static final Logger LOG = Logger.getLogger(MainController.class.getName());

    @Autowired
    public MainController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping("/products")
    public String index(Model model, @RequestParam("page") Optional<Integer> page,
                        @RequestParam("size") Optional<Integer> size) {
        page.ifPresent(p -> currentPage = p);
        size.ifPresent(s -> pageSize = s);

        Pageable pageable = new PageRequest(currentPage - 1, pageSize);
        Page<Product> productPage = productService.getAllProduct(pageable);

        model.addAttribute("productPage", productPage);

        int totalPages = productPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "products";
    }


    @RequestMapping(value = "/product/add")
    public String addProduct(@Valid Model model) {
        model.addAttribute("product", new Product());

        return "addproduct";
    }

    @RequestMapping(value = "/product/save", method = RequestMethod.POST)
    public String save(Product product, RedirectAttributes redirectAttributes, @RequestParam("file") MultipartFile file) throws IOException{

        if(file.isEmpty()){
            product.setImagem("/img/default-food-image.jpg");
        }

        byte[] bytes = file.getBytes();
        Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
        Files.write(path, bytes);

        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded '" + file.getOriginalFilename() + "'");

        product.setImagem(file.getOriginalFilename());

        productService.saveProduct(product);

        return "redirect:/deliveryApp/products";
    }

    @RequestMapping(value = "/product/delete/{id}", method = RequestMethod.GET)
    public String deleteProduct(@PathVariable("id") Integer id) {
        productService.deleteProduct(id);

        return "redirect:/deliveryApp/products";
    }

    @RequestMapping(value = "/product/edit/{id}")
    public String editProduct(@PathVariable("id") Integer id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);

        return "editproduct";
    }

    @RequestMapping(value = "/product/update", method = RequestMethod.POST)
    public String update(Product product, RedirectAttributes redirectAttributes, @RequestParam("file") MultipartFile file) throws IOException{

        byte[] bytes = file.getBytes();
        Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
        Files.write(path, bytes);

        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded '" + file.getOriginalFilename() + "'");

        product.setImagem(file.getOriginalFilename());

        productService.saveProduct(product);

        return "redirect:/products";
    }

    @RequestMapping(value = "/productsForSale")
    public String listProductsForSale(Model model, @RequestParam("page") Optional<Integer> page,
                                      @RequestParam("size") Optional<Integer> size) {
        page.ifPresent(p -> currentPage = p);
        size.ifPresent(s -> pageSize = s);

        Pageable pageable = new PageRequest(currentPage - 1, pageSize);
        Page<Product> productPage = productService.getAllProduct(pageable);

        model.addAttribute("productPage", productPage);

        int totalPages = productPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "listproducts";
    }

    @RequestMapping("/addToCart/{id}")
    public String addToCart(@PathVariable Integer id, Model model, HttpSession session) {

        Product p = productService.findById(id);

        if (session.getAttribute("prodsess") == null) {

            Map<String, Float> cart = new HashMap<>();
            cart.put(p.getName(), p.getPrice());
            session.setAttribute("prodsess", cart);
            model.addAttribute("cart", cart);
            Float sum = 0.0f;
            for (Float val : cart.values()) {
                sum += val;
            }
            model.addAttribute("sum", sum);
        } else {

            Map<String, Float> cart = (Map<String, Float>) session.getAttribute("prodsess");
            cart.put(p.getName(), p.getPrice());
            session.setAttribute("prodsess", cart);
            model.addAttribute("cart", cart);
            Float sum = 0.0f;
            for (Float val : cart.values()) {
                sum += val;
            }
            model.addAttribute("sum", sum);

        }

        return "orders";

    }

    @RequestMapping("/cart")
    public String cart(HttpSession session, Model model) {

        Map<String, Integer> cart = (Map<String, Integer>) session.getAttribute("prodsess");
        model.addAttribute("cart", cart);
        Integer sum = 0;
        for (Integer val : cart.values()) {
            sum += val;
        }
        model.addAttribute("sum", sum);
        return "show-cart";
    }


    @ExceptionHandler(NoHandlerFoundException.class)
    public String handlerException() {
        return "error/404";
    }
}
