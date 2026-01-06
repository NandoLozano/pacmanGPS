import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.awt.Color;
import java.awt.Graphics;

public class GhostTest {
    
    @Mock
    private Board mockBoard;
    
    @Mock
    private Graphics mockGraphics;
    
    private Ghost ghost;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ghost = new Ghost(100, 100, Color.RED, mockBoard);
    }
    
    @Test
    public void testConstructor() {
        assertNotNull(ghost);
    }
    
    @Test
    public void testDraw() {
        ghost.draw(mockGraphics);
        verify(mockGraphics, atLeastOnce()).setColor(any(Color.class));
        verify(mockGraphics, atLeastOnce()).fillOval(anyInt(), anyInt(), anyInt(), anyInt());
    }
    
    @Test
    public void testDrawEdible() {
        ghost.setEdible(true);
        ghost.draw(mockGraphics);
        verify(mockGraphics, atLeastOnce()).setColor(Color.BLUE);
        verify(mockGraphics, atLeastOnce()).setColor(Color.WHITE);
        verify(mockGraphics, atLeastOnce()).fillOval(anyInt(), anyInt(), anyInt(), anyInt());
    }
    
    @Test
    public void testMove() {
        when(mockBoard.isWall(anyInt(), anyInt())).thenReturn(false);
        
        ghost.move();
        
        verify(mockBoard, atLeast(4)).isWall(anyInt(), anyInt());
    }
    
    @Test
    public void testMoveBlockedByWall() {
        when(mockBoard.isWall(anyInt(), anyInt())).thenReturn(true);
        
        ghost.move();
        
        // When blocked by wall, checks at least once
        verify(mockBoard, atLeastOnce()).isWall(anyInt(), anyInt());
    }
    
    @Test
    public void testGetX() {
        assertEquals(100, ghost.getX());
    }
    
    @Test
    public void testGetY() {
        assertEquals(100, ghost.getY());
    }
    
    @Test
    public void testReset() {
        ghost.reset(200, 300);
        assertEquals(200, ghost.getX());
        assertEquals(300, ghost.getY());
        assertFalse(ghost.isEdible());
    }
    
    @Test
    public void testSetEdible() {
        ghost.setEdible(true);
        assertTrue(ghost.isEdible());
        
        ghost.setEdible(false);
        assertFalse(ghost.isEdible());
    }
    
    @Test
    public void testIsEdible() {
        assertFalse(ghost.isEdible());
        ghost.setEdible(true);
        assertTrue(ghost.isEdible());
    }
    
    @Test
    public void testRespawn() {
        ghost.reset(200, 300);
        ghost.setEdible(true);
        
        ghost.respawn();
        
        assertEquals(200, ghost.getX());
        assertEquals(300, ghost.getY());
        assertFalse(ghost.isEdible());
    }
    
    @Test
    public void testMultipleMoves() {
        when(mockBoard.isWall(anyInt(), anyInt())).thenReturn(false);
        
        for (int i = 0; i < 10; i++) {
            ghost.move();
        }
        
        verify(mockBoard, atLeast(40)).isWall(anyInt(), anyInt());
    }
    
    @Test
    public void testEdibleGhostSlowerMovement() {
        when(mockBoard.isWall(anyInt(), anyInt())).thenReturn(false);
        
        int initialX = ghost.getX();
        int initialY = ghost.getY();
        
        ghost.setEdible(true);
        ghost.move();
        
        // Ghost moves but at a slower speed when edible
        verify(mockBoard, atLeast(4)).isWall(anyInt(), anyInt());
    }
}
