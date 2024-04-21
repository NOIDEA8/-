package com.example.myapplication.Fragment;

import static android.content.Intent.getIntent;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Activities.Change_icon;
import com.example.myapplication.Activities.Change_name;
import com.example.myapplication.Activities.history_show;
import com.example.myapplication.Activities.login;
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
    private TextView change_Icon;
    private TextView history_search;
    private TextView exit;
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
        //change_Icon=view.findViewById(R.id.change_icon);
        history_search=view.findViewById(R.id.history_search);
        exit=view.findViewById(R.id.exit);
        change_name.setOnClickListener(this);
       /* change_Icon.setOnClickListener(this);*/
        history_search.setOnClickListener(this);
        exit.setOnClickListener(this);
        showDetail(mParam2);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change_name:
                changeName();
                break;
           /*case R.id.change_icon:
                Intent intentIcon=new Intent(context, Change_icon.class);
                startActivity(intentIcon);
                break;*/
            case R.id.history_search:
                Intent intenthistory=new Intent(context, history_show.class);
                startActivity(intenthistory);
                break;
            case R.id.exit:
                Intent intentlog=new Intent(context, login.class);
                startActivity(intentlog);
                getActivity().finish();
                break;
            default:
                break;
        }
    }
    @SuppressLint("Range")
    private void showDetail(String newAccount) {
        UsersDatabaseHelper dbhelp = new UsersDatabaseHelper(context, "Users", null, 1);
        SQLiteDatabase db = dbhelp.getWritableDatabase();
        Cursor cursor = db.query("Users", null, "account=?", new String[]{mParam2}, null, null, null);
        int num = cursor.getCount();//问符合条件值个数
        cursor.moveToFirst();
        if (num > 0) {
            name.setText(cursor.getString(cursor.getColumnIndex("name")));
            account.setText(newAccount);
        }
    }

    private void changeName() {
        Intent intent=new Intent(context, Change_name.class);
        intent.putExtra("account",mParam2);
        intent.putExtra("MineFragID",android.os.Process.myPid());
        startActivity(intent);
    }
}