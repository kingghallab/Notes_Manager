package org.example;
import org.bson.types.ObjectId;
import java.util.Date;

public class Note {
    private String title;
    private String content;
    private String creationTime;
    private ObjectId id;

    public Note(String title, String content) {
        this.id = new ObjectId();
        this.title = title;
        this.content = content;
        this.creationTime = new Date().toString();
    }
    public ObjectId getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getContent() {
        return content;
    }
    public String getCreationTime() {
        return creationTime;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }
    public void setId(ObjectId id) {
        this.id = id;
    }

    public void displayNote() {
        System.out.println("\n📝 Note ID: " + id);
        System.out.println("📌 Title: " + title);
        System.out.println("📄 Content: " + content);
        System.out.println("⏳ Created At: " + creationTime);
        System.out.println("-----------------------------");
    }
}
