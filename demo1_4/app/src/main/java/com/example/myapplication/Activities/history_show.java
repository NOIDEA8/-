package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.myapplication.Database.HistoryWordDatabaseHelper;
import com.example.myapplication.R;
import com.example.myapplication.RecyclerViews.HistoryWords;
import com.example.myapplication.RecyclerViews.History_words_Adaptor;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class history_show extends AppCompatActivity {
    private List<HistoryWords> history_words;
    private RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    History_words_Adaptor adaptor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_show);
        recyclerView = findViewById(R.id.show_history);
        history_words = new ArrayList<>();
        history_words.clear();
        addDataFromBase();
        initRecycler(history_words);
    }
    private void initRecycler( List<HistoryWords> resouseList) {

        layoutManager = new LinearLayoutManager(this);
        adaptor = new History_words_Adaptor(resouseList, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adaptor);
    }
    @SuppressLint("Range")
    private void addDataFromBase() {
        HistoryWordDatabaseHelper dbHelper = new HistoryWordDatabaseHelper(this, "HistoryWords",
                null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query("HistoryWords", null, null, null, null, null, "id DESC");
            if (cursor.moveToFirst()) {
                do {
                    HistoryWords hw = new HistoryWords();
                    hw.setOrigin(cursor.getString(cursor.getColumnIndex("origin")));
                    hw.setTranslated(cursor.getString(cursor.getColumnIndex("translated")));
                    hw.setFromLanguage(cursor.getString(cursor.getColumnIndex("fromLanguage")));
                    hw.setToLanguage(cursor.getString(cursor.getColumnIndex("toLanguage")));
                    history_words.add(hw);

                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            // Handle the exception here
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        history_words=arrange(history_words);
    }

   private void addNewData(Intent intent) {
        String origin1 = intent.getStringExtra("origin");
        String result1 = intent.getStringExtra("result");
        if (origin1 != null && result1 != null) {
            HistoryWords hw = new HistoryWords();
            hw.setOrigin(intent.getStringExtra("origin"));
            hw.setTranslated(intent.getStringExtra("result"));
            history_words.add(hw);
        }
        history_words=arrange(history_words);
    }

   private List<HistoryWords> arrange(List<HistoryWords> list){
        //temp仅为了排名字
        List<String> temp=new ArrayList<>();
        //最后返回的排好的list
        List<HistoryWords> finalList=new ArrayList<>();
        //取出传入的列表的原句内容
        for(HistoryWords hs:list){
            String stemp=hs.getOrigin();
            temp.add(stemp);
        }
        //排序
        String[] arrays = temp.toArray(new String[temp.size()]);
        List<String> ids=new ArrayList<>();
        Comparator<Object> com = Collator.getInstance(java.util.Locale.CHINA);
        Arrays.sort(arrays, com);
        //排序后与源数据比较后返回处理后的数据源
        for(String s:arrays){
            for(HistoryWords hs1:list){
                String origin=hs1.getOrigin();
                if(origin.equals(s)){
                    if(ids.contains(hs1.getTranslated())){

                    } else{
                        finalList.add(hs1);
                        ids.add(hs1.getTranslated());
                        break;}
                }
            }
        }
        return finalList;
    }
}