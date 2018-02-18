package com.rokuta96.simpletodo.Model;

import java.io.Serializable;

public class EntityToDo implements Serializable {
    public int id;
    public int priority;
    public String title;

    public boolean isChecked;

    public int getPriorityColor() {
        switch (this.priority) {
            case 1:
                return android.R.color.darker_gray;
            case 3:
                return android.R.color.transparent;
            case 5:
                return android.R.color.holo_orange_dark;
        }
        return android.R.color.transparent;
    }

}
