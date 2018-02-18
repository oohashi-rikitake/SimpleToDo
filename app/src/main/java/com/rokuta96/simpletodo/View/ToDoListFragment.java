package com.rokuta96.simpletodo.View;


import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.rokuta96.simpletodo.Model.EntityToDo;
import com.rokuta96.simpletodo.R;
import com.rokuta96.simpletodo.ViewModel.ToDoListViewModel;
import com.rokuta96.simpletodo.databinding.FragmentToDoListBinding;

public class ToDoListFragment extends Fragment {

    private ToDoListViewModel viewModel;

    public static ToDoListFragment newInstance() {
        return new ToDoListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_to_do_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.app_name);

        FragmentToDoListBinding binding = DataBindingUtil.bind(view);
        viewModel = ToDoListViewModel.newInstance(getActivity());
        binding.setViewModel(viewModel);

        binding.setOnClickListenerAdd(v -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, ToDoEditFragment.newInstance(null))
                    .addToBackStack(null)
                    .commit();
        });

        ToDoListAdapter.onClickListener = v -> {
            EntityToDo entityToDo = (EntityToDo) v.getTag();

            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, ToDoEditFragment.newInstance(entityToDo))
                    .addToBackStack(null)
                    .commit();
        };

        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            viewModel.refresh();
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        viewModel.onPause();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
        setSortIcon(menu.findItem(R.id.menu_sort));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();
        switch (id) {
            case R.id.menu_sort:
                viewModel.onSort();
                setSortIcon(item);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setSortIcon(MenuItem item) {
        if (viewModel.isSort) {
            Drawable drawable = item.getIcon();
            drawable.setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        } else {
            Drawable drawable = item.getIcon();
            drawable.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
        }
    }
}
