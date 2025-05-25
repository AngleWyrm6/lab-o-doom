package org.anglewyrm.labodoom;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import java.util.Arrays;
import java.util.Objects;
import static org.anglewyrm.labodoom.ColorConstants.*;
import static org.anglewyrm.labodoom.Constants.Component.*;

public class Attitude {
    /* Attitude class
     * FAQ:
     * certainty/confidence is represented by {SURE}, {SURE,INTENSE}
     * attitudes are a tiered jewel:
     * Tier-1: the simple 1-bit states, such as LIKE or FEAR
     * Tier-2: 2-bit composite states, such as {FEAR, INTENSE}=pain
     * Tier-3: 3-bit composite states, such as {LIKE, INTERESTED, INTENSE}=love, etc.
     * attitudes are thus collections rather than individual states
     *
     * Only Boolean and Integer values are used
     * there are no floats (Reals)
     * any such aggregation is a meta-task outside the scope of the class
     *
     * integral()/derivative() operations:
     * For those with a math background, these functions bring to mind usages in calculus,
     * or more specifically a Boolean calculus. For those without such training,
     * differenceFrom() serves as a form of subtraction that highlights it's inherent first-person perspective.
     * a - b ≠ b - a
     */

    private Map<Component, Boolean> componentStates;
    private static final Component[] ALL_COMPONENTS = {INTERESTED, LIKE, SURE, FEAR, INTENSE};

    private static final Map<Map<Component, Boolean>, String> ATTITUDE_LABELS = new HashMap<>();

    static {
        // Helper method to create state maps more cleanly
        Map<Component, Boolean> state;

        // All Five Components Active
        state = createState(true, true, true, true, true); // {INTERESTED, LIKE, SURE, FEAR, INTENSE}
        ATTITUDE_LABELS.put(state, "Passionate Obsession"); // Maximum engagement with conflicted certainty

        // Four Components Active
        state = createState(true, true, true, false, true); // {INTERESTED, LIKE, SURE, INTENSE}
        ATTITUDE_LABELS.put(state, "Burning Enthusiasm"); // Confident positive intensity without fear

        state = createState(true, true, false, true, true); // {INTERESTED, LIKE, FEAR, INTENSE}
        ATTITUDE_LABELS.put(state, "Thrilling Fascination"); // Intense attraction to something dangerous/unknown

        state = createState(true, false, true, true, true); // {INTERESTED, SURE, FEAR, INTENSE}
        ATTITUDE_LABELS.put(state, "Vindictive Rage"); // Fascinated by downfall, certain they deserve it

        state = createState(false, true, true, true, true); // {LIKE, SURE, FEAR, INTENSE}
        ATTITUDE_LABELS.put(state, "Conflicted Devotion"); // Loving something you're certain will hurt you

        // Three Components Active
        state = createState(true, true, true, false, false); // {INTERESTED, LIKE, SURE}
        ATTITUDE_LABELS.put(state, "Confident Affection"); // Comfortable positive certainty

        state = createState(true, true, false, true, false); // {INTERESTED, LIKE, FEAR}
        ATTITUDE_LABELS.put(state, "Nervous Attraction"); // Drawn to something that makes you uneasy

        state = createState(true, true, false, false, true); // {INTERESTED, LIKE, INTENSE}
        ATTITUDE_LABELS.put(state, "Passionate Fondness"); // Deep caring without certainty or fear

        state = createState(true, false, true, true, false); // {INTERESTED, SURE, FEAR}
        ATTITUDE_LABELS.put(state, "Anxious Vigilance"); // Certain something concerning is happening

        state = createState(true, false, true, false, true); // {INTERESTED, SURE, INTENSE}
        ATTITUDE_LABELS.put(state, "Focused Conviction"); // Intense certainty about something engaging

        state = createState(true, false, false, true, true); // {INTERESTED, FEAR, INTENSE}
        ATTITUDE_LABELS.put(state, "Morbid Curiosity"); // Compulsive interest in something frightening

        state = createState(false, true, true, true, false); // {LIKE, SURE, FEAR}
        ATTITUDE_LABELS.put(state, "Bittersweet Acceptance"); // Loving something you know is problematic

        state = createState(false, true, true, false, true); // {LIKE, SURE, INTENSE}
        ATTITUDE_LABELS.put(state, "Fierce Loyalty"); // Intense certainty in your positive feelings

        state = createState(false, true, false, true, true); // {LIKE, FEAR, INTENSE}
        ATTITUDE_LABELS.put(state, "Protective Worry"); // Intense caring mixed with concern

        state = createState(false, false, true, true, true); // {SURE, FEAR, INTENSE}
        ATTITUDE_LABELS.put(state, "Burning Hatred"); // Intense, certain loathing / Agonizing dread

        // Two Components Active
        state = createState(true, true, false, false, false); // {INTERESTED, LIKE}
        ATTITUDE_LABELS.put(state, "Casual Fondness"); // Simple positive engagement

        state = createState(true, false, true, false, false); // {INTERESTED, SURE}
        ATTITUDE_LABELS.put(state, "Cold Calculation"); // Clinical interest - chess master studying opponent

        state = createState(true, false, false, true, false); // {INTERESTED, FEAR}
        ATTITUDE_LABELS.put(state, "Cautious Curiosity"); // Wary but engaged

        state = createState(true, false, false, false, true); // {INTERESTED, INTENSE}
        ATTITUDE_LABELS.put(state, "Burning Questions"); // Overwhelming need to understand

        state = createState(false, true, true, false, false); // {LIKE, SURE}
        ATTITUDE_LABELS.put(state, "Quiet Contentment"); // Peaceful positive certainty

        state = createState(false, true, false, true, false); // {LIKE, FEAR}
        ATTITUDE_LABELS.put(state, "Tender Concern"); // Caring mixed with worry

        state = createState(false, true, false, false, true); // {LIKE, INTENSE}
        ATTITUDE_LABELS.put(state, "Deep Affection"); // Strong positive feelings

        state = createState(false, false, true, true, false); // {SURE, FEAR}
        ATTITUDE_LABELS.put(state, "Grim Certainty"); // Confident about something unwelcome

        state = createState(false, false, true, false, true); // {SURE, INTENSE}
        ATTITUDE_LABELS.put(state, "Cold Fury"); // Overwhelming confidence / Quiet certain anger

        state = createState(false, false, false, true, true); // {FEAR, INTENSE}
        ATTITUDE_LABELS.put(state, "Raw Terror"); // Pure intense distress / Acute pain

        // Single Components
        state = createState(true, false, false, false, false); // {INTERESTED}
        ATTITUDE_LABELS.put(state, "Mild Curiosity"); // Simple engagement

        state = createState(false, true, false, false, false); // {LIKE}
        ATTITUDE_LABELS.put(state, "Gentle Approval"); // Basic positive feeling

        state = createState(false, false, true, false, false); // {SURE}
        ATTITUDE_LABELS.put(state, "Calm Confidence"); // Quiet certainty

        state = createState(false, false, false, true, false); // {FEAR}
        ATTITUDE_LABELS.put(state, "Vague Unease"); // Background worry

        state = createState(false, false, false, false, true); // {INTENSE}
        ATTITUDE_LABELS.put(state, "Raw Intensity"); // Pure overwhelming feeling - vastness of space

        // No Components
        state = createState(false, false, false, false, false); // {}
        ATTITUDE_LABELS.put(state, "Empty Indifference"); // Complete absence of engagement
    }

    // Helper method to create state maps
    private static Map<Component, Boolean> createState(boolean interested, boolean like, boolean sure, boolean fear, boolean intense) {
        Map<Component, Boolean> state = new HashMap<>();
        state.put(INTERESTED, interested);
        state.put(LIKE, like);
        state.put(SURE, sure);
        state.put(FEAR, fear);
        state.put(INTENSE, intense);
        return state;
    }

    public Attitude(Map<Component, Boolean> componentStates) {
        this.componentStates = new HashMap<>(componentStates);
    }

    public Attitude(Boolean interested, Boolean like, Boolean sure, Boolean fear, Boolean intense) {
        this.componentStates = new HashMap<>();
        this.componentStates.put(INTERESTED, interested);
        this.componentStates.put(LIKE, like);
        this.componentStates.put(SURE, sure);
        this.componentStates.put(FEAR, fear);
        this.componentStates.put(INTENSE, intense);
    }

    public Map<Component, Boolean> getComponentStates() {
        return new HashMap<>(componentStates);
    }

    public boolean isPresent(Component component) {
        return componentStates.containsKey(component) && componentStates.get(component);
    }

    public boolean isNotPresent(Component component) {
        return !componentStates.containsKey(component) || !componentStates.getOrDefault(component, false);
    }

    public void set(Component component, boolean present) {
        componentStates.put(component, present);
    }

    public String getLabel() {
        return ATTITUDE_LABELS.getOrDefault(componentStates, "error");
    }

    public String print() {
        StringBuilder sb = new StringBuilder("Attitude: ").append(getLabel()).append(" (");
        for (int i = 0; i < ALL_COMPONENTS.length; i++) {
            Component component = ALL_COMPONENTS[i];
            String color = isPresent(component) ? LIGHT_GREEN : DARK_RED;
            sb.append(component.getLabel()).append(": ").append(color).append(componentStates.get(component)).append(RESET_COLOR);
            if (i < ALL_COMPONENTS.length - 1) {
                sb.append(", ");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public String toString() {
        return String.format("Attitude: %s (Components: %s)", getLabel(), componentStates);
    }

    public static Attitude not(Attitude attitude) {
        Map<Component, Boolean> negatedStates = new HashMap<>();
        for (Map.Entry<Component, Boolean> entry : attitude.componentStates.entrySet()) {
            negatedStates.put(entry.getKey(), !entry.getValue());
        }
        return new Attitude(negatedStates);
    }

    public static Attitude or(Attitude a, Attitude b) {
        Map<Component, Boolean> resultStates = new HashMap<>();
        for (Component component : ALL_COMPONENTS) {
            boolean presentInA = a.componentStates.getOrDefault(component, false);
            boolean presentInB = b.componentStates.getOrDefault(component, false);
            resultStates.put(component, presentInA || presentInB);
        }
        return new Attitude(resultStates);
    }

    public static Attitude and(Attitude a, Attitude b) {
        Map<Component, Boolean> resultStates = new HashMap<>();
        for (Component component : ALL_COMPONENTS) {
            boolean inA = a.componentStates.getOrDefault(component, false);
            boolean inB = b.componentStates.getOrDefault(component, false);
            resultStates.put(component, inA && inB);
        }
        return new Attitude(resultStates);
    }

    public static Attitude xor(Attitude a, Attitude b) {
        Map<Component, Boolean> resultStates = new HashMap<>();
        for (Component component : ALL_COMPONENTS) {
            boolean inA = a.componentStates.getOrDefault(component, false);
            boolean inB = b.componentStates.getOrDefault(component, false);
            resultStates.put(component, inA ^ inB);
        }
        return new Attitude(resultStates);
    }

    public static Attitude differenceFrom(Attitude a, Attitude b) {
        return xor(a, b); // Use xor()
    }

    public static Attitude if_(Attitude a, Attitude b) {
        Attitude notA = not(a);
        return or(notA, b);
    }

    public static Attitude nand(Attitude a, Attitude b) {
        Attitude andResult = and(a, b);
        return not(andResult);
    }

    public static Attitude nor(Attitude a, Attitude b) {
        Attitude orResult = or(a, b);
        return not(orResult);
    }

    public static Attitude derivative(Attitude a, Attitude b) {
        return xor(a, b); // Use xor()
    }

    public static Attitude integral(Attitude a, Attitude d) { // d is the "derivative"
        return xor(a, d); // Use xor()
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Attitude other = (Attitude) obj;
        return Objects.equals(this.componentStates, other.componentStates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(componentStates);
    }

    public static void main(String[] args) {
        // Example usage with derivative and integral
        Attitude a = new Attitude(true, true, false, false, true); // Passionate Fondness
        Attitude b = new Attitude(false, true, true, true, true);  // Conflicted Devotion
        Attitude d = Attitude.derivative(a, b); // d is the change from a to b

        System.out.println("A: " + a);
        System.out.println("B: " + b);
        System.out.println("Derivative (A to B): " + d);

        Attitude reconstructedB = Attitude.integral(a, d); // Applying the change to a
        System.out.println("Integral (A + Derivative): " + reconstructedB);

        System.out.println("Reconstructed B equals B: " + reconstructedB.equals(b)); // Should be true

        // Example Usage of differenceFrom
        Attitude diffAB = Attitude.differenceFrom(a, b);
        System.out.println("A differenceFrom B: " + diffAB);

        // Show some interesting emotional states
        System.out.println("\n--- Sample Emotional States ---");
        System.out.println(new Attitude(true, false, true, false, false)); // Cold Calculation
        System.out.println(new Attitude(false, false, false, true, true));  // Raw Terror
        System.out.println(new Attitude(false, false, true, true, true));   // Burning Hatred
        System.out.println(new Attitude(false, false, false, false, true)); // Raw Intensity
    }
}