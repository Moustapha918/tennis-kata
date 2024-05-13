package fr.kata.tennis;

import fr.kata.tennis.usecases.TennisGame;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TennisGame tennisGame = new TennisGame();
        Scanner scanner = new Scanner(System.in);
        String gameInput = scanner.nextLine();

        var scenario = tennisGame.printScenario(gameInput);

        for(var roundResult: scenario){
            System.out.println(roundResult);
        }
    }
}
