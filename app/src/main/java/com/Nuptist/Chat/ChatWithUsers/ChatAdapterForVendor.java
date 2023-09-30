package com.Nuptist.Chat.ChatWithUsers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Nuptist.Chat.ChatAdapter;
import com.Nuptist.Chat.ChatModelNew;
import com.Nuptist.Chat.MessageModel;
import com.Nuptist.R;
import com.Nuptist.Session.Session;
import com.Nuptist.databinding.ReciverLayoutBinding;
import com.Nuptist.databinding.SenderLayoutBinding;

import java.util.ArrayList;

public class ChatAdapterForVendor extends RecyclerView.Adapter{

    int SENDER = 1 ;
    int RECIVER = 2 ;
    Session session ;


    Context context ;
    ArrayList<ChatModelNew> models;

    public ChatAdapterForVendor(Context context, ArrayList<ChatModelNew> models) {
        this.context = context;
        this.models = models;
        session = new Session(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == SENDER){
            return  new SenderViewHolder(LayoutInflater.from(context).inflate(R.layout.sender_layout,parent,false));
        }
        else {
            return  new ReciverViewHolder(LayoutInflater.from(context).inflate(R.layout.reciver_layout,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        if(holder.getClass() == SenderViewHolder.class){
            ((SenderViewHolder)holder).binding.senderMessage.setText(models.get(position).getMessage());
            ((SenderViewHolder)holder).binding.senderTime.setText(models.get(position).getTime());
        }else {
            ((ReciverViewHolder)holder).binding.reciverMessage.setText(models.get(position).getMessage());
            ((ReciverViewHolder)holder).binding.reciverTime.setText(models.get(position).getTime());
        }

    }

    @Override
    public int getItemCount() {
        return models.size();
    }


    @Override
    public int getItemViewType(int position) {
        if(models.get(position).getSenderID().equalsIgnoreCase(session.getUserId_Vendor())) {
            return  SENDER ;
        }
        else{
            return  RECIVER;
        }
    }

    public  class  ReciverViewHolder extends RecyclerView.ViewHolder{
        ReciverLayoutBinding binding ;
        public ReciverViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ReciverLayoutBinding.bind(itemView);
        }
    }


    public  class  SenderViewHolder extends RecyclerView.ViewHolder{
        SenderLayoutBinding binding;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SenderLayoutBinding.bind(itemView);
        }
    }

}
