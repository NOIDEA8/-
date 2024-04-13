package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class login extends BaseActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Button login=findViewById(R.id.login);
        EditText editAccount=findViewById(R.id.account_edit);
        EditText editPassword=findViewById(R.id.password_edit);
        TextView sign_up=findViewById(R.id.sign_up);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//测试代码979349 116669
                //547894  030286
                if(editAccount.getText().toString().isEmpty()||
                        editPassword.getText().toString().isEmpty()){
                    Toast.makeText(com.example.myapplication.login.this,"请完整填写账号与密码",Toast.LENGTH_SHORT).show();
                }else{
                    checkLogin(editAccount.getText().toString(),editPassword.getText().toString());
                }
            }
        });
       sign_up.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(com.example.myapplication.login.this,sign_up.class);
               startActivity(intent);
           }
       });
    }
    private void checkLogin(String account,String password){
        UsersDatabaseHelper dbHelper=new UsersDatabaseHelper(this,"Users",null,1);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        String selection = "account= ? AND password= ?";
        String[] selectionArgs = new String[]{account,password};
        Cursor cursor = db.query("Users", null, selection, selectionArgs, null, null, null);
        int num=cursor.getCount();//问符合条件值个数
        cursor.moveToFirst();
        if(num==0){
            Toast.makeText(this,"账号或密码错误，请重新输入",Toast.LENGTH_SHORT).show();
        }else{
          @SuppressLint("Range") String name=cursor.getString(cursor.getColumnIndex("name"));
            Intent intent=new Intent(login.this, Mainpage.class);
            intent.putExtra("name",name);
            intent.putExtra("account",account);
            startActivity(intent);
            Toast.makeText(login.this,"您已成功登录",Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}