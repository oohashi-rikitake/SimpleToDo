package com.rokuta96.simpletodo.ViewModel;

import android.databinding.ObservableInt;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.CompoundButton;

import com.rokuta96.simpletodo.Model.EntityToDo;

public class ToDoListItemViewModel {

    public ObservableInt color = new ObservableInt();
    public ObservableInt flag = new ObservableInt();
    private int default_color;
    private int default_flag;

    public static ToDoListItemViewModel newInstance(int default_color, int default_flag) {
        ToDoListItemViewModel viewModel = new ToDoListItemViewModel();
        viewModel.init(default_color, default_flag);
        return viewModel;
    }

    private void init(int default_color, int default_flag) {
        this.default_color = default_color;
        this.default_flag = default_flag;

        this.color.set(this.default_color);
        this.flag.set(this.default_flag);
    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        View v = (View) compoundButton.getParent();
        EntityToDo entityToDo = (EntityToDo) v.getTag();
        entityToDo.isChecked = isChecked;

        if (isChecked) {
            // グレーにして取り消し線を引く
            color.set(Color.LTGRAY);
            flag.set(default_flag | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            color.set(default_color);
            flag.set(default_flag);
        }
    }
}
