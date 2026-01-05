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
    
    // Ghost spawn positions for different difficulty levels
    private static final int[][] GHOST_SPAWN_POSITIONS = {
        {18 * 20, 18 * 20},  // Bottom-right
        {1 * 20, 18 * 20},   // Bottom-left
        {18 * 20, 1 * 20},   // Top-right
        {9 * 20, 9 * 20}     // Center (for 4th ghost in hard mode)
    };
    
    // Level system
    private int currentLevel = 1;
    private int[][][] levels;
    private int[][] currentMap;
    
    // Power-up system
    private int powerUpTimer = 0;
    private static final int POWER_UP_DURATION = 150; // About 6 seconds (150 ticks * 40ms/tick from Timer)
    
    // Portal system
    private int portalCooldown = 0;
    private static final int PORTAL_COOLDOWN_DURATION = 15; // About 0.6 seconds to prevent infinite loops
    
    // Ability system
    private GameAbility selectedAbility;
    private GameDifficulty selectedDifficulty;
    private int lives = 1;
    private int savedScore = 0;
    
    // Map elements: 0 = empty, 1 = wall, 2 = dot, 3 = power-up, 4 = portal
    
    public Board(GameAbility ability, GameDifficulty difficulty) {
        this.selectedAbility = ability;
        this.selectedDifficulty = difficulty;
        
        // Set lives based on ability
        if (ability == GameAbility.THREE_LIVES) {
            lives = 3;
        } else {
            lives = 1;
        }
        
        setFocusable(true);
        setBackground(Color.BLACK);
        
        initializeLevels();
        loadLevel(currentLevel);
        
        // Create Pacman with speed boost if selected
        boolean speedBoost = (ability == GameAbility.SPEED_BOOST);
        pacman = new Pacman(BLOCK_SIZE, BLOCK_SIZE, this, speedBoost);
        
        // Initialize ghosts based on difficulty
        initializeGhosts();
        
        // Activate extra powerup if selected
        if (ability == GameAbility.EXTRA_POWERUP) {
            powerUpTimer = POWER_UP_DURATION;
            for (Ghost ghost : ghosts) {
                ghost.setEdible(true);
            }
        }
        
        timer = new Timer(40, this);
        timer.start();
        addKeyListener(new PacmanKeyAdapter());
    }
    
    private void initializeGhosts() {
        int numGhosts;
        switch (selectedDifficulty) {
            case EASY:
                numGhosts = 2;
                break;
            case HARD:
                numGhosts = 4;
                break;
            case NORMAL:
            default:
                numGhosts = 3;
                break;
        }
        
        // Define ghost colors
        Color[] ghostColors = {Color.RED, Color.PINK, Color.CYAN, Color.ORANGE};
        
        ghosts = new Ghost[numGhosts];
        for (int i = 0; i < numGhosts; i++) {
            ghosts[i] = new Ghost(GHOST_SPAWN_POSITIONS[i][0], GHOST_SPAWN_POSITIONS[i][1], ghostColors[i], this);
        }
    }
    
    private void initializeLevels() {
        levels = new int[4][][]; // 3 levels + index 0 unused
        
        // Level 1: Simple maze
        levels[1] = new int[][] {
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,2,2,2,2,2,2,2,2,1,1,2,2,2,2,2,2,2,2,1},
            {1,2,1,1,2,1,1,1,2,1,1,2,1,1,1,2,1,1,2,1},
            {1,2,1,1,2,1,1,1,2,1,1,2,1,1,1,2,1,1,2,1},
            {1,3,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,3,1},
            {1,2,1,1,2,1,2,1,1,1,1,1,1,2,1,2,1,1,2,1},
            {1,2,2,2,2,1,2,2,2,1,1,2,2,2,1,2,2,2,2,1},
            {1,1,1,1,2,1,1,1,0,1,1,0,1,1,1,2,1,1,1,1},
            {1,1,1,1,2,1,0,0,0,0,0,0,0,0,1,2,1,1,1,1},
            {1,1,1,1,2,1,0,1,1,0,0,1,1,0,1,2,1,1,1,1},
            {4,0,0,0,2,0,0,1,0,0,0,0,1,0,0,2,0,0,0,4},
            {1,1,1,1,2,1,0,1,1,1,1,1,1,0,1,2,1,1,1,1},
            {1,1,1,1,2,1,0,0,0,0,0,0,0,0,1,2,1,1,1,1},
            {1,1,1,1,2,1,0,1,1,1,1,1,1,0,1,2,1,1,1,1},
            {1,2,2,2,2,2,2,2,2,1,1,2,2,2,2,2,2,2,2,1},
            {1,3,1,1,2,1,1,1,2,1,1,2,1,1,1,2,1,1,3,1},
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
            {1,3,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,3,1},
            {4,2,1,1,2,1,1,1,2,1,1,2,1,1,1,2,1,1,2,4},
            {1,2,1,1,2,1,1,1,2,1,1,2,1,1,1,2,1,1,2,1},
            {1,2,2,2,2,1,1,0,0,0,0,0,0,1,1,2,2,2,2,1},
            {1,1,1,1,2,1,0,0,1,1,1,1,0,0,1,2,1,1,1,1},
            {1,2,2,2,2,1,0,0,1,0,0,1,0,0,1,2,2,2,2,1},
            {1,2,1,1,2,1,1,1,1,0,0,1,1,1,1,2,1,1,2,1},
            {1,3,1,1,2,2,2,2,2,2,2,2,2,2,2,2,1,1,3,1},
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
            {1,3,1,0,1,1,0,1,2,1,1,2,1,0,1,1,0,1,3,1},
            {1,2,1,0,0,0,0,1,2,2,2,2,1,0,0,0,0,1,2,1},
            {1,2,1,1,1,1,1,1,2,1,1,2,1,1,1,1,1,1,2,1},
            {1,2,2,2,2,2,2,2,2,1,1,2,2,2,2,2,2,2,2,1},
            {4,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,4},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1},
            {1,2,2,2,2,2,2,2,2,1,1,2,2,2,2,2,2,2,2,1},
            {1,2,1,1,1,1,1,1,2,1,1,2,1,1,1,1,1,1,2,1},
            {1,3,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,3,1},
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
            } else if (currentMap[gridY][gridX] == 3) {
                currentMap[gridY][gridX] = 0; // Remove the power-up
                pacman.addScore(50);
                powerUpTimer = POWER_UP_DURATION;
                // Make all ghosts edible
                for (Ghost ghost : ghosts) {
                    ghost.setEdible(true);
                }
            }
        }
    }
    
    public boolean isPoweredUp() {
        return powerUpTimer > 0;
    }
    
    // Check and handle portal teleportation for Pacman
    public void checkPortal(int x, int y) {
        // Only teleport if cooldown has expired
        if (portalCooldown > 0) {
            return;
        }
        
        int gridX = x / BLOCK_SIZE;
        int gridY = y / BLOCK_SIZE;
        
        if (gridX >= 0 && gridX < GRID_WIDTH && gridY >= 0 && gridY < GRID_HEIGHT) {
            if (currentMap[gridY][gridX] == 4) {
                // Find the other portal
                for (int i = 0; i < GRID_HEIGHT; i++) {
                    for (int j = 0; j < GRID_WIDTH; j++) {
                        if (currentMap[i][j] == 4 && !(i == gridY && j == gridX)) {
                            // Found the other portal, teleport Pacman
                            // Place Pacman offset from portal to prevent getting stuck
                            int newX, newY;
                            if (j == 0) {
                                // Left portal - place Pacman one block to the right
                                newX = (j + 1) * BLOCK_SIZE;
                            } else if (j == GRID_WIDTH - 1) {
                                // Right portal - place Pacman one block to the left
                                newX = (j - 1) * BLOCK_SIZE;
                            } else {
                                // Middle portal - use portal position
                                newX = j * BLOCK_SIZE;
                            }
                            newY = i * BLOCK_SIZE;
                            
                            pacman.teleport(newX, newY);
                            portalCooldown = PORTAL_COOLDOWN_DURATION;
                            return;
                        }
                    }
                }
            }
        }
    }
    
    private void checkLevelComplete() {
        // Check if all dots and power-ups are eaten
        for (int i = 0; i < GRID_HEIGHT; i++) {
            for (int j = 0; j < GRID_WIDTH; j++) {
                if (currentMap[i][j] == 2 || currentMap[i][j] == 3) {
                    return; // Still dots or power-ups remaining
                }
            }
        }
        
        // All items eaten, load next level
        if (currentLevel < 3) {
            currentLevel++;
            loadLevel(currentLevel);
            powerUpTimer = 0; // Reset power-up timer
            portalCooldown = 0; // Reset portal cooldown
            // Reset positions
            pacman.reset(BLOCK_SIZE, BLOCK_SIZE);
            
            // Reset ghost positions
            for (int i = 0; i < ghosts.length; i++) {
                ghosts[i].reset(GHOST_SPAWN_POSITIONS[i][0], GHOST_SPAWN_POSITIONS[i][1]);
            }
            
            // Activate extra powerup if ability is selected
            if (selectedAbility == GameAbility.EXTRA_POWERUP) {
                powerUpTimer = POWER_UP_DURATION;
                for (Ghost ghost : ghosts) {
                    ghost.setEdible(true);
                }
            }
        } else {
            // Game won!
            JOptionPane.showMessageDialog(this, "¡Felicidades! Has completado todos los niveles.\nPuntuación final: " + pacman.getScore());
            System.exit(0);
        }
    }
    
    private void restartLevel() {
        // DO NOT reload level - we want to keep dots that were already eaten
        // Only reset power-up timer and portal cooldown
        powerUpTimer = 0;
        portalCooldown = 0;
        
        // Reset positions
        pacman.reset(BLOCK_SIZE, BLOCK_SIZE);
        
        // Reset ghost positions
        for (int i = 0; i < ghosts.length; i++) {
            ghosts[i].reset(GHOST_SPAWN_POSITIONS[i][0], GHOST_SPAWN_POSITIONS[i][1]);
        }
        
        // Restore saved score
        pacman.setScore(savedScore);
        
        // Activate extra powerup if ability is selected
        if (selectedAbility == GameAbility.EXTRA_POWERUP) {
            powerUpTimer = POWER_UP_DURATION;
            for (Ghost ghost : ghosts) {
                ghost.setEdible(true);
            }
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
                } else if (currentMap[i][j] == 3) {
                    // Draw power-up (larger, blinking)
                    if ((System.currentTimeMillis() / 250) % 2 == 0) {
                        g.setColor(Color.WHITE);
                        g.fillOval(x + BLOCK_SIZE/2 - 5, y + BLOCK_SIZE/2 - 5, 10, 10);
                    }
                } else if (currentMap[i][j] == 4) {
                    // Draw portal (swirling purple/magenta effect)
                    long time = System.currentTimeMillis();
                    Color portalColor = ((time / 200) % 2 == 0) ? new Color(255, 0, 255) : new Color(138, 43, 226);
                    g.setColor(portalColor);
                    g.fillOval(x + 2, y + 2, BLOCK_SIZE - 4, BLOCK_SIZE - 4);
                    g.setColor(Color.WHITE);
                    g.drawOval(x + 4, y + 4, BLOCK_SIZE - 8, BLOCK_SIZE - 8);
                }
            }
        }
        
        // Draw score and level
        g.setColor(Color.YELLOW);
        g.drawString("Score: " + pacman.getScore(), 10, 410);
        g.drawString("Level: " + currentLevel, 320, 410);
        
        // Draw lives (only for THREE_LIVES ability)
        if (selectedAbility == GameAbility.THREE_LIVES) {
            g.setColor(Color.YELLOW);
            g.drawString("Vidas: " + lives, 180, 410);
        }
        
        // Draw power-up indicator
        if (powerUpTimer > 0) {
            g.setColor(Color.ORANGE);
            g.drawString("POWER UP!", 170, 410);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        pacman.move();
        for (Ghost ghost : ghosts) {
            ghost.move();
        }
        
        // Update power-up timer
        if (powerUpTimer > 0) {
            powerUpTimer--;
            if (powerUpTimer == 0) {
                // Power-up expired, make ghosts normal again
                for (Ghost ghost : ghosts) {
                    ghost.setEdible(false);
                }
            }
        }
        
        // Update portal cooldown
        if (portalCooldown > 0) {
            portalCooldown--;
        }
        
        // Check collisions with ghosts
        for (Ghost ghost : ghosts) {
            if (pacman.collidesWith(ghost)) {
                if (isPoweredUp() && ghost.isEdible()) {
                    // Eat the ghost
                    pacman.addScore(200);
                    ghost.respawn();
                } else if (!isPoweredUp() || !ghost.isEdible()) {
                    // Lost a life
                    lives--;
                    if (lives > 0) {
                        // Still have lives left - restart level but keep score
                        savedScore = pacman.getScore();
                        restartLevel();
                    } else {
                        // Game over
                        JOptionPane.showMessageDialog(this, "¡Game Over! Los fantasmas te atraparon.\nPuntuación: " + pacman.getScore());
                        System.exit(0);
                    }
                }
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