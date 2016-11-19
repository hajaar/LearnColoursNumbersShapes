package com.skva.learncolours;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

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
        Log.d("MyRecycleViewAdapter", "onBind " + itemsName.get(i));
        itemHolder.setItemValue(itemsName.get(i));
        itemHolder.setImageView(drawable);
    }

    @Override
    public int getItemCount() {
        return itemsName.size();
    }

    public void add(int location, String iName, int iValue) {
        Log.d("MyRecycleViewAdapter", "add " + iName + " " + iValue);
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
        TextView textView;
        private MyRecyclerViewAdapter parent;
        private View cardView;

        public ItemHolder(View cView, MyRecyclerViewAdapter parent) {
            super(cView);
            cardView = cView;
            cView.setOnClickListener(this);
            this.parent = parent;
            textView = (TextView) cardView.findViewById(R.id.item_text);
            imageView = (ImageView) cardView.findViewById(R.id.item_image);
        }


        public void setItemValue(CharSequence val) {
            Animation animLeft =
                    AnimationUtils.loadAnimation(parent.context,
                            R.anim.appear);
            textView.setAnimation(animLeft);
            textView.setText(val);
        }


        public void setImageView(Drawable drawable) {
            Animation animLeft =
                    AnimationUtils.loadAnimation(parent.context,
                            R.anim.appear);
            imageView.setAnimation(animLeft);
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