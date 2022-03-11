package com.example.micontentproviderusar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText txtCodigo, txtNombre, txtDescripcion;
    Button btnInsertar, btnEditar, btnEliminar, btnBuscar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtCodigo=findViewById(R.id.txtCodigo);
        txtNombre=findViewById(R.id.txtNombre);
        txtDescripcion=findViewById(R.id.txtDescripcion);
        btnInsertar=findViewById(R.id.btnInsertar);
        btnEditar=findViewById(R.id.btnEditar);
        btnEliminar=findViewById(R.id.btnEliminar);
        btnBuscar=findViewById(R.id.btnBuscar);
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscar();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar();
            }
        });
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editar();
            }
        });
        btnInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertar();
            }
        });
    }
    public void buscar(){
        String codigo=txtCodigo.getText().toString();
        if(!codigo.isEmpty()){
            String[] projeccion={
                Contrato.Producto.CODIGO,
                Contrato.Producto.NOMBRE,
                Contrato.Producto.DESCRIPCION
            };
            String where="codigo = "+codigo;
            String[] whereArgs={codigo};
            Cursor cursor=getContentResolver().query(
                    Contrato.Producto.CONTENT_URI,
                    projeccion,
                    where,
                    null,
                    null
            );
            if(cursor.getCount()>0){
                while (cursor.moveToNext()){
                    txtCodigo.setText(cursor.getString(0));
                    txtNombre.setText(cursor.getString(1));
                    txtDescripcion.setText(cursor.getString(2));
                }
            }else {
                Toast.makeText(getBaseContext(),"No se ha encontrado",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getBaseContext(),"No se puede eliminar",Toast.LENGTH_SHORT).show();
        }
    }
    public void eliminar(){
        String codigo=txtCodigo.getText().toString();
        if(!codigo.isEmpty()){
            String where="codigo = ?";
            String[] whereArgs={codigo};
            int cantidad=getContentResolver().delete(
                    Contrato.Producto.CONTENT_URI,
                    where,
                    whereArgs
            );
            if (cantidad>0){
                Toast.makeText(getBaseContext(),"Se ha eliminado el registro", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getBaseContext(),"No existe el registro", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getBaseContext(),"Es necesario el codigo",Toast.LENGTH_SHORT).show();
        }
    }
    public void editar(){
        String nombre=txtNombre.getText().toString();
        String codigo=txtCodigo.getText().toString();
        String descripcion=txtDescripcion.getText().toString();

        if(!nombre.isEmpty()||!codigo.isEmpty()||!descripcion.isEmpty()){
            ContentValues contentValues=new ContentValues();
            contentValues.put(Contrato.Producto.CODIGO,txtCodigo.getText().toString());
            contentValues.put(Contrato.Producto.NOMBRE,txtNombre.getText().toString());
            contentValues.put(Contrato.Producto.DESCRIPCION,txtDescripcion.getText().toString());
            String where="Codigo = ?";
            String[] whereArgs={codigo};
            int cantidad=getContentResolver().update(
                    Contrato.Producto.CONTENT_URI,
                    contentValues,
                    where,
                    whereArgs
            );
            if (cantidad>0){
                Toast.makeText(getBaseContext(),"Se ha editado el registro", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getBaseContext(),"No existe el registro", Toast.LENGTH_SHORT).show();
            }
            txtNombre.setText("");
            txtDescripcion.setText("");
            txtCodigo.setText("");
        }else {
            Toast.makeText(getBaseContext(),"Llene todos los campors", Toast.LENGTH_SHORT).show();
        }

    }
    public void insertar(){
        String nombre=txtNombre.getText().toString();
        String codigo=txtCodigo.getText().toString();
        String descripcion=txtDescripcion.getText().toString();

        if(!nombre.isEmpty()||!codigo.isEmpty()||!descripcion.isEmpty()){
            ContentValues contentValues=new ContentValues();
            contentValues.put(Contrato.Producto.CODIGO,txtCodigo.getText().toString());
            contentValues.put(Contrato.Producto.NOMBRE,txtNombre.getText().toString());
            contentValues.put(Contrato.Producto.DESCRIPCION,txtDescripcion.getText().toString());
            Uri uri=getContentResolver().insert(
                    Contrato.Producto.CONTENT_URI,
                    contentValues
            );
            txtNombre.setText("");
            txtDescripcion.setText("");
            txtCodigo.setText("");
            Toast.makeText(getBaseContext(),uri.toString(), Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(getBaseContext(),"Llene todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
}