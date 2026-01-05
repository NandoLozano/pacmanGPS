import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Board extends JPanel implements ActionListener {
    private Timer timer;
    private Pacman pacman;
    private Ghost[] ghosts;
    
    // Constants for the game
    private static final int BLOCK_SIZE = 20;
    private static final int GRID_WIDTH = 20;
    private static final int GRID_HEIGHT = 20;
    
    // Level system
    private int currentLevel = 1;
    private int[][][] levels;
    private int[][] currentMap;
    
    // Map elements: 0 = empty, 1 = wall, 2 = dot
    
    public Board() {
        setFocusable(true);
        setBackground(Color.BLACK);
        
        initializeLevels();
        loadLevel(currentLevel);
        
        pacman = new Pacman(BLOCK_SIZE, BLOCK_SIZE, this);
        ghosts = new Ghost[] {
            new Ghost(18 * BLOCK_SIZE, 18 * BLOCK_SIZE, Color.RED, this),
            new Ghost(1 * BLOCK_SIZE, 18 * BLOCK_SIZE, Color.PINK, this),
            new Ghost(18 * BLOCK_SIZE, 1 * BLOCK_SIZE, Color.CYAN, this)
        };
        timer = new Timer(40, this);
        timer.start();
        addKeyListener(new PacmanKeyAdapter());
    }
    
    private void initializeLevels() {
        levels = new int[4][][]; // 3 levels + index 0 unused
        
        // Level 1: Simple maze
        levels[1] = new int[][] {
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,2,2,2,2,2,2,2,2,1,1,2,2,2,2,2,2,2,2,1},
            {1,2,1,1,2,1,1,1,2,1,1,2,1,1,1,2,1,1,2,1},
            {1,2,1,1,2,1,1,1,2,1,1,2,1,1,1,2,1,1,2,1},
            {1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1},
            {1,2,1,1,2,1,2,1,1,1,1,1,1,2,1,2,1,1,2,1},
            {1,2,2,2,2,1,2,2,2,1,1,2,2,2,1,2,2,2,2,1},
            {1,1,1,1,2,1,1,1,0,1,1,0,1,1,1,2,1,1,1,1},
            {1,1,1,1,2,1,0,0,0,0,0,0,0,0,1,2,1,1,1,1},
            {1,1,1,1,2,1,0,1,1,0,0,1,1,0,1,2,1,1,1,1},
            {0,0,0,0,2,0,0,1,0,0,0,0,1,0,0,2,0,0,0,0},
            {1,1,1,1,2,1,0,1,1,1,1,1,1,0,1,2,1,1,1,1},
            {1,1,1,1,2,1,0,0,0,0,0,0,0,0,1,2,1,1,1,1},
            {1,1,1,1,2,1,0,1,1,1,1,1,1,0,1,2,1,1,1,1},
            {1,2,2,2,2,2,2,2,2,1,1,2,2,2,2,2,2,2,2,1},
            {1,2,1,1,2,1,1,1,2,1,1,2,1,1,1,2,1,1,2,1},
            {1,2,2,1,2,2,2,2,2,2,2,2,2,2,2,2,1,2,2,1},
            {1,1,2,1,2,1,2,1,1,1,1,1,1,2,1,2,1,2,1,1},
            {1,2,2,2,2,1,2,2,2,1,1,2,2,2,1,2,2,2,2,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
        };
        
        // Level 2: More complex maze
        levels[2] = new int[][] {
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1},
            {1,2,1,1,1,1,2,1,1,1,1,1,1,2,1,1,1,1,2,1},
            {1,2,1,0,0,1,2,1,0,0,0,0,1,2,1,0,0,1,2,1},
            {1,2,1,0,0,1,2,1,0,0,0,0,1,2,1,0,0,1,2,1},
            {1,2,1,1,1,1,2,1,1,1,1,1,1,2,1,1,1,1,2,1},
            {1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1},
            {1,2,1,1,2,1,1,1,2,1,1,2,1,1,1,2,1,1,2,1},
            {1,2,1,1,2,1,1,1,2,1,1,2,1,1,1,2,1,1,2,1},
            {1,2,2,2,2,1,1,0,0,0,0,0,0,1,1,2,2,2,2,1},
            {1,1,1,1,2,1,0,0,1,1,1,1,0,0,1,2,1,1,1,1},
            {1,2,2,2,2,1,0,0,1,0,0,1,0,0,1,2,2,2,2,1},
            {1,2,1,1,2,1,1,1,1,0,0,1,1,1,1,2,1,1,2,1},
            {1,2,1,1,2,2,2,2,2,2,2,2,2,2,2,2,1,1,2,1},
            {1,2,1,1,1,1,2,1,1,0,0,1,1,2,1,1,1,1,2,1},
            {1,2,2,2,2,2,2,1,0,0,0,0,1,2,2,2,2,2,2,1},
            {1,2,1,1,1,1,2,1,1,1,1,1,1,2,1,1,1,1,2,1},
            {1,2,1,1,1,1,2,2,2,2,2,2,2,2,1,1,1,1,2,1},
            {1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
        };
        
        // Level 3: Advanced maze
        levels[3] = new int[][] {
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,2,2,2,2,2,2,2,2,1,1,2,2,2,2,2,2,2,2,1},
            {1,2,1,1,1,1,1,1,2,1,1,2,1,1,1,1,1,1,2,1},
            {1,2,1,0,0,0,0,1,2,1,1,2,1,0,0,0,0,1,2,1},
            {1,2,1,0,1,1,0,1,2,1,1,2,1,0,1,1,0,1,2,1},
            {1,2,1,0,1,1,0,1,2,1,1,2,1,0,1,1,0,1,2,1},
            {1,2,1,0,0,0,0,1,2,2,2,2,1,0,0,0,0,1,2,1},
            {1,2,1,1,1,1,1,1,2,1,1,2,1,1,1,1,1,1,2,1},
            {1,2,2,2,2,2,2,2,2,1,1,2,2,2,2,2,2,2,2,1},
            {1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1},
            {1,2,2,2,2,2,2,2,2,1,1,2,2,2,2,2,2,2,2,1},
            {1,2,1,1,1,1,1,1,2,1,1,2,1,1,1,1,1,1,2,1},
            {1,2,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,2,1},
            {1,2,1,2,1,1,2,1,1,1,1,1,1,2,1,1,2,1,2,1},
            {1,2,1,2,1,1,2,2,2,1,1,2,2,2,1,1,2,1,2,1},
            {1,2,1,2,1,1,1,1,2,1,1,2,1,1,1,1,2,1,2,1},
            {1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
        };
    }
    
    private void loadLevel(int level) {
        if (level >= 1 && level <= 3) {
            currentLevel = level;
            // Create a copy of the level map
            currentMap = new int[GRID_HEIGHT][GRID_WIDTH];
            for (int i = 0; i < GRID_HEIGHT; i++) {
                for (int j = 0; j < GRID_WIDTH; j++) {
                    currentMap[i][j] = levels[level][i][j];
                }
            }
        }
    }
    
    public boolean isWall(int x, int y) {
        int gridX = x / BLOCK_SIZE;
        int gridY = y / BLOCK_SIZE;
        
        if (gridX < 0 || gridX >= GRID_WIDTH || gridY < 0 || gridY >= GRID_HEIGHT) {
            return true; // Out of bounds is considered a wall
        }
        
        return currentMap[gridY][gridX] == 1;
    }
    
    public void eatDot(int x, int y) {
        int gridX = x / BLOCK_SIZE;
        int gridY = y / BLOCK_SIZE;
        
        if (gridX >= 0 && gridX < GRID_WIDTH && gridY >= 0 && gridY < GRID_HEIGHT) {
            if (currentMap[gridY][gridX] == 2) {
                currentMap[gridY][gridX] = 0; // Remove the dot
                pacman.addScore(10);
                checkLevelComplete();
            }
        }
    }
    
    private void checkLevelComplete() {
        // Check if all dots are eaten
        for (int i = 0; i < GRID_HEIGHT; i++) {
            for (int j = 0; j < GRID_WIDTH; j++) {
                if (currentMap[i][j] == 2) {
                    return; // Still dots remaining
                }
            }
        }
        
        // All dots eaten, load next level
        if (currentLevel < 3) {
            currentLevel++;
            loadLevel(currentLevel);
            // Reset positions
            pacman.reset(BLOCK_SIZE, BLOCK_SIZE);
            ghosts[0].reset(18 * BLOCK_SIZE, 18 * BLOCK_SIZE);
            ghosts[1].reset(1 * BLOCK_SIZE, 18 * BLOCK_SIZE);
            ghosts[2].reset(18 * BLOCK_SIZE, 1 * BLOCK_SIZE);
        } else {
            // Game won!
            JOptionPane.showMessageDialog(this, "¡Felicidades! Has completado todos los niveles.\nPuntuación final: " + pacman.getScore());
            System.exit(0);
        }
    }
    
    public int getBlockSize() {
        return BLOCK_SIZE;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
        pacman.draw(g);
        for (Ghost ghost : ghosts) {
            ghost.draw(g);
        }
    }

    private void drawBoard(Graphics g) {
        // Draw walls and dots
        for (int i = 0; i < GRID_HEIGHT; i++) {
            for (int j = 0; j < GRID_WIDTH; j++) {
                int x = j * BLOCK_SIZE;
                int y = i * BLOCK_SIZE;
                
                if (currentMap[i][j] == 1) {
                    // Draw wall
                    g.setColor(Color.BLUE);
                    g.fillRect(x, y, BLOCK_SIZE, BLOCK_SIZE);
                    g.setColor(Color.CYAN);
                    g.drawRect(x, y, BLOCK_SIZE, BLOCK_SIZE);
                } else if (currentMap[i][j] == 2) {
                    // Draw dot
                    g.setColor(Color.WHITE);
                    g.fillOval(x + BLOCK_SIZE/2 - 2, y + BLOCK_SIZE/2 - 2, 4, 4);
                }
            }
        }
        
        // Draw score and level
        g.setColor(Color.YELLOW);
        g.drawString("Score: " + pacman.getScore(), 10, 410);
        g.drawString("Level: " + currentLevel, 320, 410);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        pacman.move();
        for (Ghost ghost : ghosts) {
            ghost.move();
        }
        
        // Check collisions with ghosts
        for (Ghost ghost : ghosts) {
            if (pacman.collidesWith(ghost)) {
                JOptionPane.showMessageDialog(this, "¡Game Over! Los fantasmas te atraparon.\nPuntuación: " + pacman.getScore());
                System.exit(0);
            }
        }
        
        repaint();
    }

    private class PacmanKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            pacman.keyPressed(e);
        }
    }
}