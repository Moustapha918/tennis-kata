package fr.kata.tennis.usecases;

import fr.kata.tennis.exceptions.UnknownPlayerException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class TennisGame {

    public static final String PLAYER_A_NAME = "A";
    public static final String PLAYER_B_NAME = "B";
    private static final String WIN_MESSAGE_TEMPLATE = "Player %s wins the game";
    public static final String SCORE_MESSAGE_TEMPLATE = "Player A : %s / Player B : %s";
    public static final String DEUCE_MESSAGE = "Deuce";

    Player playerA = new Player(PLAYER_A_NAME);
    Player playerB = new Player(PLAYER_B_NAME);

    private final List<String> scenario = new ArrayList<>();

    public List<String> printScenario(String gameInput) {

        var scorers = fetchScorersFromGameInput(gameInput);

        for (var scorerName : scorers) {
            Player ballWinner = whoWonThisBall(scorerName);
            Player ballLooser = whoLostThisBall(scorerName);

            ballWinner.increaseScore();
            scenario.add(calculateRoundScore(ballWinner, ballLooser));
        }
        return scenario;
    }

    private Player whoWonThisBall(String scorerName) throws UnknownPlayerException {
        return switch (scorerName) {
            case "A" -> playerA;
            case "B" -> playerB;
            default -> throw new UnknownPlayerException("Unknown player name" + scorerName);
        };
    }
    private Player whoLostThisBall(String ballWinnerName) {
        return whoWonThisBall(ballWinnerName).equals(playerA) ? playerB : playerA;
    }

    private String calculateRoundScore(Player roundScorer, Player roundLooser) {

        if (playerA.getScore() < 3 || playerB.getScore() < 3) {
            if (roundScorer.doesHitTheWinPoint()) {
                return String.format(WIN_MESSAGE_TEMPLATE, roundScorer.getName());
            } else {
                return String.format(SCORE_MESSAGE_TEMPLATE, playerA.scoreToDisplay(), playerB.scoreToDisplay());
            }
        } else {
            if (playerA.getScore() == playerB.getScore()) {
                return DEUCE_MESSAGE;
            } else
            {
                if (roundScorer.getScore() - roundLooser.getScore() == 2) {
                    return String.format(WIN_MESSAGE_TEMPLATE, roundScorer.getName());
                }
                else {
                    return String.format("Deuce, advantage for Player %s", playerA.getName());
                }
            }
        }
    }

    private List<String> fetchScorersFromGameInput(String gameInput) {

        return IntStream.range(0, gameInput.length())
                .mapToObj(index -> gameInput.substring(index, index + 1))
                .toList();
    }
}
