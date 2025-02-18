package usage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class Grid extends JPanel{
    
    private int rows;
    private int cols;
    private int cellSize;
    private HashMap<Cell, Color> filledCells;

    private int dotRow;
    private int dotCol;


    public Grid(int rows, int cols, int cellSize) {
        this.rows = rows;
        this.cols = cols;
        this.cellSize = cellSize;
        filledCells = new HashMap<>();
        setBackground(Color.BLACK);
        this.dotRow = 2;
        this.dotCol = 2;

        // Add key listener to handle dot movement
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int newRow = dotRow;
                int newCol = dotCol;
                Color endCol = new Color(255,0,0);
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        newRow--;
                        break;
                    case KeyEvent.VK_DOWN:
                        newRow++;
                        break;
                    case KeyEvent.VK_LEFT:
                        newCol--;
                        break;
                    case KeyEvent.VK_RIGHT:
                        newCol++;
                        break;
                }

                //se questa cella è stata colorata allora posso muovermi su di essa
                if(colored(newRow,newCol)){
                    dotRow = newRow;
                    dotCol = newCol;
                    if(dotCol == cols && dotRow  == rows){
                        System.out.println("END");
                        setVisible(false);
                        
                        
                 }  
                    /*Cell end = new Cell(rows, cols, null);
                    Color currColor = filledCells.get(end);
                    if(currColor.equals(e)){
                        //System.out.println("FINEEEE");
                    }*/
                }
                
                repaint();

            }

        });

        // Ensure the panel is focusable to receive key events
        setFocusable(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
       // drawGrid(g);
        fillCells(g);
        drawDot(g);

    }

   /*  private void drawGrid(Graphics g) {
        for (int x = 0; x <= rows; x += 1){
            for (int y = 0; y <= cols; y += 1){
                g.drawRect(10 + x*cellSize, 10 + y*cellSize, cellSize, cellSize);
                g.fillRect(10 + x*cellSize, 10 + y*cellSize, cellSize, cellSize);
            }      
        }
    }*/

    private void fillCells(Graphics g) {
        for (Cell cell : filledCells.keySet()) {
            g.setColor(cell.color);
            g.fillRect(cell.col * cellSize, cell.row * cellSize, cellSize, cellSize);
        }
    }

    public void fillCell(int row, int col, Color color) {
        filledCells.put(new Cell(row, col, color), color);
        repaint();
    }

    private void drawDot(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(dotCol * cellSize, dotRow * cellSize, cellSize, cellSize);
    }

    private boolean colored(int row, int col) {
        Cell toCheck = new Cell(row, col, null);
        return filledCells.containsKey(toCheck);
    
    }

}
