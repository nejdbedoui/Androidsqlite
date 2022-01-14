package com.example.revision;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Edit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        EditText nom=(EditText) findViewById(R.id.nom);
        EditText dis=(EditText) findViewById(R.id.dis);
        Button btn=(Button) findViewById(R.id.btn);
        Intent intent=getIntent();
        nom.setText(intent.getStringExtra("nom"));
        dis.setText(""+(intent.getIntExtra("dis",-1)));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("nom1",nom.getText().toString());
                intent.putExtra("dis1",dis.getText().toString());
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}