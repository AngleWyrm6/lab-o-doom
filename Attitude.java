package org.anglewyrm.labodoom;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import java.util.Arrays;
import static org.anglewyrm.labodoom.ColorConstants.*;
import static org.anglewyrm.labodoom.Constants.Component.*;

public class Attitude {

    private List<Component> activeComponents;
    private static final Component[] ALL_COMPONENTS = {INTERESTED, LIKE, CONFIDENT, FEAR, EXCITED}; // Updated

    private static final Map<List<Component>, String> ATTITUDE_LABELS = new HashMap<>();

    static {
        ATTITUDE_LABELS.put(Arrays.asList(INTERESTED, LIKE, CONFIDENT, FEAR, EXCITED), "Passionate Enthusiasm"); // Updated
        ATTITUDE_LABELS.put(Arrays.asList(INTERESTED, LIKE, CONFIDENT, FEAR), "Eager Excitement");
        ATTITUDE_LABELS.put(Arrays.asList(INTERESTED, LIKE, CONFIDENT, EXCITED), "Emboldened Optimism"); // Updated
        ATTITUDE_LABELS.put(Arrays.asList(INTERESTED, LIKE, CONFIDENT), "Lighthearted Hopefulness");
        ATTITUDE_LABELS.put(Arrays.asList(INTERESTED, LIKE, FEAR, EXCITED), "Fascinated Admiration"); // Updated
        ATTITUDE_LABELS.put(Arrays.asList(INTERESTED, LIKE, FEAR), "Curious Attraction");
        ATTITUDE_LABELS.put(Arrays.asList(INTERESTED, LIKE, EXCITED), "Devoted Affection"); // Updated
        ATTITUDE_LABELS.put(Arrays.asList(INTERESTED, LIKE), "Comfortable Contentment");
        ATTITUDE_LABELS.put(Arrays.asList(INTERESTED, CONFIDENT, FEAR, EXCITED), "Intense Observance"); // Updated
        ATTITUDE_LABELS.put(Arrays.asList(INTERESTED, CONFIDENT, FEAR), "Cautious Interest");
        ATTITUDE_LABELS.put(Arrays.asList(INTERESTED, CONFIDENT, EXCITED), "Anxious Anticipation"); // Updated
        ATTITUDE_LABELS.put(Arrays.asList(INTERESTED, CONFIDENT), "Mild Worry");
        ATTITUDE_LABELS.put(Arrays.asList(INTERESTED, FEAR, EXCITED), "Investigative Suspicion"); // Updated
        ATTITUDE_LABELS.put(Arrays.asList(INTERESTED, FEAR), "Guarded Skepticism");
        ATTITUDE_LABELS.put(Arrays.asList(INTERESTED, EXCITED), "Profound Unease"); // Updated
        ATTITUDE_LABELS.put(Arrays.asList(INTERESTED), "Quiet Concern");
        ATTITUDE_LABELS.put(Arrays.asList(LIKE, CONFIDENT, FEAR, EXCITED), "Reluctant Acceptance"); // Updated
        ATTITUDE_LABELS.put(Arrays.asList(LIKE, CONFIDENT, FEAR), "Diplomatic Tolerance");
        ATTITUDE_LABELS.put(Arrays.asList(LIKE, CONFIDENT, EXCITED), "Cautious Optimism"); // Updated
        ATTITUDE_LABELS.put(Arrays.asList(LIKE, CONFIDENT), "Modest Satisfaction");
        ATTITUDE_LABELS.put(Arrays.asList(LIKE, FEAR, EXCITED), "Polite Interest"); // Updated
        ATTITUDE_LABELS.put(Arrays.asList(LIKE, FEAR), "Casual Acknowledgment");
        ATTITUDE_LABELS.put(Arrays.asList(LIKE, EXCITED), "Respectful Deference"); // Updated
        ATTITUDE_LABELS.put(Arrays.asList(LIKE), "Passive Compliance");
        ATTITUDE_LABELS.put(Arrays.asList(CONFIDENT, FEAR, EXCITED), "Petrified Horror"); // Updated
        ATTITUDE_LABELS.put(Arrays.asList(CONFIDENT, FEAR), "Quiet Dread");
        ATTITUDE_LABELS.put(Arrays.asList(CONFIDENT, EXCITED), "Vulnerable Insecurity"); // Updated
        ATTITUDE_LABELS.put(Arrays.asList(CONFIDENT), "Awkward Discomfort");
        ATTITUDE_LABELS.put(Arrays.asList(FEAR, EXCITED), "Pain"); // Re-interpreting (fear, excited) as pain
        ATTITUDE_LABELS.put(Arrays.asList(FEAR), "Fearful");
        ATTITUDE_LABELS.put(Arrays.asList(EXCITED), "Profound Resignation"); // Updated
        ATTITUDE_LABELS.put(new ArrayList<>(), "Empty Indifference");
    }

    public Attitude(boolean interested, boolean like, boolean confident, boolean fear, boolean excited) { // Updated
        this.activeComponents = new ArrayList<>();
        if (interested) activeComponents.add(INTERESTED);
        if (like) activeComponents.add(LIKE);
        if (confident) activeComponents.add(CONFIDENT);
        if (fear) activeComponents.add(FEAR);
        if (excited) activeComponents.add(EXCITED); // Updated
    }

    public Attitude(List<Component> components) {
        this.activeComponents = new ArrayList<>(components);
    }

    public List<Component> getActiveComponents() {
        return new ArrayList<>(activeComponents);
    }

    public boolean isInterested() {
        return activeComponents.contains(INTERESTED);
    }

    public boolean isLike() {
        return activeComponents.contains(LIKE);
    }

    public boolean isConfident() {
        return activeComponents.contains(CONFIDENT);
    }

    public boolean isFearful() {
        return activeComponents.contains(FEAR);
    }

    public boolean isExcited() { // Updated getter
        return activeComponents.contains(EXCITED); // Updated
    }

    public void setInterested(boolean interested) {
        updateComponent(INTERESTED, interested);
    }

    public void setLike(boolean like) {
        updateComponent(LIKE, like);
    }

    public void setConfident(boolean confident) {
        updateComponent(CONFIDENT, confident);
    }

    public void setFearful(boolean fear) {
        updateComponent(FEAR, fear);
    }

    public void setExcited(boolean excited) { // Updated setter
        updateComponent(EXCITED, excited); // Updated
    }

    private void updateComponent(Component component, boolean active) {
        if (active && !activeComponents.contains(component)) {
            activeComponents.add(component);
        } else if (!active && activeComponents.contains(component)) {
            activeComponents.remove(component);
        }
    }

    public String getLabel() {
        return ATTITUDE_LABELS.getOrDefault(activeComponents, "error");
    }

    public String print() {
        StringBuilder sb = new StringBuilder("Attitude: ").append(getLabel()).append(" (");
        for (int i = 0; i < ALL_COMPONENTS.length; i++) {
            Component component = ALL_COMPONENTS[i];
            String color = activeComponents.contains(component) ? LIGHT_GREEN : DARK_RED;
            sb.append(component.getLabel()).append(": ").append(color).append(activeComponents.contains(component)).append(RESET_COLOR);
            if (i < ALL_COMPONENTS.length - 1) {
                sb.append(", ");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public String toString() {
        return String.format("Attitude: %s (Components: %s)", getLabel(), activeComponents);
    }

    public static void main(String[] args) {
        Random rng = new Random();
        int sample_count = 4;

        for (int i = 0; i < sample_count; i++) {
            int numComponents = rng.nextInt(ALL_COMPONENTS.length + 1);
            List<Component> randomComponents = new ArrayList<>();
            List<Component> availableComponents = new ArrayList<>(Arrays.asList(ALL_COMPONENTS));
            for (int j = 0; j < numComponents; j++) {
                int randomIndex = rng.nextInt(availableComponents.size());
                randomComponents.add(availableComponents.remove(randomIndex));
            }

            Attitude attitude = new Attitude(randomComponents);
            System.out.println("Generated components: " + attitude.getActiveComponents());
            System.out.println(attitude.print());
            System.out.println("  (Evaluated: " + attitude + ")");
            System.out.println();
        }

        Attitude fearExcited = new Attitude(Arrays.asList(FEAR, EXCITED));
        System.out.println("Fear then Excited: " + fearExcited);
        System.out.println("  Label: " + fearExcited.getLabel());

        Attitude likeExcited = new Attitude(Arrays.asList(LIKE, EXCITED));
        System.out.println("Like then Excited: " + likeExcited);
        System.out.println("  Label: " + likeExcited.getLabel());

        Attitude enthusiastic = new Attitude(Arrays.asList(INTERESTED, LIKE, CONFIDENT, FEAR, EXCITED));
        System.out.println("Enthusiastic: " + enthusiastic);
        System.out.println("  Label: " + enthusiastic.getLabel());

        printAttitudeLengthHistogram();
    }

    public static void printAttitudeLengthHistogram() {
        Map<Integer, Integer> lengthCounts = new HashMap<>();
        for (List<Component> components : ATTITUDE_LABELS.keySet()) {
            int length = components.size();
            lengthCounts.put(length, lengthCounts.getOrDefault(length, 0) + 1);
        }

        System.out.println("\n--- Attitude Length Histogram ---");
        for (int i = 0; i <= ALL_COMPONENTS.length; i++) {
            int count = lengthCounts.getOrDefault(i, 0);
            System.out.printf("%d components: %d%n", i, count);
        }
        System.out.println("-------------------------------");
    }
}