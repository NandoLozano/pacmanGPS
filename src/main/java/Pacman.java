import java.awt.*;
import java.awt.event.*;

public class Pacman {
    private int x, y;
    private Direction direction = Direction.LEFT;
    private Direction nextDirection = Direction.LEFT; // Buffered direction
    private int score = 0;
    private Board board;
    private static final int SIZE = 20;
    private int speed = 4; // Default speed
    private boolean hasSpeedBoost = false;

    public Pacman(int x, int y, Board board, boolean speedBoost) {
        this.x = x;
        this.y = y;
        this.board = board;
        this.hasSpeedBoost = speedBoost;
        if (speedBoost) {
            this.speed = 5; // 25% faster than normal speed of 4 (5/4 = 1.25)
        }
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
            case LEFT: newX -= speed; break;
            case RIGHT: newX += speed; break;
            case UP: newY -= speed; break;
            case DOWN: newY += speed; break;
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
            case LEFT: testX -= speed; break;
            case RIGHT: testX += speed; break;
            case UP: testY -= speed; break;
            case DOWN: testY += speed; break;
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
    
    public void setScore(int score) {
        this.score = score;
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