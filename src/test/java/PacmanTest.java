import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class PacmanTest {
    
    @Mock
    private Board mockBoard;
    
    @Mock
    private Graphics mockGraphics;
    
    @Mock
    private KeyEvent mockKeyEvent;
    
    private Pacman pacman;
    private Pacman pacmanWithSpeedBoost;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        pacman = new Pacman(100, 100, mockBoard, false);
        pacmanWithSpeedBoost = new Pacman(100, 100, mockBoard, true);
    }
    
    @Test
    public void testConstructorNormalSpeed() {
        assertNotNull(pacman);
    }
    
    @Test
    public void testConstructorWithSpeedBoost() {
        assertNotNull(pacmanWithSpeedBoost);
    }
    
    @Test
    public void testDraw() {
        pacman.draw(mockGraphics);
        verify(mockGraphics, atLeastOnce()).setColor(any());
        verify(mockGraphics, times(1)).fillArc(anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt());
    }
    
    @Test
    public void testMoveLeft() {
        when(mockBoard.isWall(anyInt(), anyInt())).thenReturn(false);
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_LEFT);
        
        pacman.keyPressed(mockKeyEvent);
        pacman.move();
        
        verify(mockBoard, atLeast(4)).isWall(anyInt(), anyInt());
    }
    
    @Test
    public void testMoveRight() {
        when(mockBoard.isWall(anyInt(), anyInt())).thenReturn(false);
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_RIGHT);
        
        pacman.keyPressed(mockKeyEvent);
        pacman.move();
        
        verify(mockBoard, atLeast(4)).isWall(anyInt(), anyInt());
    }
    
    @Test
    public void testMoveUp() {
        when(mockBoard.isWall(anyInt(), anyInt())).thenReturn(false);
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_UP);
        
        pacman.keyPressed(mockKeyEvent);
        pacman.move();
        
        verify(mockBoard, atLeast(4)).isWall(anyInt(), anyInt());
    }
    
    @Test
    public void testMoveDown() {
        when(mockBoard.isWall(anyInt(), anyInt())).thenReturn(false);
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_DOWN);
        
        pacman.keyPressed(mockKeyEvent);
        pacman.move();
        
        verify(mockBoard, atLeast(4)).isWall(anyInt(), anyInt());
    }
    
    @Test
    public void testMoveBlockedByWall() {
        when(mockBoard.isWall(anyInt(), anyInt())).thenReturn(true);
        
        pacman.move();
        
        // When blocked by wall, checks at least once
        verify(mockBoard, atLeastOnce()).isWall(anyInt(), anyInt());
    }
    
    @Test
    public void testKeyPressedLeft() {
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_LEFT);
        pacman.keyPressed(mockKeyEvent);
        verify(mockKeyEvent).getKeyCode();
    }
    
    @Test
    public void testKeyPressedRight() {
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_RIGHT);
        pacman.keyPressed(mockKeyEvent);
        verify(mockKeyEvent).getKeyCode();
    }
    
    @Test
    public void testKeyPressedUp() {
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_UP);
        pacman.keyPressed(mockKeyEvent);
        verify(mockKeyEvent).getKeyCode();
    }
    
    @Test
    public void testKeyPressedDown() {
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_DOWN);
        pacman.keyPressed(mockKeyEvent);
        verify(mockKeyEvent).getKeyCode();
    }
    
    @Test
    public void testGetScore() {
        assertEquals(0, pacman.getScore());
    }
    
    @Test
    public void testAddScore() {
        pacman.addScore(100);
        assertEquals(100, pacman.getScore());
        
        pacman.addScore(50);
        assertEquals(150, pacman.getScore());
    }
    
    @Test
    public void testSetScore() {
        pacman.setScore(500);
        assertEquals(500, pacman.getScore());
    }
    
    @Test
    public void testReset() {
        pacman.reset(200, 300);
        // After reset, Pacman should be at new position
        // We can verify by checking if it doesn't throw an exception
        assertDoesNotThrow(() -> pacman.draw(mockGraphics));
    }
    
    @Test
    public void testTeleport() {
        pacman.teleport(400, 500);
        // After teleport, Pacman should be at new position
        assertDoesNotThrow(() -> pacman.draw(mockGraphics));
    }
    
    @Test
    public void testCollidesWithGhost() {
        Ghost mockGhost = mock(Ghost.class);
        when(mockGhost.getX()).thenReturn(100);
        when(mockGhost.getY()).thenReturn(100);
        
        assertTrue(pacman.collidesWith(mockGhost));
    }
    
    @Test
    public void testNotCollidesWithGhost() {
        Ghost mockGhost = mock(Ghost.class);
        when(mockGhost.getX()).thenReturn(300);
        when(mockGhost.getY()).thenReturn(300);
        
        assertFalse(pacman.collidesWith(mockGhost));
    }
    
    @Test
    public void testEatDotCalled() {
        when(mockBoard.isWall(anyInt(), anyInt())).thenReturn(false);
        
        pacman.move();
        
        verify(mockBoard).eatDot(anyInt(), anyInt());
    }
    
    @Test
    public void testCheckPortalCalled() {
        when(mockBoard.isWall(anyInt(), anyInt())).thenReturn(false);
        
        pacman.move();
        
        verify(mockBoard).checkPortal(anyInt(), anyInt());
    }
}
