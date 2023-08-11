package com.eky.catatanharian;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

// Eky Nugraha Heriawan
// 10120066
// IF-2
public class FirebaseHelper {
    private DatabaseReference mDatabase;

    public FirebaseHelper() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void addNote(String id, String tanggal, String waktu, String judul, String isi, String kategori) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        CatatanModel catatanModel = new CatatanModel(id, tanggal, waktu, judul, isi, kategori);
        mDatabase.child("note").child(userId).child(id).setValue(catatanModel);
    }

    public void updateNote(String noteId, String newTitle, String newCategory, String newNote) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();

        DatabaseReference updatedNoteRef = mDatabase.child("note").child(userId).child(noteId);
        updatedNoteRef.child("judul").setValue(newTitle);
        updatedNoteRef.child("kategori").setValue(newCategory);
        updatedNoteRef.child("isi").setValue(newNote);
    }

    public void deleteNote(String noteId) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();

        DatabaseReference noteRef = mDatabase.child("note").child(userId).child(noteId);
        noteRef.removeValue();

    }
}