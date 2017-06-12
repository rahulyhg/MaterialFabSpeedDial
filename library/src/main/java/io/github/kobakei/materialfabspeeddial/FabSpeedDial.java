package io.github.kobakei.materialfabspeeddial;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by keisukekobayashi on 2017/06/12.
 */

public class FabSpeedDial extends FrameLayout {

    private FloatingActionButton fabMain;
    private LinearLayout menuContainer;
    private View touchGuard;
    private List<View> itemViews = new ArrayList<>();

    private boolean isOpened = false;

    public FabSpeedDial(@NonNull Context context) {
        super(context);
        initLayout(context, null, 0);
    }

    public FabSpeedDial(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initLayout(context, attrs, 0);
    }

    public FabSpeedDial(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout(context, attrs, defStyleAttr);
    }

    private void initLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fab_speed_dial, this, false);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.BOTTOM | Gravity.END;
        addView(view, params);

        fabMain = (FloatingActionButton) findViewById(R.id.fab_main);
        fabMain.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpened) {
                    closeMenu();
                } else {
                    openMenu();
                }
            }
        });

        menuContainer = (LinearLayout) findViewById(R.id.menu_container);
        for (int i = 0; i < 3; i++) {
            View itemView = inflater.inflate(R.layout.fab_speed_dial_item, menuContainer, false);
            FloatingActionButton miniFab = (FloatingActionButton) itemView.findViewById(R.id.fab_mini);
            miniFab.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            TextView label = (TextView) itemView.findViewById(R.id.text);
            label.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            menuContainer.addView(itemView);
            itemViews.add(itemView);
        }

        touchGuard = findViewById(R.id.touch_guard);
        touchGuard.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMenu();
            }
        });

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FabSpeedDial, defStyleAttr, 0);

        // TODO
        int menuId = ta.getResourceId(R.styleable.FabSpeedDial_fab_menu, 0);

        ta.recycle();
    }

    public void openMenu() {
        fabMain.animate().rotation(45.0f)
                .setDuration(300L)
                .start();

        for (int i = itemViews.size() - 1; i >= 0; i--) {
            View itemView = itemViews.get(i);

            itemView.setAlpha(0.0f);
            itemView.setTranslationY(24.0f);

            itemView.animate()
                    .translationY(0.0f)
                    .alpha(1.0f)
                    .setDuration(100L)
                    .setStartDelay((itemViews.size() - 1 - i) * 50L)
                    .start();
        }

        menuContainer.setVisibility(View.VISIBLE);
        touchGuard.setVisibility(View.VISIBLE);
        isOpened = true;
    }

    public void closeMenu() {
        fabMain.animate().rotation(0.0f)
                .setDuration(300L)
                .start();

        menuContainer.setVisibility(View.GONE);
        touchGuard.setVisibility(View.GONE);
        isOpened = false;
    }

    public void show() {
        fabMain.show();
    }

    public void hide() {
        fabMain.hide();
    }

    public boolean isShown() {
        return fabMain.isShown();
    }
}