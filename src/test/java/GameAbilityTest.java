import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameAbilityTest {
    
    @Test
    public void testThreeLivesAbility() {
        GameAbility ability = GameAbility.THREE_LIVES;
        assertNotNull(ability);
        assertEquals("THREE_LIVES", ability.name());
    }
    
    @Test
    public void testSpeedBoostAbility() {
        GameAbility ability = GameAbility.SPEED_BOOST;
        assertNotNull(ability);
        assertEquals("SPEED_BOOST", ability.name());
    }
    
    @Test
    public void testExtraPowerupAbility() {
        GameAbility ability = GameAbility.EXTRA_POWERUP;
        assertNotNull(ability);
        assertEquals("EXTRA_POWERUP", ability.name());
    }
    
    @Test
    public void testAllAbilitiesPresent() {
        GameAbility[] abilities = GameAbility.values();
        assertEquals(3, abilities.length);
        assertTrue(abilities[0] == GameAbility.THREE_LIVES);
        assertTrue(abilities[1] == GameAbility.SPEED_BOOST);
        assertTrue(abilities[2] == GameAbility.EXTRA_POWERUP);
    }
    
    @Test
    public void testAbilityValueOf() {
        assertEquals(GameAbility.THREE_LIVES, GameAbility.valueOf("THREE_LIVES"));
        assertEquals(GameAbility.SPEED_BOOST, GameAbility.valueOf("SPEED_BOOST"));
        assertEquals(GameAbility.EXTRA_POWERUP, GameAbility.valueOf("EXTRA_POWERUP"));
    }
}
