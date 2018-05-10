import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class AlphaBeta {
    public static int [] run(BoardNode node, char player, int alpha, int beta, boolean maxPlayer){
        int [] result = new int[2];

        if(maxPlayer) {
            ArrayList<BoardNode> moves;



            if (player == 'X') {
                moves = node.xCreateChildren();
                Collections.sort(moves);

                System.out.println();
                result[0] = moves.get(0).xRowPos;
                result[1] = moves.get(0).xColPos;
                return result;

            }
            else if (player == 'Y'){
                moves = node.oCreateChildren();

            }
        }
        else{
            ArrayList<BoardNode> moves;



            if (player == 'Y') {
                moves = node.oCreateChildren();
                Collections.sort(moves);

                System.out.println();
                result[0] = moves.get(moves.size()-1).oRowPos;
                result[1] = moves.get(moves.size()-1).oColPos;
                return result;

            }
            else if (player == 'X'){
                moves = node.oCreateChildren();

            }

        }
        return result;
    }
    public static int alphaBetaMax(BoardNode node){
        ArrayList<BoardNode> moves;
        if(node.xPossibleMoves() == 0){
            return -1000;
        }
        if(node.oPossibleMoves() == 0){
            return 1000;
        }
        moves = node.xCreateChildren();
        Collections.sort(moves);
        alphaBetaMin(moves.get(0));
        return 0;
    }

    public static int alphaBetaMin(BoardNode node){
        ArrayList<BoardNode> moves;
        if(node.xPossibleMoves() == 0){
            return -1000;
        }
        if(node.oPossibleMoves() == 0){
            return 1000;
        }
        moves = node.xCreateChildren();
        Collections.sort(moves);
        alphaBetaMax(moves.get(moves.size()-1));
        return 0;
    }
}
