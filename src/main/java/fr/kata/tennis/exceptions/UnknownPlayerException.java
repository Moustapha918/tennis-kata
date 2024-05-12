package fr.kata.tennis.exceptions;

public class UnknownPlayerException extends Exception {
    public UnknownPlayerException(String message) {
        super(message);
    }
}
