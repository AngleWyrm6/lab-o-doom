package org.anglewyrm.labodoom;

public class Constants {
    public enum Component {
        INTERESTED("Interest"),
        FAVORED("Favor"),
        CONFIDENT("Confidence"),
        FEAR("Fear"),
        INTENSE("Intensity");

        private final String label;

        Component(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }
}