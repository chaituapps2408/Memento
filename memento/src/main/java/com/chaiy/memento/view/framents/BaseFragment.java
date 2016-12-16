package com.chaiy.memento.view.framents;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;
import android.view.View;

import com.chaiy.memento.R;

/**
 * Created by Chaiy on 12/4/2016.
 */

public class BaseFragment extends Fragment {

    protected boolean isLandscape;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public boolean isLandscape() {
        return isLandscape;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        isLandscape = getResources().getBoolean(R.bool.is_landscape);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }
}
