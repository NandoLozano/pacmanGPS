import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.function.Consumer;

public class AbilitySelection extends JPanel {
    private JFrame parentFrame;
    private GameAbility selectedAbility;
    private Consumer<GameAbility> onAbilitySelected;
    
    public AbilitySelection(JFrame parentFrame, GameAbility currentAbility, Consumer<GameAbility> onAbilitySelected) {
        this.parentFrame = parentFrame;
        this.selectedAbility = currentAbility;
        this.onAbilitySelected = onAbilitySelected;
        
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Title
        JLabel titleLabel = new JLabel("HABILIDADES DEL PERSONAJE");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.YELLOW);
        gbc.anchor = GridBagConstraints.CENTER;
        add(titleLabel, gbc);
        
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.WEST;
        
        // Ability 1: Three Lives
        gbc.gridy++;
        JRadioButton ability1 = new JRadioButton("3 Vidas");
        styleRadioButton(ability1);
        ability1.setSelected(selectedAbility == GameAbility.THREE_LIVES);
        add(ability1, gbc);
        
        gbc.gridy++;
        JLabel desc1 = new JLabel("<html><i>Si te da un fantasma, reinicia el nivel pero mantienes tus puntos.<br>Tienes 3 vidas en total.</i></html>");
        desc1.setFont(new Font("Arial", Font.PLAIN, 12));
        desc1.setForeground(Color.LIGHT_GRAY);
        add(desc1, gbc);
        
        // Ability 2: Speed Boost
        gbc.gridy++;
        gbc.insets = new Insets(20, 10, 10, 10);
        JRadioButton ability2 = new JRadioButton("Velocidad Aumentada");
        styleRadioButton(ability2);
        ability2.setSelected(selectedAbility == GameAbility.SPEED_BOOST);
        add(ability2, gbc);
        
        gbc.gridy++;
        gbc.insets = new Insets(10, 10, 10, 10);
        JLabel desc2 = new JLabel("<html><i>Tu personaje se mueve un 25% más rápido.<br>Solo tienes 1 vida.</i></html>");
        desc2.setFont(new Font("Arial", Font.PLAIN, 12));
        desc2.setForeground(Color.LIGHT_GRAY);
        add(desc2, gbc);
        
        // Ability 3: Extra Powerup
        gbc.gridy++;
        gbc.insets = new Insets(20, 10, 10, 10);
        JRadioButton ability3 = new JRadioButton("Potenciador Extra al Inicio");
        styleRadioButton(ability3);
        ability3.setSelected(selectedAbility == GameAbility.EXTRA_POWERUP);
        add(ability3, gbc);
        
        gbc.gridy++;
        gbc.insets = new Insets(10, 10, 10, 10);
        JLabel desc3 = new JLabel("<html><i>Comienzas cada nivel con un potenciador activado.<br>Solo tienes 1 vida.</i></html>");
        desc3.setFont(new Font("Arial", Font.PLAIN, 12));
        desc3.setForeground(Color.LIGHT_GRAY);
        add(desc3, gbc);
        
        // Group radio buttons
        ButtonGroup group = new ButtonGroup();
        group.add(ability1);
        group.add(ability2);
        group.add(ability3);
        
        // Add action listeners
        ability1.addActionListener(e -> selectedAbility = GameAbility.THREE_LIVES);
        ability2.addActionListener(e -> selectedAbility = GameAbility.SPEED_BOOST);
        ability3.addActionListener(e -> selectedAbility = GameAbility.EXTRA_POWERUP);
        
        // Back button
        gbc.gridy++;
        gbc.insets = new Insets(30, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        JButton backButton = new JButton("VOLVER AL MENÚ");
        styleButton(backButton);
        backButton.addActionListener(e -> {
            onAbilitySelected.accept(selectedAbility);
        });
        add(backButton, gbc);
    }
    
    private void styleRadioButton(JRadioButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(Color.BLACK);
        button.setFocusPainted(false);
    }
    
    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setPreferredSize(new Dimension(250, 40));
        button.setBackground(Color.BLUE);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));
    }
}
