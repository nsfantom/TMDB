package tm.fantom.tmdb.ui;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentTransaction;

import tm.fantom.tmdb.R;
import tm.fantom.tmdb.databinding.ActivityMainBinding;
import tm.fantom.tmdb.ui.base.BaseActivity;
import tm.fantom.tmdb.ui.base.BaseFragment;
import tm.fantom.tmdb.ui.dashboard.DashboardFragment;
import tm.fantom.tmdb.ui.detail.MovieDetailFragment;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding layout;
    private BaseFragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setUiOptions();
        super.onCreate(savedInstanceState);
        layout = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(layout.getRoot());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            layout.getRoot().setSystemUiVisibility(layout.getRoot().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        if (savedInstanceState == null) {
            setTheme(R.style.AppTheme);
            setUiOptions();
            navigateTo(Fragments.DASHBOARD);
        }
    }

    private void setUiOptions() {
        View decorView = getWindow().getDecorView();
        int uiOptions = getUIOption();
        decorView.setSystemUiVisibility(uiOptions);
    }

    private int getUIOption() {
        if (Build.VERSION.SDK_INT == 21
                || Build.VERSION.SDK_INT == 22) {
            return View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        } else {
            return View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        }
    }

    public void navigateTo(@Fragments int fragments) {
        BaseFragment fragment = null;
        hideProgress();

        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            layout.mainRoot.setBackgroundResource(R.color.colorBackgroundLight);
        }
        switch (fragments) {
            case Fragments.DASHBOARD:
                fragment = DashboardFragment.newInstance();
                break;
            case Fragments.DETAILS:
                fragment = MovieDetailFragment.newInstance();
                break;

        }
        if (fragment != null) {
            if (currentFragment != null && currentFragment.getFragmentTag().equals(fragment.getFragmentTag())) {
                return;
            }

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            if (fragment.needBackStack()) {
                fragment.setRetainInstance(true);
                fragmentTransaction.addToBackStack(fragment.getFragmentTag());
            }
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                fragmentTransaction.add(layout.container.getId(), fragment);
            } else {
                fragmentTransaction.replace(layout.container.getId(), fragment);
            }
            fragmentTransaction.commitAllowingStateLoss();
            currentFragment = fragment;
        }
    }

    public void setCurrentFragment(BaseFragment currentFragment) {
        this.currentFragment = currentFragment;
    }

    @Override
    public void onBackPressed() {
        if (currentFragment != null) {
            if (currentFragment instanceof DashboardFragment) {
                finish();
            } else {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }
}
