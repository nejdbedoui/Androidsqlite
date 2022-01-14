package com.example.revision;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayAdapter<Planete> ad;
ArrayList<Planete> mlist;
private static final int code_appel_act1=1;
    private static final int code_appel_act2=2;
    DatabaseHelpers db=new DatabaseHelpers(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      //  Resources res=getResources();
       // final String nom[]=res.getStringArray(R.array.nom);
       // final int dis[]=res.getIntArray(R.array.dis);
       // mlist=new ArrayList<Planete>();
       // for (int i=0;i< nom.length;i++){
       //     mlist.add(new Planete(nom[i],dis[i]));
       // }
        mlist=new ArrayList<Planete>();
        viewData();
        ad=new ArrayAdapter<Planete>(this,android.R.layout.simple_list_item_1,android.R.id.text1,mlist);
        ListView lv=(ListView) findViewById(R.id.list1);
        lv.setAdapter(ad);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               String nom= mlist.get(position).getNom();
               int dis= mlist.get(position).getDis();
                Intent intent = new Intent(MainActivity.this,Edit.class);
                intent.putExtra("nom",nom);
                intent.putExtra("dis",dis);
                intent.putExtra("pos", mlist.get(position).getId());
                startActivityForResult(intent,code_appel_act1);
            }
        });
        Button btn=(Button) findViewById(R.id.ajouter);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Ajout.class);
                startActivityForResult(intent,code_appel_act2);
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
               // mlist.remove(position);
               // lv.setAdapter(ad);
                db.deleteData(mlist.get(position).getId());
                viewData();
                ad.notifyDataSetChanged();
                return false;
            }
        });
    }

    private void viewData() {
        mlist.clear();
        Cursor cursor = db.viewData();
        if (cursor.getCount()==0){
            Toast.makeText(this, "NO DATA FOUND", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                mlist.add(new Planete(cursor.getInt(0),cursor.getString(1),cursor.getInt(2)));
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Planete p;
        ListView lv=(ListView) findViewById(R.id.list1);
        switch (requestCode){
            case code_appel_act1:
                p = new Planete(0,data.getStringExtra("nom1"),Integer.parseInt(data.getStringExtra("dis1")));
                //mlist.get(data.getIntExtra("pos",-1)).setNom(p.getNom());
                //mlist.get(data.getIntExtra("pos",-1)).setDis(p.getDis());
                //ad=new ArrayAdapter<Planete>(this,android.R.layout.simple_list_item_1,android.R.id.text1,mlist);
                //lv.setAdapter(ad);
                db.updateData(data.getIntExtra("pos",-1),p.getNom(),p.getDis());
                ad.notifyDataSetChanged();
                viewData();
                break;
            case code_appel_act2:
                p = new Planete(0,data.getStringExtra("nom1"),Integer.parseInt(data.getStringExtra("dis1")));
                if(!p.getNom().equals("") && db.insert(p.getNom(),p.getDis())){
                    Toast.makeText(MainActivity.this,"data added",Toast.LENGTH_SHORT).show();
                    mlist.add(new Planete(0,p.getNom(),p.getDis()));
                    lv.setAdapter(ad);
                    viewData();
                }else {
                    Toast.makeText(MainActivity.this,"data not added",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}