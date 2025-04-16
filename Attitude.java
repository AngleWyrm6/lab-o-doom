package org.anglewyrm.labodoom;

import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import static org.anglewyrm.labodoom.ColorConstants.*;

public class Attitude {

    private boolean interested;
    private boolean favored;
    private boolean confident;
    private boolean fear;
    private boolean magnitude;

    private static final int INTEREST_MASK = 0b10000;
    private static final int FAVOR_MASK = 0b01000;
    private static final int CONFIDENCE_MASK = 0b00100;
    private static final int FEAR_MASK = 0b00010;
    private static final int MAGNITUDE_MASK = 0b00001;

    private static final Map<Integer, String> ATTITUDE_LABELS = new HashMap<>();

    static {
        ATTITUDE_LABELS.put(0b11111, "Overwhelmingly Excited Acceptance");   // I+ F+ C+ Fe+ M+
        ATTITUDE_LABELS.put(0b11110, "Excited Acceptance");                // I+ F+ C+ Fe+ M-
        ATTITUDE_LABELS.put(0b11101, "Intensely Hopeful Confidence");      // I+ F+ C- Fe+ M+
        ATTITUDE_LABELS.put(0b11100, "Hopeful Confidence");                // I+ F+ C- Fe+ M-
        ATTITUDE_LABELS.put(0b11011, "Deeply Curious Approval");           // I+ F+ C+ Fe- M+
        ATTITUDE_LABELS.put(0b11010, "Curious Approval");                  // I+ F+ C+ Fe- M-
        ATTITUDE_LABELS.put(0b11001, "Profoundly Confident Liking");       // I+ F+ C- Fe- M+
        ATTITUDE_LABELS.put(0b11000, "Confident Liking");                // I+ F+ C- Fe- M-
        ATTITUDE_LABELS.put(0b10111, "Terrified Interest");                // I+ F- C+ Fe+ M+
        ATTITUDE_LABELS.put(0b10110, "Fearful Interest");                  // I+ F- C+ Fe+ M-
        ATTITUDE_LABELS.put(0b10101, "Extremely Apprehensive Uncertainty");// I+ F- C- Fe+ M+
        ATTITUDE_LABELS.put(0b10100, "Apprehensive Uncertainty");          // I+ F- C- Fe+ M-
        ATTITUDE_LABELS.put(0b10011, "Intensely Cautious Curiosity");      // I+ F- C+ Fe- M+
        ATTITUDE_LABELS.put(0b10010, "Cautious Curiosity");                // I+ F- C+ Fe- M-
        ATTITUDE_LABELS.put(0b10001, "Utterly Dreadful Uncertainty");      // I+ F- C- Fe- M+
        ATTITUDE_LABELS.put(0b10000, "Dreadful Uncertainty");              // I+ F- C- Fe- M-
        ATTITUDE_LABELS.put(0b01111, "Strongly Reluctant Agreement");      // I- F+ C+ Fe+ M+
        ATTITUDE_LABELS.put(0b01110, "Reluctant Agreement");               // I- F+ C+ Fe+ M-
        ATTITUDE_LABELS.put(0b01101, "Very Tolerant Confidence");         // I- F+ C- Fe+ M+
        ATTITUDE_LABELS.put(0b01100, "Tolerant Confidence");               // I- F+ C- Fe+ M-
        ATTITUDE_LABELS.put(0b01011, "Quite Hesitant Curiosity");          // I- F+ C+ Fe- M+
        ATTITUDE_LABELS.put(0b01010, "Hesitant Curiosity");                // I- F+ C+ Fe- M-
        ATTITUDE_LABELS.put(0b01001, "Performative Scripted Approval");    // I- F+ C- Fe- M+
        ATTITUDE_LABELS.put(0b01000, "Uncertain Approval");                // I- F+ C- Fe- M-
        ATTITUDE_LABELS.put(0b00111, "Completely Paralyzing Fear");       // I- F- C+ Fe+ M+
        ATTITUDE_LABELS.put(0b00110, "Paralyzing Fear");                   // I- F- C+ Fe- M-
        ATTITUDE_LABELS.put(0b00101, "Severely Nervous Insecurity");       // I- F- C- Fe+ M+
        ATTITUDE_LABELS.put(0b00100, "Nervous Insecurity");                // I- F- C- Fe+ M-
        ATTITUDE_LABELS.put(0b00011, "Terrified");                         // I- F- C+ Fe+ M+
        ATTITUDE_LABELS.put(0b00010, "Fearful");                           // I- F- C+ Fe- M-
        ATTITUDE_LABELS.put(0b00001, "Profound Apathetic Resignation");    // I- F- C- Fe- M+
        ATTITUDE_LABELS.put(0b00000, "Apathetic Resignation");             // I- F- C- Fe- M-
    }

    public Attitude(boolean _interested, boolean _favored, boolean _confident, boolean _fear, boolean _magnitude) {
        this.interested = _interested;
        this.favored = _favored;
        this.confident = _confident;
        this.fear = _fear;
        this.magnitude = _magnitude;
    }

    public Attitude(int bitCode) {
        this.interested = (bitCode & INTEREST_MASK) != 0;
        this.favored = (bitCode & FAVOR_MASK) != 0;
        this.confident = (bitCode & CONFIDENCE_MASK) != 0;
        this.fear = (bitCode & FEAR_MASK) != 0;
        this.magnitude = (bitCode & MAGNITUDE_MASK) != 0;
    }

    public int getAttitudeCode() {
        int interestBit = interested ? 1 : 0;
        int favorBit = favored ? 1 : 0;
        int confidenceBit = confident ? 1 : 0;
        int fearBit = fear ? 1 : 0;
        int magnitudeBit = magnitude ? 1 : 0;
        return (interestBit << 4) | (favorBit << 3) | (confidenceBit << 2) | (fearBit << 1) | magnitudeBit;
    }

    public boolean isInterested() { return interested; }
    public boolean isFavored() { return favored; }
    public boolean isConfident() { return confident; }
    public boolean isFearful() { return fear; }
    public boolean isMagnitude() { return magnitude; }

    public void setInterested(boolean _interested) { this.interested = _interested; }
    public void setFavored(boolean _favored) { this.favored = _favored; }
    public void setConfident(boolean _confident) { this.confident = _confident; }
    public void setFearful(boolean _fear) { this.fear = _fear; }
    public void setMagnitude(boolean _magnitude) { this.magnitude = _magnitude; }

    public String getAttitudeLabel() {
        return ATTITUDE_LABELS.getOrDefault(getAttitudeCode(), "error");
    }

    public String print() {
        String interestColor = interested ? LIGHT_GREEN : DARK_RED;
        String favorColor = favored ? LIGHT_GREEN : DARK_RED;
        String confidenceColor = confident ? LIGHT_GREEN : DARK_RED;
        String fearColor = fear ? LIGHT_GREEN : DARK_RED;
        String magnitudeColor = magnitude ? LIGHT_GREEN : DARK_RED;

        return String.format(
                "Attitude: %s (Interest: %s%s%s, Favor: %s%s%s, Confidence: %s%s%s, Fear: %s%s%s, Magnitude: %s%s%s)",
                getAttitudeLabel(),
                interestColor, interested, RESET_COLOR,
                favorColor, favored, RESET_COLOR,
                confidenceColor, confident, RESET_COLOR,
                fearColor, fear, RESET_COLOR,
                magnitudeColor, magnitude, RESET_COLOR
        );
    }

    @Override
    public String toString() {
        return String.format(
                "Attitude: %s (Interest: %s, Favor: %s, Confidence: %s, Fear: %s, Magnitude: %s)",
                getAttitudeLabel(),
                interested,
                favored,
                confident,
                fear,
                magnitude
        );
    }

    public static void main(String[] args) {
        Random rng = new Random();
        int sample_count = 4;

        for (int i = 0; i < sample_count; i++) {
            int bit_code = rng.nextInt(32);
            String binaryBitCode = String.format("%5s", Integer.toBinaryString(bit_code)).replace(' ', '0');
            System.out.println("Generated bit_code: " + binaryBitCode + " (" + bit_code + ")");

            Attitude attitude = new Attitude(bit_code);
            System.out.println(attitude.print());
            System.out.println("  (Evaluated: " + attitude + ")");
            System.out.println("  Code: " + attitude.getAttitudeCode());
            System.out.println();
        }
    }
}