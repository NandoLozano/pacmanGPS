import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.Graphics;
import java.awt.event.ActionEvent;

public class BoardTest {
    
    private Board board;
    private Board boardThreeLives;
    private Board boardSpeedBoost;
    private Board boardExtraPowerup;
    private Board boardEasyDifficulty;
    private Board boardHardDifficulty;
    
    @BeforeEach
    public void setUp() {
        board = new Board(GameAbility.THREE_LIVES, GameDifficulty.NORMAL);
        boardThreeLives = new Board(GameAbility.THREE_LIVES, GameDifficulty.NORMAL);
        boardSpeedBoost = new Board(GameAbility.SPEED_BOOST, GameDifficulty.NORMAL);
        boardExtraPowerup = new Board(GameAbility.EXTRA_POWERUP, GameDifficulty.NORMAL);
        boardEasyDifficulty = new Board(GameAbility.THREE_LIVES, GameDifficulty.EASY);
        boardHardDifficulty = new Board(GameAbility.THREE_LIVES, GameDifficulty.HARD);
    }
    
    @Test
    public void testConstructorThreeLives() {
        assertNotNull(boardThreeLives);
        assertTrue(boardThreeLives.isFocusable());
    }
    
    @Test
    public void testConstructorSpeedBoost() {
        assertNotNull(boardSpeedBoost);
    }
    
    @Test
    public void testConstructorExtraPowerup() {
        assertNotNull(boardExtraPowerup);
    }
    
    @Test
    public void testConstructorEasyDifficulty() {
        assertNotNull(boardEasyDifficulty);
    }
    
    @Test
    public void testConstructorHardDifficulty() {
        assertNotNull(boardHardDifficulty);
    }
    
    @Test
    public void testIsWallOnBoundary() {
        assertTrue(board.isWall(0, 0));
    }
    
    @Test
    public void testIsWallOutOfBounds() {
        assertTrue(board.isWall(-20, -20));
        assertTrue(board.isWall(1000, 1000));
    }
    
    @Test
    public void testIsWallInEmptySpace() {
        // Position 40, 40 might be a wall or empty depending on level design
        // Just verify it doesn't throw an exception
        assertDoesNotThrow(() -> board.isWall(40, 40));
    }
    
    @Test
    public void testEatDotAtValidPosition() {
        // Eating a dot should not throw an exception
        assertDoesNotThrow(() -> board.eatDot(40, 40));
    }
    
    @Test
    public void testEatDotAtInvalidPosition() {
        // Eating at invalid position should not throw
        assertDoesNotThrow(() -> board.eatDot(-1, -1));
    }
    
    @Test
    public void testEatDotOnWall() {
        assertDoesNotThrow(() -> board.eatDot(0, 0));
    }
    
    @Test
    public void testIsPoweredUpInitially() {
        assertFalse(board.isPoweredUp());
    }
    
    @Test
    public void testIsPoweredUpWithExtraPowerup() {
        assertTrue(boardExtraPowerup.isPoweredUp());
    }
    
    @Test
    public void testCheckPortalAtInvalidPosition() {
        assertDoesNotThrow(() -> board.checkPortal(-1, -1));
    }
    
    @Test
    public void testCheckPortalAtNonPortalPosition() {
        assertDoesNotThrow(() -> board.checkPortal(40, 40));
    }
    
    @Test
    public void testGetBlockSize() {
        assertEquals(20, board.getBlockSize());
    }
    
    @Test
    public void testActionPerformedDoesNotThrow() {
        ActionEvent mockEvent = new ActionEvent(board, 0, "test");
        assertDoesNotThrow(() -> board.actionPerformed(mockEvent));
    }
    
    @Test
    public void testMultipleActionPerformed() {
        ActionEvent mockEvent = new ActionEvent(board, 0, "test");
        for (int i = 0; i < 10; i++) {
            assertDoesNotThrow(() -> board.actionPerformed(mockEvent));
        }
    }
    
    @Test
    public void testBoardFocusable() {
        assertTrue(board.isFocusable());
    }
    
    @Test
    public void testBoardWithAllAbilities() {
        Board board1 = new Board(GameAbility.THREE_LIVES, GameDifficulty.NORMAL);
        Board board2 = new Board(GameAbility.SPEED_BOOST, GameDifficulty.NORMAL);
        Board board3 = new Board(GameAbility.EXTRA_POWERUP, GameDifficulty.NORMAL);
        
        assertNotNull(board1);
        assertNotNull(board2);
        assertNotNull(board3);
    }
    
    @Test
    public void testBoardWithAllDifficulties() {
        Board board1 = new Board(GameAbility.THREE_LIVES, GameDifficulty.EASY);
        Board board2 = new Board(GameAbility.THREE_LIVES, GameDifficulty.NORMAL);
        Board board3 = new Board(GameAbility.THREE_LIVES, GameDifficulty.HARD);
        
        assertNotNull(board1);
        assertNotNull(board2);
        assertNotNull(board3);
    }
    
    @Test
    public void testIsWallAtDifferentPositions() {
        // Test multiple positions
        board.isWall(0, 0);
        board.isWall(100, 100);
        board.isWall(200, 200);
        board.isWall(300, 300);
        
        // Should not throw exceptions
        assertTrue(true);
    }
    
    @Test
    public void testEatDotMultipleTimes() {
        board.eatDot(40, 40);
        board.eatDot(40, 40); // Eating same position twice
        board.eatDot(60, 60);
        
        // Should not throw exceptions
        assertTrue(true);
    }
    
    @Test
    public void testCheckPortalMultipleTimes() {
        board.checkPortal(0, 200);
        board.checkPortal(380, 200);
        
        // Should not throw exceptions
        assertTrue(true);
    }
    
    @Test
    public void testCombinedOperations() {
        board.isWall(40, 40);
        board.eatDot(40, 40);
        board.checkPortal(40, 40);
        ActionEvent mockEvent = new ActionEvent(board, 0, "test");
        board.actionPerformed(mockEvent);
        
        // Should execute without errors
        assertTrue(true);
    }
    
    @Test
    public void testPowerUpStartsActiveForExtraPowerup() {
        assertTrue(boardExtraPowerup.isPoweredUp());
    }
    
    @Test
    public void testPowerUpNotActiveForOtherAbilities() {
        assertFalse(boardThreeLives.isPoweredUp());
        assertFalse(boardSpeedBoost.isPoweredUp());
    }
}
