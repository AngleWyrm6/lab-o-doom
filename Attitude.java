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

    private Map<Component, Boolean> componentStates;
    private static final Component[] ALL_COMPONENTS = {INTERESTED, LIKE, SURE, FEAR, INTENSITY};

    private static final Map<Map<Component, Boolean>, String> ATTITUDE_LABELS = new HashMap<>();

    static {
        // You'll need to define these labels to account for the NOT state
        // This is just a placeholder - you'll need to map all the new combinations
        Map<Component, Boolean> passionateEnthusiasm = new HashMap<>();
        passionateEnthusiasm.put(INTERESTED, true);
        passionateEnthusiasm.put(LIKE, true);
        passionateEnthusiasm.put(SURE, true);
        passionateEnthusiasm.put(FEAR, true);
        passionateEnthusiasm.put(INTENSITY, true);
        ATTITUDE_LABELS.put(passionateEnthusiasm, "Passionate Enthusiasm");

        Map<Component, Boolean> notInterested = new HashMap<>();
        notInterested.put(INTERESTED, false);
        notInterested.put(LIKE, true);
        notInterested.put(SURE, true);
        notInterested.put(FEAR, true);
        notInterested.put(INTENSITY, true);
        ATTITUDE_LABELS.put(notInterested, "Disinterested Enthusiasm"); // Example label
        // ... and so on for all 2^5 = 32 combinations
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
        Map<Component, Boolean> resultStates = new HashMap<>();
        for (Component component : ALL_COMPONENTS) {
            boolean inA = a.componentStates.getOrDefault(component, false);
            boolean inB = b.componentStates.getOrDefault(component, false);
            resultStates.put(component, inA && !inB);
        }
        return new Attitude(resultStates);
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
        // Example usage with the new representation
        Attitude a = new Attitude(true, true, false, false, true);
        System.out.println("A: " + a);
        System.out.println("A Label: " + a.getLabel());

        Attitude notA = Attitude.not(a);
        System.out.println("NOT A: " + notA);
        System.out.println("NOT A Label: " + notA.getLabel()); // You'll need to define this label

        Attitude b = new Attitude(false, true, true, true, false);
        System.out.println("B: " + b);
        System.out.println("B Label: " + b.getLabel());

        Attitude orAB = Attitude.or(a, b);
        System.out.println("A OR B: " + orAB);
        System.out.println("A OR B Label: " + orAB.getLabel()); // You'll need to define this label

        Attitude andAB = Attitude.and(a, b);
        System.out.println("A AND B: " + andAB);
        System.out.println("A AND B Label: " + andAB.getLabel());

        Attitude xorAB = Attitude.xor(a, b);
        System.out.println("A XOR B: " + xorAB);
        System.out.println("A XOR B Label: " + xorAB.getLabel());

        Attitude diffAB = Attitude.differenceFrom(a, b);
        System.out.println("A differenceFrom B: " + diffAB);
        System.out.println("A differenceFrom B Label: " + diffAB.getLabel());

        Attitude ifAB = Attitude.if_(a,b);
        System.out.println("A if B: " + ifAB);
        System.out.println("A if B Label: " + ifAB.getLabel());

        Attitude eqAB = Attitude.equivalent(a,b);
        System.out.println("A equivalent B: " + eqAB);
        System.out.println("A equivalent B Label: " + eqAB.getLabel());

        Attitude nandAB = Attitude.nand(a,b);
        System.out.println("A nand B: " + nandAB);
        System.out.println("A nand B Label: " + nandAB.getLabel());

        Attitude norAB = Attitude.nor(a,b);
        System.out.println("A nor B: " + norAB);
        System.out.println("A nor B Label: " + norAB.getLabel());

        System.out.println("a.equals(b): " + a.equals(b));
        System.out.println("a.equals(a): " + a.equals(a));

    }
}

