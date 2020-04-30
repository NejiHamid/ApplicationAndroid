package com.example.recyclerview;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RangeFileAsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.util.Collections;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {
    AsyncHttpClient client = new AsyncHttpClient();
    RequestParams requestParams = new RequestParams();
    SharedPreferences preferences;
//    SharedPreferences.Editor editor = preferences.edit();
    private List<NoteDto> listeNotes;
    public AppCompatActivity activity;

    public NotesAdapter(List<NoteDto> listNotes, AppCompatActivity activity) {
        this.listeNotes = listNotes;
        this.activity = activity;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewNote = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new NoteViewHolder(viewNote);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.textViewLibelleNote.setText(listeNotes.get(position).intitule);
    }

    // Appelé à chaque changement de position, pendant un déplacement.
    public boolean onItemMove(int positionDebut, int positionFin) {
        Collections.swap(listeNotes, positionDebut, positionFin);
        notifyItemMoved(positionDebut, positionFin);
        return true;
    }

    // Appelé une fois à la suppression.
    public void onItemDismiss(int position) {
        if (position > -1) {
            listeNotes.remove(position);
            notifyItemRemoved(position);
        }
    }

    @Override
    public int getItemCount() {
        return listeNotes.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        // TextView intitulé course :

        public TextView textViewLibelleNote;

        // Constructeur :
        public NoteViewHolder(View itemView) {
            super(itemView);
            textViewLibelleNote = itemView.findViewById(R.id.libelle_note);
            textViewLibelleNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    NoteDto note = listeNotes.get(getAdapterPosition());
                    /*requestParams.put("memo", note.intitule);
                    client.post("http://httpbin.org/post", requestParams, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers,
                                              byte[] response) {
                            // retour du webservice :
                          //  editor.remove("cle2");
                         //   editor.apply();
                            String retour = new String(response);
                            // conversion en un objet Java (à faire!) ayant le même format que le JSON :
                            Gson gson = new Gson();
                            NoteDto note = gson.fromJson(retour, NoteDto.class);
                            Toast.makeText(v.getContext(), note.intitule, Toast.LENGTH_SHORT).show();
                         //   editor.putString("cle2", note.intitule);
                           // editor.apply();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers,
                                              byte[] errorResponse, Throwable e) {
                        }
                    });*/
                        int orientation = view.getResources().getConfiguration().orientation;
                        int screenSize = view.getResources().getConfiguration().screenLayout &
                                Configuration.SCREENLAYOUT_SIZE_MASK;
                        if (orientation == Configuration.ORIENTATION_PORTRAIT && (screenSize == Configuration.SCREENLAYOUT_SIZE_NORMAL || screenSize == Configuration.SCREENLAYOUT_SIZE_SMALL)) {
                            Intent intent = new Intent(view.getContext(), DetailActivity.class);
                            intent.putExtra("memo", note.intitule);
                            view.getContext().startActivity(intent);
                        } else {
                            DetailsFragment fragment = new DetailsFragment();
                            Bundle bundle = new Bundle();
                            fragment.setArguments(bundle);
                            bundle.putString("memo", note.intitule);
                            FragmentManager fragmentManager = activity.getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.conteneur_detail, fragment, "details");
                            fragmentTransaction.commit();
                        }
                    }
            });
        }
    }
}
