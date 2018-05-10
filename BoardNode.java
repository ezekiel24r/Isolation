import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class BoardNode implements Comparable<BoardNode>{
    public ArrayList<String> board;
    int xRowPos;
    int xColPos;
    int oRowPos;
    int oColPos;
    int depth;
    int alpha;
    int beta;
    int score;
    public BoardNode child;

    BoardNode(){
        board = new ArrayList<String>(8);
        board.add("X-------");
        board.add("--------");
        board.add("--------");
        board.add("--------");
        board.add("--------");
        board.add("--------");
        board.add("--------");
        board.add("-------O");

        xRowPos = 0;
        xColPos = 0;
        oRowPos = 7;
        oColPos = 7;

        depth = 0;

        child = null;

    }

    BoardNode(BoardNode parent){
        board = new ArrayList<String>(8);
        for(int i = 0; i<8; i++){
            this.board.add(parent.board.get(i));
        }

        xRowPos = parent.xRowPos;
        xColPos = parent.xColPos;
        oRowPos = parent.oRowPos;
        oColPos = parent.oColPos;

        depth = parent.depth+1;

        score = xPossibleMoves();

        parent.child = this;
    }

    public boolean moveX(int row, int col){
        if (xLegalMove(row, col)) {
            //make old position #
            StringBuilder str = new StringBuilder(board.get(xRowPos));
            str.replace(xColPos,xColPos+1, "#");
            board.set(xRowPos, str.toString());

            //add X to new position
            str = new StringBuilder(board.get(row));
            str.replace(col,col+1, "X");
            board.set(row, str.toString());

            xRowPos = row;
            xColPos = col;
            score = xPossibleMoves();
            return true;
        }
        //System.out.println("Illegal move!");
        return false;
    }

    public boolean moveO(int row, int col){
        if (oLegalMove(row, col)) {
            //make old position #
            StringBuilder str = new StringBuilder(board.get(oRowPos));
            str.replace(oColPos,oColPos+1, "#");
            board.set(oRowPos, str.toString());

            //add X to new position
            str = new StringBuilder(board.get(row));
            str.replace(col,col+1, "O");
            board.set(row, str.toString());

            oRowPos = row;
            oColPos = col;
            score = xPossibleMoves();


            return true;
        }
        //System.out.println("Illegal move!");
        return false;
    }

    public boolean xLegalMove(int row, int col) {
        if(row < 0 || row > 7 || col < 0 || col > 7){
            return false;
        }
        if(row == xRowPos && col == xColPos){
            return false;
        }

        if(board.get(row).charAt(col) == '#' || board.get(row).charAt(col) == 'X' || board.get(row).charAt(col) == 'O'){
            return false;
        }

        //right move
        if(row == xRowPos && col > xColPos){
            int colPtr = col;
            char temp;
            while(colPtr > xColPos){
                colPtr--;
                temp = board.get(xRowPos).charAt(colPtr);
                if(temp == '#' || temp == 'O'){
                    return false;
                }

            }
            if(board.get(xRowPos).charAt(colPtr) == 'X'){
                return true;
            }
        }
        //left move
        if(row == xRowPos && col < xColPos){
            int colPtr = col;
            char temp;
            while(colPtr < xColPos){
                colPtr++;
                temp = board.get(xRowPos).charAt(colPtr);
                if(temp == '#' || temp == 'O'){
                    return false;
                }

            }
            if(board.get(xRowPos).charAt(colPtr) == 'X'){
                return true;
            }
        }
        //up move
        if(row < xRowPos && col == xColPos){
            int rowPtr = row;
            char temp;
            while(rowPtr < xRowPos){
                rowPtr++;
                temp = board.get(rowPtr).charAt(xColPos);
                if(temp == '#' || temp == 'O'){
                    return false;
                }

            }
            if(board.get(rowPtr).charAt(xColPos) == 'X'){
                return true;
            }
        }

        //down move
        if(row > xRowPos && col == xColPos){
            int rowPtr = row;
            char temp;
            while(rowPtr > xRowPos){
                rowPtr--;
                temp = board.get(rowPtr).charAt(xColPos);
                if(temp == '#' || temp == 'O'){
                    return false;
                }

            }
            if(board.get(rowPtr).charAt(xColPos) == 'X'){
                return true;
            }
        }

        //up right move
        if(row < xRowPos && col > xColPos){
            int rowPtr = row;
            int colPtr = col;
            char temp;
            while(rowPtr < xRowPos && colPtr > xColPos){
                rowPtr++;
                colPtr--;
                temp = board.get(rowPtr).charAt(colPtr);
                if(temp == '#' || temp == 'O'){
                    return false;
                }

            }
            if(board.get(rowPtr).charAt(colPtr) == 'X'){
                return true;
            }
        }

        //down right move
        if(row > xRowPos && col > xColPos){
            int rowPtr = row;
            int colPtr = col;
            char temp;
            while(rowPtr > xRowPos && colPtr > xColPos){
                rowPtr--;
                colPtr--;
                temp = board.get(rowPtr).charAt(colPtr);
                if(temp == '#' || temp == 'O'){
                    return false;
                }

            }
            if(board.get(rowPtr).charAt(colPtr) == 'X'){
                return true;
            }
        }

        //down left move
        if(row > xRowPos && col < xColPos){
            int rowPtr = row;
            int colPtr = col;
            char temp;
            while(rowPtr > xRowPos && colPtr < xColPos){
                rowPtr--;
                colPtr++;
                temp = board.get(rowPtr).charAt(colPtr);
                if(temp == '#' || temp == 'O'){
                    return false;
                }

            }
            if(board.get(rowPtr).charAt(colPtr) == 'X'){
                return true;
            }
        }

        //up left move
        if(row < xRowPos && col < xColPos){
            int rowPtr = row;
            int colPtr = col;
            char temp;
            while(rowPtr < xRowPos && colPtr < xColPos){
                rowPtr++;
                colPtr++;
                temp = board.get(rowPtr).charAt(colPtr);
                if(temp == '#' || temp == 'O'){
                    return false;
                }

            }
            if(board.get(rowPtr).charAt(colPtr) == 'X'){
                return true;
            }
        }

        return false;
    }

    public boolean oLegalMove(int row, int col){
        if(row < 0 || row > 7 || col < 0 || col > 7){
            return false;
        }
        if(row == oRowPos && col == oColPos){
            return false;
        }

        if(board.get(row).charAt(col) == '#' || board.get(row).charAt(col) == 'X' || board.get(row).charAt(col) == 'O'){
            return false;
        }

        //right move
        if(row == oRowPos && col > oColPos){
            int colPtr = col;
            char temp;
            while(colPtr > oColPos){
                colPtr--;
                temp = board.get(oRowPos).charAt(colPtr);
                if(temp == '#' || temp == 'X'){
                    return false;
                }

            }
            if(board.get(oRowPos).charAt(colPtr) == 'O'){
                return true;
            }
        }
        //left move
        if(row == oRowPos && col < oColPos){
            int colPtr = col;
            char temp;
            while(colPtr < oColPos){
                colPtr++;
                temp = board.get(oRowPos).charAt(colPtr);
                if(temp == '#' || temp == 'X'){
                    return false;
                }

            }
            if(board.get(oRowPos).charAt(colPtr) == 'O'){
                return true;
            }
        }
        //up move
        if(row < oRowPos && col == oColPos){
            int rowPtr = row;
            char temp;
            while(rowPtr < oRowPos){
                rowPtr++;
                temp = board.get(rowPtr).charAt(oColPos);
                if(temp == '#' || temp == 'X'){
                    return false;
                }

            }
            if(board.get(rowPtr).charAt(oColPos) == 'O'){
                return true;
            }
        }

        //down move
        if(row > oRowPos && col == oColPos){
            int rowPtr = row;
            char temp;
            while(rowPtr > oRowPos){
                rowPtr--;
                temp = board.get(rowPtr).charAt(oColPos);
                if(temp == '#' || temp == 'X'){
                    return false;
                }

            }
            if(board.get(rowPtr).charAt(oColPos) == 'O'){
                return true;
            }
        }

        //up right move
        if(row < oRowPos && col > oColPos){
            int rowPtr = row;
            int colPtr = col;
            char temp;
            while(rowPtr < oRowPos && colPtr > oColPos){
                rowPtr++;
                colPtr--;
                temp = board.get(rowPtr).charAt(colPtr);
                if(temp == '#' || temp == 'X'){
                    return false;
                }

            }
            if(board.get(rowPtr).charAt(colPtr) == 'O'){
                return true;
            }
        }

        //down right move
        if(row > oRowPos && col > oColPos){
            int rowPtr = row;
            int colPtr = col;
            char temp;
            while(rowPtr > oRowPos && colPtr > oColPos){
                rowPtr--;
                colPtr--;
                temp = board.get(rowPtr).charAt(colPtr);
                if(temp == '#' || temp == 'X'){
                    return false;
                }

            }
            if(board.get(rowPtr).charAt(colPtr) == 'O'){
                return true;
            }
        }

        //down left move
        if(row > oRowPos && col < oColPos){
            int rowPtr = row;
            int colPtr = col;
            char temp;
            while(rowPtr > oRowPos && colPtr < oColPos){
                rowPtr--;
                colPtr++;
                temp = board.get(rowPtr).charAt(colPtr);
                if(temp == '#' || temp == 'X'){
                    return false;
                }

            }
            if(board.get(rowPtr).charAt(colPtr) == 'O'){
                return true;
            }
        }

        //up left move
        if(row < oRowPos && col < oColPos){
            int rowPtr = row;
            int colPtr = col;
            char temp;
            while(rowPtr < oRowPos && colPtr < oColPos){
                rowPtr++;
                colPtr++;
                temp = board.get(rowPtr).charAt(colPtr);
                if(temp == '#' || temp == 'X'){
                    return false;
                }

            }
            if(board.get(rowPtr).charAt(colPtr) == 'O'){
                return true;
            }
        }

        return false;
    }

    public int xPossibleMoves(){
        int sum = 0;
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                if(xLegalMove(i,j)){
                    sum++;
                }
            }
        }
        return sum;
    }

    public int oPossibleMoves(){
        int sum = 0;
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                if(oLegalMove(i,j)){
                    sum++;
                }
            }
        }
        return sum;
    }

    public ArrayList<String> xListPossibleMoves(){
        ArrayList<String> moves= new ArrayList<>();
        StringBuilder str;
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                if(xLegalMove(i,j)){
                    str = new StringBuilder("");
                    str.append(i);
                    str.append(j);
                    moves.add(str.toString());
                }
            }
        }
        return moves;
    }

    public ArrayList<String> oListPossibleMoves(){
        ArrayList<String> moves= new ArrayList<>();
        StringBuilder str;
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                if(oLegalMove(i,j)){
                    str = new StringBuilder("");
                    str.append(i);
                    str.append(j);
                    moves.add(str.toString());
                }
            }
        }
        return moves;
    }

    public ArrayList<BoardNode> xCreateChildren(){
        ArrayList<String> moves = this.xListPossibleMoves();
        ArrayList<BoardNode> results = new ArrayList<>();
        BoardNode temp;
        int row, col;
        for(int i=0; i<moves.size(); i++){
            row = Character.getNumericValue(moves.get(i).charAt(0));
            col = Character.getNumericValue(moves.get(i).charAt(1));

            temp = new BoardNode(this);
            temp.moveX(row, col);
            results.add(temp);
        }
        return results;
    }

    public ArrayList<BoardNode> oCreateChildren(){
        ArrayList<String> moves = this.oListPossibleMoves();
        ArrayList<BoardNode> results = new ArrayList<>();
        BoardNode temp;
        int row, col;
        for(int i=0; i<moves.size(); i++){
            row = Character.getNumericValue(moves.get(i).charAt(0));
            col = Character.getNumericValue(moves.get(i).charAt(1));

            temp = new BoardNode(this);
            temp.moveO(row, col);
            results.add(temp);
        }
        return results;
    }

    public void printBoard(){
        System.out.print("\n  ");
        for(int i=0; i<8; i++){
            System.out.print(i+1 + " ");
        }
        System.out.println();
        for(int i=0; i<8; i++){
            System.out.print((char)(i+65) + " ");
            for(int j=0; j<8; j++){
                System.out.print(board.get(i).charAt(j) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    @Override
    public int compareTo(BoardNode right) {
        if(this.score < right.score){
            return 1;
        }
        else if(this.score == right.score){
            return ThreadLocalRandom.current().nextInt(0,2);
        }
        else{
            return -1;
        }
    }
}
