package bps.sultra.sutasbarcode;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class SentActivity extends AppCompatActivity {
    String barcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        barcode = getIntent().getStringExtra("code");
//        Toast.makeText(getApplicationContext(), barcode,  Toast.LENGTH_LONG).show();
        showDokDialog(this);
    }

    private void showDokDialog(final Context context) {
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.dialog_jumlah_dok, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setView(promptsView);

        final EditText edit_blok = (EditText) promptsView.findViewById(R.id.editblok);
        final EditText edit_l1 = (EditText) promptsView.findViewById(R.id.editl1);
        final EditText edit_l2 = (EditText) promptsView.findViewById(R.id.editl2);

        edit_blok.setText(barcode);


        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Kirim",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
//                                String username = edit_name.getText().toString();
//                                String password = edit_password.getText().toString();
//                                ModelLogin modelLogin = new ModelLogin(context);
//                                Login login;
//
//                                if((username.equals("bps7401")&&password.equals("bps7401")||(username.equals("userbps")&&password.equals("userbps")))){
//                                    login = modelLogin.getById(1);
//                                    login.setFlag(1);
//                                    modelLogin.update(login);
//                                    Toast.makeText(context, "Login Succesful", Toast.LENGTH_SHORT).show();
//
//                                }else{
//                                    Toast.makeText(context, "Incorrect username or password", Toast.LENGTH_SHORT).show();
//                                }
//
//                                initializeLogin(context);


                            }
                        })
                .setNegativeButton("Batal",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
