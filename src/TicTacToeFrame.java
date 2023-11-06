import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class TileButton extends JButton {
    private int row, col;

    public TileButton(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}

// Defining the Board class which represents the Tic Tac Toe board
class Board extends JPanel {
    private TileButton[][] buttons;

    public Board(ActionListener actionListener) {
        setLayout(new GridLayout(3, 3));
        buttons = new TileButton[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new TileButton(i, j);
                buttons[i][j].addActionListener(actionListener);
                add(buttons[i][j]);
            }
        }
    }

    public TileButton[][] getButtons() {
        return buttons;
    }

    public void resetBoard() {
        for (TileButton[] buttonRow : buttons) {
            for (TileButton button : buttonRow) {
                button.setText("");
                button.setEnabled(true);
            }
        }
    }
}

// Defining the GameController class to control the game logic
class GameController implements ActionListener {
    private Board board;
    private String currentPlayer = "X";
    private int xWins = 0, oWins = 0, ties = 0;

    public GameController() {
        board = new Board(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        TileButton button = (TileButton) e.getSource();
        button.setText(currentPlayer);
        button.setEnabled(false);

        if (checkWin(currentPlayer)) {
            JOptionPane.showMessageDialog(null, currentPlayer + " Wins!");
            if (currentPlayer.equals("X")) xWins++;
            else oWins++;
            newGame();
        } else if (isBoardFull()) {
            JOptionPane.showMessageDialog(null, "It's a tie!");
            ties++;
            newGame();
        } else {
            currentPlayer = (currentPlayer.equals("X")) ? "O" : "X";
        }
    }

    private boolean checkWin(String player) {
        TileButton[][] buttons = board.getButtons();
        // Check rows, columns, and diagonals
        for (int i = 0; i < 3; i++) {
            if ((buttons[i][0].getText().equals(player) && buttons[i][1].getText().equals(player) && buttons[i][2].getText().equals(player)) ||
                    (buttons[0][i].getText().equals(player) && buttons[1][i].getText().equals(player) && buttons[2][i].getText().equals(player))) {
                return true;
            }
        }
        return (buttons[0][0].getText().equals(player) && buttons[1][1].getText().equals(player) && buttons[2][2].getText().equals(player)) ||
                (buttons[0][2].getText().equals(player) && buttons[1][1].getText().equals(player) && buttons[2][0].getText().equals(player));
    }

    private boolean isBoardFull() {
        TileButton[][] buttons = board.getButtons();
        for (TileButton[] row : buttons) {
            for (TileButton btn : row) {
                if (btn.getText().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    public void newGame() {
        currentPlayer = "X";
        board.resetBoard();
    }

    public Board getBoard() {
        return board;
    }

    public int getXWins() {
        return xWins;
    }

    public int getOWins() {
        return oWins;
    }

    public int getTies() {
        return ties;
    }
}

// Defining the main frame for the Tic Tac Toe game
public class TicTacToeFrame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Tic Tac Toe");
        GameController gameController = new GameController();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel scorePanel = new JPanel();
        JLabel xWinsLabel = new JLabel("X Wins: 0");
        JLabel oWinsLabel = new JLabel("O Wins: 0");
        JLabel tiesLabel = new JLabel("Ties: 0");
        scorePanel.add(xWinsLabel);
        scorePanel.add(oWinsLabel);
        scorePanel.add(tiesLabel);

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> {
            gameController.newGame();
            xWinsLabel.setText("X Wins: " + gameController.getXWins());
            oWinsLabel.setText("O Wins: " + gameController.getOWins());
            tiesLabel.setText("Ties: " + gameController.getTies());
        });

        mainPanel.add(gameController.getBoard(), BorderLayout.CENTER);
        mainPanel.add(scorePanel, BorderLayout.NORTH);
        mainPanel.add(resetButton, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setVisible(true);
    }
}
