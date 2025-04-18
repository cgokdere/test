import java.util.*;

public class Main {

	static char[][] board;
	static int row, col;
	static int knightRow, knightCol;// Knight's position
	static int goldRow, goldCol;// Gold's position

	static int[] moveRow = { -2, -1, 1, 2, 2, 1, -1, -2 };// Possible moves for a knight in chess
	static int[] moveCol = { 1, 2, 2, 1, -1, -2, -2, -1 };

	public void Path(String text) {
		row = Character.getNumericValue(text.charAt(0));
		col = Character.getNumericValue(text.charAt(1));
		board = new char[row][col];
		int index = 2;// Start reading board data from index 2

		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				board[i][j] = text.charAt(index++);
				if (board[i][j] == 'K') {
					knightRow = i;
					knightCol = j;
				} else if (board[i][j] == 'G') {
					goldRow = i;
					goldCol = j;
				}
			}
		}
	}

	public void findShortestPath() {
		boolean[][] visited = new boolean[row][col];
		int[][] edgeTo = new int[row][col]; // Stores the previous position for backtracking
		int[][] distTo = new int[row][col]; // Stores the shortest distance from the knight
		Queue<int[]> q = new LinkedList<>();

		q.add(new int[] { knightRow, knightCol });
		visited[knightRow][knightCol] = true;
		distTo[knightRow][knightCol] = 0;

		while (!q.isEmpty()) {
			int[] current = q.remove();
			int currentRow = current[0], currentCol = current[1];

			for (int i = 0; i < 8; i++) {
				int newRow = currentRow + moveRow[i];
				int newCol = currentCol + moveCol[i];

				if (newRow >= 0 && newRow < row && newCol >= 0 && newCol < col && !visited[newRow][newCol]
						&& board[newRow][newCol] != 'T') { // Checks if the new position is within bounds, not visited,
															// and not a 'T' (obstacle)
					visited[newRow][newCol] = true;
					edgeTo[newRow][newCol] = currentRow * col + currentCol;
					distTo[newRow][newCol] = distTo[currentRow][currentCol] + 1;// Update distance
					q.add(new int[] { newRow, newCol }); // Add new position to queue
				}
			}
		}

		if (visited[goldRow][goldCol]) {
			printPath(edgeTo);
		} else {
			System.out.println("No path to gold");
		}

	}

	public void printPath(int[][] edgeTo) {
		LinkedList<String> path = new LinkedList<>();
		int currentRow = goldRow;
		int currentCol = goldCol;
		// Trace back from the gold position to the knight's starting position
		while (currentRow != knightRow || currentCol != knightCol) {
			path.addFirst("c" + (currentRow + 1) + "," + (currentCol + 1) + "->");
			int previous = edgeTo[currentRow][currentCol];
			currentRow = previous / col;
			currentCol = previous % col;
		}
		path.addFirst("c" + (knightRow + 1) + "," + (knightCol + 1)); // add the knight's starting position to the path
		System.out.println(path); // Print the path
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String text = sc.next();
		sc.close();

		Main path = new Main();
		path.Path(text);
		path.findShortestPath();
	}

}
