import java.util.*;

public class Main {
    static int pX, pY, pZ;
    static int gX, gY, gZ;
    static int[][][] grid;
    static boolean[][][] seen;
    static int[][] moveDirs = {{1, 0, 0}, {-1, 0, 0}, {0, 1, 0}, {0, -1, 0}, {0, 0, 1}, {0, 0, -1}};


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        pX = in.nextInt();
        pY = in.nextInt();
        pZ = in.nextInt();
        gX = in.nextInt();
        gY = in.nextInt();
        gZ = in.nextInt();

        grid = new int[gZ][gY][gX];
        for (int z = 0; z < gZ; z++) {
            for (int y = 0; y < gY; y++) {
                for (int x = 0; x < gX; x++) {
                    grid[z][y][x] = in.nextInt();
                }
            }
        }

        printOutput();
    }

    static void printOutput(){
        List<int[]> answer = BFS();
        if (answer == null) {
            System.out.println("0");
        } else {
            System.out.println("1");
            for (int[] p : answer) {
                System.out.println((p[0] + 1) + " " + (p[1] + 1) + " " + (p[2] + 1));
            }
        }
    }

    static class Position {
        int x, y, z;
        List<int[]> trail;

        Position(int x, int y, int z, List<int[]> previous) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.trail = new ArrayList<>();
            if (previous != null) {
                this.trail.addAll(previous);
            }
            this.trail.add(new int[]{x, y, z});
        }
    }


    static boolean checkFit(int x, int y, int z) {
        if (x + pX > gX || y + pY > gY || z + pZ > gZ) {
            return false;
        }
        for (int dz = 0; dz < pZ; dz++) {

            for (int dy = 0; dy < pY; dy++) {

                for (int dx = 0; dx < pX; dx++) {
                    if (grid[z + dz][y + dy][x + dx] == 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    static List<int[]> BFS() {
        Queue<Position> q = new LinkedList<>();
        seen = new boolean[gZ][gY][gX];

        for (int x = 0; x <= gX - pX; x++) {

            for (int y = 0; y <= gY - pY; y++) {
                if (checkFit(x, y, 0)) {
                    q.add(new Position(x, y, 0, null));
                    seen[0][y][x] = true;
                }
            }
        }


        while (!q.isEmpty()) {
            Position cur = q.poll();
            if (cur.z + pZ == gZ) {
                return cur.trail;
            }

            for (int[] move : moveDirs) {
                int nx = cur.x + move[0];
                int ny = cur.y + move[1];
                int nz = cur.z + move[2];
                if (nx >= 0 && ny >= 0 && nz >= 0 && nx < gX && ny < gY && nz < gZ && !seen[nz][ny][nx] && checkFit(nx, ny, nz)) {
                    seen[nz][ny][nx] = true;
                    q.add(new Position(nx, ny, nz, cur.trail));
                }
            }
        }

        return null;
    }


}
