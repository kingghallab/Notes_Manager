package org.example;

import org.bson.types.ObjectId;

import java.util.List;
import java.util.Scanner;

public class CLI {
    private final Scanner scanner = new Scanner(System.in);
    private final Database database;

    public CLI(Database database) {
        this.database = database;
    }

    // Main menu loop
    public void start() {
        while (true) {
            System.out.println("\n=== Notes Manager ===");
            System.out.println("1️⃣ Add a Note");
            System.out.println("2️⃣ View All Notes");
            System.out.println("3️⃣ Search Notes");
            System.out.println("4️⃣ Delete a Note");
            System.out.println("5️⃣ Update a Note");
            System.out.println("6  Exit");
            System.out.print("👉 Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1 -> addNote();
                case 2 -> viewNotes();
                case 3 -> searchNotes();
                case 4 -> deleteNote();
                case 5 -> updateNote();
                case 6 -> {
                    System.out.println("👋 Exiting...");
                    return;
                }
                default -> System.out.println("❌ Invalid choice! Try again.");
            }
        }
    }

    // Get note title from user input
    public String getTitle() {
        System.out.print("📌 Enter title: ");
        return scanner.nextLine();
    }

    // Get multi-line content input from the user
    public String getContent() {
        System.out.println("📝 Enter content (type 'END' on a new line to finish):");
        StringBuilder contentBuilder = new StringBuilder();

        while (true) {
            String line = scanner.nextLine();
            if (line.equalsIgnoreCase("END")) { // Stop input when user types END
                break;
            }
            contentBuilder.append(line).append("\n");
        }
        return contentBuilder.toString().trim();
    }

    // Update an existing note
    private void updateNote() {
        System.out.print("🗑 Enter Note ID to update: ");
        String strId = scanner.nextLine();

        ObjectId objId = new ObjectId(strId);

        // Ask the user what they want to update
        System.out.println("What do you want to update?\n1️⃣ Title\n2️⃣ Content\n3️⃣ Both");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        String newTitle = null;
        String newContent = null;

        switch (choice) {
            case 1 -> newTitle = getTitle();
            case 2 -> newContent = getContent();
            case 3 -> {
                newTitle = getTitle();
                newContent = getContent();
            }
            default -> {
                System.out.println("❌ Invalid choice! Try again.");
                return;
            }
        }

        // Update note in the database
        if (database.updateNote(objId, newTitle, newContent)) {
            System.out.println("✅ Note updated successfully!");
        } else {
            System.out.println("❌ Note not found or no changes made.");
        }
    }

    // Add a new note
    private void addNote() {
        String title = getTitle();
        String content = getContent();

        Note newNote = new Note(title, content);
        database.addNote(newNote);
    }

    // Display all notes
    private void viewNotes() {
        List<Note> allNotes = database.getAllNotes();
        if (allNotes.isEmpty()) {
            System.out.println("📭 No notes found.");
            return;
        }
        for (int i = 0; i < allNotes.size(); ++i) {
            System.out.printf("Note %d:", i + 1);
            allNotes.get(i).displayNote();
        }
    }

    // Search notes by keyword
    private void searchNotes() {
        System.out.print("🔍 Enter search keyword: ");
        String keyword = scanner.nextLine();

        List<Note> searchResults = database.searchNotes(keyword);
        if (searchResults.isEmpty()) {
            System.out.println("❌ No matching notes found.");
            return;
        }
        System.out.printf("Found %d results!", searchResults.size());
        for (int i = 0; i < searchResults.size(); ++i) {
            System.out.printf("Note %d:", i + 1);
            searchResults.get(i).displayNote();
        }
    }

    // Delete a note by ID
    private void deleteNote() {
        System.out.print("🗑 Enter Note ID to delete: ");
        String idStr = scanner.nextLine();
        ObjectId id = new ObjectId(idStr);
        if (database.deleteNote(id)) {
            System.out.println("✅ Note deleted.");
        } else {
            System.out.println("❌ Note not found.");
        }
    }
}
