package com.example.listenandlearn;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class QAdapter extends RecyclerView.Adapter<QAdapter.MyViewHolder> {
    private List<QList> qList;
    private Context context;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        holder.qname.setText(qList.get(i).getQname());
        holder.qno.setText(Integer.toString(qList.get(i).getQno()));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context,ViewQuestions.class));
            }
        });
    }

    public QAdapter(List<QList> qList,Context context) {
        this.qList=qList;this.context=context;
    }

    @Override
    public int getItemCount() {
        return qList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView qno,qname;
        CardView cardView;

        public MyViewHolder(View v) {
            super(v);
            qno = v.findViewById(R.id.qno);
            qname = v.findViewById(R.id.qname);
            cardView=v.findViewById(R.id.card);
        }
    }

}
