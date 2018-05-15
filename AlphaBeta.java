import java.util.ArrayList;
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
        BoardNode temp = new BoardNode(node, 0);
        BoardNode best = new BoardNode(node, 0);
        ArrayList<BoardNode> moves = temp.xCreateChildren();
        int [] result = new int[2];
        long time = System.nanoTime();
        int initialDepth=3;
        while(((System.nanoTime() - time)) < 18000000000.0  && initialDepth < 100) {
            temp.children = new ArrayList<>();
            alphaBeta(temp, initialDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, true, time);

            System.out.println("max depth = " + initialDepth);
            if((((System.nanoTime() - time)) > 18000000000.0)){

                break;
            }
            best = temp;

            initialDepth++;
        }

        Collections.sort(best.children);
        result[0] = best.children.get(0).xRowPos;
        result[1] = best.children.get(0).xColPos;
        System.out.println();
        //ArrayList<BoardNode> moves = node.createChildren();
        //for(int i=0; i<moves.size(); i++){
            //alphaBeta()
        //}
        return result;
    }

    public static int alphaBeta(BoardNode node, int depth, int alpha, int beta, boolean maximizingPlayer, long startTime) {
        ArrayList<BoardNode> moves;
        int val;

        if (depth == 0)
            return node.score;
        if (node.xMoves == 0) {
            return Integer.MIN_VALUE+10;

        }
        if (node.oMoves == 0) {
            return Integer.MAX_VALUE-10;
        }

        if(maximizingPlayer){
            val = Integer.MIN_VALUE;
            moves = node.createChildren();
            Collections.sort(moves);

            for(int i=0; i<moves.size(); i++){
                val = Math.max(val, alphaBeta(moves.get(i), depth-1, alpha, beta, false, startTime));
                node.score = val;
                alpha = Math.max(alpha, val);
                if (beta <= alpha) {
                    break;
                }
                if (System.nanoTime() - startTime > 18000000000.0){
                    break;
                }
            }
            return val;
        }
        else{
            val = Integer.MAX_VALUE;
            moves = node.createChildren();
            moves.sort(Collections.reverseOrder());

            for(int i=0; i<moves.size(); i++){
                val = Math.min(val, alphaBeta(moves.get(i), depth-1, alpha, beta, true, startTime));
                node.score = val;
                alpha = Math.min(beta, val);
                if (beta <= alpha) {
                    break;
                }
                if (System.nanoTime() - startTime > 18000000000.0){
                    break;
                }
            }
            return val;
        }
    }

}