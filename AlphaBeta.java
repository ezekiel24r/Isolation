import java.util.ArrayList;

public class AlphaBeta {
    public static void run(BoardNode node, char player, int alpha, int beta, boolean maxPlayer){
        if(player == 'X') {
            ArrayList<String> moves = node.xListPossibleMoves();
        }
    }
}
