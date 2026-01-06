import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DirectionTest {
    
    @Test
    public void testLeftDirection() {
        Direction left = Direction.LEFT;
        assertEquals(180, left.getAngle());
    }
    
    @Test
    public void testRightDirection() {
        Direction right = Direction.RIGHT;
        assertEquals(0, right.getAngle());
    }
    
    @Test
    public void testUpDirection() {
        Direction up = Direction.UP;
        assertEquals(90, up.getAngle());
    }
    
    @Test
    public void testDownDirection() {
        Direction down = Direction.DOWN;
        assertEquals(270, down.getAngle());
    }
    
    @Test
    public void testAllDirectionsPresent() {
        Direction[] directions = Direction.values();
        assertEquals(4, directions.length);
        assertTrue(directions[0] == Direction.LEFT);
        assertTrue(directions[1] == Direction.RIGHT);
        assertTrue(directions[2] == Direction.UP);
        assertTrue(directions[3] == Direction.DOWN);
    }
    
    @Test
    public void testDirectionValueOf() {
        assertEquals(Direction.LEFT, Direction.valueOf("LEFT"));
        assertEquals(Direction.RIGHT, Direction.valueOf("RIGHT"));
        assertEquals(Direction.UP, Direction.valueOf("UP"));
        assertEquals(Direction.DOWN, Direction.valueOf("DOWN"));
    }
}
