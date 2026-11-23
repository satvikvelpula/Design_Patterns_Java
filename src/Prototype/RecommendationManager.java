package Prototype;

import java.util.ArrayList;
import java.util.List;

public class RecommendationManager {
    private final List<Recommendation> recommendations;

    public RecommendationManager() {
        this.recommendations = new ArrayList<>();
    }

    public void save(Recommendation recommendation) {
        recommendations.add(recommendation);
    }

    public List<Recommendation> getRecommendations() {
        return recommendations;
    }

    public Recommendation get(int index) {
        return recommendations.get(index);
    }

    public int size() {
        return recommendations.size();
    }

    public void displayAll() {
        if (recommendations.isEmpty()) {
            System.out.println("No saved recommendations.");
            return;
        }
        for (int i = 0; i < recommendations.size(); i++) {
            System.out.println();
            System.out.println("Recommendation " + (i + 1) + ":");
            recommendations.get(i).display();
        }
    }
}
