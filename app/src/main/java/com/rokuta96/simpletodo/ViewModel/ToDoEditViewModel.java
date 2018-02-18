package com.rokuta96.simpletodo.ViewModel;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.ObservableBoolean;
import android.view.View;
import android.widget.AdapterView;

import com.rokuta96.simpletodo.Model.EntityToDo;
import com.rokuta96.simpletodo.Model.Logger;
import com.rokuta96.simpletodo.Model.RepositoryService;
import com.rokuta96.simpletodo.Model.Util;
import com.rokuta96.simpletodo.R;

public class ToDoEditViewModel {

    public int id;
    public String title;
    public int priority;
    public int spinnerPriorityPosition;
    public String btnText;
    public ObservableBoolean btn_enabled = new ObservableBoolean();
    private boolean is_add;
    private Context context;

    public static ToDoEditViewModel newInstance(Context context, EntityToDo entityToDo) {
        ToDoEditViewModel viewModel = new ToDoEditViewModel();
        viewModel.init(context, entityToDo);
        return viewModel;
    }

    private void init(Context context, EntityToDo entityToDo) {
        this.context = context;
        if (entityToDo == null) {
            is_add = true;
            initAdd(context);
        } else {
            is_add = false;
            initEdit(context, entityToDo);
        }
    }

    private void initAdd(Context context) {
        btnText = context.getString(R.string.btn_add);
        spinnerPriorityPosition = 1;
    }

    private void initEdit(Context context, EntityToDo entityToDo) {
        id = entityToDo.id;
        title = entityToDo.title;
        priority = entityToDo.priority;
        btnText = context.getString(R.string.btn_update);
        selectSpinnerPriorityDefault(context);
        setBtnEnabled();
    }

    private void selectSpinnerPriorityDefault(Context context) {
        TypedArray array = context.getResources().obtainTypedArray(R.array.todo_priority_listvalue);
        String priority_str = String.valueOf(priority);
        for(int i=0; i<array.length(); ++i) {
            if (array.getString(i).equals(priority_str)) {
                spinnerPriorityPosition = i;
                break;
            }
        }
    }

    public void onItemSelectedPriority(AdapterView<?> adapterView, View view, int i, long l) {
        TypedArray array = view.getResources().obtainTypedArray(R.array.todo_priority_listvalue);
        String value = array.getString(i);
        if (Util.isNumber(value)) {
            priority = Integer.parseInt(value);
        }
    }

    public void onTextChangedTitle(CharSequence s, int start, int before, int count) {
        title = s.toString();
        setBtnEnabled();
    }

    private void setBtnEnabled() {
        boolean is_enable = !title.isEmpty();
        btn_enabled.set(is_enable);
    }

    public void onClickListenerAdd() {
        Logger.i("title=%s,priority=%d", title, priority);
        if (is_add) {
            RepositoryService.startActionAdd(context, priority, title);
        } else {
            RepositoryService.startActionUpdate(context, id, priority, title);
        }
    }
}
