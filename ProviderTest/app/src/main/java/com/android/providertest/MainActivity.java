package com.android.providertest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private String newId;//新的ID


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//加入视图
        //添加数据
        Button addData = findViewById(R.id.btn_add_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //添加数据
                Uri uri = Uri.parse("content://com.android.databasetest.provider/book");//content://权限/路径
                ContentValues values = new ContentValues();
                values.put("name","A Clash of Kings");
                values.put("author","George Marith");
                values.put("pages",1040);
                values.put("price",55.55);
                Uri newUri = getContentResolver().insert(uri,values);
                newId = newUri.getPathSegments().get(1);

            }
        });
      //查询数据
        Button querryData = findViewById(R.id.btn_querry_data);
        querryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://com.android.databasetest.provider/book");
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                if(cursor != null){
                    while(cursor.moveToNext()){
                        int id = cursor.getInt(cursor.getColumnIndex("id"));
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        //debug模式下的日志查看
                        Log.d("MainActivity","book Id is : "+id);
                        Log.d("MainActivity","book name is "+name);
                        Log.d("MainActivity","book author is "+author);
                        Log.d("MainActivity","book pages is "+pages);
                        Log.d("MainActivity","book price is "+price);
                    }
                    cursor.close();
                }
            }
        });

        //更新数据
        Button updateData = findViewById(R.id.btn_update_data);
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://com.android.databasetest.provider/book/"+newId);
                ContentValues values = new ContentValues();
                values.put("name","A Storm of Swords");
                values.put("pages",1216);
                values.put("author","duyangge");
                values.put("price",12.56);
                getContentResolver().update(uri,values,null,null);
            }
        });

        //删除数据
        Button deleteData = findViewById(R.id.btn_delete_data);
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://com.android.databasetest.provider/book/"+newId);
                getContentResolver().delete(uri,null,null);
            }
        });
    }
}
