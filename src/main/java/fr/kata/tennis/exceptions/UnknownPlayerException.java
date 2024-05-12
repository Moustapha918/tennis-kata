package fr.kata.tennis.exceptions;

public class UnknownPlayerException extends RuntimeException {
    public UnknownPlayerException(String message) {
        super(message);
    }
}
