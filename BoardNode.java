/*
Author: Eric Rensel
CS420 - Artificial Intelligence

Class BoardNode creates and manipulates the game board for Isolation, and also provides the scoring heuristic
for the current state of the board. It also enforces the rules of play so no illegal moves are made.
 */


import java.util.ArrayList;

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

    //Default Constructor is that X is the first player
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

    //Constructor if the first player was chosen
    BoardNode(String choice){
        if(choice.equals("O")) {
            board = new ArrayList<String>(8);
            board.add("O-------");
            board.add("--------");
            board.add("--------");
            board.add("--------");
            board.add("--------");
            board.add("--------");
            board.add("--------");
            board.add("-------X");

            xRowPos = 7;
            xColPos = 7;
            oRowPos = 0;
            oColPos = 0;

            xMoves = 20;
            oMoves = 20;

            score = 0;
            depth = 0;

            player = 'O';

            children = new ArrayList<>();
        }
        else{
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
    }

    //this constructor is used to create a copy of a board state so that the old state is not manipulated.
    BoardNode(BoardNode in){
        board = new ArrayList<String>(8);
        for(int i = 0; i<8; i++){
            this.board.add(in.board.get(i));
        }

        xRowPos = in.xRowPos;
        xColPos = in.xColPos;
        oRowPos = in.oRowPos;
        oColPos = in.oColPos;

        depth = in.depth;

        score = in.score;
        xMoves = in.xMoves;
        oMoves = in.oMoves;

        children = new ArrayList<>();

        player = in.player;

    }

    //moveX changes the position of X and places the # barrier.
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
        return false;
    }

    //moveO changes the position of O and places the # barrier.
    public boolean moveO(int row, int col){
        if (oLegalMove(row, col)) {
            //make old position #
            StringBuilder str = new StringBuilder(board.get(oRowPos));
            str.replace(oColPos,oColPos+1, "#");
            board.set(oRowPos, str.toString());

            //add O to new position
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
        return false;
    }


    /*updateScore: This is the simple heuristic used to determine if a state of the board is a good state or not.
        I decided to use (xMoves - (oMoves)*2) as my scoring heuristic, because I found that while this
        heuristic can still lose to the (xMoves - oMoves) heuristic, it appeared to win more decisively
        when it was in an advantageous position by playing more aggressively than the opponent.
     */
    public void updateScore(){
        xMoves = xPossibleMoves();
        oMoves = oPossibleMoves();

        /*explanation of the next line:
            The AI favors moves that limit the Opponents moves more than maximizing it's own moves. In other words,
            it places more focus on trapping the opponent and less focus on trying to avoid becoming trapped.
         */
        score = (xMoves-(oMoves*2));
    }

    //xLegalMove returns whether an X move is legal or not
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
        //move is impossible
        return false;
    }

    //oLegalMove returns whether an O move is legal or not
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
        //move is impossible
        return false;
    }

    //xPossibleMoves returns the sum of total legal moves that X has in it's current position
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

    //oPossibleMoves returns the sum ot total legal moves that O has in it's current position
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

    //xListPossibleMoves creates a list of the coordinates of all legal moves that X can make
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

    //oListPossibleMoves creates a list of the coordinates of all legal moves that O can make
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

    //createChildren calls the appropriate function based on who's turn it is
    public void createChildren(){
        if(player == 'X'){
            xCreateChildren();
        }
        else{
            oCreateChildren();
        }
    }

    //xCreateChildren generates the child boards that are created by playing every possible legal move by X
    public void xCreateChildren(){
        ArrayList<String> moves = this.xListPossibleMoves();
        ArrayList<BoardNode> results = new ArrayList<>();
        BoardNode temp;
        int row, col;
        for(int i=0; i<moves.size(); i++){
            row = Character.getNumericValue(moves.get(i).charAt(0));
            col = Character.getNumericValue(moves.get(i).charAt(1));

            temp = new BoardNode(this);
            temp.moveX(row, col);
            //this.children.add(temp);
            results.add(temp);
        }

        children = results;
    }

    //oCreateChildren generates the child boards that are created by playing every possible legal move by O
    public void oCreateChildren(){
        ArrayList<String> moves = this.oListPossibleMoves();
        ArrayList<BoardNode> results = new ArrayList<>();
        BoardNode temp;
        int row, col;
        for(int i=0; i<moves.size(); i++){
            row = Character.getNumericValue(moves.get(i).charAt(0));
            col = Character.getNumericValue(moves.get(i).charAt(1));

            temp = new BoardNode(this);
            temp.moveO(row, col);
            //this.children.add(temp);
            results.add(temp);
        }

        children = results;
    }

    //printBoard outputs the board and every move that the players took
    public void printBoard(ArrayList<int []> moveList, char firstPlayer){
        int movePtr=0;
        int size = moveList.size();
        int temp[];
        ArrayList<int []> tempList = new ArrayList<>();
        for(int i=0; i<moveList.size(); i++){
            tempList.add(moveList.get(i).clone());
        }
        if(firstPlayer == 'X') {
            System.out.print("\n  ");
            for (int i = 0; i < 8; i++) {
                System.out.print(i + 1 + " ");
            }
            System.out.print("\tComputer vs. Opponent\n");
            for (int i = 0; i < 8; i++) {
                System.out.print((char) (i + 65) + " ");
                for (int j = 0; j < 8; j++) {
                    System.out.print(board.get(i).charAt(j) + " ");
                }
                //read two moves off list
                if(!tempList.isEmpty()) {
                    temp = tempList.remove(0);
                    temp[0]+=65;
                    temp[1]+=1;
                    movePtr++;
                    System.out.print("\t" + movePtr + ".\t" + ((char)temp[0]) + temp[1]);
                }
                if(!tempList.isEmpty()) {
                    temp = tempList.remove(0);
                    temp[0] += 65;
                    temp[1] += 1;
                    System.out.print("\t" + (char) temp[0] + temp[1]);
                }

                System.out.println();
            }
            while(!tempList.isEmpty()){
                movePtr++;
                System.out.print("                \t" + movePtr + ".\t");
                temp = tempList.remove(0);
                temp[0] += 65;
                temp[1] += 1;
                System.out.print("" + (char) temp[0] + (temp[1]));
                if(!tempList.isEmpty()) {
                    temp = tempList.remove(0);
                    temp[0] += 65;
                    temp[1] += 1;
                    System.out.print("\t" + ((char)temp[0]) + temp[1]);
                }
                System.out.println();
            }
            System.out.println();
        }
        else{
            System.out.print("\n  ");
            for (int i = 0; i < 8; i++) {
                System.out.print(i + 1 + " ");
            }
            System.out.print("\tOpponent vs. Computer\n");
            for (int i = 0; i < 8; i++) {
                System.out.print((char) (i + 65) + " ");
                for (int j = 0; j < 8; j++) {
                    System.out.print(board.get(i).charAt(j) + " ");
                }
                //read two moves off list
                if(!tempList.isEmpty()) {
                    temp = tempList.remove(0);
                    temp[0]+=65;
                    temp[1]+=1;
                    movePtr++;
                    System.out.print("\t" + movePtr + ".\t" + ((char)temp[0]) + temp[1]);
                }
                if(!tempList.isEmpty()) {
                    temp = tempList.remove(0);
                    temp[0] += 65;
                    temp[1] += 1;
                    System.out.print("\t" + (char) temp[0] + temp[1]);
                }

                System.out.println();
            }
            while(!tempList.isEmpty()){
                movePtr++;
                System.out.print("                \t" + movePtr + ".\t");
                temp = tempList.remove(0);
                temp[0] += 65;
                temp[1] += 1;
                System.out.print("" + (char) temp[0] + (temp[1]));
                if(!tempList.isEmpty()) {
                    temp = tempList.remove(0);
                    temp[0] += 65;
                    temp[1] += 1;
                    System.out.print("\t" + ((char)temp[0]) + temp[1]);
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    //compareTo is necessary to implement so that the generated child boards can be sorted by score
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