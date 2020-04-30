package com.example.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<NoteDto> listNotes = new ArrayList<>();
    NotesAdapter notesAdapter;

    //SharedPreferences preferences =PreferenceManager.getDefaultSharedPreferences(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.liste_notes);
// à ajouter pour de meilleures performances :
        recyclerView.setHasFixedSize(true);
// layout manager, décrivant comment les items sont disposés :
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

// contenu d'exemple :
        // String note = preferences.getString("cle2", "file Empty");
        // Toast.makeText(this, note, Toast.LENGTH_SHORT).show();

        AppDatabaseHelper.getDatabase(this);
        listNotes = AppDatabaseHelper.getDatabase(this).noteDAO().getListeCourses();
        notesAdapter = new NotesAdapter(listNotes, this);
        recyclerView.setAdapter(notesAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelperCallback(notesAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public void click(View v) {
        EditText newNote = findViewById(R.id.editText);
        NoteDto note = new NoteDto(newNote.getText().toString());
        listNotes.add(note);
        AppDatabaseHelper.getDatabase(this).noteDAO().insert(note);
        notesAdapter.notifyItemInserted(listNotes.size());
        newNote.setText("");
    }
}
