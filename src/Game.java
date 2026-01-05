import javax.swing.JFrame;

public class Game extends JFrame {
    public Game() {
        // Start with the main menu
        add(new MainMenu(this));
        setTitle("Pac-Man");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 450);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.setVisible(true);
    }
}