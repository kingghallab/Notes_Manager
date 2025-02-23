package org.example;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.List;
import com.mongodb.client.result.UpdateResult;
import static com.mongodb.client.model.Filters.eq;

public class Database {
    private static final String URI = ConfigLoader.get("mongodb.uri");
    private static final String DB_NAME = ConfigLoader.get("mongodb.dbname");
    private static final String COLLECTION_NAME = "notes";

    private final MongoClient client;
    private final MongoCollection<Document> noteCollection;

    // ✅ Initialize MongoDB connection properly
    public Database() {
        this.client = MongoClients.create(URI);
        MongoDatabase database = client.getDatabase(DB_NAME);
        this.noteCollection = database.getCollection(COLLECTION_NAME);
        System.out.println("✅ Successfully connected to MongoDB Atlas!");
    }

    // ✅ Create a new note
    public void addNote(Note note) {
        Document doc = new Document("_id", note.getId())
                .append("title", note.getTitle())
                .append("content", note.getContent())
                .append("creationTime", note.getCreationTime());

        noteCollection.insertOne(doc);
        System.out.println("✅ Note added successfully!");
    }

    // ✅ Retrieve all notes
    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();
        FindIterable<Document> docs = noteCollection.find();
        for (Document doc : docs) {
            Note note = new Note(doc.getString("title"), doc.getString("content"));
            note.setId(doc.getObjectId("_id"));
            note.setCreationTime(doc.getString("creationTime"));
            notes.add(note);
        }
        return notes;
    }

    // ✅ Search for a note by title (case-insensitive)
    public List<Note> searchNotes(String keyword) {
        List<Note> notes = new ArrayList<>();
        FindIterable<Document> docs = noteCollection.find(Filters.regex("title", "(?i)" + keyword));
        for (Document doc : docs) {
            Note QueryResultNote = new Note(doc.getString("title"), doc.getString("content"));
            QueryResultNote.setId(doc.getObjectId("_id"));
            QueryResultNote.setCreationTime(doc.getString("creationTime"));
            notes.add(QueryResultNote);
        }
        return notes;
    }


    // ✅ Delete a note by ID
    public boolean deleteNote(ObjectId id) {
        long deletedCount = noteCollection.deleteOne(Filters.eq("_id", id)).getDeletedCount();
        return deletedCount > 0;
    }

    // Update Note
    public boolean updateNote(ObjectId id, String newTitle, String newContent) {
        Document updateFields = new Document();
        if (newTitle != null && !newTitle.isEmpty()) {
            updateFields.append("title", newTitle);
        }
        if (newContent != null && !newContent.isEmpty()) {
            updateFields.append("content", newContent);
        }

        if (updateFields.isEmpty()) {
            System.out.println("❌ No updates provided.");
            return false;
        }

        UpdateResult result = noteCollection.updateOne(
                eq("_id", id),
                new Document("$set", updateFields)
        );

        return result.getModifiedCount() > 0;
    }

    // ✅ Close MongoClient when done
    public void close() {
        System.out.println("Successfully Closed MongoClient !");
        client.close();
    }

}
