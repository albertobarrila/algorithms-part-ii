import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public class SeamCarver {

    private Picture pic;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null)
            throw new IllegalArgumentException("Picture is null");

        pic = new Picture(picture);
    }

    // current picture
    public Picture picture() {
        return new Picture(pic);
    }

    // width of current picture
    public int width() {
        return pic.width();
    }

    // height of current picture
    public int height() {
        return pic.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || x >= pic.width())
            throw new IllegalArgumentException("x is invalid");
        if (y < 0 || y >= pic.height())
            throw new IllegalArgumentException("y is invalid");

        // se il pixel è sul bordo torno l'energia a 1000
        if (x == 0 || y == 0 || x == pic.width() - 1 || y == pic.height() - 1)
            return 1000;

        // se il pixel non è sul bordo calcolo l'energia
        Color up = pic.get(x, y - 1);
        Color down = pic.get(x, y + 1);
        Color left = pic.get(x + 1, y);
        Color right = pic.get(x - 1, y);

        return Math.sqrt(
                Math.pow(right.getRed() - left.getRed(), 2) +
                        Math.pow(right.getGreen() - left.getGreen(), 2) +
                        Math.pow(right.getBlue() - left.getBlue(), 2) +
                        Math.pow(down.getRed() - up.getRed(), 2) +
                        Math.pow(down.getGreen() - up.getGreen(), 2) +
                        Math.pow(down.getBlue() - up.getBlue(), 2)
        );

    }

    private void transposePicture() {
        // swap width and height for new image
        Picture transpose = new Picture(pic.height(), pic.width());
        for (int col = 0; col < pic.width(); col++) {
            for (int row = 0; row < pic.height(); row++) {
                transpose.setRGB(row, col, pic.getRGB(col, row));
            }
        }
        this.pic = transpose;
    }


    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        transposePicture();
        int[] res = findVerticalSeam();
        transposePicture();
        return res;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        double[][] weights = new double[pic.height()][pic.width()];
        double[][] energyLocal = new double[pic.height()][pic.width()];
        int[][] edges = new int[pic.height()][pic.width()];

        for (int row = 0; row < height(); row++) {
            for (int col = 0; col < width(); col++) {
                weights[row][col] = row == 0 ? 0 : Double.POSITIVE_INFINITY;
                edges[row][col] = -1;
                energyLocal[row][col] = energy(col, row);
            }
        }

        for (int row = 0; row < height() - 1; row++) {
            for (int col = 0; col < width(); col++) {
                int nextRow = row + 1;
                for (int nextCol = col - 1; nextCol <= col + 1; nextCol++) {
                    if (nextCol >= 0 && nextCol < width()) {
                        double weight = energyLocal[nextRow][nextCol];
                        if (weights[nextRow][nextCol] > weights[row][col] + weight) {
                            weights[nextRow][nextCol] = weights[row][col] + weight;
                            edges[nextRow][nextCol] = col;
                        }
                    }

                }
            }
        }

        double min = Double.POSITIVE_INFINITY;
        int minCol = 0;
        for (int y = 0; y < pic.width(); y++) {
            if (weights[pic.height() - 1][y] < min) {
                min = weights[pic.height() - 1][y];
                minCol = y;
            }
        }

        int[] res = new int[pic.height()];
        for (int x = pic.height() - 1; x >= 0; x--) {
            res[x] = minCol;
            minCol = edges[x][minCol];
        }
        return res;

    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null)
            throw new IllegalArgumentException("Seam is null");

        if (seam.length != pic.width())
            throw new IllegalArgumentException("Seam length is invalid");

        if (pic.height() <= 1)
            throw new IllegalArgumentException("width is invalid");

        if (!valid(seam))
            throw new IllegalArgumentException("seam is invalid");

        transposePicture();
        removeVerticalSeam(seam);
        transposePicture();
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null)
            throw new IllegalArgumentException("Seam is null");

        if (seam.length != pic.height())
            throw new IllegalArgumentException("Seam height is invalid");

        if (pic.width() <= 1)
            throw new IllegalArgumentException("width is invalid");

        if (!valid(seam))
            throw new IllegalArgumentException("seam is invalid");

        Picture trimmed = new Picture(pic.width() - 1, pic.height());
        for (int row = 0; row < pic.height(); row++) {
            int trimColumn = seam[row];
            for (int col = 0; col < trimColumn; col++) {
                trimmed.setRGB(col, row, pic.getRGB(col, row));
            }
            for (int col = trimColumn + 1; col < pic.width(); col++) {
                trimmed.setRGB(col - 1, row, pic.getRGB(col, row));
            }
        }
        this.pic = trimmed;
    }

    private boolean valid(int[] seam) {
        for (int i = 1; i < seam.length; i++)
            if (seam[i] < seam[i - 1] - 1 || seam[i] > seam[i - 1] + 1)
                return false;
        return true;
    }
}
