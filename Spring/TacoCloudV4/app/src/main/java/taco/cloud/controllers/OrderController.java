package taco.cloud.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import taco.cloud.models.Ingredient;
import taco.cloud.models.IngredientRef;
import taco.cloud.models.Taco;
import taco.cloud.models.TacoOrder;
import taco.cloud.repositories.OrderRepository;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {
    private final String ORDER_VIEW_NAME = "order-form-view";
    private final String REDIRECT_PATH_TO_START_PAGE = "redirect:/back";

    private OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/current")
    public String orderForm() {
        return ORDER_VIEW_NAME;
    }

    @PostMapping
    public String processOrder(@Valid TacoOrder order, Errors errors, SessionStatus sessionStatus) {
        if (errors.hasErrors()) {
            return ORDER_VIEW_NAME;
        }

        if (order.getTacos() != null) {
            for (Taco taco : order.getTacos()) {
                List<IngredientRef> refs = new ArrayList<>();

                for (Ingredient ingredient : taco.getIngredients()) {
                    refs.add(new IngredientRef(ingredient.getId()));
                }

                taco.setIngredientRefs(refs);
            }
        }

        log.info("Order submitted: {}", order);
        orderRepository.save(order);
        sessionStatus.setComplete();

        return REDIRECT_PATH_TO_START_PAGE;
    }
}
