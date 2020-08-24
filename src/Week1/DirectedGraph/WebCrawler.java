package Week1.DirectedGraph;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdOut;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebCrawler {
    public static void main(String[] args) {
        Queue<String> queue = new Queue<String>();
        SET<String> discovered = new SET<String>();

        String root = "http://www.princeton.edu";
        queue.enqueue(root);
        discovered.add(root);

        while (!queue.isEmpty()) {
            String v = queue.dequeue();
            StdOut.println(v);
            In in = new In(v);
            String input = in.readAll();

            String regexp = "http://(\\w+\\.)*(\\w+)";
            Pattern pattern = Pattern.compile(regexp);
            Matcher matcher = pattern.matcher(input);

            while (matcher.find()) {
                String w = matcher.group();
                if (!discovered.contains(w)) {
                    queue.enqueue(w);
                    discovered.add(w);
                }
            }
        }

    }
}
