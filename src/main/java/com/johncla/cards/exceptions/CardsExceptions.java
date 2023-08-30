package com.johncla.cards.exceptions;

public class CardsExceptions {
    public static class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }

    public static class AccessDeniedException extends RuntimeException {
        public AccessDeniedException(String message) {
            super(message);
        }
    }

    public static class CardCreationException extends RuntimeException {
        public CardCreationException(String message) {
            super(message);
        }
    }

    public static class CardUpdateException extends RuntimeException {
        public CardUpdateException(String message) {
            super(message);
        }
    }

    public static class CardDeletionException extends RuntimeException {
        public CardDeletionException(String message) {
            super(message);
        }
    }
    public static class InvalidColorFormatException extends RuntimeException {
        public InvalidColorFormatException(String message) { super(message); }
    }

    public static class InvalidTokenException extends RuntimeException {
        public InvalidTokenException(String message) {
            super(message);
        }
    }
}
