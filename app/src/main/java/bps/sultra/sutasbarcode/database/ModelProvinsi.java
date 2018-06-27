package bps.sultra.sutasbarcode.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import bps.sultra.sutasbarcode.model.Login;
import bps.sultra.sutasbarcode.model.Provinsi;


/**
 * Created by Lenovo 17 on 4/27/2016.
 */
public class ModelProvinsi extends Model{
    String[] col = {"_id", "nama_prop"};

    public ModelProvinsi(Context ctx) {
        super(ctx);
        // To do create table Publikasi in db sidewi

        this.setTableName("provinsi");
    }

    public List<Provinsi> getAll(){
        List<Provinsi> data = new ArrayList<Provinsi>();
        Cursor c = super.getAll(col);
        c.moveToFirst();
        while(!c.isAfterLast()){
            Provinsi p = new Provinsi();
            p.setId(c.getString(c.getColumnIndex("_id")));
            p.setNama_prop(c.getString(c.getColumnIndex("nama_prop")));

            data.add(p);
            c.moveToNext();
        }
        super.closeDB();
        return data;
    }

    public ArrayList<String> getAllArrayList(){
        ArrayList<String> data = new ArrayList<String>();
        Cursor c = super.getAll(col);
        c.moveToFirst();
        while(!c.isAfterLast()){
            data.add(c.getString(c.getColumnIndex("nama_prop")));
            c.moveToNext();
        }
        super.closeDB();
        return data;
    }






}
