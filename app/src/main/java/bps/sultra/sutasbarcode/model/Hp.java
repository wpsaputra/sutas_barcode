package bps.sultra.sutasbarcode.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Hp {
    @SerializedName("no_hp")
    @Expose
    private String noHp;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("id_status")
    @Expose
    private Integer idStatus;

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Integer getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(Integer idStatus) {
        this.idStatus = idStatus;
    }
}
