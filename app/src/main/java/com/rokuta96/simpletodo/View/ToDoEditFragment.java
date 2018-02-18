package com.rokuta96.simpletodo.View;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rokuta96.simpletodo.Model.EntityToDo;
import com.rokuta96.simpletodo.R;
import com.rokuta96.simpletodo.ViewModel.ToDoEditViewModel;
import com.rokuta96.simpletodo.databinding.FragmentToDoEditBinding;

public class ToDoEditFragment extends Fragment {

    private static final String PARAM_TODO = "PARAM_TODO";
    private EntityToDo mEntityToDo;

    public static ToDoEditFragment newInstance(EntityToDo entityToDo) {
        ToDoEditFragment fragment = new ToDoEditFragment();
        Bundle args = new Bundle();
        args.putSerializable(PARAM_TODO, entityToDo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mEntityToDo = (EntityToDo) getArguments().getSerializable(PARAM_TODO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_to_do_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mEntityToDo == null) {
            getActivity().setTitle(R.string.title_todoadd);
        } else {
            getActivity().setTitle(R.string.title_todoupdate);
        }

        FragmentToDoEditBinding binding = DataBindingUtil.bind(view);
        ToDoEditViewModel viewModel = ToDoEditViewModel.newInstance(getActivity(), mEntityToDo);
        binding.setViewModel(viewModel);

        binding.setOnClickListenerAdd(v -> {
            viewModel.onClickListenerAdd();

            getActivity().getSupportFragmentManager().popBackStack();
        });
    }
}
