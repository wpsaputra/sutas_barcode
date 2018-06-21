package bps.sultra.sutasbarcode.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Batch implements Parcelable {
    String id, kode_prop, kode_kab, kode_kec, kode_desa, kode_bs, id_barcode, no_hp, id_posisi, jumlah_l1, jumlah_l2, date_terima;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKode_prop() {
        return kode_prop;
    }

    public void setKode_prop(String kode_prop) {
        this.kode_prop = kode_prop;
    }

    public String getKode_kab() {
        return kode_kab;
    }

    public void setKode_kab(String kode_kab) {
        this.kode_kab = kode_kab;
    }

    public String getKode_kec() {
        return kode_kec;
    }

    public void setKode_kec(String kode_kec) {
        this.kode_kec = kode_kec;
    }

    public String getKode_desa() {
        return kode_desa;
    }

    public void setKode_desa(String kode_desa) {
        this.kode_desa = kode_desa;
    }

    public String getKode_bs() {
        return kode_bs;
    }

    public void setKode_bs(String kode_bs) {
        this.kode_bs = kode_bs;
    }

    public String getId_barcode() {
        return id_barcode;
    }

    public void setId_barcode(String id_barcode) {
        this.id_barcode = id_barcode;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getId_posisi() {
        return id_posisi;
    }

    public void setId_posisi(String id_posisi) {
        this.id_posisi = id_posisi;
    }

    public String getJumlah_l1() {
        return jumlah_l1;
    }

    public void setJumlah_l1(String jumlah_l1) {
        this.jumlah_l1 = jumlah_l1;
    }

    public String getJumlah_l2() {
        return jumlah_l2;
    }

    public void setJumlah_l2(String jumlah_l2) {
        this.jumlah_l2 = jumlah_l2;
    }

    public String getDate_terima() {
        return date_terima;
    }

    public void setDate_terima(String date_terima) {
        this.date_terima = date_terima;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.kode_prop);
        dest.writeString(this.kode_kab);
        dest.writeString(this.kode_kec);
        dest.writeString(this.kode_desa);
        dest.writeString(this.kode_bs);
        dest.writeString(this.id_barcode);
        dest.writeString(this.no_hp);
        dest.writeString(this.id_posisi);
        dest.writeString(this.jumlah_l1);
        dest.writeString(this.jumlah_l2);
        dest.writeString(this.date_terima);
    }

    public Batch() {
    }

    protected Batch(Parcel in) {
        this.id = in.readString();
        this.kode_prop = in.readString();
        this.kode_kab = in.readString();
        this.kode_kec = in.readString();
        this.kode_desa = in.readString();
        this.kode_bs = in.readString();
        this.id_barcode = in.readString();
        this.no_hp = in.readString();
        this.id_posisi = in.readString();
        this.jumlah_l1 = in.readString();
        this.jumlah_l2 = in.readString();
        this.date_terima = in.readString();
    }

    public static final Parcelable.Creator<Batch> CREATOR = new Parcelable.Creator<Batch>() {
        @Override
        public Batch createFromParcel(Parcel source) {
            return new Batch(source);
        }

        @Override
        public Batch[] newArray(int size) {
            return new Batch[size];
        }
    };
}
