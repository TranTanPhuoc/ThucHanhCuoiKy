package com.example.a12_19531531_trantanphuoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a12_19531531_trantanphuoc.adapter.Adapter;
import com.example.a12_19531531_trantanphuoc.model.KhoaHoc;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListKhoaHocMain extends AppCompatActivity {
    private ListView listView;
    private Button btnSave,btnUpd;
    private EditText txtTenKhoaHoc;
    private EditText txtGiaTien;
    private DatabaseReference mDatabase;
    private int i = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_khoa_hoc_main);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        txtTenKhoaHoc = findViewById(R.id.txtTenKhoaHocMain);
        txtGiaTien = findViewById(R.id.txtSoTienMain);
        btnSave = findViewById(R.id.btnSave);
        btnUpd = findViewById(R.id.btnUpdate);
        btnUpd.setEnabled(false);
        listView = findViewById(R.id.listView);
        layDuLieu();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenChiTieu = txtTenKhoaHoc.getText().toString().trim();
                String soTien = txtGiaTien.getText().toString().trim();
                layDuLieu();
                ThemDuLieu(i, tenChiTieu, soTien);
                ++i;
            }
        });
        Intent intent = getIntent();
        if(intent != null) {
            String id = intent.getStringExtra("id");
            String name = intent.getStringExtra("tenKhoaHoc");
            String money = intent.getStringExtra("giaTien");
//            Toast.makeText(this, ""+money, Toast.LENGTH_SHORT).show();

            txtTenKhoaHoc.setText(name);
            txtGiaTien.setText(money);
            btnUpd.setEnabled(true);

            btnUpd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String _name  = txtTenKhoaHoc.getText().toString().trim();
                    String _money = txtGiaTien.getText().toString().trim();

                    ThemDuLieu(Integer.parseInt(id), _name, _money);
                    layDuLieu();
                }
            });


            if(intent.getStringExtra("del") != null) {
                mDatabase.child("khoaHoc").child(id).removeValue();
                layDuLieu();
            }
        }
        else{
            Toast.makeText(this, "Nó bằng null", Toast.LENGTH_SHORT).show();
        }
    }


    public void layDuLieu(){
        List<KhoaHoc> list = new ArrayList<>();
        mDatabase.child("khoaHoc").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot sn : snapshot.getChildren()) {
                    KhoaHoc khoaHoc = sn.getValue(KhoaHoc.class);
                    list.add(khoaHoc);
                }

//                i = list.get(list.size()-1).getId() + 1;
                loadDuLieu(list);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void ThemDuLieu(int id, String ten, String giaTien) {
        KhoaHoc khoaHoc = new KhoaHoc(id, ten, giaTien);

        mDatabase.child("khoaHoc").child(khoaHoc.getId() + "").setValue(khoaHoc);
    }
    public void loadDuLieu(List<KhoaHoc> list){
        txtTenKhoaHoc.setText("");
        txtGiaTien.setText("");
        Adapter adapter = new Adapter(this, R.layout.item, list);
        listView.setAdapter(adapter);
    }
}