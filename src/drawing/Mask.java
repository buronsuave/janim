package drawing;

import java.util.ArrayList;

public class Mask {
    private String maskString;

    public Mask(String maskString) {
        this.maskString = maskString;
    }

    public void scaleMask(int scalar) {
        if (scalar == 1) return;

        StringBuilder newMaskString = new StringBuilder();
        char state = maskString.charAt(0);
        for (char c : maskString.toCharArray()) {
            if (c != state) {
                // Add offset zeros
                newMaskString.append(String.valueOf('0').repeat(Math.max(0, 2 * (int) Math.sqrt(scalar))));
            }

            // Scale enabled
            // newMaskString.append(c);
            newMaskString.append(String.valueOf(c).repeat(Math.max(0, scalar)));
        }
        maskString = newMaskString.toString();
    }

    public void fixToIterations(int iterations) {
        // Check if there's at least one 0. Otherwise, is equivalent to the "1" mask
        if (!maskString.contains("0")) {
            maskString = "1";
            return;
        }

        // Check if there's at least one 1. Otherwise, is equivalent to the "0" mask
        if (!maskString.contains("1")) {
            maskString = "0";
            return;
        }

        StringBuilder backupMask = new StringBuilder(maskString);
        // Obtain scale factor
        while (maskString.length() < iterations) {
            maskString += backupMask.toString();
        }

        double scale = (double) iterations / maskString.length();

        // Compute starts and ends of all 1s sequences
        ArrayList<double[]> doubleSequences = new ArrayList<>();
        for (int i = 0; i < maskString.length(); ++i) {
            if (maskString.charAt(i) == '1') {
              int start = i;
              while (i < maskString.length() && maskString.charAt(i) == '1') {
                  ++i;
              }
              doubleSequences.add(new double[] {start, i-1});
            }
        }

        // Scale all sequences
        for (double[] sequence : doubleSequences) {
            sequence[0] *= scale;
            sequence[1] *= scale;
        }

        // Back to integers
        ArrayList<int[]> integerSequences = new ArrayList<>();
        for (double[] sequence : doubleSequences) {
            integerSequences.add(new int[] {
                    (int) sequence[0],
                    (int) sequence[1]
            });
        }

        // Make new string using integerSequences
        StringBuilder newMaskString = new StringBuilder();
        int i = 0;
        for (int[] sequence : integerSequences) {
            while (i < sequence[0]) {
                newMaskString.append('0');
                ++i;
            }
            while (i <= sequence[1]) {
                newMaskString.append('1');
                ++i;
            }

        }
        while(i < iterations) {
            newMaskString.append('0');
            ++i;
        }

        maskString = newMaskString.toString();
    }

    public boolean validate(int index) {
        return maskString.charAt(index % maskString.length()) == '1';
    }

    public int length() {
        return maskString.length();
    }

    public String getMaskString() {
        return maskString;
    }
}
