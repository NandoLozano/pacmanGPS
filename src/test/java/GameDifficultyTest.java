import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameDifficultyTest {
    
    @Test
    public void testEasyDifficulty() {
        GameDifficulty difficulty = GameDifficulty.EASY;
        assertNotNull(difficulty);
        assertEquals("EASY", difficulty.name());
    }
    
    @Test
    public void testNormalDifficulty() {
        GameDifficulty difficulty = GameDifficulty.NORMAL;
        assertNotNull(difficulty);
        assertEquals("NORMAL", difficulty.name());
    }
    
    @Test
    public void testHardDifficulty() {
        GameDifficulty difficulty = GameDifficulty.HARD;
        assertNotNull(difficulty);
        assertEquals("HARD", difficulty.name());
    }
    
    @Test
    public void testAllDifficultiesPresent() {
        GameDifficulty[] difficulties = GameDifficulty.values();
        assertEquals(3, difficulties.length);
        assertTrue(difficulties[0] == GameDifficulty.EASY);
        assertTrue(difficulties[1] == GameDifficulty.NORMAL);
        assertTrue(difficulties[2] == GameDifficulty.HARD);
    }
    
    @Test
    public void testDifficultyValueOf() {
        assertEquals(GameDifficulty.EASY, GameDifficulty.valueOf("EASY"));
        assertEquals(GameDifficulty.NORMAL, GameDifficulty.valueOf("NORMAL"));
        assertEquals(GameDifficulty.HARD, GameDifficulty.valueOf("HARD"));
    }
}
