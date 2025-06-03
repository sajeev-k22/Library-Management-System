package qbfd;

interface Authenticatable {
    boolean authenticate(String username, String password);
}