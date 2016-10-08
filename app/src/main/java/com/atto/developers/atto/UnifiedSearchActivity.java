package com.atto.developers.atto;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.atto.developers.atto.fragment.SearchResultMakerFragment;
import com.atto.developers.atto.fragment.SearchResultPortFragment;
import com.atto.developers.atto.fragment.SearchResultTradeFragment;
import com.gigamole.navigationtabstrip.NavigationTabStrip;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UnifiedSearchActivity extends AppCompatActivity {

    @BindView(R.id.edit_input_search)
    AppCompatEditText inputSearchView;

    @BindView(R.id.pager)
    ViewPager pager;

    SearchResultPagerAdapter mAdapter;
    private NavigationTabStrip mNavigationTabStrip;


    private final static String TAG = "UnifiedSearchActivity";
    private String keyword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unified_search);
        ButterKnife.bind(this);
        mNavigationTabStrip = (NavigationTabStrip) findViewById(R.id.nts);
        initToolBar();

    }

    private void setUI() {

        pager.setAdapter(mAdapter);
        mNavigationTabStrip.setTabIndex(0, true);
        mNavigationTabStrip.setViewPager(pager, 0);
        mNavigationTabStrip.setAnimationDuration(300);
    }

    @OnClick(R.id.btn_search)
    public void onSearch() {
        keyword = "";
        keyword = inputSearchView.getText().toString();
        mAdapter = new SearchResultPagerAdapter(getSupportFragmentManager(), keyword);
        setUI();

    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle(R.string.activity_unified_search);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_navigate_before_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public class SearchResultPagerAdapter extends FragmentPagerAdapter {

        public static final int PAGE_COUNT = 3;
        String keyword;

        public SearchResultPagerAdapter(FragmentManager fm, String keyword) {
            super(fm);
            this.keyword = keyword;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return SearchResultPortFragment.newInstance(keyword);
                case 1:
                    return SearchResultTradeFragment.newInstance(keyword);
                case 2:
                    return SearchResultMakerFragment.newInstance(keyword);
                default:
                    return SearchResultPortFragment.newInstance(keyword);
            }
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "tab" + position;
        }
    }
}
