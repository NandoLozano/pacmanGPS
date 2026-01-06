import javax.swing.JFrame;

public class Game extends JFrame {
    public Game() {
        // Start with the main menu
        add(new MainMenu(this));
        setTitle("Pac-Man");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 600); // Increased size to accommodate ability selection screen
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.setVisible(true);
    }
}