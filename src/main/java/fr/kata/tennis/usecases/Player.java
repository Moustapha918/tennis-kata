package fr.kata.tennis.usecases;

public class Player {
    private final String name;
    private int score = 0;

    public Player(String name) {
        this.name = name;
    }

    public void increaseScore() {
        score += 1;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    String scoreToDisplay() {
        return switch (score) {
            case 0 -> "0";
            case 1 -> "15";
            case 2 -> "30";
            case 3 -> "40";
            default -> throw new IllegalStateException("Unexpected value: " + score);
        };
    }

    boolean doesHitTheWinPoint() {
        return getScore() >= 4;
    }
}
