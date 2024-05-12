package fr.kata.tennis.usecases;

import fr.kata.tennis.exceptions.UnknownPlayerException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TennisGameUTest {

    @Test
    void should_write_scenario_for_A_player_scoring_all_balls() throws UnknownPlayerException {

        // GIVEN
        String scorerList = "AAAA";
        var expectedScenario = List.of(
                "Player A : 15 / Player B : 0",
                "Player A : 30 / Player B : 0",
                "Player A : 40 / Player B : 0",
                "Player A wins the game");

        // WHEN
        TennisGame tennisGame = new TennisGame();
        var actualScenario = tennisGame.printScenario(scorerList);

        // THEN
        assertEquals(expectedScenario, actualScenario);
    }

    @Test
    void should_get_error_when_scorer_list_is_invalid() {
        // GIVEN
        String scorerList = "ACAZ";

        // WHEN
        TennisGame tennisGame = new TennisGame();

        // THEN
        assertThrows(UnknownPlayerException.class , () -> tennisGame.printScenario(scorerList));
    }

}
