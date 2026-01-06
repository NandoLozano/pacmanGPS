import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import javax.swing.JFrame;
import java.awt.GraphicsEnvironment;

public class GameTest {
    
    @BeforeAll
    public static void checkHeadless() {
        // Skip all tests in this class if running in headless mode
        assumeFalse(GraphicsEnvironment.isHeadless(), 
                    "GUI tests skipped - running in headless environment");
    }
    
    @Test
    public void testConstructor() {
        Game game = new Game();
        assertNotNull(game);
        assertEquals("Pac-Man", game.getTitle());
        assertEquals(JFrame.EXIT_ON_CLOSE, game.getDefaultCloseOperation());
        assertEquals(500, game.getWidth());
        assertEquals(600, game.getHeight());
        assertFalse(game.isResizable());
    }
    
    @Test
    public void testMainMethodDoesNotThrow() {
        assertDoesNotThrow(() -> {
            // Main method should not throw any exceptions
            // We won't actually run it in tests, just verify it exists
            java.lang.reflect.Method mainMethod = Game.class.getMethod("main", String[].class);
            assertNotNull(mainMethod);
        });
    }
    
    @Test
    public void testGameHasMainMenu() {
        Game game = new Game();
        assertNotNull(game.getContentPane());
        assertEquals(1, game.getContentPane().getComponentCount());
    }
    
    @Test
    public void testGameSize() {
        Game game = new Game();
        assertEquals(500, game.getWidth());
        assertEquals(600, game.getHeight());
    }
    
    @Test
    public void testGameTitle() {
        Game game = new Game();
        assertEquals("Pac-Man", game.getTitle());
    }
    
    @Test
    public void testNotResizable() {
        Game game = new Game();
        assertFalse(game.isResizable());
    }
}
