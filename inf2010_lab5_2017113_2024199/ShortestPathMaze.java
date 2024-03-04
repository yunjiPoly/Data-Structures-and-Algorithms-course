// matricule: 2024199 et 2017113
import java.util.*;
import java.util.stream.Collectors;
import java.lang.*; 

enum MazeTile {
    Floor,
    Wall,
    Exit;

    @Override
    public String toString() {
        switch (this) {
            case Exit: return "* ";
            case Floor: return "_ ";
            case Wall: return "# ";
            default: return "";
        }
    }
}

public class Solution {
    private static Scanner scanner = new Scanner(System.in);
    private static List<List<MazeTile>> board = new ArrayList<>();
    private static Boolean[][] visitedBoard;
    private static int M = 0;
    private static int N = 0;
    private static Case caseStart;
    private static Case caseEnd;
    
    public static class Case {
        private int x;
        private int y;
        private int distance;
        public Case(int x, int y, int distance) {
            this.x = x;
            this.y = y;
            this.distance = distance;
        }
    }
    
    public static void main(String[] args) {
        int x = 0, y= 0;
        int[] coordStartEnd = new int[4];
        MazeTile[] mazeTiles = MazeTile.values();
        
        while (scanner.hasNextLine()) {
            String listString = scanner.nextLine();
            List<MazeTile> row = Arrays
                .stream(listString.split(" +"))
                .map(Integer::parseInt)
                .map(i -> mazeTiles[i])
                .collect(Collectors.toList());
            if (row.contains(MazeTile.Exit)) {
                coordStartEnd[x++] = row.indexOf(MazeTile.Exit);
                coordStartEnd[x++] = y;
            }
            board.add(row);
            y++;
        }
        caseStart = new Case(coordStartEnd[0], coordStartEnd[1], 0);
        caseEnd = new Case(coordStartEnd[2], coordStartEnd[3], Integer.MAX_VALUE);
        N = board.size();
        M = board.get(1).size();
        visitedBoard = new Boolean[N][M];
        for (int i = 0; i < N; i++){
            Arrays.fill(visitedBoard[i], Boolean.FALSE);
        }
        System.out.println(findShortestPath());
    }  

    private static boolean notVisited(int x, int y)
    {
        if (x < M && y < N && x >= 0 && y >= 0) {
            if (board.get(y).get(x) != MazeTile.Wall) {
                if (visitedBoard[y][x] == false) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int findShortestPath()
    {
        Deque<Case> q = new LinkedList<Case>();
        q.addLast(caseStart);
        while (!q.isEmpty()) {  
            if (visitedBoard[q.getFirst().y][q.getFirst().x] == false){
                if (q.getFirst().x == caseEnd.x && q.getFirst().y == caseEnd.y) {
                    return q.getFirst().distance;
                }
                visitedBoard[q.getFirst().y][q.getFirst().x] = true;
                if (notVisited(q.getFirst().x + 1, q.getFirst().y)) {
                    q.addLast(new Case(q.getFirst().x + 1, q.getFirst().y, q.getFirst().distance + 1));
                }
                if (notVisited(q.getFirst().x - 1, q.getFirst().y)) {
                    q.addLast(new Case(q.getFirst().x - 1, q.getFirst().y, q.getFirst().distance + 1));
                }
                if (notVisited(q.getFirst().x, q.getFirst().y + 1)) {
                    q.addLast(new Case(q.getFirst().x, q.getFirst().y + 1, q.getFirst().distance + 1));
                }
                if (notVisited(q.getFirst().x, q.getFirst().y - 1)) {
                    q.addLast(new Case(q.getFirst().x, q.getFirst().y - 1, q.getFirst().distance + 1));
                }
           }
            q.removeFirst();
        }
        return 0;
    }
}
