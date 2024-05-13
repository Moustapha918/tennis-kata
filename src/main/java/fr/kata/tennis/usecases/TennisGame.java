package fr.kata.tennis.usecases;

import fr.kata.tennis.exceptions.UnknownPlayerException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static fr.kata.tennis.usecases.RoundOutCome.*;

public class TennisGame {

    public static final String PLAYER_A_NAME = "A";
    public static final String PLAYER_B_NAME = "B";
    private static final String WIN_MESSAGE_TEMPLATE = "Player %s wins the game";
    public static final String SCORE_MESSAGE_TEMPLATE = "Player A : %s / Player B : %s";
    public static final String DEUCE_MESSAGE = "Deuce";
    public static final String ADVANTAGE_MESSAGE_TEMPLATE = "Deuce, advantage for Player %s";

    Player playerA = new Player(PLAYER_A_NAME);
    Player playerB = new Player(PLAYER_B_NAME);

    private final List<String> scenario = new ArrayList<>();

    public List<String> printScenario(String gameInput) {

        var scorers = fetchScorersFromGameInput(gameInput);

        for (var scorerName : scorers) {
            Player ballWinner = whoWonThisBall(scorerName);
            Player ballLooser = whoLostThisBall(scorerName);

            ballWinner.increaseScore();

            var roundResult = calculateRoundScore(ballWinner, ballLooser);

            scenario.add(formatedScore(roundResult, ballWinner));
        }
        return scenario;
    }

    private RoundOutCome calculateRoundScore(Player roundScorer, Player roundLooser) {

        if (isGameInLoveStage(roundScorer, roundLooser)) {
            return applyLoveStageRulesAndGetScore(roundScorer);
        } else {
            if (isDeuce(roundScorer, roundLooser)) {
                return DEUCE;
            } else
            {
                return applyAfterDeuceRulesAndGetScore(roundScorer, roundLooser);
            }
        }
    }

    private RoundOutCome applyAfterDeuceRulesAndGetScore(Player roundScorer, Player roundLooser) {
        if (roundScorer.getScore() - roundLooser.getScore() == 2) {
            return WIN;
        }
        else {
            return ADVANTAGE;
        }
    }

    private boolean isDeuce(Player roundScorer, Player roundLooser) {
        return roundScorer.getScore() == roundLooser.getScore();
    }

    private RoundOutCome applyLoveStageRulesAndGetScore(Player roundScorer) {
        if (roundScorer.doesHitTheWinPoint()) {
            return WIN;
        } else {
            return SCORE;
        }
    }

    private boolean isGameInLoveStage(Player roundScorer, Player roundLooser) {
        return roundScorer.getScore() < 3 || roundLooser.getScore() < 3;
    }

    private List<String> fetchScorersFromGameInput(String gameInput) {

        return IntStream.range(0, gameInput.length())
                .mapToObj(index -> gameInput.substring(index, index + 1))
                .toList();
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

    private String formatedScore(RoundOutCome roundOutCome, Player roundScorer) {
        return switch (roundOutCome) {
            case ADVANTAGE -> String.format(ADVANTAGE_MESSAGE_TEMPLATE, roundScorer.getName());
            case WIN -> String.format(WIN_MESSAGE_TEMPLATE, roundScorer.getName());
            case SCORE -> String.format(SCORE_MESSAGE_TEMPLATE, playerA.scoreToDisplay(), playerB.scoreToDisplay());
            case DEUCE -> DEUCE_MESSAGE;
        };
    }
}
