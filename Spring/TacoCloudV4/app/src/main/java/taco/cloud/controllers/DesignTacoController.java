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

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import taco.cloud.models.Ingredient;
import taco.cloud.models.Ingredient.Type;
import taco.cloud.models.Taco;
import taco.cloud.models.TacoOrder;
import taco.cloud.repositories.IngredientRepository;
import taco.cloud.repositories.JdbcTemplateIngredientRepository;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {

    private final IngredientRepository ingredientRepository;
    private final JdbcTemplateIngredientRepository ingredientJdbcRepository;

    public DesignTacoController(IngredientRepository ingredientRepository,
            JdbcTemplateIngredientRepository ingredientJdbcRepository) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientJdbcRepository = ingredientJdbcRepository;
    }

    private final String DESIGN_VIEW_NAME = "design-view";
    private final String REDIRECT_PATH_TO_CURRENT_ORDER = "redirect:/orders/current";

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        Iterable<Ingredient> ingredients = ingredientRepository.findAll();
        Iterable<Ingredient> templateIngredients = ingredientJdbcRepository.findAll();

        List<Ingredient> ingredientsAsList = new ArrayList<>();

        for (Ingredient ingredient : ingredients) {
            ingredientsAsList.add(ingredient);
        }

        List<Ingredient> templateIngredientsAsList = new ArrayList<>();

        for (Ingredient ingredient : templateIngredients) {
            templateIngredientsAsList.add(ingredient);
        }

        System.out.println("Repositories is correct: " + ingredientsAsList.containsAll(templateIngredientsAsList));
        System.out.println("Repositories size: " + ingredientsAsList.size());

        List<Ingredient> ingredientsByName = ingredientJdbcRepository.findByName("Flour Tortilla");

        System.out.println("Ingredient by name" + ingredientsByName.getFirst());

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
