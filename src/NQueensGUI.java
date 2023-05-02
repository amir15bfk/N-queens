import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.io.FileWriter;
import java.io.IOException;

public class NQueensGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private int N = 8;
    private String algoselect = "GA";
    private JButton startButton;
    private JComboBox<Integer> sizeComboBox;
    private JComboBox<String> algoComboBox;
    private JPanel boardPanel;
    private JLabel statusLabel;
    private JProgressBar progressBar;
    private JLabel statsLabel;
    private JCheckBox c1;

    public NQueensGUI() {
        setTitle("N-Queens Solver");
        setSize(700, 500);
        

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, 6));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        topPanel.add(new JLabel("<html><b>Board Size:</b></html>"));

        Integer[] sizes = { 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15, 20, 30 ,50,100};
        JComboBox<Integer> sizeComboBox = new JComboBox<>(sizes);
        sizeComboBox.setToolTipText("Select the size of the board (number of queens)");
        sizeComboBox.setSelectedItem(N);
        sizeComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                N = (int) sizeComboBox.getSelectedItem();
                createBoardPanel();
                revalidate();
                repaint();
            }
        });
        topPanel.add(sizeComboBox);
        JLabel sal = new JLabel("<html><b>Search Algorithm:</b></html>");
        sal.setBorder(new EmptyBorder(0,10,0,0));
        topPanel.add(sal); 
        String[] algo = { "GA", "PSO","DFS", "BFS", "A* h1", "A* h2" };
        algoComboBox = new JComboBox<>(algo);
        algoComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                algoselect = (String) algoComboBox.getSelectedItem();
            }
        });
        topPanel.add(algoComboBox);
        topPanel.add(new JLabel("      log: "));
        c1 = new JCheckBox();
        topPanel.add(c1);
        startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        progressBar.setVisible(true);
                        add(progressBar, BorderLayout.SOUTH);
                        revalidate();
                        repaint();
                        // Run the solver here...
                        startSolver();
                        return null;
                    }

                    @Override
                    protected void done() {
                        // Hide the progress bar when the solver is done
                        progressBar.setVisible(false);
                    }
                };
                worker.execute();
            }
        });
        topPanel.add(startButton);
        add(topPanel, BorderLayout.NORTH);

        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(N, N));
        int marginSize = 10;
        boardPanel.setBorder(BorderFactory.createEmptyBorder(marginSize, marginSize, marginSize, marginSize));
        progressBar = new JProgressBar(0, 100);
        // Set the progress bar to indeterminate mode
        progressBar.setIndeterminate(true);
        progressBar.setVisible(false);
        // Add the progress bar to the frame
        add(progressBar, BorderLayout.SOUTH);

        createBoardPanel();
        add(boardPanel, BorderLayout.CENTER);
        statusLabel = new JLabel(" ");
        statsLabel = new JLabel();
        statsLabel.setHorizontalAlignment(JLabel.CENTER);
        statsLabel.setVerticalAlignment(JLabel.CENTER);
        statsLabel.setPreferredSize(new Dimension(180, 200));
        updateStats(" welcome <br> chose a problem size <br> and the search technique");
        add(statsLabel, BorderLayout.EAST);
        statsLabel.setMaximumSize(new Dimension(200, 200));

    }

    private void createBoardPanel() {
        boardPanel.removeAll();
        boardPanel.setLayout(new GridLayout(N, N));

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                JButton button = new JButton();

                button.setEnabled(false);
                if ((i + j) % 2 == 0) {
                    button.setBackground(Color.WHITE);
                } else {
                    button.setBackground(Color.GRAY);
                }
                boardPanel.add(button);
            }
        }
    }

    private void startSolver() {
        statusLabel.setText("Solving...");

        NQueensSolver solver = new NQueensSolver(N);
        Sol sol = solver.solve(algoselect);
        boardPanel.removeAll();
        int[] solution = sol.board;
        if (sol.flag == 1) {
            updateStats("No solution found.");
        } else {
            for (int i = 0; i < N; i++) {
                int queenCol = solution[i];
                for (int j = 0; j < N; j++) {
                    JButton button = new JButton();
                    if(j==(-queenCol-1)){
                        queenCol=-queenCol-1;
                        button.setBackground(Color.RED);
                    }else {
                        if(j==queenCol){
                            button.setBackground(Color.green);
                        }else{
                    if ((i + j) % 2 == 0) {
                        button.setBackground(Color.WHITE);
                    } else {
                        button.setBackground(Color.GRAY);
                    }}}
                    if (j == queenCol) {

                        ImageIcon queenIcon = new ImageIcon("queensii.png");
                        int widthimg = (int) ((((50 * boardPanel.getSize().width) / 500) * 8) / N);
                        int heightimg = (int) ((((50 * boardPanel.getSize().height) / 500) * 8) / N);
                        Image queenImage = queenIcon.getImage().getScaledInstance(widthimg, heightimg,
                                Image.SCALE_SMOOTH);
                        ImageIcon scaledQueenIcon = new ImageIcon(queenImage);
                        button.setIcon(scaledQueenIcon);
                    } else {
                        button.setEnabled(false);
                    }
                    boardPanel.add(button);
                }
            }

            updateStats("Solution found<br>" + sol.msg);
            if (c1.isSelected()) {
                try {
                    FileWriter fw = new FileWriter("exp.log", true); // true for append mode
                    fw.write(N + "," + algoselect + sol.logmsg);
                    fw.write("\n"); // add newline
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }

        revalidate();
        repaint();
    }
    public void updateStats(String data) {
        statsLabel.setText(
                "<html><div style='text-align: center;font-size: large;font-family: \"Courier New\", Courier, monospace;color: #f93a3a;background-color: #fee373;padding: 10px;width: 100%;display: block;margin:10px;'>"
                        + data + "</div></html>");
    }
    public static void main(String[] args) {
        NQueensGUI gui = new NQueensGUI();
        gui.setVisible(true);
    }

    

}