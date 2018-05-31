package bps.sultra.sutasbarcode.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Pusdiklat-16 on 21/04/2016.
 */
public class Model {
    DBHelper help;
    SQLiteDatabase db;
    Context ctx;
    String tableName;

    public Model(Context ctx) {
        this.ctx = ctx;
        help = new DBHelper(ctx);
//        help.onUpgrade(help.getWritableDatabase(),1,2);
    }

    protected void openRead(){
        this.db = help.getReadableDatabase();
    }

    protected void openWrite(){
        this.db = help.getWritableDatabase();
    }

    protected void closeDB(){
        this.db.close();
    }

    public Cursor getAll(String proj[]){
        this.openRead();
        Cursor c = this.db.query(getTableName(), proj, null, null, null, null, null);
//        this.closeDB();
        return c;
    }

    public void insert(ContentValues cv, String nullable){
        this.openWrite();
        this.db.insert(this.getTableName(), nullable, cv);
        this.closeDB();
    }

    public void delete(String pk, int pk_delete){
        openWrite();
        this.db.delete(getTableName(),
                pk+" like ?",
                new String[]{String.valueOf(pk_delete)});
    }

    //update
    public void update(String pk, int pk_update, ContentValues cv){
        String selection = pk+" = ?";
        String[] args = new String[]{String.valueOf(pk_update)};
        this.openWrite();
        db.update(getTableName(),cv,selection,args);
        this.closeDB();

    }

    //getbyid
    public Cursor getById(String pk, int id_cari, String proj[]){
        this.openRead();
        Cursor c = this.db.query(getTableName(), proj, pk+" = ?", new String[]{String.valueOf(id_cari)}, null, null, null);
//        this.closeDB();
        return c;
    }

    //findbyattr
    public Cursor findByAttr(String col_name, String searchQuery){
        this.openRead();
        searchQuery = "'%"+searchQuery+"%'";
        String selectQ = "select * from %s where %s like %s";
        selectQ = String.format(selectQ, getTableName(), col_name, searchQuery);

        Cursor c = this.db.rawQuery(selectQ, null);
        return c;
    }




    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
