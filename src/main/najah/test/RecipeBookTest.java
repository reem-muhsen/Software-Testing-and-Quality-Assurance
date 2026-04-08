package main.najah.test;

import main.najah.code.Recipe;
import main.najah.code.RecipeBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class RecipeBookTest {

    private RecipeBook recipeBook;

    @BeforeEach
    void setup() {
        recipeBook = new RecipeBook();
    }

    private Recipe makeRecipe(String name) {
        Recipe recipe = new Recipe();
        recipe.setName(name);
        return recipe;
    }

    @Test
    @DisplayName("Recipes: array size")
    void getRecipesArraySize() {
        assertNotNull(recipeBook.getRecipes());
        assertEquals(4, recipeBook.getRecipes().length);
    }

    @Test
    @DisplayName("Add: new recipe")
    void addNewRecipe() {
        Recipe recipe = makeRecipe("Test Recipe");

        assertTrue(recipeBook.addRecipe(recipe));
        assertEquals("Test Recipe", recipeBook.getRecipes()[0].getName());
    }

    @Test
    @DisplayName("Add: duplicate recipe")
    void addDuplicateRecipe() {
        Recipe recipe1 = makeRecipe("Test Recipe");
        Recipe recipe2 = makeRecipe("Test Recipe");

        assertTrue(recipeBook.addRecipe(recipe1));
        assertFalse(recipeBook.addRecipe(recipe2));
    }

    @Test
    @DisplayName("Add: full book")
    void addRecipeToFullRecipeBook() {
        for (int i = 0; i < 4; i++) {
            assertTrue(recipeBook.addRecipe(makeRecipe("Recipe " + i)));
        }

        Recipe recipe = makeRecipe("Extra Recipe");
        assertFalse(recipeBook.addRecipe(recipe));
    }

    @ParameterizedTest
    @DisplayName("Add: parameterized")
    @ValueSource(strings = {"Mocha", "Latte", "Espresso"})
    void addRecipeParameterized(String recipeName) {
        Recipe recipe = makeRecipe(recipeName);

        assertTrue(recipeBook.addRecipe(recipe));
        assertEquals(recipeName, recipeBook.getRecipes()[0].getName());
    }

    @Test
    @DisplayName("Delete: existing recipe")
    void deleteExistingRecipe() {
        Recipe recipe = makeRecipe("Test Recipe");
        recipeBook.addRecipe(recipe);

        assertEquals("Test Recipe", recipeBook.deleteRecipe(0));
        assertEquals("", recipeBook.getRecipes()[0].getName());
    }

    @Test
    @DisplayName("Delete: non-existing recipe")
    void deleteNonExistingRecipe() {
        assertNull(recipeBook.deleteRecipe(0));
    }

    @Test
    @DisplayName("Edit: existing recipe")
    void editExistingRecipe() {
        Recipe recipe = makeRecipe("Test Recipe");
        recipeBook.addRecipe(recipe);

        Recipe newRecipe = makeRecipe("New Recipe");

        assertEquals("Test Recipe", recipeBook.editRecipe(0, newRecipe));
        assertEquals("", recipeBook.getRecipes()[0].getName());
    }

    @Test
    @DisplayName("Edit: non-existing recipe")
    void editNonExistingRecipe() {
        Recipe newRecipe = makeRecipe("New Recipe");

        assertNull(recipeBook.editRecipe(0, newRecipe));
    }

    @Test
    @DisplayName("Performance test")
    @Timeout(2)
    void performanceTest() {
        Recipe recipe = makeRecipe("Fast Recipe");
        recipeBook.addRecipe(recipe);
        recipeBook.getRecipes();

        assertNotNull(recipeBook);
    }
}