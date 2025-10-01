package taco.cloud.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import taco.cloud.models.TacoOrder;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {
    private final String ORDER_VIEW_NAME = "order-form-view";
    private final String REDIRECT_PATH_TO_START_PAGE = "redirect:/back";

    @GetMapping("/current")
    public String orderForm() {
        return ORDER_VIEW_NAME;
    }

    @PostMapping
    public String processOrder(@Valid TacoOrder order, Errors errors, SessionStatus sessionStatus) {
        if (errors.hasErrors()) {
            return ORDER_VIEW_NAME;
        }

        log.info("Order submitted: {}", order);
        sessionStatus.setComplete();

        return REDIRECT_PATH_TO_START_PAGE;
    }
}
