package com.br.product.controller;

import com.br.product.model.User;
import com.br.product.repository.UserRepository;
import com.br.product.service.ProductService;
import com.br.product.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Autowired
    private UserRepository userRepository;
    private ProductService productService;
    @Autowired
    private JavaMailSender javaMailSender;
    private static int currentPage = 1;
    private static int pageSize = 5;

    Map<String, Float> conteudo = new HashMap<String, Float>();
    Float totalPedido = 0.0f;

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
                //System.out.println(cart);
                //System.out.println(cart.values());
                sum += val;
            }
            model.addAttribute("sum", sum);
        } else {

            Map<String, Float> cart = (Map<String, Float>) session.getAttribute("prodsess");
            cart.put(p.getName(), p.getPrice());
            session.setAttribute("prodsess", cart);
            model.addAttribute("cart", cart);
            Float sum = 0.0f;
            System.out.println(cart.getClass().getSimpleName());
            for (Float val : cart.values()) {
                 //System.out.println(cart);
                //System.out.println(cart.values());
                sum += val;
                conteudo = cart;

                System.out.println(conteudo);
            }
            model.addAttribute("sum", sum);
            totalPedido = sum;
        }

        return "orders";

    }

    @RequestMapping("/delete")
    public String deleteFromCart(@RequestParam("key") String key, HttpSession session, Model model) {

        Map<String, Float> cart = (Map<String, Float>) session.getAttribute("prodsess");
        cart.remove(key);
        Float sum = 0.0f;
        for (Float val : cart.values()) {
            sum += val;
        }
        session.setAttribute("prodsess", cart);
        model.addAttribute("cart", cart);
        model.addAttribute("sum", sum);
        return "redirect:/deliveryApp/productsForSale";
    }

    @RequestMapping(path = "/email-send", method = RequestMethod.GET)
    public String sendMail() {

        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String email = loggedInUser.getName();

        User user = userRepository.findByEmail(email);

       // System.out.println(user.getEmail());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setText(user.getName() + "-" + conteudo.toString() + totalPedido + "-" + user.getEndereco());
        message.setTo("mensones@outlook.com");
        message.setFrom("mensones.1@gmail.com");

        try {
            javaMailSender.send(message);
            return "redirect:/deliveryApp/productsForSale";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/deliveryApp/addToCart";
        }
    }


    @ExceptionHandler(NoHandlerFoundException.class)
    public String handlerException() {
        return "error/404";
    }
}
