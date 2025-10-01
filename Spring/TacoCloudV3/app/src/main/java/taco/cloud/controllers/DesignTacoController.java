package taco.cloud.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import taco.cloud.models.Ingredient;
import taco.cloud.models.Ingredient.Type;
import taco.cloud.models.Taco;
import taco.cloud.models.TacoOrder;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {
    private final String INGREDIENT_1_ID = "FLTO";
    private final String INGREDIENT_1_NAME = "Flour Tortilla";
    private final Type INGREDIENT_1_TYPE = Type.WRAP;
    private final String INGREDIENT_2_ID = "COTO";
    private final String INGREDIENT_2_NAME = "Corn Tortilla";
    private final Type INGREDIENT_2_TYPE = Type.WRAP;
    private final String INGREDIENT_3_ID = "GRBF";
    private final String INGREDIENT_3_NAME = "Ground Beef";
    private final Type INGREDIENT_3_TYPE = Type.PROTEIN;
    private final String INGREDIENT_4_ID = "CARN";
    private final String INGREDIENT_4_NAME = "Carnitas";
    private final Type INGREDIENT_4_TYPE = Type.PROTEIN;
    private final String INGREDIENT_5_ID = "TMTO";
    private final String INGREDIENT_5_NAME = "Diced Tomatoes";
    private final Type INGREDIENT_5_TYPE = Type.VEGGIES;
    private final String INGREDIENT_6_ID = "LETC";
    private final String INGREDIENT_6_NAME = "Lettuce";
    private final Type INGREDIENT_6_TYPE = Type.VEGGIES;
    private final String INGREDIENT_7_ID = "CHED";
    private final String INGREDIENT_7_NAME = "Cheddar";
    private final Type INGREDIENT_7_TYPE = Type.CHEESE;
    private final String INGREDIENT_8_ID = "JACK";
    private final String INGREDIENT_8_NAME = "Monterrey Jack";
    private final Type INGREDIENT_8_TYPE = Type.CHEESE;
    private final String INGREDIENT_9_ID = "SLSA";
    private final String INGREDIENT_9_NAME = "Salsa";
    private final Type INGREDIENT_9_TYPE = Type.SAUCE;
    private final String INGREDIENT_10_ID = "SRCR";
    private final String INGREDIENT_10_NAME = "Sour Cream";
    private final Type INGREDIENT_10_TYPE = Type.SAUCE;
    private final String DESIGN_VIEW_NAME = "design-view";
    private final String REDIRECT_PATH_TO_CURRENT_ORDER = "redirect:/orders/current";

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient(INGREDIENT_1_ID, INGREDIENT_1_NAME, INGREDIENT_1_TYPE),
                new Ingredient(INGREDIENT_2_ID, INGREDIENT_2_NAME, INGREDIENT_2_TYPE),
                new Ingredient(INGREDIENT_3_ID, INGREDIENT_3_NAME, INGREDIENT_3_TYPE),
                new Ingredient(INGREDIENT_4_ID, INGREDIENT_4_NAME, INGREDIENT_4_TYPE),
                new Ingredient(INGREDIENT_5_ID, INGREDIENT_5_NAME, INGREDIENT_5_TYPE),
                new Ingredient(INGREDIENT_6_ID, INGREDIENT_6_NAME, INGREDIENT_6_TYPE),
                new Ingredient(INGREDIENT_7_ID, INGREDIENT_7_NAME, INGREDIENT_7_TYPE),
                new Ingredient(INGREDIENT_8_ID, INGREDIENT_8_NAME, INGREDIENT_8_TYPE),
                new Ingredient(INGREDIENT_9_ID, INGREDIENT_9_NAME, INGREDIENT_9_TYPE),
                new Ingredient(INGREDIENT_10_ID, INGREDIENT_10_NAME, INGREDIENT_10_TYPE));

        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }
    }

    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order() {
        return new TacoOrder();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping
    public String showDesignForm() {
        return DESIGN_VIEW_NAME;
    }

    @PostMapping
    public String processTaco(@Valid Taco taco, Errors errors, @ModelAttribute TacoOrder tacoOrder) {
        if (errors.hasErrors()) {
            return DESIGN_VIEW_NAME;
        }

        tacoOrder.addTaco(taco);
        log.info("Processing taco: {}", taco);

        return REDIRECT_PATH_TO_CURRENT_ORDER;
    }

    private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());
    }
}
