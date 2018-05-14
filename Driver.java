import java.util.Scanner;

public class Driver {
    public static void main(String [] args){
        //test

        Scanner scan = new Scanner(System.in);

        BoardNode b = new BoardNode();
        BoardNode temp;
        System.out.println();
        b.printBoard();
        System.out.println();

        int row;
        int col;
        int move[];
        while(true){
            if(b.xPossibleMoves() == 0){
                System.out.println("O has won!");
                break;
            }
            if(b.oPossibleMoves() == 0){
                System.out.println("X has won!");
                break;
            }
            System.out.println("X has " + b.xPossibleMoves() + " moves available");
            System.out.println("Enter a move for X");

            //move = parseInput(scan.nextLine());
            move = AlphaBeta.getBestMove(b);
            temp = new BoardNode(b);
            while(!temp.moveX(move[0], move[1])){
                b.printBoard();
                System.out.println("Illegal Move!");
                System.out.println("X has " + b.xPossibleMoves() + " moves available");
                System.out.println("Enter a valid move for X");
                move = parseInput(scan.nextLine());
                temp = new BoardNode(b);

            }
            b = temp;
            b.printBoard();
            System.out.println("total spaces accessible to x: " + b.totalSpace(new BoardNode(b,0),b.xRowPos,b.xColPos,0));
            System.out.println("total spaces accessible to o: " + b.totalSpace(new BoardNode(b,0),b.oRowPos,b.oColPos,0));




            //move for O
            if(b.oPossibleMoves() == 0){
                System.out.println("X has won!");
                break;
            }
            if(b.xPossibleMoves() == 0){
                System.out.println("O has won!");
                break;
            }
            System.out.println("O has " + b.oPossibleMoves() + " moves available");
            System.out.println("Enter a move for O");
            move = parseInput(scan.nextLine());
            //move = AlphaBeta.run(b, 'Y', 0, 0, false);


            temp = new BoardNode(b);
            while(!temp.moveO(move[0], move[1])){
                b.printBoard();
                System.out.println("Illegal Move!");
                System.out.println("O has " + b.oPossibleMoves() + " moves available");
                System.out.println("Enter a valid move for O");
                move = parseInput(scan.nextLine());
                temp = new BoardNode(b);

            }
            b = temp;
            b.printBoard();
            System.out.println("total spaces accessible to x: " + b.totalSpace(new BoardNode(b,0),b.xRowPos,b.xColPos,0));

            //ai move


        }

    }


    public static int [] parseInput(String in){
        int [] result = new int[2];
        if(in.length() != 2){
            result[0] = -1;
            result[1] = -1;
            return result;
        }

        if(Character.isAlphabetic(in.charAt(0))) {
            result[0] = in.charAt(0) - 65;
        }
        else{
            result[0] = -1;
        }
        if(Character.isDigit(in.charAt(1))){
            result[1] = Character.getNumericValue(in.charAt(1)) - 1;
        }
        else{
            result[1] = -1;
        }

        return result;
    }
}
