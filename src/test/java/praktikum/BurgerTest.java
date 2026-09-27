package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {

    private Burger burger;

    @Mock
    private Bun mockBun;

    @Mock
    private Ingredient mockSauce;

    @Mock
    private Ingredient mockFilling;

    @Before
    public void setUp() {
        burger = new Burger();
    }

    @Test
    public void testSetBuns() {
        burger.setBuns(mockBun);
        assertEquals(mockBun, burger.bun);
    }

    @Test
    public void testAddIngredient() {
        burger.addIngredient(mockSauce);
        assertEquals(1, burger.ingredients.size());
        assertEquals(mockSauce, burger.ingredients.get(0));
    }

    @Test
    public void testRemoveIngredient() {
        burger.addIngredient(mockSauce);
        burger.addIngredient(mockFilling);
        burger.removeIngredient(0);
        assertEquals(1, burger.ingredients.size());
        assertEquals(mockFilling, burger.ingredients.get(0));
    }

    @Test
    public void testMoveIngredient() {
        burger.addIngredient(mockSauce);
        burger.addIngredient(mockFilling);
        burger.moveIngredient(0, 1);
        assertEquals(mockFilling, burger.ingredients.get(0));
        assertEquals(mockSauce, burger.ingredients.get(1));
    }

    @Test
    public void testGetPrice() {
        when(mockBun.getPrice()).thenReturn(100f);
        when(mockSauce.getPrice()).thenReturn(50f);
        when(mockFilling.getPrice()).thenReturn(200f);

        burger.setBuns(mockBun);
        burger.addIngredient(mockSauce);
        burger.addIngredient(mockFilling);

        // (100 * 2) + 50 + 200 = 450
        assertEquals(450f, burger.getPrice(), 0.001f);
    }

    @Test
    public void testGetPriceWithNoIngredients() {
        when(mockBun.getPrice()).thenReturn(150f);
        burger.setBuns(mockBun);
        // только булки: 150 * 2 = 300
        assertEquals(300f, burger.getPrice(), 0.001f);
    }

    @Test
    public void testGetReceipt() {
        when(mockBun.getName()).thenReturn("black bun");
        when(mockBun.getPrice()).thenReturn(100f);
        when(mockSauce.getName()).thenReturn("hot sauce");
        when(mockSauce.getType()).thenReturn(IngredientType.SAUCE);
        when(mockSauce.getPrice()).thenReturn(100f);

        burger.setBuns(mockBun);
        burger.addIngredient(mockSauce);

        String receipt = burger.getReceipt();

        // Проверяем ключевые строки чека
        assertTrue(receipt.contains("(==== black bun ====)"));
        assertTrue(receipt.contains("= sauce hot sauce ="));
        assertTrue(receipt.contains("Price:"));
        assertTrue(receipt.contains("300"));    }

    @Test
    public void testGetReceiptWithFilling() {
        when(mockBun.getName()).thenReturn("white bun");
        when(mockBun.getPrice()).thenReturn(200f);
        when(mockFilling.getName()).thenReturn("cutlet");
        when(mockFilling.getType()).thenReturn(IngredientType.FILLING);
        when(mockFilling.getPrice()).thenReturn(100f);

        burger.setBuns(mockBun);
        burger.addIngredient(mockFilling);

        String receipt = burger.getReceipt();
        assertTrue(receipt.contains("= filling cutlet ="));
    }
}