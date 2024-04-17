package com.example.myapplication.RecyclerViews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class History_words_Adaptor extends RecyclerView.Adapter<History_words_Adaptor.ViewHolder> {
    private List<HistoryWords> mwordsList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView origin;
        TextView translated;
        public ViewHolder(View view) {
            super(view);
            origin=view.findViewById(R.id.origin);
            translated=view.findViewById(R.id.translated);
        }
    }//建立内部类viewfolder继承自recyclerview

    public History_words_Adaptor(List<HistoryWords> mwordsList) {
        this.mwordsList = mwordsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.history_words,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }//为viewholder建模
    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        HistoryWords words=mwordsList.get(position);
        holder.origin.setText(words.getOrigin());
        holder.translated.setText(words.getTranslated());
    }//为viewfolder赋值
    @Override
    public int getItemCount(){
        return mwordsList.size();
    }//展示内容容量
}

