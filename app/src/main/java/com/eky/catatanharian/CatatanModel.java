package com.eky.catatanharian;

// Eky Nugraha Heriawan
// 10120066
// IF-2
public class CatatanModel {
    public String id;
    public String tanggal;
    public String waktu;
    public String judul;
    public String isi;
    public String kategori;

    public CatatanModel() {
        // Default constructor required for Firebase
    }

    public CatatanModel(String id, String tanggal, String waktu, String judul, String isi, String kategori) {
        this.id = id;
        this.tanggal = tanggal;
        this.waktu = waktu;
        this.judul = judul;
        this.isi = isi;
        this.kategori = kategori;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
