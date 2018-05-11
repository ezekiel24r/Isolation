import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class AlphaBeta {
    public static int[] run(BoardNode node, char player, int alpha, int beta, boolean maxPlayer) {
        int[] result = new int[2];

        if (maxPlayer) {
            ArrayList<BoardNode> moves;


            if (player == 'X') {
                moves = node.xCreateChildren();
                Collections.sort(moves);

                System.out.println();
                result[0] = moves.get(0).xRowPos;
                result[1] = moves.get(0).xColPos;
                return result;

            } else if (player == 'Y') {
                moves = node.oCreateChildren();

            }
        } else {
            ArrayList<BoardNode> moves;


            if (player == 'Y') {
                moves = node.oCreateChildren();
                Collections.sort(moves);

                System.out.println();
                result[0] = moves.get(moves.size() - 1).oRowPos;
                result[1] = moves.get(moves.size() - 1).oColPos;
                return result;

            } else if (player == 'X') {
                moves = node.oCreateChildren();

            }

        }
        return result;
    }

    public static int [] getBestMove(BoardNode node){
        int [] result = new int[2];

        alphaBeta(node, 4, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
        Collections.sort(node.children);
        result[0] = node.children.get(0).xRowPos;
        result[1] = node.children.get(0).xColPos;
        System.out.println();
        //ArrayList<BoardNode> moves = node.createChildren();
        //for(int i=0; i<moves.size(); i++){
            //alphaBeta()
        //}
        return result;
    }

    public static int alphaBeta(BoardNode node, int depth, int alpha, int beta, boolean maximizingPlayer) {
        ArrayList<BoardNode> moves;
        int val;

        if (depth == 0)
            return node.xPossibleMoves() - node.oPossibleMoves();
        if (node.xPossibleMoves() == 0) {
            return -1000;
        }
        if (node.oPossibleMoves() == 0) {
            return 1000;
        }


        if(maximizingPlayer){
            val = Integer.MIN_VALUE;
            moves = node.createChildren();

            for(int i=0; i<moves.size(); i++){
                val = Math.max(val, alphaBeta(moves.get(i), depth-1, alpha, beta, false));
                alpha = Math.max(alpha, val);
                if (beta <= alpha) {
                    break;
                }
            }
            return val;
        }
        else{
            val = Integer.MAX_VALUE;
            moves = node.createChildren();

            for(int i=0; i<moves.size(); i++){
                val = Math.min(val, alphaBeta(moves.get(i), depth-1, alpha, beta, true));
                alpha = Math.min(beta, val);
                if (beta <= alpha) {
                    break;
                }
            }
            return val;
        }
    }

}