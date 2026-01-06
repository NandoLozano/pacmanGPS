import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class DifficultySelection extends JPanel {
    private JFrame parentFrame;
    private GameAbility selectedAbility;
    private Consumer<GameDifficulty> onDifficultySelected;
    
    public DifficultySelection(JFrame parentFrame, GameAbility ability, Consumer<GameDifficulty> onDifficultySelected) {
        this.parentFrame = parentFrame;
        this.selectedAbility = ability;
        this.onDifficultySelected = onDifficultySelected;
        
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        
        // Title
        JLabel titleLabel = new JLabel("SELECCIONA DIFICULTAD");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.YELLOW);
        add(titleLabel, gbc);
        
        // Easy difficulty button
        gbc.gridy++;
        JButton easyButton = new JButton("FÁCIL");
        styleButton(easyButton, Color.GREEN);
        easyButton.addActionListener(e -> selectDifficulty(GameDifficulty.EASY));
        add(easyButton, gbc);
        
        gbc.gridy++;
        JLabel easyDesc = new JLabel("2 fantasmas por nivel");
        easyDesc.setFont(new Font("Arial", Font.PLAIN, 14));
        easyDesc.setForeground(Color.LIGHT_GRAY);
        add(easyDesc, gbc);
        
        // Normal difficulty button
        gbc.gridy++;
        gbc.insets = new Insets(20, 10, 15, 10);
        JButton normalButton = new JButton("NORMAL");
        styleButton(normalButton, Color.ORANGE);
        normalButton.addActionListener(e -> selectDifficulty(GameDifficulty.NORMAL));
        add(normalButton, gbc);
        
        gbc.gridy++;
        gbc.insets = new Insets(5, 10, 15, 10);
        JLabel normalDesc = new JLabel("3 fantasmas por nivel");
        normalDesc.setFont(new Font("Arial", Font.PLAIN, 14));
        normalDesc.setForeground(Color.LIGHT_GRAY);
        add(normalDesc, gbc);
        
        // Hard difficulty button
        gbc.gridy++;
        gbc.insets = new Insets(20, 10, 15, 10);
        JButton hardButton = new JButton("DIFÍCIL");
        styleButton(hardButton, Color.RED);
        hardButton.addActionListener(e -> selectDifficulty(GameDifficulty.HARD));
        add(hardButton, gbc);
        
        gbc.gridy++;
        gbc.insets = new Insets(5, 10, 15, 10);
        JLabel hardDesc = new JLabel("4 fantasmas por nivel");
        hardDesc.setFont(new Font("Arial", Font.PLAIN, 14));
        hardDesc.setForeground(Color.LIGHT_GRAY);
        add(hardDesc, gbc);
        
        // Back button
        gbc.gridy++;
        gbc.insets = new Insets(30, 10, 10, 10);
        JButton backButton = new JButton("VOLVER AL MENÚ");
        styleBackButton(backButton);
        backButton.addActionListener(e -> returnToMenu());
        add(backButton, gbc);
    }
    
    private void styleButton(JButton button, Color color) {
        button.setFont(new Font("Arial", Font.BOLD, 24));
        button.setPreferredSize(new Dimension(300, 60));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
    }
    
    private void styleBackButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setPreferredSize(new Dimension(250, 40));
        button.setBackground(Color.DARK_GRAY);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
    }
    
    private void selectDifficulty(GameDifficulty difficulty) {
        onDifficultySelected.accept(difficulty);
    }
    
    private void returnToMenu() {
        parentFrame.getContentPane().removeAll();
        parentFrame.add(new MainMenu(parentFrame, selectedAbility));
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}
