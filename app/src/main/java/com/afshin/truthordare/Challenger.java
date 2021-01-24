package com.afshin.truthordare;

public class Challenger {

        public Challenger ( String name, int color ) {
            this.name = name;
            this.color = color;

        }

        String name;
        int color;
        double startAngle;
        double endAngle;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public double getStartAngle() {
            return startAngle;
        }

        public void setStartAngle(double startAngle) {
            this.startAngle = startAngle;
        }

        public double getEndAngle() {
            return endAngle;
        }

        public void setEndAngle(double endAngle) {
            this.endAngle = endAngle;
        }

}
