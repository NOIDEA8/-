package com.example.myapplication.Fragment;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.Database.UsersDatabaseHelper;

public class MineFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private TextView account;
    private TextView name;
    private TextView change_name;
    private Context context;

    public MineFragment() {

    }

    public static MineFragment newInstance(String param1, String param2) {
        MineFragment fragment = new MineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        account = view.findViewById(R.id.detail_account);
        name = view.findViewById(R.id.detail_name);
        change_name = view.findViewById(R.id.change_name);
        change_name.setOnClickListener(this);
        showDetail(mParam1, mParam2);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change_name:
                changeName();
                Toast.makeText(context, "成功", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    private void showDetail(String newName, String newAccount) {
        name.setText(newName);
        account.setText(newAccount);
    }

    private void changeName() {
        UsersDatabaseHelper dbhelp = new UsersDatabaseHelper(context, "Users", null, 1);
        SQLiteDatabase db = dbhelp.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", "大哥");
        db.update("Users", cv, "account=?", new String[]{mParam2});
        cv.clear();
        Cursor cursor = db.query("Users", null, "account=?", new String[]{mParam2}, null, null, null);
        int num = cursor.getCount();//问符合条件值个数
        cursor.moveToFirst();
        if (num > 0) {
            @SuppressLint("Range") String newName = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String newAccount = cursor.getString(cursor.getColumnIndex("account"));
            showDetail(newName, newAccount);
        }
    }


}