package com.htnsoft.todolist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Database database;
    ListView lvcongviec;
    ArrayList<CongViec> arrayCongViec;
    CongViecAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvcongviec = (ListView) findViewById(R.id.listviewcongviec);
        arrayCongViec = new ArrayList<>();
        adapter = new CongViecAdapter(this,R.layout.dong_cong_viec, arrayCongViec);
        lvcongviec.setAdapter(adapter);

        //Khoi ta database ghi chu
        database = new Database(this,"Ghichu.sqlite",null,1);
        //Tao bang cong viec
        database.QueryData("CREATE TABLE IF NOT EXISTS CongViec(Id INTEGER PRIMARY KEY AUTOINCREMENT, TenCV VARCHAR(250))");

        //INSERT DATA
       // database.QueryData("INSERT INTO CongViec VALUES(null,'Lam bai tap android')");

        GetDataCongViec();

    }

    private void GetDataCongViec(){
        //Select Data
        Cursor dataCongViec = database.GetData("SELECT * FROM CongViec");
        arrayCongViec.clear();
        while (dataCongViec.moveToNext()){
            String ten= dataCongViec.getString(1);
            int id = dataCongViec.getInt(0);
            arrayCongViec.add(new CongViec(id,ten));
        }
        adapter.notifyDataSetChanged();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_congviec, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuAdd){
            DialogThem();
        }
        return super.onOptionsItemSelected(item);
    }

    private  void DialogThem(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_them_cong_viec1);

        final EditText edtTen = dialog.findViewById(R.id.edtThemCV);
        Button btnThem = dialog.findViewById(R.id.btnThemCV);
        Button btnHuy = dialog.findViewById(R.id.btnHuyCV);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tencv = edtTen.getText().toString();
                if (tencv.equals("")){
                    Toast.makeText(MainActivity.this, "Vui lòng nhập công việc",Toast.LENGTH_SHORT).show();
                }else {
                    database.QueryData("INSERT INTO CongViec VALUES (null, '"+tencv+"')");
                    Toast.makeText(MainActivity.this,"Đã Thêm", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    GetDataCongViec();
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public  void  DialogSuaCV(String ten, final int id){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sua_cong_viec);
        final EditText edtTenCV = (EditText) dialog.findViewById(R.id.edtSuaCV);
        Button btnSuaCV = (Button) dialog.findViewById(R.id.btnSuaCV);
        Button btnHuy  = (Button) dialog.findViewById(R.id.btnHuy);

        edtTenCV.setText(ten);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        btnSuaCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenMoi = edtTenCV.getText().toString().trim();
                database.QueryData("UPDATE CongViec SET TenCV = '"+tenMoi+"' WHERE Id = '"+ id +"'");
                Toast.makeText(MainActivity.this,"Đã cập nhật",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                GetDataCongViec();
            }
        });
        dialog.show();

    }

    public void DialogXoaCV(final String tencv, final int id){
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn có mốn xóa công việc" + tencv + "không ?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                database.QueryData("DELETE FROM CongViec WHERE Id = '"+ id +"'");
                Toast.makeText(MainActivity.this,"Đã xóa" + tencv, Toast.LENGTH_SHORT).show();
                GetDataCongViec();
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogXoa.show();
    }
}
