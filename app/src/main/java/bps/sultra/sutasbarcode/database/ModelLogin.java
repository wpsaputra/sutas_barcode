package bps.sultra.sutasbarcode.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import bps.sultra.sutasbarcode.model.Login;


/**
 * Created by Lenovo 17 on 4/27/2016.
 */
public class ModelLogin extends Model{
    String[] col = {"_id", "no_hp", "nama", "id_status", "flag"};

    public ModelLogin(Context ctx) {
        super(ctx);
        // To do create table Publikasi in db sidewi

        this.setTableName("login");
    }


    public Login getById(int id_cari){
        Login p = new Login();
        Cursor c = super.getById("_id",id_cari, this.col);
        c.moveToFirst();

        p.setId(c.getInt(c.getColumnIndex("_id")));
        p.setNo_hp(c.getString(c.getColumnIndex("no_hp")));
        p.setNama(c.getString(c.getColumnIndex("nama")));
        p.setId_status(c.getInt(c.getColumnIndex("id_status")));
        p.setFlag(c.getInt(c.getColumnIndex("flag")));

        return p;
    }

    public void update(Login p){
        ContentValues cv = new ContentValues();

        cv.put("_id", p.getId());
        cv.put("no_hp", p.getNo_hp());
        cv.put("nama", p.getNama());
        cv.put("id_status", p.getId_status());
        cv.put("flag", p.getFlag());

        super.update("_id", p.getId(), cv);
    }


}
