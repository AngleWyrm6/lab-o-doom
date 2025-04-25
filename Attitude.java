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
     * certainty/confidence is represented by {SURE}, {SURE,INTENSITY}
     * attitudes are a tiered jewel:
     * Tier-1: the simple 1-bit states, such as LIKE or FEAR
     * Tier-2: 2-bit composite states, such as {FEAR, INTENSITY}=pain
     * Tier-3: 3-bit composite sttaes, such as {LIKE, INTERESTED, INTENSITY}=love, etc.
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
    private static final Component[] ALL_COMPONENTS = {INTERESTED, LIKE, SURE, FEAR, INTENSITY};

    private static final Map<Map<Component, Boolean>, String> ATTITUDE_LABELS = new HashMap<>();

    static {
        // ... (rest of your static initialization for ATTITUDE_LABELS)
    }

    public Attitude(Map<Component, Boolean> componentStates) {
        this.componentStates = new HashMap<>(componentStates);
    }

    public Attitude(Boolean interested, Boolean like, Boolean sure, Boolean fear, Boolean intensity) {
        this.componentStates = new HashMap<>();
        this.componentStates.put(INTERESTED, interested);
        this.componentStates.put(LIKE, like);
        this.componentStates.put(SURE, sure);
        this.componentStates.put(FEAR, fear);
        this.componentStates.put(INTENSITY, intensity);
    }

    public Map<Component, Boolean> getComponentStates() {
        return new HashMap<>(componentStates);
    }

    public boolean isPresent(Component component) {
        return componentStates.containsKey(component) && componentStates.get(component);
    }

    public boolean isNotPresent(Component component) {
        return componentStates.containsKey(component) && !componentStates.get(component);
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

    public static Attitude equivalent(Attitude a, Attitude b) {
        Attitude aImpliesB = if_(a, b);
        Attitude bImpliesA = if_(b, a);
        return and(aImpliesB, bImpliesA);
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
        Attitude a = new Attitude(true, true, false, false, true);
        Attitude b = new Attitude(false, true, true, true, true);
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

    }
}

