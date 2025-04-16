package org.anglewyrm.labodoom;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import java.util.Arrays;
import static org.anglewyrm.labodoom.ColorConstants.*;
import static org.anglewyrm.labodoom.Constants.Component.*; // Static import

public class Attitude {

    private List<Component> activeComponents;
    private static final Component[] ALL_COMPONENTS = {INTERESTED, FAVORED, CONFIDENT, FEAR, MAGNITUDE};

    private static final Map<List<Component>, String> ATTITUDE_LABELS = new HashMap<>();

    static {
        ATTITUDE_LABELS.put(Arrays.asList(INTERESTED, FAVORED, CONFIDENT, FEAR, MAGNITUDE), "Passionate Enthusiasm");
        ATTITUDE_LABELS.put(Arrays.asList(INTERESTED, FAVORED, CONFIDENT, FEAR), "Eager Excitement");
        ATTITUDE_LABELS.put(Arrays.asList(INTERESTED, FAVORED, CONFIDENT, MAGNITUDE), "Emboldened Optimism");
        ATTITUDE_LABELS.put(Arrays.asList(INTERESTED, FAVORED, CONFIDENT), "Lighthearted Hopefulness");
        ATTITUDE_LABELS.put(Arrays.asList(INTERESTED, FAVORED, FEAR, MAGNITUDE), "Fascinated Admiration");
        ATTITUDE_LABELS.put(Arrays.asList(INTERESTED, FAVORED, FEAR), "Curious Attraction");
        ATTITUDE_LABELS.put(Arrays.asList(INTERESTED, FAVORED, MAGNITUDE), "Devoted Affection");
        ATTITUDE_LABELS.put(Arrays.asList(INTERESTED, FAVORED), "Comfortable Contentment");
        ATTITUDE_LABELS.put(Arrays.asList(INTERESTED, CONFIDENT, FEAR, MAGNITUDE), "Intense Observance");
        ATTITUDE_LABELS.put(Arrays.asList(INTERESTED, CONFIDENT, FEAR), "Cautious Interest");
        ATTITUDE_LABELS.put(Arrays.asList(INTERESTED, CONFIDENT, MAGNITUDE), "Anxious Anticipation");
        ATTITUDE_LABELS.put(Arrays.asList(INTERESTED, CONFIDENT), "Mild Worry");
        ATTITUDE_LABELS.put(Arrays.asList(INTERESTED, FEAR, MAGNITUDE), "Investigative Suspicion");
        ATTITUDE_LABELS.put(Arrays.asList(INTERESTED, FEAR), "Guarded Skepticism");
        ATTITUDE_LABELS.put(Arrays.asList(INTERESTED, MAGNITUDE), "Profound Unease");
        ATTITUDE_LABELS.put(Arrays.asList(INTERESTED), "Quiet Concern");
        ATTITUDE_LABELS.put(Arrays.asList(FAVORED, CONFIDENT, FEAR, MAGNITUDE), "Reluctant Acceptance");
        ATTITUDE_LABELS.put(Arrays.asList(FAVORED, CONFIDENT, FEAR), "Diplomatic Tolerance");
        ATTITUDE_LABELS.put(Arrays.asList(FAVORED, CONFIDENT, MAGNITUDE), "Cautious Optimism");
        ATTITUDE_LABELS.put(Arrays.asList(FAVORED, CONFIDENT), "Modest Satisfaction");
        ATTITUDE_LABELS.put(Arrays.asList(FAVORED, FEAR, MAGNITUDE), "Polite Interest");
        ATTITUDE_LABELS.put(Arrays.asList(FAVORED, FEAR), "Casual Acknowledgment");
        ATTITUDE_LABELS.put(Arrays.asList(FAVORED, MAGNITUDE), "Respectful Deference");
        ATTITUDE_LABELS.put(Arrays.asList(FAVORED), "Passive Compliance");
        ATTITUDE_LABELS.put(Arrays.asList(CONFIDENT, FEAR, MAGNITUDE), "Petrified Horror");
        ATTITUDE_LABELS.put(Arrays.asList(CONFIDENT, FEAR), "Quiet Dread");
        ATTITUDE_LABELS.put(Arrays.asList(CONFIDENT, MAGNITUDE), "Vulnerable Insecurity");
        ATTITUDE_LABELS.put(Arrays.asList(CONFIDENT), "Awkward Discomfort");
        ATTITUDE_LABELS.put(Arrays.asList(FEAR, MAGNITUDE), "Terrified");
        ATTITUDE_LABELS.put(Arrays.asList(FEAR), "Fearful");
        ATTITUDE_LABELS.put(Arrays.asList(MAGNITUDE), "Profound Resignation");
        ATTITUDE_LABELS.put(new ArrayList<>(), "Empty Indifference");
    }

    public Attitude(boolean interested, boolean favored, boolean confident, boolean fear, boolean magnitude) {
        this.activeComponents = new ArrayList<>();
        if (interested) activeComponents.add(INTERESTED);
        if (favored) activeComponents.add(FAVORED);
        if (confident) activeComponents.add(CONFIDENT);
        if (fear) activeComponents.add(FEAR);
        if (magnitude) activeComponents.add(MAGNITUDE);
    }

    public Attitude(List<Component> components) {
        this.activeComponents = new ArrayList<>(components);
    }

    public List<Component> getActiveComponents() {
        return new ArrayList<>(activeComponents);
    }

    public boolean isInterested() { return activeComponents.contains(INTERESTED); }
    public boolean isFavored() { return activeComponents.contains(FAVORED); }
    public boolean isConfident() { return activeComponents.contains(CONFIDENT); }
    public boolean isFearful() { return activeComponents.contains(FEAR); }
    public boolean isMagnitude() { return activeComponents.contains(MAGNITUDE); }

    public void setInterested(boolean interested) { updateComponent(INTERESTED, interested); }
    public void setFavored(boolean favored) { updateComponent(FAVORED, favored); }
    public void setConfident(boolean confident) { updateComponent(CONFIDENT, confident); }
    public void setFearful(boolean fear) { updateComponent(FEAR, fear); }
    public void setMagnitude(boolean magnitude) { updateComponent(MAGNITUDE, magnitude); }

    private void updateComponent(Component component, boolean active) {
        if (active && !activeComponents.contains(component)) {
            activeComponents.add(component);
        } else if (!active && activeComponents.contains(component)) {
            activeComponents.remove(component);
        }
    }

    public String getLabel() { // Renamed from getAttitudeLabel()
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

        Attitude fearConfidence = new Attitude(Arrays.asList(FEAR, CONFIDENT));
        System.out.println("Fear then Confidence: " + fearConfidence);
        System.out.println("  Label: " + fearConfidence.getLabel());

        Attitude confidenceFear = new Attitude(Arrays.asList(CONFIDENT, FEAR));
        System.out.println("Confidence then Fear: " + confidenceFear);
        System.out.println("  Label: " + confidenceFear.getLabel());

        Attitude enthusiastic = new Attitude(Arrays.asList(INTERESTED, FAVORED, CONFIDENT, FEAR, MAGNITUDE));
        System.out.println("Enthusiastic: " + enthusiastic);
        System.out.println("  Label: " + enthusiastic.getLabel());
    }
}