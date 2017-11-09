package com.lim.lim_labexer3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    EditText etMessage, etFileName;
    SharedPreferences preferences;
    FileOutputStream fos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etMessage = (EditText) findViewById(R.id.et_Message);
        etFileName = (EditText) findViewById(R.id.et_filename);

        preferences = getSharedPreferences("preference", Context.MODE_WORLD_READABLE);
    }
    public void SharedPreferences(View view){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("message", etMessage.getText().toString());
        editor.putString("filename", etFileName.getText().toString());
        editor.commit();

        Toast.makeText(this, "Saved in Shared Preferences!", Toast.LENGTH_LONG).show();
    }

    public void InternalStorage(View view){
        String message = etMessage.getText().toString();
        String filename = etFileName.getText().toString();
        String output = "Internal Storage - The Message: " + message + ", Filename: " + filename;

        try{
            fos = openFileOutput(etFileName.getText().toString(), Context.MODE_PRIVATE);
            fos.write(output.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try{
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Toast.makeText(this, "Saved in Internal Storage!", Toast.LENGTH_LONG).show();
    }
    public void saveInternalCache(View view){
        File folder = getCacheDir();
        File file = new File(folder, etFileName.getText().toString());

        String message = etMessage.getText().toString();

        writeData(file, message);

        Toast.makeText(this, "Successfully written to Internal Cache!", Toast.LENGTH_LONG).show();

    }

    public void saveExternalCache(View view){
        File folder = getExternalCacheDir();
        File file = new File(folder, etFileName.getText().toString());

        String message = etMessage.getText().toString();

        writeData(file, message);

        Toast.makeText(this, "Successfully written to External Cache!", Toast.LENGTH_LONG).show();
    }

    public void saveExternalStorage(View view){
        File folder = getExternalFilesDir("temp");
        File file = new File(folder, etFileName.getText().toString());

        String message = etMessage.getText().toString();

        writeData(file, message);

        Toast.makeText(this, "Successfully written to External Storage!", Toast.LENGTH_LONG).show();
    }

    public void saveExternalPublicStorage(View view){
        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(folder, etFileName.getText().toString());

        String message = etMessage.getText().toString();

        writeData(file, message);

        Toast.makeText(this, "Successfully written to External Public Storage!", Toast.LENGTH_LONG).show();
    }

    public void next(View view){
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
        finish();
    }

    private void writeData(File file, String message) {

        try {
            fos = new FileOutputStream(file);
            fos.write(message.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
