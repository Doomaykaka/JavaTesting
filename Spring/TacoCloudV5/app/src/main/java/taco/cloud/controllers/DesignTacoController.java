package taco.cloud.controllers;

import java.util.ArrayList;
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

import lombok.extern.slf4j.Slf4j;
import taco.cloud.models.Ingredient;
import taco.cloud.models.Ingredient.Type;
import taco.cloud.models.Taco;
import taco.cloud.models.TacoOrder;
import taco.cloud.models.TacoUDT;
import taco.cloud.repositories.IngredientRepository;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {
    private final String DESIGN_VIEW_NAME = "design-view";
    private final String REDIRECT_PATH_TO_CURRENT_ORDER = "redirect:/orders/current";

    private final IngredientRepository ingredientRepository;

    public DesignTacoController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        Iterable<Ingredient> ingredients = ingredientRepository.findAll();

        List<Ingredient> ingredientsAsList = new ArrayList<>();

        for (Ingredient ingredient : ingredients) {
            ingredientsAsList.add(ingredient);
        }

        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredientsAsList, type));
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
    public String showDesignForm(Model model) {
        return DESIGN_VIEW_NAME;
    }

    @PostMapping
    public String processTaco(Taco taco, Errors errors, @ModelAttribute TacoOrder tacoOrder) {
        if (errors.hasErrors()) {
            return DESIGN_VIEW_NAME;
        }

        TacoUDT tacoUDT = new TacoUDT(taco.getName(), taco.getIngredients());

        tacoOrder.addTaco(tacoUDT);
        log.info("Processing taco: {}", taco);

        return REDIRECT_PATH_TO_CURRENT_ORDER;
    }

    private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());
    }
}
