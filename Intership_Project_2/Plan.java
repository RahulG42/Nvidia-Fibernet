package Intership_Project_2;

import java.awt.*;
import java.util.List;

public class Plan {
    String planType;
     double planPrice;
     String planData;
     String planSpeed;
     String planDuration;
     List<String> appSubscriptions;
     Color themeColor;

    public Plan(String planType, double planPrice, String string, String string2, String string3, List<String> appSubscriptions, Color themeColor) {
        this.planType = planType;
        this.planPrice = planPrice;
        this.planData = string;
        this.planSpeed = string2;
        this.planDuration = string3;
        this.appSubscriptions = appSubscriptions;
        this.themeColor = themeColor;
    }


    // Getters and setters can be added as needed
}
