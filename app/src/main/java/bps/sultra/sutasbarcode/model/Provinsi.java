package bps.sultra.sutasbarcode.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Provinsi implements Parcelable {
    String id, nama_prop;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama_prop() {
        return nama_prop;
    }

    public void setNama_prop(String nama_prop) {
        this.nama_prop = nama_prop;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.nama_prop);
    }

    public Provinsi() {
    }

    protected Provinsi(Parcel in) {
        this.id = in.readString();
        this.nama_prop = in.readString();
    }

    public static final Parcelable.Creator<Provinsi> CREATOR = new Parcelable.Creator<Provinsi>() {
        @Override
        public Provinsi createFromParcel(Parcel source) {
            return new Provinsi(source);
        }

        @Override
        public Provinsi[] newArray(int size) {
            return new Provinsi[size];
        }
    };
}
