package com.example.lockpattern;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Formulario extends AppCompatActivity {

    EditText jetI, jetN;
    Button jbnA, jbnL,jbnM, jbnE,jbnS;
    TextView jtvL;
    SQLiteDatabase sqld;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        jetI = (EditText) findViewById(R.id.xetI);
        jetN = (EditText) findViewById(R.id.xetN);
        jbnA = (Button) findViewById(R.id.xbnA);
        jbnE = (Button) findViewById(R.id.xbnE);
        jbnS = (Button) findViewById(R.id.xbnS);
        final DbmsSQLiteHelper dsqlh = new DbmsSQLiteHelper(this, "DBContactos", null, 1);
        sqld = dsqlh.getWritableDatabase();
        actualizaLista();
        jbnA.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String id = jetI.getText().toString();
                String nombre = jetN.getText().toString();
                ContentValues cv = new ContentValues();
                //cv.put("id", id);
                cv.put("nombre", nombre);
                sqld.insert("Contactos", null, cv);
                jetI.setText("");
                jetN.setText("");
                actualizaLista();
            }
        });
        jbnE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues cv = new ContentValues();
                String id = jetI.getText().toString();
                sqld.delete("Contactos","id=?",new String[]{id});
                jetI.setText("");
                jetN.setText("");
                actualizaLista();
            }
        });
        jbnS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
    }

    protected void actualizaLista(){

        String id, nombre;
        Cursor c = sqld.rawQuery("SELECT id,nombre FROM Contactos", null);
        //jtvL.setText("");

        //tabla
        LinearLayout myRoot = (LinearLayout) findViewById(R.id.xll1);
        myRoot.removeViewAt(8);
        TableLayout table = new TableLayout(this);
        TableRow tr = new TableRow(this);
        tr.setBackgroundColor(Color.LTGRAY);
        //tr.setLayoutParams(new ViewGroup.LayoutParams());
        tr.setPadding(15, 5, 15, 5);


        TableRow.LayoutParams llp = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        //llp.setMargins(10, 10, 10, 10);

        //celda
        LinearLayout cell = new LinearLayout(this);
        //cell.setBackgroundColor(Color.WHITE);
        cell.setLayoutParams(llp);
        TextView tv = new TextView(this);
        tv.setText("ID");
        tv.setPadding(15, 5, 15, 5);
        cell.addView(tv);
        tr.addView(cell);

        //celda
        cell = new LinearLayout(this);
        //cell.setBackgroundColor(Color.WHITE);
        cell.setLayoutParams(llp);
        tv = new TextView(this);
        tv.setText("Nombre");
        tv.setPadding(15, 5, 15, 5);
        cell.addView(tv);
        tr.addView(cell);


        table.addView(tr);

        if (c.moveToFirst()) {
            do {
                id = c.getString(0);
                nombre = c.getString(1);
                //jtvL.append(" " + id + "\t" + nombre + "\n");
                tr = new TableRow(this);
                tr.setBackgroundColor(Color.WHITE);
                tr.setPadding(15, 5, 15, 5);

                llp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT);
                llp.setMargins(0,0,0,0);
                //celda
                cell = new LinearLayout(this);
                //cell.setBackgroundColor(Color.WHITE);
                cell.setLayoutParams(llp);
                tv = new TextView(this);
                tv.setText(id);
                tv.setPadding(15, 5, 15, 5);
                cell.addView(tv);
                tr.addView(cell);
                //celda
                cell = new LinearLayout(this);
                cell.setBackgroundColor(Color.WHITE);
                cell.setLayoutParams(llp);
                tv = new TextView(this);
                tv.setText(nombre);
                tv.setPadding(15, 5, 15, 5);
                cell.addView(tv);
                tr.addView(cell);

                table.addView(tr);
            } while (c.moveToNext());
        }

        myRoot.addView(table);
    }
}