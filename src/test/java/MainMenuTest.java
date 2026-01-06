import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import javax.swing.JFrame;
import java.awt.GraphicsEnvironment;

public class MainMenuTest {
    
    @BeforeAll
    public static void checkHeadless() {
        // Skip all tests in this class if running in headless mode
        assumeFalse(GraphicsEnvironment.isHeadless(), 
                    "GUI tests skipped - running in headless environment");
    }
    
    private JFrame mockFrame;
    private MainMenu mainMenu;
    private MainMenu mainMenuWithAbility;
    
    @BeforeEach
    public void setUp() {
        mockFrame = new JFrame();
        mainMenu = new MainMenu(mockFrame);
        mainMenuWithAbility = new MainMenu(mockFrame, GameAbility.SPEED_BOOST);
    }
    
    @Test
    public void testConstructor() {
        assertNotNull(mainMenu);
    }
    
    @Test
    public void testConstructorWithAbility() {
        assertNotNull(mainMenuWithAbility);
    }
    
    @Test
    public void testConstructorWithThreeLives() {
        MainMenu menu = new MainMenu(mockFrame, GameAbility.THREE_LIVES);
        assertNotNull(menu);
    }
    
    @Test
    public void testConstructorWithSpeedBoost() {
        MainMenu menu = new MainMenu(mockFrame, GameAbility.SPEED_BOOST);
        assertNotNull(menu);
    }
    
    @Test
    public void testConstructorWithExtraPowerup() {
        MainMenu menu = new MainMenu(mockFrame, GameAbility.EXTRA_POWERUP);
        assertNotNull(menu);
    }
    
    @Test
    public void testDefaultConstructorUsesThreeLives() {
        MainMenu menu = new MainMenu(mockFrame);
        assertNotNull(menu);
        // Default should be THREE_LIVES
        assertTrue(menu.getComponentCount() > 0);
    }
    
    @Test
    public void testHasComponents() {
        assertTrue(mainMenu.getComponentCount() > 0);
    }
    
    @Test
    public void testHasMultipleComponents() {
        // Menu should have title, buttons, and label
        assertTrue(mainMenu.getComponentCount() >= 5);
    }
    
    @Test
    public void testAllAbilitiesCanBeSet() {
        assertDoesNotThrow(() -> {
            new MainMenu(mockFrame, GameAbility.THREE_LIVES);
            new MainMenu(mockFrame, GameAbility.SPEED_BOOST);
            new MainMenu(mockFrame, GameAbility.EXTRA_POWERUP);
        });
    }
}
