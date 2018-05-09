import java.util.Scanner;

public class Driver {
    public static void main(String [] args){
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
            System.out.println("Enter a move for X");

            move = parseInput(scan.nextLine());
            temp = new BoardNode(b);
            while(!temp.moveX(move[0], move[1])){
                b.printBoard();
                System.out.println("Illegal Move!");
                System.out.println("Enter a valid move for X");
                move = parseInput(scan.nextLine());
                temp = new BoardNode(b);

            }
            b = temp;
            b.printBoard();

            System.out.println("Enter a move for O");
            move = parseInput(scan.nextLine());
            temp = new BoardNode(b);
            while(!temp.moveO(move[0], move[1])){
                b.printBoard();
                System.out.println("Illegal Move!");
                System.out.println("Enter a valid move for O");
                move = parseInput(scan.nextLine());
                temp = new BoardNode(b);

            }
            b = temp;
            b.printBoard();
        }

        /*
        b.moveX(0,5);
        System.out.println();
        b.printBoard();
        System.out.println();
        b.moveO(7,2);

        System.out.println();
        b.printBoard();
        System.out.println();*/
    }

    public static int [] parseInput(String in){
        int [] result = new int[2];
        if(Character.isAlphabetic(in.charAt(0))) {
            result[0] = in.charAt(0) - 65;
        }
        else{
            result[0] = -1;
        }
        result[1] = Character.getNumericValue(in.charAt(1)) - 1;
        return result;
    }
}
