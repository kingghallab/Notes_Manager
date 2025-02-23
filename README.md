# 📌 Notes Manager CLI
A simple command-line-based note manager built using Java and MongoDB.

## 🚀 Features
- ✅ Add, update, delete, and search notes
- ✅ Uses MongoDB Atlas for cloud storage
- ✅ Interactive CLI with easy-to-use options
- ✅ Stores notes securely in a NoSQL database

## 🔧 Installation
### 1️⃣ Clone the repository
```sh
git clone <your-repo-url>
cd NotesManagerV2
```

### 2️⃣ Configure MongoDB Connection
Create a `config.properties` file in `src/main/resources/` and add the following:
```
MONGO_URI=<your_mongo_atlas_connection_string>
DATABASE_NAME=notes_manager
COLLECTION_NAME=notes
```

### 3️⃣ Build & Run
```sh
mvn clean install
mvn exec:java -Dexec.mainClass="org.example.Main"
```

## 📜 Usage
Run the program and follow the prompts:
```
=== Notes Manager ===
1️⃣ Add a Note  
2️⃣ View All Notes  
3️⃣ Search Notes  
4️⃣ Delete a Note  
5️⃣ Update a Note  
6️⃣ Exit  
👉 Choose an option:  
```

## 👨‍💻 Project Structure
```
NotesManagerV2/
│── src/
│   ├── main/
│   │   ├── java/org/example/
│   │   │   ├── CLI.java        # Handles user interaction
│   │   │   ├── Database.java   # Handles MongoDB operations
│   │   │   ├── Note.java       # Note model
│   │   │   ├── Main.java       # Application entry point
│   │   │   ├── ConfigLoader.java # Loads configuration from properties
│   │   ├── resources/
│   │   │   ├── config.properties # Stores database connection info (ignored in Git)
│── pom.xml  # Maven dependencies
│── .gitignore
│── README.md
```
