package com.example.bankbloodproject;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

public abstract class parentActivity  extends AppCompatActivity implements View.OnClickListener {
    protected AppCompatActivity activity;
    private int menuId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isFullScreen()) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        if (hideInputeType()) {
            hideInputtype();
        }
        setContentView(getLayoutResource());
        activity = this;


        // initialize item
        init();

    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }


    protected abstract int getLayoutResource();

    protected abstract boolean isFullScreen();

    protected abstract void init();

    protected abstract boolean isEnableBack();


    protected abstract boolean hideInputeType();


    public void createOptionsMenu(int menuId) {
        this.menuId = menuId;
        invalidateOptionsMenu();
    }

    /**
     * function is used to create a menu
     */
    public void removeOptionsMenu() {
        menuId = 0;
        invalidateOptionsMenu();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (menuId != 0) {
            getMenuInflater().inflate(menuId, menu);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * finish the connection to database
     */
    @Override
    protected void onStop() {
        super.onStop();
    }


    public void hideInputtype() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

    }

    // to override typekit in all activities we use

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

}
