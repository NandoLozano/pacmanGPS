import java.awt.*;
import java.awt.event.*;

public class Pacman {
    private int x, y;
    private Direction direction = Direction.LEFT;
    private Direction nextDirection = Direction.LEFT; // Buffered direction
    private int score = 0;
    private Board board;
    private static final int SIZE = 20;

    public Pacman(int x, int y, Board board) {
        this.x = x;
        this.y = y;
        this.board = board;
    }

    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillArc(x, y, SIZE, SIZE, direction.getAngle(), 300);
    }

    public void move() {
        // Try to use the buffered direction first (easier turning)
        if (nextDirection != direction) {
            if (canMove(nextDirection)) {
                direction = nextDirection;
            }
        }
        
        int newX = x;
        int newY = y;
        
        switch (direction) {
            case LEFT: newX -= 4; break;
            case RIGHT: newX += 4; break;
            case UP: newY -= 4; break;
            case DOWN: newY += 4; break;
        }
        
        // Check collision with walls
        if (!board.isWall(newX, newY) && 
            !board.isWall(newX + SIZE - 1, newY) &&
            !board.isWall(newX, newY + SIZE - 1) &&
            !board.isWall(newX + SIZE - 1, newY + SIZE - 1)) {
            x = newX;
            y = newY;
            
            // Check if we can eat a dot at current position
            board.eatDot(x + SIZE/2, y + SIZE/2);
            
            // Check if we entered a portal
            board.checkPortal(x + SIZE/2, y + SIZE/2);
        }
    }
    
    private boolean canMove(Direction dir) {
        int testX = x;
        int testY = y;
        
        switch (dir) {
            case LEFT: testX -= 4; break;
            case RIGHT: testX += 4; break;
            case UP: testY -= 4; break;
            case DOWN: testY += 4; break;
        }
        
        return !board.isWall(testX, testY) && 
               !board.isWall(testX + SIZE - 1, testY) &&
               !board.isWall(testX, testY + SIZE - 1) &&
               !board.isWall(testX + SIZE - 1, testY + SIZE - 1);
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT: nextDirection = Direction.LEFT; break;
            case KeyEvent.VK_RIGHT: nextDirection = Direction.RIGHT; break;
            case KeyEvent.VK_UP: nextDirection = Direction.UP; break;
            case KeyEvent.VK_DOWN: nextDirection = Direction.DOWN; break;
        }
    }

    public int getScore() {
        return score;
    }
    
    public void addScore(int points) {
        score += points;
    }
    
    public void reset(int x, int y) {
        this.x = x;
        this.y = y;
        this.direction = Direction.LEFT;
        this.nextDirection = Direction.LEFT;
    }
    
    public void teleport(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public boolean collidesWith(Ghost ghost) {
        int ghostX = ghost.getX();
        int ghostY = ghost.getY();
        
        // Simple collision detection using squared distance (more efficient)
        int dx = (x + SIZE/2) - (ghostX + SIZE/2);
        int dy = (y + SIZE/2) - (ghostY + SIZE/2);
        int distanceSquared = dx*dx + dy*dy;
        
        return distanceSquared < (SIZE*SIZE);
    }
}