import java.awt.*;
import java.util.Random;

public class Ghost {
    private int x, y;
    private int spawnX, spawnY;
    private Direction direction;
    private Color color;
    private Random random = new Random();
    private Board board;
    private static final int SIZE = 20;
    private boolean edible = false;

    public Ghost(int x, int y, Color color, Board board) {
        this.x = x;
        this.y = y;
        this.spawnX = x;
        this.spawnY = y;
        this.color = color;
        this.board = board;
        this.direction = Direction.values()[random.nextInt(4)];
    }

    public void draw(Graphics g) {
        if (edible) {
            // Draw as blue when edible
            g.setColor(Color.BLUE);
            g.fillOval(x, y, SIZE, SIZE);
            g.setColor(Color.WHITE);
            g.fillOval(x + 5, y + 5, 3, 3);
            g.fillOval(x + 12, y + 5, 3, 3);
        } else {
            g.setColor(color);
            g.fillOval(x, y, SIZE, SIZE);
        }
    }

    public void move() {
        // Try to change direction occasionally
        if (random.nextInt(10) == 0) {
            direction = Direction.values()[random.nextInt(4)];
        }
        
        int newX = x;
        int newY = y;
        
        // Slow down when edible
        int speed = edible ? 2 : 4;
        
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
        } else {
            // Hit a wall, change direction
            direction = Direction.values()[random.nextInt(4)];
        }
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public void reset(int x, int y) {
        this.x = x;
        this.y = y;
        this.spawnX = x;
        this.spawnY = y;
        this.direction = Direction.values()[random.nextInt(4)];
        this.edible = false;
    }
    
    public void setEdible(boolean edible) {
        this.edible = edible;
    }
    
    public boolean isEdible() {
        return edible;
    }
    
    public void respawn() {
        x = spawnX;
        y = spawnY;
        edible = false;
        direction = Direction.values()[random.nextInt(4)];
    }
}