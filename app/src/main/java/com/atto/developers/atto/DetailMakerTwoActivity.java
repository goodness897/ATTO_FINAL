package com.atto.developers.atto;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.atto.developers.atto.fragment.AttoFragment;
import com.atto.developers.atto.fragment.RealTimeTradeFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.noties.scrollable.CanScrollVerticallyDelegate;
import ru.noties.scrollable.OnScrollChangedListener;
import ru.noties.scrollable.ScrollableLayout;

public class DetailMakerTwoActivity extends AppCompatActivity {


    private static final String ARG_LAST_SCROLL_Y = "arg.LastScrollY";

    private ScrollableLayout mScrollableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_maker_two);

        final View header = findViewById(R.id.header);
        final TabsLayout tabs = (TabsLayout)findViewById(R.id.tabs);

        mScrollableLayout = (ScrollableLayout) findViewById(R.id.scrollable_layout);
        mScrollableLayout.setDraggableView(tabs);
        mScrollableLayout.setFriction(0.5F);
        final ViewPager viewPager = (ViewPager)findViewById(R.id.view_pager);
        final ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), getResources(), getFragments());
        viewPager.setAdapter(adapter);

        tabs.setViewPager(viewPager);

// Note this bit, it's very important
        mScrollableLayout.setCanScrollVerticallyDelegate(new CanScrollVerticallyDelegate() {
            @Override
            public boolean canScrollVertically(int direction) {
                return adapter.canScrollVertically(viewPager.getCurrentItem(), direction);
            }
        });

        mScrollableLayout.setOnScrollChangedListener(new OnScrollChangedListener() {
            @Override
            public void onScrollChanged(int y, int oldY, int maxY) {

                // Sticky behavior
                final float tabsTranslationY;
                if (y < maxY) {
                    tabsTranslationY = .0F;
                } else {
                    tabsTranslationY = y - maxY;
                }

                tabs.setTranslationY(tabsTranslationY);

                header.setTranslationY(y / 2);
            }
        });

    }
    private List<BaseFragment> getFragments() {

        final FragmentManager manager = getSupportFragmentManager();
        final List<BaseFragment> list = new ArrayList<>();

        AttoFragment scrollViewFragment
                = (AttoFragment) manager.findFragmentByTag(RecyclerViewFragment.TAG);
        if (scrollViewFragment == null) {
            scrollViewFragment = AttoFragment.newInstance();
        }

        RealTimeTradeFragment recyclerViewFragment
                = (RealTimeTradeFragment) manager.findFragmentByTag(RecyclerViewFragment.TAG);
        if (recyclerViewFragment == null) {
            recyclerViewFragment = RealTimeTradeFragment.newInstance();
        }

        Collections.addAll(list, scrollViewFragment, recyclerViewFragment);

        return list;
    }
}
