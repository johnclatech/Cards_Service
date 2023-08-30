package com.johncla.cards.exceptions;

public class UserExceptions {
    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }

    public static class UserCreationException extends RuntimeException {
        public UserCreationException(String message) {
            super(message);
        }
    }

    public static class UserUpdateException extends RuntimeException {
        public UserUpdateException(String message) {
            super(message);
        }
    }

    public static class UserDeletionException extends RuntimeException {
        public UserDeletionException(String message) {
            super(message);
        }
    }

    public static class UserAlreadyExistException extends RuntimeException {
        public UserAlreadyExistException(String message) {
            super(message);
        }
    }

    public static class InvalidTokenException extends RuntimeException {
        public InvalidTokenException(String message) {
            super(message);
        }
    }
}
