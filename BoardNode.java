import java.util.ArrayList;
import java.util.PriorityQueue;

public class BoardNode implements Comparable<BoardNode>{
    public ArrayList<String> board;
    int xRowPos;
    int xColPos;
    int oRowPos;
    int oColPos;
    int depth;
    int score;
    int xMoves;
    int oMoves;

    char player;

    public ArrayList<BoardNode> children;

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

        xMoves = 20;
        oMoves = 20;

        score = 0;

        depth = 0;

        player = 'X';

        children = new ArrayList<>();



    }

    BoardNode(int test){
        board = new ArrayList<String>(8);
        board.add("#-------");
        board.add("--------");
        board.add("--------");
        board.add("---##O--");
        board.add("---###--");
        board.add("--------");
        board.add("-X---#--");
        board.add("-------#");

        xRowPos = 6;
        xColPos = 1;
        oRowPos = 3;
        oColPos = 5;

        updateScore();

        depth = 8;

        player = 'X';

        children = new ArrayList<>();



    }



    /*BoardNode(BoardNode parent){
        board = new ArrayList<String>(8);
        for(int i = 0; i<8; i++){
            this.board.add(parent.board.get(i));
        }



        xRowPos = parent.xRowPos;
        xColPos = parent.xColPos;
        oRowPos = parent.oRowPos;
        oColPos = parent.oColPos;

        depth = parent.depth;

        //score = xPossibleMoves()- oPossibleMoves();

        children = new ArrayList<>();

        parent.children.add(this);

        if(parent.player == 'X'){
            this.player = 'O';
        }
        else{
            this.player = 'X';
        }
    }*/

    BoardNode(BoardNode parent, int signal){
        board = new ArrayList<String>(8);
        for(int i = 0; i<8; i++){
            this.board.add(parent.board.get(i));
        }



        xRowPos = parent.xRowPos;
        xColPos = parent.xColPos;
        oRowPos = parent.oRowPos;
        oColPos = parent.oColPos;

        depth = parent.depth;

        score = parent.score;
        xMoves = parent.xMoves;
        oMoves = parent.oMoves;

        children = new ArrayList<>();

        player = parent.player;

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
            updateScore();
            player = 'O';
            depth++;
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
            updateScore();
            player = 'X';
            depth++;
            return true;
        }
        //System.out.println("Illegal move!");
        return false;
    }

    public void updateScore(){


        xMoves = xPossibleMoves();
        oMoves = oPossibleMoves();


        int xAdv;
        int oAdv;

        if(xRowPos > 2 && xColPos > 2 && xRowPos < 5 && xColPos < 5){
            xAdv = 1;
        }
        else {
            xAdv = 0;
        }
        if(oRowPos > 2 && oColPos > 2 && oRowPos < 5 && oColPos < 5){
            oAdv = 1;
        }
        else{
            oAdv = 0;
        }

        //killer move heuristic!

        if(depth > 5) {

            int xSpace = xTotalSpace();
            int oSpace = oTotalSpace();


            if (xSpace > oSpace) {
                xAdv += 1000;
            }
            if (oSpace > xSpace) {
                oAdv += 1000;
            }
        }




        if(player == 'X') {

            score = (xMoves-oMoves) + (xAdv - oAdv);
        }
        else{

            score = (xMoves-oMoves) + (xAdv - oAdv);
        }

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

    public int possibleMoves(){
        if(player == 'X'){
            return xPossibleMoves();
        }
        else{
            return oPossibleMoves();
        }
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

    public int xDirections(){
        int sum = 0;
        int degree = 2;
        char temp;
        //check left
        if(xColPos > 0){
            temp = board.get(xRowPos).charAt(xColPos-1);
            if(temp == '-'){
                sum+=degree;
            }
        }
        //check right
        if(xColPos < 7){
            temp = board.get(xRowPos).charAt(xColPos+1);
            if(temp == '-'){
                sum+=degree;
            }
        }
        //check up
        if(xRowPos > 0){
            temp = board.get(xRowPos-1).charAt(xColPos);
            if(temp == '-'){
                sum+=degree;
            }
        }
        //check down
        if(xRowPos < 7){
            temp = board.get(xRowPos+1).charAt(xColPos);
            if(temp == '-'){
                sum+=degree;
            }
        }
        //check up right
        if(xRowPos > 0 && xColPos < 7){
            temp = board.get(xRowPos-1).charAt(xColPos+1);
            if(temp == '-'){
                sum+=degree;
            }
        }
        //check down right
        if(xRowPos < 7 && xColPos < 7){
            temp = board.get(xRowPos+1).charAt(xColPos+1);
            if(temp == '-'){
                sum+=degree;
            }
        }
        //check down left
        if(xRowPos < 7 && xColPos > 0){
            temp = board.get(xRowPos+1).charAt(xColPos-1);
            if(temp == '-'){
                sum+=degree;
            }
        }
        //check up left
        if(xRowPos > 0 && xColPos > 0){
            temp = board.get(xRowPos-1).charAt(xColPos-1);
            if(temp == '-'){
                sum+=degree;
            }
        }
        return sum;
    }

    public int oDirections(){
        int sum = 0;
        int degree = 2;
        char temp;
        //check left
        if(oColPos > 0){
            temp = board.get(oRowPos).charAt(oColPos-1);
            if(temp == '-'){
                sum+=degree;
            }
        }
        //check right
        if(oColPos < 7){
            temp = board.get(oRowPos).charAt(oColPos+1);
            if(temp == '-'){
                sum+=degree;
            }
        }
        //check up
        if(oRowPos > 0){
            temp = board.get(oRowPos-1).charAt(oColPos);
            if(temp == '-'){
                sum+=degree;
            }
        }
        //check down
        if(oRowPos < 7){
            temp = board.get(oRowPos+1).charAt(oColPos);
            if(temp == '-'){
                sum+=degree;
            }
        }
        //check up right
        if(oRowPos > 0 && oColPos < 7){
            temp = board.get(oRowPos-1).charAt(oColPos+1);
            if(temp == '-'){
                sum+=degree;
            }
        }
        //check down right
        if(oRowPos < 7 && oColPos < 7){
            temp = board.get(oRowPos+1).charAt(oColPos+1);
            if(temp == '-'){
                sum+=degree;
            }
        }
        //check down left
        if(oRowPos < 7 && oColPos > 0){
            temp = board.get(oRowPos+1).charAt(oColPos-1);
            if(temp == '-'){
                sum+=degree;
            }
        }
        //check up left
        if(oRowPos > 0 && oColPos > 0){
            temp = board.get(oRowPos-1).charAt(oColPos-1);
            if(temp == '-'){
                sum+=degree;
            }
        }
        return sum;
    }







    public ArrayList<BoardNode> createChildren(){
        if(player == 'X'){
            return xCreateChildren();
        }
        else{
            return oCreateChildren();
        }
    }

    public ArrayList<BoardNode> xCreateChildren(){
        ArrayList<String> moves = this.xListPossibleMoves();
        ArrayList<BoardNode> results = new ArrayList<>();
        BoardNode temp;
        int row, col;
        for(int i=0; i<moves.size(); i++){
            row = Character.getNumericValue(moves.get(i).charAt(0));
            col = Character.getNumericValue(moves.get(i).charAt(1));

            temp = new BoardNode(this, 0);
            temp.moveX(row, col);
            //this.children.add(temp);
            results.add(temp);
        }

        children = results;
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

            temp = new BoardNode(this, 0);
            temp.moveO(row, col);
            //this.children.add(temp);
            results.add(temp);
        }

        children = results;
        return results;
    }

    //objective: return the total number of traversable spaces in the area

    public int xTotalSpace(){
        return totalSpace(new BoardNode(this, 0), xRowPos, xColPos, 0);
    }

    public int oTotalSpace(){
        return totalSpace(new BoardNode(this, 0), oRowPos, oColPos, 0);
    }

    public int totalSpace(BoardNode in, int row, int col, int sum){
        //BoardNode temp = new BoardNode(this, 0);
        char temp;
        StringBuilder str;
        //check left
        if(col > 0){
            temp = in.board.get(row).charAt(col-1);
            if(temp == '-'){
                //add + to position to show checked
                str = new StringBuilder(in.board.get(row));
                str.replace(col-1,col, "+");
                in.board.set(row, str.toString());
                sum+=1;
                sum = totalSpace(in, row, col-1, sum);
            }
        }
        //check right
        if(col < 7){
            temp = in.board.get(row).charAt(col+1);
            if(temp == '-'){
                //add + to position to show checked
                str = new StringBuilder(in.board.get(row));
                str.replace(col+1,col+2, "+");
                in.board.set(row, str.toString());
                sum+=1;
                sum = totalSpace(in, row, col+1, sum);
            }
        }
        //check up
        if(row > 0){
            temp = in.board.get(row-1).charAt(col);
            if(temp == '-'){
                //add + to position to show checked
                str = new StringBuilder(in.board.get(row-1));
                str.replace(col,col+1, "+");
                in.board.set(row-1, str.toString());
                sum+=1;
                sum = totalSpace(in, row-1, col, sum);
            }
        }
        //check down
        if(row < 7){
            temp = in.board.get(row+1).charAt(col);
            if(temp == '-'){
                //add + to position to show checked
                str = new StringBuilder(in.board.get(row+1));
                str.replace(col,col+1, "+");
                in.board.set(row+1, str.toString());
                sum+=1;
                sum = totalSpace(in, row+1, col, sum);
            }
        }
        //check up right
        if(row > 0 && col < 7){
            temp = in.board.get(row-1).charAt(col+1);
            if(temp == '-'){
                //add + to position to show checked
                str = new StringBuilder(in.board.get(row-1));
                str.replace(col+1,col+2, "+");
                in.board.set(row-1, str.toString());
                sum+=1;
                sum = totalSpace(in, row-1, col+1, sum);
            }
        }
        //check down right
        if(row < 7 && col < 7){
            temp = in.board.get(row+1).charAt(col+1);
            if(temp == '-'){
                //add + to position to show checked
                str = new StringBuilder(in.board.get(row+1));
                str.replace(col+1,col+2, "+");
                in.board.set(row+1, str.toString());
                sum+=1;
                sum = totalSpace(in, row+1, col+1, sum);
            }
        }
        //check down left
        if(row < 7 && col > 0){
            temp = in.board.get(row+1).charAt(col-1);
            if(temp == '-'){
                //add + to position to show checked
                str = new StringBuilder(in.board.get(row+1));
                str.replace(col-1,col, "+");
                in.board.set(row+1, str.toString());
                sum+=1;
                sum = totalSpace(in, row+1, col-1, sum);
            }
        }
        //check up left
        if(row > 0 && col > 0){
            temp = in.board.get(row-1).charAt(col-1);
            if(temp == '-'){
                //add + to position to show checked
                str = new StringBuilder(in.board.get(row-1));
                str.replace(col-1,col, "+");
                in.board.set(row-1, str.toString());
                sum+=1;
                sum = totalSpace(in, row-1, col-1, sum);
            }
        }


        return sum;
    }



    public void printBoard(){
        System.out.print("\n  ");
        for(int i=0; i<8; i++){
            System.out.print(i+1 + " ");
        }
        System.out.print( "Computer vs. Opponent\n");
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
            return 0;
        }
        else{
            return -1;
        }
    }
}