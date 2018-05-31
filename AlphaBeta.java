/*
Author: Eric Rensel
CS420 - Artificial Intelligence

Class ArrayList uses AlphaBeta Pruning within the getBestMove function to find the "best" move based on
using the scoring heuristic at intermediate states, or returning a score that indicates a win or loss
returned by a terminal state.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class AlphaBeta {

    //searching past depth 64 is pointless, there is only 64 spaces on the 8x8 board
    static double DEPTH_LIMIT = 64;


    /*getBestMove uses alphaBeta pruning to find a desirable move
        The function also randomly chooses from good moves in the first 5 moves of the game.
     */
    public static int [] getBestMove(BoardNode node, long timeAllowed){
        //timeAllowed is in seconds, multiply by a billion to get nanoseconds
        timeAllowed*=1000000000;

        BoardNode temp = new BoardNode(node);
        BoardNode best = new BoardNode(node);
        int [] result = new int[2];
        long initTime = System.nanoTime();
        int initialDepth=4;
        //temp is used so that we do not ever choose a move from a search that was incomplete
        while(((System.nanoTime() - initTime)) < timeAllowed  && initialDepth < DEPTH_LIMIT) {
            temp.children = new ArrayList<>();
            alphaBeta(temp, initialDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, true, initTime, timeAllowed);
            if((((System.nanoTime() - initTime)) > timeAllowed)){

                break;
            }
            best.children = temp.children;
            initialDepth++;
        }

        //sort the children of the root node
        Collections.sort(best.children);

        //chance to choose among good moves
        int j=0;
        int k=1;
        int rand;
        if(best.children.size()>1 && node.depth<5){
            while (k<best.children.size() && (best.children.get(j).score == best.children.get(k).score)) {
                rand = ThreadLocalRandom.current().nextInt(0, 2);
                if (rand == 0) {
                    j++;
                    k++;
                }
                else
                    break;
            }
        }
        result[0] = best.children.get(j).xRowPos;
        result[1] = best.children.get(j).xColPos;

        return result;
    }

    //AlphaBeta pruning with a sorted generation of children so that hopefully better moves are explored first
    public static int alphaBeta(BoardNode node, int depth, int alpha, int beta, boolean maximizingPlayer, long startTime, long timeAllowed) {
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
            node.createChildren();
            //explore the moves that appear good first
            Collections.sort(node.children);

            for(int i=0; i<node.children.size(); i++){
                val = Math.max(val, alphaBeta(node.children.get(i), depth-1, alpha, beta, false, startTime, timeAllowed));
                node.score = val;
                alpha = Math.max(alpha, val);
                if (beta <= alpha) {
                    break;
                }
                if (System.nanoTime() - startTime > timeAllowed){
                    break;
                }
            }
            return val;
        }
        //if minimizing player
        else{
            val = Integer.MAX_VALUE;
            node.createChildren();
            //explore the moves that appear good (to min) first
            Collections.sort(node.children);
            Collections.reverse(node.children);

            for(int i=0; i<node.children.size(); i++){
                val = Math.min(val, alphaBeta(node.children.get(i), depth-1, alpha, beta, true, startTime, timeAllowed));
                node.score = val;
                beta = Math.min(beta, val);
                if (beta <= alpha) {
                    break;
                }
                if (System.nanoTime() - startTime > timeAllowed){
                    break;
                }
            }
            return val;
        }
    }

}