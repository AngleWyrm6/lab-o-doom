// Package declaration (assuming you kept org.anglewyrm.labodoom)
package org.anglewyrm.labodoom;

import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import static org.anglewyrm.labodoom.ColorConstants.*;

/**
 * Represents a single attitude based on four boolean bits:
 * Interest, Favor, Confidence, and Fear.
 */
public class Attitude {

    private boolean interested;  // like
    private boolean favored;     // agree
    private boolean confident;   // sure
    private boolean fear;        // scary

    private static final int INTEREST_MASK = 0b1000; // Check the 4th bit
    private static final int FAVOR_MASK = 0b0100;    // Check the 3rd bit
    private static final int CONFIDENCE_MASK = 0b0010; // Check the 2nd bit
    private static final int FEAR_MASK = 0b0001;     // Check the 1st bit

    private static final Map<Integer, String> ATTITUDE_LABELS = new HashMap<>();

    static {
        // You'll need to define 16 labels here based on the 4 boolean states
        ATTITUDE_LABELS.put(0b1111, "Excited Acceptance");
        ATTITUDE_LABELS.put(0b1110, "Hopeful Confidence");
        ATTITUDE_LABELS.put(0b1101, "Curious Approval");
        ATTITUDE_LABELS.put(0b1100, "Confident Liking");
        ATTITUDE_LABELS.put(0b1011, "Fearful Interest");
        ATTITUDE_LABELS.put(0b1010, "Apprehensive Uncertainty");
        ATTITUDE_LABELS.put(0b1001, "Cautious Curiosity");
        ATTITUDE_LABELS.put(0b1000, "Dreadful Uncertainty");
        ATTITUDE_LABELS.put(0b0111, "Reluctant Agreement");
        ATTITUDE_LABELS.put(0b0110, "Tolerant Confidence");
        ATTITUDE_LABELS.put(0b0101, "Hesitant Curiosity");
        ATTITUDE_LABELS.put(0b0100, "Uncertain Approval");
        ATTITUDE_LABELS.put(0b0011, "Paralyzing Fear");
        ATTITUDE_LABELS.put(0b0010, "Nervous Insecurity");
        ATTITUDE_LABELS.put(0b0001, "Pure Fear");
        ATTITUDE_LABELS.put(0b0000, "Apathetic Resignation");
    }

    /**
     * Constructor to initialize the Attitude with four individual boolean values.
     *
     * @param _interested   True if Interested, False if Not_interested.
     * @param _favored      True for Yay, False for Nay (Favor).
     * @param _confident    True if Sure, False if Not_sure (Confidence).
     * @param _fear         True if Fearful, False if Not_fearful.
     */
    public Attitude(boolean _interested, boolean _favored, boolean _confident, boolean _fear) {
        this.interested = _interested;
        this.favored = _favored;
        this.confident = _confident;
        this.fear = _fear;
    }

    /**
     * Constructor to initialize the Attitude from a 4-bit integer code.
     *
     * @param bitCode An integer representing the 4-bit attitude (0-15).
     */
    public Attitude(int bitCode) {
        this.interested = (bitCode & INTEREST_MASK) != 0;
        this.favored = (bitCode & FAVOR_MASK) != 0;
        this.confident = (bitCode & CONFIDENCE_MASK) != 0;
        this.fear = (bitCode & FEAR_MASK) != 0;
    }

    /**
     * Gets the 4-bit integer representation of the attitude.
     *
     * @return The 4-bit attitude code.
     */
    public int getAttitudeCode() {
        int interestBit = interested ? 1 : 0;
        int favorBit = favored ? 1 : 0;
        int confidenceBit = confident ? 1 : 0;
        int fearBit = fear ? 1 : 0;
        return (interestBit << 3) | (favorBit << 2) | (confidenceBit << 1) | fearBit;
    }

    /**
     * Gets the Interest state.
     *
     * @return True if Interested, False if Not_interested.
     */
    public boolean isInterested() {
        return interested;
    }

    /**
     * Gets the Favor state.
     *
     * @return True if Favored, False if Not_favored.
     */
    public boolean isFavored() {
        return favored;
    }

    /**
     * Gets the Confidence state.
     *
     * @return True if Confident, False if Not_confident.
     */
    public boolean isConfident() {
        return confident;
    }

    /**
     * Gets the Fear state.
     *
     * @return True if Fearful, False if Not_fearful.
     */
    public boolean isFearful() {
        return fear;
    }

    /**
     * Sets the Interest state.
     *
     * @param _interested The new Interest state (true or false).
     */
    public void setInterested(boolean _interested) {
        this.interested = _interested;
    }

    /**
     * Sets the Favor state.
     *
     * @param _favored The new Favor state (true or false).
     */
    public void setFavored(boolean _favored) {
        this.favored = _favored;
    }

    /**
     * Sets the Confidence state.
     *
     * @param _confident The new Confidence state (true or false).
     */
    public void setConfident(boolean _confident) {
        this.confident = _confident;
    }

    /**
     * Sets the Fear state.
     *
     * @param _fear The new Fear state (true or false).
     */
    public void setFearful(boolean _fear) {
        this.fear = _fear;
    }

    /**
     * Gets a textual representation of the current attitude.
     *
     * @return A String representing the emotional attitude.
     */
    public String getAttitudeLabel() {
        return ATTITUDE_LABELS.getOrDefault(getAttitudeCode(), "error");
    }

    /**
     * Returns a colorized formatted string representing the interest, favor, confidence, and fear levels.
     * Green (light) for true, Red (dark) for false.
     *
     * @return A colorized String.
     */
    public String print() {
        String interestColor = interested ? LIGHT_GREEN : DARK_RED;
        String favorColor = favored ? LIGHT_GREEN : DARK_RED;
        String confidenceColor = confident ? LIGHT_GREEN : DARK_RED;
        String fearColor = fear ? LIGHT_GREEN : DARK_RED; // You might want a different color for fear

        return String.format(
                "Attitude: %s (Interest: %s%s%s, Favor: %s%s%s, Confidence: %s%s%s, Fear: %s%s%s)",
                getAttitudeLabel(),
                interestColor, interested, RESET_COLOR,
                favorColor, favored, RESET_COLOR,
                confidenceColor, confident, RESET_COLOR,
                fearColor, fear, RESET_COLOR
        );
    }

    /**
     * Overrides the toString() method to return a textual representation of the attitude.
     *
     * @return A String representing the attitude and its boolean states.
     */
    @Override
    public String toString() {
        return String.format(
                "Attitude: %s (Interest: %s, Favor: %s, Confidence: %s, Fear: %s)",
                getAttitudeLabel(),
                interested,
                favored,
                confident,
                fear
        );
    }

    public static void main(String[] args) {
        Random rng = new Random();
        int sample_count = 4;

        for (int i = 0; i < sample_count; i++) {
            int bit_code = rng.nextInt(16); // Generate a random integer from 0 to 15
            String binaryBitCode = String.format("%4s", Integer.toBinaryString(bit_code)).replace(' ', '0');
            System.out.println("Generated bit_code: " + binaryBitCode + " (" + bit_code + ")");

            Attitude attitude = new Attitude(bit_code); // Create Attitude directly from bit_code
            System.out.println(attitude.print()); // Use print() for colorized output
            System.out.println("  (Evaluated: " + attitude + ")"); // toString() for text composition
            System.out.println("  Code: " + attitude.getAttitudeCode());
            System.out.println();
        }
    }
}