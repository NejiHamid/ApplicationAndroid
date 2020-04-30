package com.example.recyclerview;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public abstract class NoteDAO {
    @Query("SELECT * FROM note")
    public abstract List<NoteDto> getListeCourses();
    @Query("SELECT COUNT(*) FROM note WHERE intitule = :intitule")
    public abstract long countCoursesParIntitule(String intitule);
    @Insert
    public abstract void insert(NoteDto... note);
    @Update
    public abstract void update(NoteDto... notes);
    @Delete
    public abstract void delete(NoteDto... notes);
    @Transaction
    public void insertDelete(NoteDto noteDto1, NoteDto noteDto2)
    {
        insert(noteDto1);
        delete(noteDto1);
    }
}

