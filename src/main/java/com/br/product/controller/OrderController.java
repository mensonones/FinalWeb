package com.br.product.controller;

import com.br.product.model.Product;
import com.br.product.service.ProductService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class OrderController {

    private ProductService productService;

    public OrderController(ProductService productService) {
        this.productService = productService;
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

        return "cart";

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
}
