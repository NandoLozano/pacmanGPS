import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JFrame;
import java.util.function.Consumer;
import java.util.concurrent.atomic.AtomicReference;

public class AbilitySelectionTest {
    
    private JFrame mockFrame;
    private Consumer<GameAbility> mockConsumer;
    private AtomicReference<GameAbility> selectedAbility;
    
    @BeforeEach
    public void setUp() {
        mockFrame = new JFrame();
        selectedAbility = new AtomicReference<>();
        mockConsumer = ability -> selectedAbility.set(ability);
    }
    
    @Test
    public void testConstructor() {
        AbilitySelection abilitySelection = new AbilitySelection(mockFrame, GameAbility.THREE_LIVES, mockConsumer);
        assertNotNull(abilitySelection);
    }
    
    @Test
    public void testConstructorWithThreeLives() {
        AbilitySelection selection = new AbilitySelection(mockFrame, GameAbility.THREE_LIVES, mockConsumer);
        assertNotNull(selection);
    }
    
    @Test
    public void testConstructorWithSpeedBoost() {
        AbilitySelection selection = new AbilitySelection(mockFrame, GameAbility.SPEED_BOOST, mockConsumer);
        assertNotNull(selection);
    }
    
    @Test
    public void testConstructorWithExtraPowerup() {
        AbilitySelection selection = new AbilitySelection(mockFrame, GameAbility.EXTRA_POWERUP, mockConsumer);
        assertNotNull(selection);
    }
    
    @Test
    public void testHasComponents() {
        AbilitySelection selection = new AbilitySelection(mockFrame, GameAbility.THREE_LIVES, mockConsumer);
        assertTrue(selection.getComponentCount() > 0);
    }
    
    @Test
    public void testHasMultipleComponents() {
        AbilitySelection selection = new AbilitySelection(mockFrame, GameAbility.THREE_LIVES, mockConsumer);
        // Should have title, radio buttons, descriptions, and back button
        assertTrue(selection.getComponentCount() >= 8);
    }
    
    @Test
    public void testAllAbilitiesWork() {
        assertDoesNotThrow(() -> {
            new AbilitySelection(mockFrame, GameAbility.THREE_LIVES, mockConsumer);
            new AbilitySelection(mockFrame, GameAbility.SPEED_BOOST, mockConsumer);
            new AbilitySelection(mockFrame, GameAbility.EXTRA_POWERUP, mockConsumer);
        });
    }
}
