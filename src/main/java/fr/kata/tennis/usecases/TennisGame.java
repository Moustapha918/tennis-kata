package fr.kata.tennis.usecases;

import fr.kata.tennis.exceptions.UnknownPlayerException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class TennisGame {

    public static final String PLAYER_A_NAME = "A";
    public static final String PLAYER_B_NAME = "B";
    private static final String WIN_MESSAGE = "Player %s wins the game";

    Player playerA = new Player(PLAYER_A_NAME);
    Player playerB = new Player(PLAYER_B_NAME);

    private final List<String> scenario = new ArrayList<>();

    public List<String> printScenario(String gameInput) throws UnknownPlayerException {

        var scorers = fetchScorersFromGameInput(gameInput);
        checkScorerList(scorers);

        for (var scorer : scorers) {
            updateScores(scorer);
            scenario.add(calculateRoundScore(scorer));
        }
        return scenario;
    }

    private void updateScores(String scorer) {
        if (scorer.equals(PLAYER_A_NAME)) {
            playerA.increaseScore();
        }
        if (scorer.equals(PLAYER_B_NAME)) {
            playerB.increaseScore();
        }
    }

    private String calculateRoundScore(String scorerName) {

        if (playerA.getScore() >= 4 || playerB.getScore() >= 4) {
            return String.format(WIN_MESSAGE, scorerName);
        } else {
            return String.format("Player A : %s / Player B : %s", scoreToDisplay(playerA.getScore()), scoreToDisplay(playerA.getScore()));
        }
    }

    private String scoreToDisplay(int intScore) {
        return switch (intScore) {
            case 0 -> "0";
            case 1 -> "15";
            case 2 -> "30";
            case 3 -> "40";
            default -> throw new IllegalStateException("Unexpected value: " + intScore);
        };
    }

    private void checkScorerList(List<String> scorers) throws UnknownPlayerException {
        for (String playerName : scorers) {
            if (!playerA.getName().equals(playerName) && !playerB.getName().equals(playerName)) {
                throw new UnknownPlayerException("Unknown player " + playerName);
            }
        }
    }

    private List<String> fetchScorersFromGameInput(String gameInput) {

        return IntStream.range(0, gameInput.length())
                .mapToObj(index -> gameInput.substring(index, index + 1))
                .toList();
    }
}
