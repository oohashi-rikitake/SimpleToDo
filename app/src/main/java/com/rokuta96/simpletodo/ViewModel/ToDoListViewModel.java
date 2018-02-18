package com.rokuta96.simpletodo.ViewModel;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableInt;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.rokuta96.simpletodo.Model.EntityToDo;
import com.rokuta96.simpletodo.Model.RepositoryService;
import com.rokuta96.simpletodo.Model.SimpleToDoApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ToDoListViewModel {

    public ObservableArrayList<EntityToDo> list = new ObservableArrayList<>();
    public ObservableInt progressBarVisibility = new ObservableInt(View.VISIBLE);
    public ObservableInt emptyVisibility = new ObservableInt(View.GONE);
    public boolean isSort;
    private Context context;

    private Handler handlerLoad = new Handler(msg -> {
        Bundle bundle = msg.getData();
        SimpleToDoApi.Repositories repositories = (SimpleToDoApi.Repositories) bundle.getSerializable(RepositoryService.LOAD_RESULT_MESSAGE);
        list.addAll(repositories.Items);
        sort();
        return true;
    });

    private Handler handlerLoadCompleted = new Handler(msg -> {
        progressBarVisibility.set(View.GONE);
        if (list.size() <= 0) {
            emptyVisibility.set(View.VISIBLE);
        }
        return true;
    });

    public static ToDoListViewModel newInstance(Context context) {
        ToDoListViewModel viewModel = new ToDoListViewModel();
        viewModel.init(context);
        return viewModel;
    }

    private void init(Context context) {
        this.context = context;
        RepositoryService.handlerLoad = handlerLoad;
        RepositoryService.handlerLoadCompleted = handlerLoadCompleted;
    }

    public void onResume() {
        progressBarVisibility.set(View.VISIBLE);
        list.clear();
        RepositoryService.startActionLoad(context);
    }

    public void onPause() {
        for (EntityToDo entityToDo : list) {
            if (entityToDo.isChecked) {
                RepositoryService.startActionDelete(context, entityToDo.id);
            }
        }
    }

    public void refresh() {
        onPause();
        onResume();
    }

    public void onSort() {
        isSort = !isSort;
        sort();
    }

    private void sort() {
        if (isSort) {
            sortPriority();
        } else {
            sortId();
        }
    }

    private void sortId() {
        List<EntityToDo> tempList = new ArrayList<>();
        tempList.addAll(list);

        // ID降順にソート
        Collections.sort(tempList, (o1, o2) -> {
            if (o1.id < o2.id) {
                return 1;
            } else if (o1.id > o2.id) {
                return -1;
            }
            return 0;
        });

        list.clear();
        list.addAll(tempList);
    }

    private void sortPriority() {
        List<EntityToDo> tempList = new ArrayList<>();
        tempList.addAll(list);

        // 優先降順にソート
        Collections.sort(tempList, (o1, o2) -> {
            if (o1.priority < o2.priority) {
                return 1;
            } else if (o1.priority > o2.priority) {
                return -1;
            }
            return 0;
        });

        list.clear();
        list.addAll(tempList);
    }
}
