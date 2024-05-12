package fr.kata.tennis.usecases;

import fr.kata.tennis.exceptions.UnknownPlayerException;

import java.util.List;
import java.util.stream.IntStream;

public class TennisGame {

    public static final String PLAYER_A_NAME = "A";
    public static final String PLAYER_B_NAME = "B";

    public List<String> printScenario(String gameInput) throws UnknownPlayerException {

        var scorers = fetchScorersFromGameInput(gameInput);
        checkScorerList(scorers);

        return List.of();
    }

    private void checkScorerList(List<String> scorers) throws UnknownPlayerException {
        for (String playerName : scorers) {
            if (!PLAYER_A_NAME.equals(playerName) && !PLAYER_B_NAME.equals(playerName)) {
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
