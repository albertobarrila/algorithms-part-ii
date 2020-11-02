import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.LinkedList;
import java.util.List;

public class MoveToFront {
    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        List<Character> alphabet = createAlphabet();
        while (!BinaryStdIn.isEmpty()) {
            char input = BinaryStdIn.readChar();
            int position = alphabet.indexOf(input);
            BinaryStdOut.write((char) position, 8);
            char c = alphabet.get(position);
            alphabet.remove(position);
            alphabet.add(0, c);
        }
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        List<Character> alphabet = createAlphabet();
        while (!BinaryStdIn.isEmpty()) {
            int position = BinaryStdIn.readChar();
            char c = alphabet.get(position);
            BinaryStdOut.write(c, 8);
            alphabet.remove(position);
            alphabet.add(0, c);
        }
        BinaryStdOut.close();
    }


    private static List<Character> createAlphabet() {
        List<Character> alphabet = new LinkedList<>();
        for (int i = 0; i < 256; i++) {
            alphabet.add((char) i);
        }
        return alphabet;
    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
        if (args.length == 0)
            throw new IllegalArgumentException("usage: input '-' for encoding or '+' for decoding");

        if (args[0].equals("-"))
            encode();
        else if (args[0].equals("+"))
            decode();
        else
            throw new IllegalArgumentException("usage: input '-' for encoding or '+' for decoding");
    }

}
