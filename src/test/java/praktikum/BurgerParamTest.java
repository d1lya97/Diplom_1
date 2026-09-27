package praktikum;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class BurgerParamTest {

    private final float bunPrice;
    private final float saucePrice;
    private final float fillingPrice;
    private final float expectedPrice;

    public BurgerParamTest(float bunPrice, float saucePrice,
                           float fillingPrice, float expectedPrice) {
        this.bunPrice = bunPrice;
        this.saucePrice = saucePrice;
        this.fillingPrice = fillingPrice;
        this.expectedPrice = expectedPrice;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                // bun, sauce, filling, ожидаемая цена = bun*2 + sauce + filling
                { 100f, 100f, 200f, 500f },
                { 200f,   0f,   0f, 400f },
                {  50f,  50f,  50f, 200f },
                { 300f, 300f, 300f, 1200f }
        });
    }

    @Test
    public void testPriceWithDifferentIngredients() {
        Burger burger = new Burger();
        burger.setBuns(new Bun("bun", bunPrice));
        burger.addIngredient(new Ingredient(IngredientType.SAUCE, "sauce", saucePrice));
        burger.addIngredient(new Ingredient(IngredientType.FILLING, "filling", fillingPrice));

        assertEquals(expectedPrice, burger.getPrice(), 0.001f);
    }
}