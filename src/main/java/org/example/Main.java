package org.example;

public class Main {
    public static void main(String[] args) {
        Database db = new Database();
        CLI cli = new CLI(db);
        cli.start();
        db.close();
    }
}
