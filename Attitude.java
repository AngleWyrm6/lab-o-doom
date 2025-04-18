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
    private static final Component[] ALL_COMPONENTS = {INTERESTED, LIKE, SURE, FEAR, EXCITED};

    private static final Map<List<Component>, String> ATTITUDE_LABELS = new HashMap<>();

    static {
        ATTITUDE_LABELS.put(Arrays.asList(INTERESTED, LIKE, SURE, FEAR, EXCITED), "Passionate Enthusiasm");
        ATTITUDE_LABELS.put(Arrays.asList(INTERESTED, LIKE, SURE, FEAR), "Eager Excitement");
        ATTITUDE_LABELS.put(Arrays.asList(INTERESTED, LIKE, SURE, EXCITED), "Emboldened Optimism");
        ATTITUDE_LABELS.put(Arrays.asList(INTERESTED, LIKE, SURE), "Lighthearted Hopefulness");
        ATTITUDE_LABELS.put(Arrays.asList(INTERESTED, LIKE, FEAR, EXCITED), "Fascinated Admiration");
        ATTITUDE_LABELS.put(Arrays.asList(INTERESTED, LIKE, FEAR), "Curious Attraction");
        ATTITUDE_LABELS.put(Arrays.asList(INTERESTED, LIKE, EXCITED), "Devoted Affection");
        ATTITUDE_LABELS.put(Arrays.asList(INTERESTED, LIKE), "Comfortable Contentment");
        ATTITUDE_LABELS.put(Arrays.asList(INTERESTED, SURE, FEAR, EXCITED), "Intense Observance");
        ATTITUDE_LABELS.put(Arrays.asList(INTERESTED, SURE, FEAR), "Cautious Interest");
        ATTITUDE_LABELS.put(Arrays.asList(INTERESTED, SURE, EXCITED), "Anxious Anticipation");
        ATTITUDE_LABELS.put(Arrays.asList(INTERESTED, SURE), "Mild Worry");
        ATTITUDE_LABELS.put(Arrays.asList(INTERESTED, FEAR, EXCITED), "Investigative Suspicion");
        ATTITUDE_LABELS.put(Arrays.asList(INTERESTED, FEAR), "Guarded Skepticism");
        ATTITUDE_LABELS.put(Arrays.asList(INTERESTED, EXCITED), "Profound Unease");
        ATTITUDE_LABELS.put(Arrays.asList(INTERESTED), "Quiet Concern");
        ATTITUDE_LABELS.put(Arrays.asList(LIKE, SURE, FEAR, EXCITED), "Reluctant Acceptance");
        ATTITUDE_LABELS.put(Arrays.asList(LIKE, SURE, FEAR), "Diplomatic Tolerance");
        ATTITUDE_LABELS.put(Arrays.asList(LIKE, SURE, EXCITED), "Cautious Optimism");
        ATTITUDE_LABELS.put(Arrays.asList(LIKE, SURE), "Modest Satisfaction");
        ATTITUDE_LABELS.put(Arrays.asList(LIKE, FEAR, EXCITED), "Polite Interest");
        ATTITUDE_LABELS.put(Arrays.asList(LIKE, FEAR), "Casual Acknowledgment");
        ATTITUDE_LABELS.put(Arrays.asList(LIKE, EXCITED), "Respectful Deference");
        ATTITUDE_LABELS.put(Arrays.asList(LIKE), "Passive Compliance");
        ATTITUDE_LABELS.put(Arrays.asList(SURE, FEAR, EXCITED), "Petrified Horror");
        ATTITUDE_LABELS.put(Arrays.asList(SURE, FEAR), "Quiet Dread");
        ATTITUDE_LABELS.put(Arrays.asList(SURE, EXCITED), "Vulnerable Insecurity");
        ATTITUDE_LABELS.put(Arrays.asList(SURE), "Awkward Discomfort");
        ATTITUDE_LABELS.put(Arrays.asList(FEAR, EXCITED), "Pain");
        ATTITUDE_LABELS.put(Arrays.asList(FEAR), "Fearful");
        ATTITUDE_LABELS.put(Arrays.asList(EXCITED), "Restless");
        ATTITUDE_LABELS.put(new ArrayList<>(), "Empty Indifference");
    }

    public Attitude(boolean interested, boolean like, boolean sure, boolean fear, boolean excited) {
        this.activeComponents = new ArrayList<>();
        if (interested) activeComponents.add(INTERESTED);
        if (like) activeComponents.add(LIKE);
        if (sure) activeComponents.add(SURE);
        if (fear) activeComponents.add(FEAR);
        if (excited) activeComponents.add(EXCITED);
    }

    public Attitude(List<Component> components) {
        this.activeComponents = new ArrayList<>(components);
    }

    public List<Component> getActiveComponents() {
        return new ArrayList<>(activeComponents);
    }

    public boolean isPresent(Component component) {
        return activeComponents.contains(component);
    }

    public void insert(Component component) {
        if (!activeComponents.contains(component)) {
            activeComponents.add(component);
        }
    }

    public void remove(Component component) {
        activeComponents.remove(component);
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

    // New compare method
    public static Attitude compare(Attitude a, Attitude b) {
        List<Component> resultComponents = new ArrayList<>();
        for (Component component : ALL_COMPONENTS) {
            // OR operation: If the component is in either attitude, include it
            if (a.isPresent(component) || b.isPresent(component)) {
                resultComponents.add(component);
            }
        }
        return new Attitude(resultComponents);
    }

    public static void main(String[] args) {
        Random rng = new Random();
        int sample_count = 4;

        for (int i = 0; i < sample_count; i++) {
            int numComponents = rng.nextInt(ALL_COMPONENTS.length + 1);
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

        Attitude enthusiastic = new Attitude(Arrays.asList(INTERESTED, LIKE, SURE, FEAR, EXCITED));
        System.out.println("Enthusiastic: " + enthusiastic);
        System.out.println("  Label: " + enthusiastic.getLabel());

        printAttitudeLengthHistogram();

        // Test the compare method
        Attitude a = new Attitude(true, true, false, false, true); // Interested, Like, Excited
        Attitude b = new Attitude(false, true, true, true, false); // Like, Sure, Fear
        Attitude c = Attitude.compare(a, b);
        System.out.println("\n--- Compare Test (OR Combination) ---");
        System.out.println("A: " + a + " Label: " + a.getLabel());
        System.out.println("B: " + b + " Label: " + b.getLabel());
        System.out.println("C (A OR B): " + c + " Label: " + c.getLabel());
        System.out.println("---------------------------------------");

        //Demonstrate insert and remove
        Attitude d = new Attitude(false, false, false, false, false);
        System.out.println("\n--- insert and remove Test ---");
        System.out.println("Initial d: " + d + " Label: " + d.getLabel());
        d.insert(INTERESTED);
        d.insert(EXCITED);
        System.out.println("d after inserting Interested and Excited: " + d + " Label: " + d.getLabel());
        if (d.isPresent(INTERESTED)) {
            System.out.println("d is Interested");
        } else {
            System.out.println("d is not Interested");
        }
        d.remove(INTERESTED);
        System.out.println("d after removing Interested: " + d + " Label: " + d.getLabel());
        if (d.isPresent(INTERESTED)) {
            System.out.println("d is Interested");
        } else {
            System.out.println("d is not Interested");
        }
        System.out.println("------------------------------------------");

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
