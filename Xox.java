public class Xox {
    
    private int[][] table = new int[3][3];
    private int moveCount = 0;

    public enum ImageType{
        X_WIN,
        O_WIN,
        DRAW,
        NONE
    }

    public void setMove(int row, int col, int player) {
        table[row][col] = player;
        moveCount++;

    }
    public int getMoveCount() { 
        return moveCount;
    }
    
    public ImageType threeInARow(){

        if (check(1)) return ImageType.X_WIN;
        if (check(2)) return ImageType.O_WIN;   
        if (moveCount==9) return ImageType.DRAW;
        return ImageType.NONE;
    }

    private boolean check(int p) {
        if (table[0][0]==p && table[0][1]==p && table[0][2]==p) { winLine = WinLine.ROW0; return true; }
        if (table[1][0]==p && table[1][1]==p && table[1][2]==p) { winLine = WinLine.ROW1; return true; }
        if (table[2][0]==p && table[2][1]==p && table[2][2]==p) { winLine = WinLine.ROW2; return true; }

        if (table[0][0]==p && table[1][0]==p && table[2][0]==p) { winLine = WinLine.COL0; return true; }
        if (table[0][1]==p && table[1][1]==p && table[2][1]==p) { winLine = WinLine.COL1; return true; }
        if (table[0][2]==p && table[1][2]==p && table[2][2]==p) { winLine = WinLine.COL2; return true; }

        if (table[0][0]==p && table[1][1]==p && table[2][2]==p) { winLine = WinLine.DIAG1; return true; }
        if (table[0][2]==p && table[1][1]==p && table[2][0]==p) { winLine = WinLine.DIAG2; return true; }

        return false;

    }

    public enum WinLine {
    ROW0, ROW1, ROW2,
    COL0, COL1, COL2,
    DIAG1, DIAG2,
    NONE
    }

    private WinLine winLine = WinLine.NONE;

    public WinLine getWinLine() {
        return winLine;
    }


 
    /*public static void main(String[] args) {
        Xox game = new Xox();
        Xox.ImageType result = game.threeInARow();
        System.out.println("Status: " + result);
    } */
}