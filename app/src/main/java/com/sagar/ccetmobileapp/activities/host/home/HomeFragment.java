package com.sagar.ccetmobileapp.activities.host.home;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sagar.ccetmobileapp.R;
import com.sagar.ccetmobileapp.services.TokenService;

import javax.inject.Inject;

import androidx.navigation.Navigation;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    @Inject
    TokenService tokenService;

    /*Menu */

    @BindView(R.id.notice_board_card)
    CardView noticeBoardCard;

    @BindView(R.id.assignment_card)
    CardView assignmentsCard;

    @BindView(R.id.syllabus_card)
    CardView syllabusCard;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, inflate);

        noticeBoardCard.setOnClickListener(v -> {
            if (tokenService.hasToken()) {
                Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_noticesFragment);
            } else {
                Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_loginFragment);
            }
        });

        assignmentsCard.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_assignmentsFragment));

        syllabusCard.setOnClickListener(v -> {
            //todo
        });

        return inflate;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.home, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.profile) {
            if (tokenService.hasToken()) {
                showMessage("Already Signed In");
            } else {
                Navigation.findNavController(assignmentsCard).navigate(R.id.action_homeFragment_to_loginFragment);
            }
        } else if (id == R.id.log_out) {
            if (tokenService.hasToken()) {
                tokenService.removeToken();
                showMessage("Signed out");
            } else {
                showMessage("Already signed out");
            }
        }

        return true;
    }

    private void showMessage(String message) {
        Toast.makeText(this.getContext(), message, Toast.LENGTH_SHORT).show();
    }

}
