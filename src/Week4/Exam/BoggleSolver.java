/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

public class BoggleSolver {
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)

    private final TreeSet<String> dictionary;

    public BoggleSolver(String[] dictionary) {
        this.dictionary = new TreeSet<String>(Arrays.asList(dictionary));
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        if (board == null)
            throw new IllegalArgumentException("Board is null");

        TreeSet<String> words = new TreeSet<String>();
        for (int row = 0; row < board.rows(); row++) {
            for (int col = 0; col < board.cols(); col++) {
                String letters = String.valueOf(board.getLetter(row, col));
                if (letters.equals("Q"))
                    letters = "QU";
                boolean[][] usedLetter = new boolean[board.rows()][board.cols()];
                usedLetter[row][col] = true;
                depthFirstSearch(letters, words, board, row, col, usedLetter);
            }
        }
        return words;
    }

    private void depthFirstSearch(String letters, TreeSet<String> words, BoggleBoard board,
                                  int row,
                                  int col, boolean[][] usedLetter) {

        Set<String> prefix = dictionary.subSet(letters, letters + "ZZZ");
        if (prefix.isEmpty())
            return;

        if (scoreOf(letters) > 0) {
            words.add(letters);
        }

        for (int y = row - 1; y <= row + 1; y++) {
            for (int x = col - 1; x <= col + 1; x++) {
                if (y < 0 || x < 0 || y >= board.rows() || x >= board.cols() || usedLetter[y][x])
                    continue;
                usedLetter[y][x] = true;

                String value = String.valueOf(board.getLetter(y, x));
                if (value.equals("Q"))
                    value = "QU";
                depthFirstSearch(letters + value, words, board, y, x, usedLetter);
                usedLetter[y][x] = false;
            }
        }
    }


    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {

        if (word.length() < 3)
            return 0;

        if (!dictionary.contains(word))
            return 0;
        if (word.length() == 3 || word.length() == 4)
            return 1;

        if (word.length() == 5)
            return 2;

        if (word.length() == 6)
            return 3;

        if (word.length() == 7)
            return 5;

        return 11;

    }


    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}
