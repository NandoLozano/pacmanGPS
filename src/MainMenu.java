import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu extends JPanel {
    private JFrame parentFrame;
    private GameAbility selectedAbility;
    
    public MainMenu(JFrame parentFrame) {
        this(parentFrame, GameAbility.THREE_LIVES); // Default
    }
    
    public MainMenu(JFrame parentFrame, GameAbility ability) {
        this.parentFrame = parentFrame;
        this.selectedAbility = ability;
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Title
        JLabel titleLabel = new JLabel("PAC-MAN");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setForeground(Color.YELLOW);
        add(titleLabel, gbc);
        
        // Play button
        gbc.gridy++;
        JButton playButton = new JButton("JUGAR");
        styleButton(playButton);
        playButton.addActionListener(e -> startGame());
        add(playButton, gbc);
        
        // Abilities button
        gbc.gridy++;
        JButton abilitiesButton = new JButton("HABILIDADES DEL PERSONAJE");
        styleButton(abilitiesButton);
        abilitiesButton.addActionListener(e -> showAbilities());
        add(abilitiesButton, gbc);
        
        // Exit button
        gbc.gridy++;
        JButton exitButton = new JButton("SALIR");
        styleButton(exitButton);
        exitButton.addActionListener(e -> System.exit(0));
        add(exitButton, gbc);
        
        // Current ability display
        gbc.gridy++;
        JLabel abilityLabel = new JLabel("Habilidad actual: " + getAbilityName(selectedAbility));
        abilityLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        abilityLabel.setForeground(Color.WHITE);
        add(abilityLabel, gbc);
    }
    
    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setPreferredSize(new Dimension(300, 50));
        button.setBackground(Color.BLUE);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));
    }
    
    private void startGame() {
        parentFrame.getContentPane().removeAll();
        DifficultySelection difficultySelection = new DifficultySelection(parentFrame, selectedAbility,
            difficulty -> {
                Board board = new Board(selectedAbility, difficulty);
                parentFrame.getContentPane().removeAll();
                parentFrame.add(board);
                parentFrame.revalidate();
                parentFrame.repaint();
                board.requestFocusInWindow();
            });
        parentFrame.add(difficultySelection);
        parentFrame.revalidate();
        parentFrame.repaint();
    }
    
    private void showAbilities() {
        parentFrame.getContentPane().removeAll();
        AbilitySelection abilitySelection = new AbilitySelection(parentFrame, selectedAbility, 
            ability -> {
                selectedAbility = ability;
                returnToMenu();
            });
        parentFrame.add(abilitySelection);
        parentFrame.revalidate();
        parentFrame.repaint();
    }
    
    private void returnToMenu() {
        parentFrame.getContentPane().removeAll();
        parentFrame.add(new MainMenu(parentFrame, selectedAbility));
        parentFrame.revalidate();
        parentFrame.repaint();
    }
    
    private String getAbilityName(GameAbility ability) {
        switch (ability) {
            case THREE_LIVES: return "3 Vidas";
            case SPEED_BOOST: return "Velocidad Aumentada";
            case EXTRA_POWERUP: return "Potenciador Extra";
            default: return "Desconocida";
        }
    }
}
