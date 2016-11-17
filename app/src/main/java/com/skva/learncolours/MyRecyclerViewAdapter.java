package com.skva.learncolours;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAdapter extends
        RecyclerView.Adapter<MyRecyclerViewAdapter.ItemHolder> {

    private List<String> itemsName;
    private List<Integer> itemsValue;
    private LayoutInflater layoutInflater;
    private MyRecyclerViewAdapter.OnItemClickListener onItemClickListener;
    private Context context;

    public MyRecyclerViewAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        itemsName = new ArrayList<String>();
        itemsValue = new ArrayList<Integer>();
    }

    @Override
    public MyRecyclerViewAdapter.ItemHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemCardView = layoutInflater.inflate(R.layout.layout_cardview, viewGroup, false);
        return new ItemHolder(itemCardView, this);
    }

    @Override
    public void onBindViewHolder(MyRecyclerViewAdapter.ItemHolder itemHolder, int i) {

        int value = itemsValue.get(i);
        itemHolder.setItemValue(String.valueOf(value));
        Drawable drawable = context.getResources().getDrawable(value);
        itemHolder.setImageView(drawable);
    }

    @Override
    public int getItemCount() {
        return itemsName.size();
    }

    public void add(int location, String iName, int iValue) {
        itemsName.add(location, iName);
        itemsValue.add(location, iValue);
        notifyItemInserted(location);
    }

    public void remove(int location) {
        if (location >= itemsName.size())
            return;

        itemsName.remove(location);
        itemsValue.remove(location);
        notifyItemRemoved(location);
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(ItemHolder item, int position);
    }


    public static class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        private MyRecyclerViewAdapter parent;
        private View cardView;

        public ItemHolder(View cView, MyRecyclerViewAdapter parent) {
            super(cView);
            cardView = cView;
            cView.setOnClickListener(this);
            this.parent = parent;

            imageView = (ImageView) cardView.findViewById(R.id.item_image);
        }


        public void setItemValue(CharSequence val) {

        }


        public void setImageView(Drawable drawable) {
            imageView.setImageDrawable(drawable);
        }

        @Override
        public void onClick(View v) {
            final OnItemClickListener listener = parent.getOnItemClickListener();
            if (listener != null) {
                listener.onItemClick(this, getPosition());
            }
        }

    }
}