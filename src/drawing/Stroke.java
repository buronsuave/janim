package drawing;

import java.util.ArrayList;

public class Stroke {
    private final ArrayList<int[]> scopeMatrix;
    private final int size;

    public Stroke(int size) {
        scopeMatrix = new ArrayList<>();
        this.size = size;
        buildScopeMatrix(size/2);
    }

    private void buildScopeMatrix(int radius) {
        // Midpoint approach
        int f = (int) (radius / Math.sqrt(2));

        // First half
        int pk = 1 - radius;
        int yk = radius;
        int xk = 0;
        addVerticalStrip(0, yk);
        for (; xk <= f; ++xk) {
            if (pk < 0) {
                pk += 2*xk + 3;
            } else {
                --yk;
                pk += 2*xk - 2*yk + 3;
            }
            addVerticalStrip(xk+1, yk);
            addVerticalStrip(-(xk+1), yk);
        }

        // Second half
        pk = 1 - radius;
        xk = radius;
        yk = 0;
        addHorizontalStrip(xk, 0, f);
        for (; yk <= f; ++yk) {
            if (pk < 0) {
                pk += 2*yk + 3;
            } else {
                --xk;
                pk += 2*yk - 2*xk + 3;
            }
            addHorizontalStrip(xk, yk+1, f);
            addHorizontalStrip(xk, -(yk+1), f);
        }
    }

    private void addVerticalStrip(int x, int y) {
        for (int k = y; k >= 0; --k) {
            scopeMatrix.add(new int[] {x, k});
            scopeMatrix.add(new int[] {x, -k});
        }
    }

    private void addHorizontalStrip(int x, int y, int xf) {
        for (int k = x; k >= xf; --k) {
            scopeMatrix.add(new int[] {k, y});
            scopeMatrix.add(new int[] {-k, y});
        }
    }

    public ArrayList<int[]> getScopeMatrix() {
        return scopeMatrix;
    }

    public int getSize(){
        return size;
    }
}
