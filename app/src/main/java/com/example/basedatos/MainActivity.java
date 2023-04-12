package com.example.basedatos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText et1,et2,et3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1 = (EditText)findViewById(R.id.et1);
        et2 = (EditText)findViewById(R.id.et2);
        et3 = (EditText)findViewById(R.id.et3);
    }
    //Metodo dar de alta los productos
    public void Registrar (View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        //abrimos en modo lectura y escritura
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String codigo = et1.getText().toString();
        String descripcion = et2.getText().toString();
        String precio = et3.getText().toString();

        if(!codigo.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty() ){
            ContentValues registro = new ContentValues();

            registro.put("codigo", codigo);
            registro.put("descripcion", descripcion);
            registro.put("precio", precio);

            //Insertamos dentro de la tabla los registros
            BaseDeDatos.insert("articulos", null,registro);

            //Cerramos
            BaseDeDatos.close();

            et1.setText("");
            et2.setText("");
            et3.setText("");

            Toast.makeText(this, "El producto se ha guardado correctamente", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Debes rellenar todos los campos", Toast.LENGTH_LONG).show();
        }
    }
    //Metodo para buscar articulos
    public void buscar (View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String codigo = et1.getText().toString();

        if (!codigo.isEmpty()){
            Cursor fila = BaseDeDatos.rawQuery("select descripcion, precio from articulos where codigo="+codigo, null);

            if (fila.moveToFirst()){
                et2.setText(fila.getString(0));
                et3.setText(fila.getString(1));

                BaseDeDatos.close();
            }else{
                et2.setText("");
                et3.setText("");
                Toast.makeText(this, "No se ha encontrado el articulo", Toast.LENGTH_SHORT).show();
                BaseDeDatos.close();
            }

        }else {
            Toast.makeText(this, "Debes introducir el codigo del producto", Toast.LENGTH_SHORT).show();
        }
    }
    //Metdodo elimina producto
    public void eliminar (View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null,1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        String codigo = et1.getText().toString();
        if(!codigo.isEmpty()){
            int cantidad = BaseDeDatos.delete("articulos","codigo="+ codigo,null);
            BaseDeDatos.close();

            if (cantidad==1){
                Toast.makeText(this, "El articulo ha sido eliminado", Toast.LENGTH_SHORT).show();
                et1.setText("");
                et2.setText("");
                et3.setText("");
            }else{
                Toast.makeText(this, "El articulo no existe", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Debes introducir el codigo del producto", Toast.LENGTH_SHORT).show();
        }
    }
    public void modificar (View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administacion",null,1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        String codigo = et1.getText().toString();
        String descripcion = et2.getText().toString();
        String precio = et3.getText().toString();

        if (!codigo.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()){
            ContentValues registro = new ContentValues();
            registro.put("codigo",codigo);
            registro.put("descripcion",descripcion);
            registro.put("precio",precio);

            int cantidad = BaseDeDatos.update("articulos", registro, "codigo="+codigo, null);
            BaseDeDatos.close();

            if (cantidad==1){
                Toast.makeText(this, "El articulo ha sido modificado", Toast.LENGTH_SHORT).show();
                et1.setText("");
                et2.setText("");
                et3.setText("");
            }else{
                Toast.makeText(this, "El articuo no existe", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Debes rellenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
}