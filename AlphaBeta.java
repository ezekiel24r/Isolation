import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class AlphaBeta {

    static double TIME_LIMIT = 2000000000.0; //18 seconds
    static double DEPTH_LIMIT = 100;


    public static int [] getBestMove(BoardNode node){
        BoardNode temp = new BoardNode(node, 0);
        BoardNode best = new BoardNode(node, 0);
        //ArrayList<BoardNode> moves = new ArrayList<>();
        int [] result = new int[2];
        long time = System.nanoTime();
        int initialDepth=4;
        while(((System.nanoTime() - time)) < TIME_LIMIT  && initialDepth < DEPTH_LIMIT) {
            temp.children = new ArrayList<>();
            alphaBeta(temp, initialDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, true, time);

            //System.out.println("max depth = " + initialDepth);
            if((((System.nanoTime() - time)) > TIME_LIMIT)){

                break;
            }
            best.children = temp.children;

            initialDepth++;
        }

        Collections.sort(best.children);
        int j=0;
        int k=1;
        int rand;
        if(best.children.size()>1 && node.depth<5){
            while (k<best.children.size() && (best.children.get(j).score == best.children.get(k).score)) {
                rand = ThreadLocalRandom.current().nextInt(0, 2);
                //System.out.println("Random number = " + rand);

                if (rand == 0) {
                    //System.out.println("Next choice selected");
                    j++;
                    k++;
                }
                else
                    break;
            }
        }
        result[0] = best.children.get(j).xRowPos;
        result[1] = best.children.get(j).xColPos;
        //ArrayList<BoardNode> moves = node.createChildren();
        //for(int i=0; i<moves.size(); i++){
            //alphaBeta()
        //}
        return result;
    }

    public static int alphaBeta(BoardNode node, int depth, int alpha, int beta, boolean maximizingPlayer, long startTime) {
        //ArrayList<BoardNode> moves;

        int val;


        if (depth == 0)
            return node.score;
        if (node.xMoves == 0) {
            node.score = Integer.MIN_VALUE+10;
            return Integer.MIN_VALUE+10;

        }
        if (node.oMoves == 0) {
            node.score = Integer.MAX_VALUE;
            return Integer.MAX_VALUE-10;
        }

        if(maximizingPlayer){
            val = Integer.MIN_VALUE;
            //moves = node.createChildren();
            node.createChildren();
            Collections.sort(node.children);

            for(int i=0; i<node.children.size(); i++){
                val = Math.max(val, alphaBeta(node.children.get(i), depth-1, alpha, beta, false, startTime));
                node.score = val;
                alpha = Math.max(alpha, val);
                if (beta <= alpha) {
                    break;
                }
                if (System.nanoTime() - startTime > TIME_LIMIT){
                    break;
                }
            }
            //node.children = moves;
            return val;
        }
        else{
            val = Integer.MAX_VALUE;
            //moves = node.createChildren();
            node.createChildren();
            Collections.sort(node.children);
            Collections.reverse(node.children);

            for(int i=0; i<node.children.size(); i++){
                val = Math.min(val, alphaBeta(node.children.get(i), depth-1, alpha, beta, true, startTime));
                node.score = val;
                beta = Math.min(beta, val);
                if (beta <= alpha) {
                    break;
                }
                if (System.nanoTime() - startTime > TIME_LIMIT){
                    break;
                }
            }
            //node.children = moves;
            return val;
        }
    }

}