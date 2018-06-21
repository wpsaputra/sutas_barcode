package bps.sultra.sutasbarcode.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BatchOld {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("kode_prop")
    @Expose
    private String kodeProp;
    @SerializedName("kode_kab")
    @Expose
    private String kodeKab;
    @SerializedName("kode_kec")
    @Expose
    private String kodeKec;
    @SerializedName("kode_desa")
    @Expose
    private String kodeDesa;
    @SerializedName("kode_bs")
    @Expose
    private String kodeBs;
    @SerializedName("no_hp")
    @Expose
    private String noHp;
    @SerializedName("id_status")
    @Expose
    private Integer idStatus;
    @SerializedName("jumlah_l1")
    @Expose
    private Integer jumlahL1;
    @SerializedName("jumlah_l2")
    @Expose
    private Integer jumlahL2;
    @SerializedName("date_terima")
    @Expose
    private String dateTerima;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKodeProp() {
        return kodeProp;
    }

    public void setKodeProp(String kodeProp) {
        this.kodeProp = kodeProp;
    }

    public String getKodeKab() {
        return kodeKab;
    }

    public void setKodeKab(String kodeKab) {
        this.kodeKab = kodeKab;
    }

    public String getKodeKec() {
        return kodeKec;
    }

    public void setKodeKec(String kodeKec) {
        this.kodeKec = kodeKec;
    }

    public String getKodeDesa() {
        return kodeDesa;
    }

    public void setKodeDesa(String kodeDesa) {
        this.kodeDesa = kodeDesa;
    }

    public String getKodeBs() {
        return kodeBs;
    }

    public void setKodeBs(String kodeBs) {
        this.kodeBs = kodeBs;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public Integer getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(Integer idStatus) {
        this.idStatus = idStatus;
    }

    public Integer getJumlahL1() {
        return jumlahL1;
    }

    public void setJumlahL1(Integer jumlahL1) {
        this.jumlahL1 = jumlahL1;
    }

    public Integer getJumlahL2() {
        return jumlahL2;
    }

    public void setJumlahL2(Integer jumlahL2) {
        this.jumlahL2 = jumlahL2;
    }

    public String getDateTerima() {
        return dateTerima;
    }

    public void setDateTerima(String dateTerima) {
        this.dateTerima = dateTerima;
    }
}
