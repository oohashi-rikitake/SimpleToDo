package com.rokuta96.simpletodo.View;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rokuta96.simpletodo.Model.EntityToDo;
import com.rokuta96.simpletodo.R;
import com.rokuta96.simpletodo.ViewModel.ToDoListItemViewModel;
import com.rokuta96.simpletodo.databinding.ListitemTodoBinding;

import java.util.ArrayList;
import java.util.List;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ViewHolder> {

    public static View.OnClickListener onClickListener;
    private List<EntityToDo> mToDos;

    private ToDoListAdapter(List<EntityToDo> results) {
        if (results == null) {
            this.mToDos = new ArrayList<>();
        } else {
            this.mToDos = results;
        }
    }

    @BindingAdapter("list")
    public static void setList(RecyclerView recyclerView, List<EntityToDo> results) {
        if (recyclerView.getAdapter() == null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));

            // 区切り線を付ける
            recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        }
        ToDoListAdapter adapter = new ToDoListAdapter(results);
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter("flag")
    public static void setFlag(TextView textView, int flag) {
        TextPaint paint = textView.getPaint();
        paint.setFlags(flag);
    }

    @BindingAdapter("srcCompat")
    public static void srcCompat(ImageView view, int resourceId) {
        view.setImageResource(resourceId);
    }

    @Override
    public ToDoListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ToDoListAdapter.ViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.listitem_todo, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ToDoListAdapter.ViewHolder viewHolder, final int i) {
        if (mToDos == null || mToDos.size() <= i || mToDos.get(i) == null) {
            return;
        }
        EntityToDo entityToDo = mToDos.get(i);
        viewHolder.mItemBinding.setItem(entityToDo);
        viewHolder.mItemBinding.getRoot().setTag(entityToDo);
        // アイテム行のクリックイベント
        viewHolder.mItemBinding.getRoot().setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return mToDos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ListitemTodoBinding mItemBinding;
        private ToDoListItemViewModel viewModel;

        private ViewHolder(View view) {
            super(view);
            mItemBinding = DataBindingUtil.bind(view);

            TextView textView = (TextView) view.findViewById(R.id.txtTitle);
            int default_color = textView.getCurrentTextColor();
            int default_flag = textView.getPaintFlags();
            viewModel = ToDoListItemViewModel.newInstance(default_color, default_flag);
            mItemBinding.setViewModel(viewModel);
        }
    }
}
