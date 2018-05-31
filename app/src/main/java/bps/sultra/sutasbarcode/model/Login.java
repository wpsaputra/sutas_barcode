package bps.sultra.sutasbarcode.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Login implements Parcelable {
    int id, flag, id_status;
    String no_hp, nama;

    public Login() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getId_status() {
        return id_status;
    }

    public void setId_status(int id_status) {
        this.id_status = id_status;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.flag);
        dest.writeInt(this.id_status);
        dest.writeString(this.no_hp);
        dest.writeString(this.nama);
    }

    protected Login(Parcel in) {
        this.id = in.readInt();
        this.flag = in.readInt();
        this.id_status = in.readInt();
        this.no_hp = in.readString();
        this.nama = in.readString();
    }

    public static final Parcelable.Creator<Login> CREATOR = new Parcelable.Creator<Login>() {
        @Override
        public Login createFromParcel(Parcel source) {
            return new Login(source);
        }

        @Override
        public Login[] newArray(int size) {
            return new Login[size];
        }
    };
}
