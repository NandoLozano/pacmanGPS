import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import javax.swing.JFrame;
import java.awt.GraphicsEnvironment;
import java.util.function.Consumer;
import java.util.concurrent.atomic.AtomicReference;

public class DifficultySelectionTest {
    
    @BeforeAll
    public static void checkHeadless() {
        // Skip all tests in this class if running in headless mode
        assumeFalse(GraphicsEnvironment.isHeadless(), 
                    "GUI tests skipped - running in headless environment");
    }
    
    private JFrame mockFrame;
    private Consumer<GameDifficulty> mockConsumer;
    private AtomicReference<GameDifficulty> selectedDifficulty;
    
    @BeforeEach
    public void setUp() {
        mockFrame = new JFrame();
        selectedDifficulty = new AtomicReference<>();
        mockConsumer = difficulty -> selectedDifficulty.set(difficulty);
    }
    
    @Test
    public void testConstructor() {
        DifficultySelection selection = new DifficultySelection(mockFrame, GameAbility.THREE_LIVES, mockConsumer);
        assertNotNull(selection);
    }
    
    @Test
    public void testConstructorWithThreeLives() {
        DifficultySelection selection = new DifficultySelection(mockFrame, GameAbility.THREE_LIVES, mockConsumer);
        assertNotNull(selection);
    }
    
    @Test
    public void testConstructorWithSpeedBoost() {
        DifficultySelection selection = new DifficultySelection(mockFrame, GameAbility.SPEED_BOOST, mockConsumer);
        assertNotNull(selection);
    }
    
    @Test
    public void testConstructorWithExtraPowerup() {
        DifficultySelection selection = new DifficultySelection(mockFrame, GameAbility.EXTRA_POWERUP, mockConsumer);
        assertNotNull(selection);
    }
    
    @Test
    public void testHasComponents() {
        DifficultySelection selection = new DifficultySelection(mockFrame, GameAbility.THREE_LIVES, mockConsumer);
        assertTrue(selection.getComponentCount() > 0);
    }
    
    @Test
    public void testHasMultipleComponents() {
        DifficultySelection selection = new DifficultySelection(mockFrame, GameAbility.THREE_LIVES, mockConsumer);
        // Should have title, difficulty buttons, descriptions, and back button
        assertTrue(selection.getComponentCount() >= 8);
    }
    
    @Test
    public void testAllAbilitiesWork() {
        assertDoesNotThrow(() -> {
            new DifficultySelection(mockFrame, GameAbility.THREE_LIVES, mockConsumer);
            new DifficultySelection(mockFrame, GameAbility.SPEED_BOOST, mockConsumer);
            new DifficultySelection(mockFrame, GameAbility.EXTRA_POWERUP, mockConsumer);
        });
    }
}
