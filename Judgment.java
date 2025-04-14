// Package declaration
package org.anglewyrm.labodoom;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import static org.anglewyrm.labodoom.ColorConstants.*;

/**
 * Represents a single emotional judgment based on three boolean bits:
 * Interest, Favor, and Confidence.
 */
public class Judgment {

    private boolean interested;
    private boolean favored;
    private boolean confident;

    private static final int INTEREST_MASK = 0b100; // Check the 3rd bit
    private static final int FAVOR_MASK = 0b010;    // Check the 2nd bit
    private static final int CONFIDENCE_MASK = 0b001; // Check the 1st bit

    private static final Map<Integer, String> JUDGMENT_LABELS = new HashMap<>();

    static {
        JUDGMENT_LABELS.put(0b111, "yay");
        JUDGMENT_LABELS.put(0b110, "hope");
        JUDGMENT_LABELS.put(0b101, "not");
        JUDGMENT_LABELS.put(0b100, "dread");
        JUDGMENT_LABELS.put(0b011, "whatevs");
        JUDGMENT_LABELS.put(0b010, "maybe");
        JUDGMENT_LABELS.put(0b001, "nah");
        JUDGMENT_LABELS.put(0b000, "meh");
    }

    /**
     * Constructor to initialize the Judgment with three individual boolean values.
     *
     * @param _interested   True if Interested, False if Not_interested.
     * @param _favored      True for Yay, False for Nay (Favor).
     * @param _confident    True if Sure, False if Not_sure (Confidence).
     */
    public Judgment(boolean _interested, boolean _favored, boolean _confident) {
        this.interested = _interested;
        this.favored = _favored;
        this.confident = _confident;
    }

    /**
     * Constructor to initialize the Judgment from a 3-bit integer code.
     *
     * @param bitCode An integer representing the 3-bit judgment (0-7).
     */
    public Judgment(int bitCode) {
        this.interested = (bitCode & INTEREST_MASK) != 0;
        this.favored = (bitCode & FAVOR_MASK) != 0;
        this.confident = (bitCode & CONFIDENCE_MASK) != 0;
    }

    /**
     * Gets the 3-bit integer representation of the judgment.
     *
     * @return The 3-bit judgment code.
     */
    public int getJudgmentCode() {
        int interestBit = interested ? 1 : 0;
        int favorBit = favored ? 1 : 0;
        int confidenceBit = confident ? 1 : 0;
        return (interestBit << 2) | (favorBit << 1) | confidenceBit;
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
     * Gets a textual representation of the current judgment.
     *
     * @return A String representing the emotional judgment (e.g., "yay", "hope", "meh"),
     * or "error" if the internal state is logically invalid.
     */
    public String getJudgmentLabel() {
        return JUDGMENT_LABELS.getOrDefault(getJudgmentCode(), "error");
    }

    /**
     * Returns a colorized formatted string representing the interest, favor, and confidence levels.
     * Green (light) for true, Red (dark) for false.
     *
     * @return A colorized String.
     */
    public String print() {
        String interestColor = interested ? LIGHT_GREEN : DARK_RED;
        String favorColor = favored ? LIGHT_GREEN : DARK_RED;
        String confidenceColor = confident ? LIGHT_GREEN : DARK_RED;

        return String.format(
                "Judgment: %s (Interest: %s%s%s, Favor: %s%s%s, Confidence: %s%s%s)",
                getJudgmentLabel(),
                interestColor, interested, RESET_COLOR,
                favorColor, favored, RESET_COLOR,
                confidenceColor, confident, RESET_COLOR
        );
    }

    /**
     * Overrides the toString() method to return a textual representation of the judgment.
     *
     * @return A String representing the judgment and its boolean states.
     */
    @Override
    public String toString() {
        return String.format(
                "Judgment: %s (Interest: %s, Favor: %s, Confidence: %s)",
                getJudgmentLabel(),
                interested,
                favored,
                confident
        );
    }

    public static void main(String[] args) {
        Random rng = new Random();
        int sample_count = 4;

        for (int i = 0; i < sample_count; i++) {
            int bit_code = rng.nextInt(8); // Generate a random integer from 0 to 7
            String binaryBitCode = String.format("%3s", Integer.toBinaryString(bit_code)).replace(' ', '0');
            System.out.println("Generated bit_code: " + binaryBitCode + " (" + bit_code + ")");

            Judgment judgment = new Judgment(bit_code); // Create Judgment directly from bit_code
            System.out.println(judgment.print()); // Use print() for colorized output
            System.out.println("  (Evaluated: " + judgment + ")"); // toString() for text composition
            System.out.println("  Code: " + judgment.getJudgmentCode());
            System.out.println();
        }
    }
}