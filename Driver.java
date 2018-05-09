import java.util.Scanner;

public class Driver {
    public static void main(String [] args){
        //test
        System.out.println("hi");

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
            System.out.println("X has " + b.xPossibleMoves() + " moves available");
            System.out.println("Enter a move for X");

            move = parseInput(scan.nextLine());
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

            if(b.oPossibleMoves() == 0){
                System.out.println("X has won!");
                break;
            }
            if(b.xPossibleMoves() == 0){
                System.out.println("O has won!");
                break;
            }
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
