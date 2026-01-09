package taco.cloud.controllers;

import java.security.Principal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
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
import taco.cloud.models.UserData;
import taco.cloud.repositories.OrderRepository;
import taco.cloud.repositories.TacoRepository;
import taco.cloud.repositories.UserRepository;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {
    private final String ORDER_VIEW_NAME = "order-form-view";
    private final String REDIRECT_PATH_TO_START_PAGE = "redirect:/back";

    private TacoRepository tacoRepository;;
    private OrderRepository orderRepository;
    private UserRepository userRepository;

    public OrderController(OrderRepository orderRepository, UserRepository userRepository, TacoRepository tacoRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.tacoRepository = tacoRepository;
    }

    @GetMapping("/current")
    public String orderForm() {
        return ORDER_VIEW_NAME;
    }

    @PostMapping
    public String processOrder(@Valid TacoOrder order, Errors errors, SessionStatus sessionStatus,
            Principal principal) {
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
                
                tacoRepository.save(taco);
            }
        }

        UserData user = userRepository.findByUsername(principal.getName());

        order.setPlaced(Date.from(Instant.now()));
        user.addTacoOrder(order);

        log.info("Order submitted: {}", order);
        orderRepository.save(order);
        sessionStatus.setComplete();

        return REDIRECT_PATH_TO_START_PAGE;
    }
}
