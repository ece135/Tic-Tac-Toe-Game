import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class XoxInterface extends JFrame {
    private JButton[][] buttons = new JButton[3][3];
    private int playerTurn = 1; 
    private int scoreX = 0;
    private int scoreO = 0;
    private JLabel scoreLabel;
    private Xox game = new Xox(); 
    private JPanel boardPanel;
    private JPanel gridPanel;
    private WinLinePanel winPanel;
    private final Color neonX = new Color(0, 200, 255);  
    private final Color neonO = new Color(180, 0, 255);   
    private final Color cellDark = new Color(20, 20, 20);
    private final Color bgDark = new Color(15, 15, 15);
    
    

    public XoxInterface() {
        setTitle("XOX Game");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout()); 
        getContentPane().setBackground(bgDark);
        


        scoreLabel = new JLabel("Score - X: 0 | O: 0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        scoreLabel.setOpaque(true);
        scoreLabel.setBackground(bgDark);
        scoreLabel.setForeground(new Color(0, 255, 180));
        add(scoreLabel, BorderLayout.NORTH);

        boardPanel = new JPanel(); 
        boardPanel.setLayout(new OverlayLayout(boardPanel));
        boardPanel.setBackground(bgDark);

        winPanel = new WinLinePanel();
        winPanel.setOpaque(false);
        winPanel.setAlignmentX(0.5f); 
        winPanel.setAlignmentY(0.5f);

        gridPanel = new JPanel(new GridLayout(3, 3));
        gridPanel.setOpaque(false);
        gridPanel.setAlignmentX(0.5f);
        gridPanel.setAlignmentY(0.5f);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 60));
                buttons[i][j].setBackground(new Color(12, 12, 12));
                buttons[i][j].setOpaque(true);
                buttons[i][j].setContentAreaFilled(false);
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].setBorderPainted(true);
                buttons[i][j].setMargin(new Insets(0, 0, 0, 0));

                final int row = i;
                final int col = j;
                
                buttons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String mark = (playerTurn == 1) ? "X" : "O";
                        Color winColor = (playerTurn == 1) ? neonX : neonO;
                        buttons[row][col].setText(mark); 
                        buttons[row][col].setForeground(winColor);
                        buttons[row][col].setFont(new Font("Arial", Font.BOLD, 70));

                        game.setMove(row, col, playerTurn);
                        Xox.ImageType result = game.threeInARow(); 
                        if (result != Xox.ImageType.NONE) {
                            if (result != Xox.ImageType.DRAW) {
                                winPanel.setWinLine(game.getWinLine(), winColor);
                                if (playerTurn == 1) scoreX++; else scoreO++;
                                    updateScoreLabel();
                                JOptionPane.showMessageDialog(null, "Winner is: " + mark);
                            } else {
                                JOptionPane.showMessageDialog(null, "Draw!");
                            }
                            resetGame();
                        } else {
                            playerTurn = (playerTurn == 1) ? 2 : 1; 
                        }
                    }
                });
                gridPanel.add(buttons[i][j]);
            }
        }


        boardPanel.add(winPanel);   
        boardPanel.add(gridPanel); 
        

        add(boardPanel, BorderLayout.CENTER);
    }

        private void updateScoreLabel() {
        scoreLabel.setText("Score - X: " + scoreX + " | O: " + scoreO);
        scoreLabel.setForeground(neonX);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));

        }

        private void resetGame() {
        winPanel.setWinLine(Xox.WinLine.NONE, null);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setForeground(Color.WHITE);
                buttons[i][j].setBackground(cellDark);
                buttons[i][j].setBorder(
                    BorderFactory.createLineBorder(new Color(40, 40, 40), 1)
                );
                buttons[i][j].setOpaque(true);
                buttons[i][j].setContentAreaFilled(true);
                

            
                
            }
        }
            game = new Xox();
            playerTurn = 1;
        }

    }
    class WinLinePanel extends JPanel {
    private Xox.WinLine line;
    private Color color;
    

    public void setWinLine(Xox.WinLine line, Color color) {
        this.line = line;
        this.color = color;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (line == null || line == Xox.WinLine.NONE) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(6));
        g2.setColor(color);

        int w = getWidth();
        int h = getHeight();

        switch (line) {
            case ROW0 -> g2.drawLine(20, h/6, w-20, h/6);
            case ROW1 -> g2.drawLine(20, h/2, w-20, h/2);
            case ROW2 -> g2.drawLine(20, 5*h/6, w-20, 5*h/6);

            case COL0 -> g2.drawLine(w/6, 20, w/6, h-20);
            case COL1 -> g2.drawLine(w/2, 20, w/2, h-20);
            case COL2 -> g2.drawLine(5*w/6, 20, 5*w/6, h-20);

            case DIAG1 -> g2.drawLine(20, 20, w-20, h-20);
            case DIAG2 -> g2.drawLine(w-20, 20, 20, h-20);
            case NONE -> { }
        }
    }

    public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {
                new XoxInterface().setVisible(true);
            });
        }
}


    
        

   