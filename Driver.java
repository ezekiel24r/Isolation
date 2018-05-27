
import java.util.ArrayList;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        //test

        ArrayList<int[]> moveList = new ArrayList<>();

        Scanner scan = new Scanner(System.in);
        String choice;
        long timeAllowed;


        System.out.println("How much time (in seconds) is allowed for the AI?");
        choice = scan.nextLine();
        while(!isValidTime(choice)){
                System.out.println("Invalid time");
                choice = scan.nextLine();
        }
        timeAllowed = Long.parseLong(choice);
        while(true) {
            System.out.println("Does this computer's AI play first? (Y/N)");
            choice = scan.nextLine();
            if (choice.equals("Y")) {


                BoardNode b = new BoardNode();
                BoardNode temp;
                System.out.println();
                b.printBoard(moveList, 'X');
                System.out.println();

                int row;
                int col;
                int move[];
                while (true) {

                    //X Moves

                    //X cannot make a move
                    if (b.xPossibleMoves() == 0) {
                        System.out.println("O has won!");
                        break;
                    }

                    //O cannot make a move, and X can, so there is no reason for X to choose
                    if (b.oPossibleMoves() == 0) {
                        System.out.println("X has won!");
                        break;
                    }

                    System.out.println("X is choosing a move");

                    //move = parseInput(scan.nextLine());
                    move = AlphaBeta.getBestMove(b, timeAllowed);
                    temp = new BoardNode(b, 0);
                    while (!temp.moveX(move[0], move[1])) {
                        System.out.println("Illegal Move!");
                        System.out.println("Enter a valid move for X");
                        move = parseInput(scan.nextLine());
                        temp = new BoardNode(b, 0);

                    }
                    b = temp;
                    moveList.add(move);
                    b.printBoard(moveList, 'X');
                    System.out.println("Computer's move is: " + ((char)(move[0]+65)) + (move[1]+1));


                    //O moves

                    //O cannot make a move
                    if (b.oPossibleMoves() == 0) {
                        System.out.println("X has won!");
                        break;
                    }

                    //X cannot make a move, and O can, so there is no reason for O to choose
                    if (b.xPossibleMoves() == 0) {
                        System.out.println("O has won!");
                        break;
                    }

                    System.out.print("Enter opponent's move: ");
                    move = parseInput(scan.nextLine());
                    System.out.println();
                    //move = AlphaBeta.run(b, 'Y', 0, 0, false);


                    temp = new BoardNode(b, 0);
                    while (!temp.moveO(move[0], move[1])) {
                        System.out.println("Illegal Move!");
                        System.out.println("Enter a valid move for O");
                        move = parseInput(scan.nextLine());
                        temp = new BoardNode(b, 0);

                    }
                    b = temp;
                    moveList.add(move);
                    b.printBoard(moveList, 'X');

                }
                break;


            } else if (choice.equals("N")) {

                BoardNode b = new BoardNode("O");
                BoardNode temp;
                System.out.println();
                b.printBoard(moveList, 'O');
                System.out.println();

                int row;
                int col;
                int move[];
                while (true) {

                    //O Moves

                    //O cannot move
                    if (b.oPossibleMoves() == 0) {
                        System.out.println("X has won!");
                        break;
                    }

                    //O can move, and X cannot move,  so O has no reason to choose a move
                    if (b.xPossibleMoves() == 0) {
                        System.out.println("O has won!");
                        break;
                    }
                    System.out.print("Enter opponent's move: ");
                    move = parseInput(scan.nextLine());
                    System.out.println();
                    //move = AlphaBeta.run(b, 'Y', 0, 0, false);


                    temp = new BoardNode(b, 0);
                    while (!temp.moveO(move[0], move[1])) {
                        System.out.println("Illegal Move!");
                        System.out.println("Enter a valid move for O");
                        move = parseInput(scan.nextLine());
                        temp = new BoardNode(b, 0);

                    }
                    b = temp;
                    moveList.add(move);
                    b.printBoard(moveList, 'O');


                    //X Moves

                    //X cannot make a moves
                    if (b.xPossibleMoves() == 0) {
                        System.out.println("O has won!");
                        break;
                    }
                    //O cannot make a move, so there is no reason for X to move
                    if (b.oPossibleMoves() == 0) {
                        System.out.println("X has won!");
                        break;
                    }
                    System.out.println("X is choosing a move");

                    //move = parseInput(scan.nextLine());
                    move = AlphaBeta.getBestMove(b, timeAllowed);
                    temp = new BoardNode(b, 0);
                    while (!temp.moveX(move[0], move[1])) {
                        System.out.println("Illegal Move!");
                        System.out.println("Enter a valid move for X");
                        move = parseInput(scan.nextLine());
                        temp = new BoardNode(b, 0);

                    }
                    b = temp;
                    moveList.add(move);
                    b.printBoard(moveList, 'O');
                    System.out.println("Computer's move is: " + ((char)(move[0]+65)) + (move[1]+1));


                    //move for O

                    //ai move
                }
                break;

            }
            else{
                System.out.println("Invalid choice");
            }
        }

    }


    public static int[] parseInput(String in) {
        int[] result = new int[2];
        if (in.length() != 2) {
            result[0] = -1;
            result[1] = -1;
            return result;
        }

        if (Character.isAlphabetic(in.charAt(0))) {
            result[0] = in.charAt(0) - 65;
        } else {
            result[0] = -1;
        }
        if (Character.isDigit(in.charAt(1))) {
            result[1] = Character.getNumericValue(in.charAt(1)) - 1;
        } else {
            result[1] = -1;
        }

        return result;
    }

    public static boolean isValidTime(String str){
        return str.matches("[1-9][0-9]*");
    }
}
