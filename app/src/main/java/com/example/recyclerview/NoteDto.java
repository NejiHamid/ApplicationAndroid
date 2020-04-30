package com.example.recyclerview;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Note")
public class NoteDto{
    // Attributs :
    @PrimaryKey(autoGenerate = true)
    long noteId = 0;
    public String intitule;

    // Constructeur :
    public NoteDto(String intitule) {
        this.intitule = intitule;
    }
}

