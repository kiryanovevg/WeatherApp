package com.kiryanov.weatherapp.adapters;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter<T, P> extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private P presenter;
    private List<T> itemList;
    private int layout;

    public RecyclerViewAdapter(int layout, P presenter) {
        this.layout = layout;
        this.presenter = presenter;
        itemList = new ArrayList<>();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;

        ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        private ViewDataBinding getBinding() {
            return binding;
        }

    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        final T item = itemList.get(position);

        holder.getBinding().setVariable(BR.item, item);
        holder.getBinding().setVariable(BR.presenter, presenter);
        holder.getBinding().executePendingBindings();
    }
    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public List<T> getItemList() {
        return itemList;
    }

    public void add(T item) {
        itemList.add(item);
        notifyItemChanged(itemList.size() - 1);
    }

    public void addAll(List<T> addedList) {
        itemList.addAll(addedList);
        notifyDataSetChanged();
    }

    public void addAllWithClear(List<T> requestsList) {
        itemList.clear();
        itemList.addAll(requestsList);
        notifyDataSetChanged();
    }

    public void setList(List<T> list) {
        itemList = list;
        notifyDataSetChanged();
    }

    public void clear() {
        itemList.clear();
    }

}
