package com.atto.developers.atto;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.atto.developers.atto.fragment.CompleteTradeFragment;
import com.atto.developers.atto.fragment.MakerFragment;
import com.atto.developers.atto.fragment.PortfolioFragment;
import com.atto.developers.atto.manager.PropertyManager;
import com.atto.developers.atto.networkdata.makerdata.MakerData;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import ru.noties.scrollable.CanScrollVerticallyDelegate;
import ru.noties.scrollable.OnScrollChangedListener;
import ru.noties.scrollable.ScrollableLayout;

public class DetailMakerTwoActivity extends AppCompatActivity {


    private static final int REQUEST_MAKER_UPDATE = 2000;
    private static final String ARG_LAST_SCROLL_Y = "arg.LastScrollY";
    private static final int REQUEST_FOR_PORTFOLIO = 1000;

    @BindView(R.id.img_detail_maker_profile)
    ImageView profileImageView;

    @BindView(R.id.text_detail_maker_nickname)
    TextView nickNameView;

    @BindView(R.id.ratingbar_detail_maker_grade)
    RatingBar ratingBar;

    @BindView(R.id.text_detail_maker_intro)
    TextView introView;

    @BindView(R.id.btn_add_port)
    TextView addPortView;

    private String makerNickName;
    private int maker_id;
    private MakerData makerData;
    private ScrollableLayout mScrollableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_maker_two);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        makerData = (MakerData) intent.getSerializableExtra(MakerFragment.MAKER);
        makerNickName = PropertyManager.getInstance().getNickName();
        Log.d("DetailMakerTwoActivity", "저장 닉네임 : " + makerNickName);
        Log.d("DetailMakerTwoActivity", "메이커 닉네임 : " + makerData.getMaker_name());
        maker_id = makerData.getMaker_id();

        initData(makerData);

        final View header = findViewById(R.id.header);
        final TabsLayout tabs = (TabsLayout) findViewById(R.id.tabs);

        mScrollableLayout = (ScrollableLayout) findViewById(R.id.scrollable_layout);
        mScrollableLayout.setDraggableView(tabs);
        mScrollableLayout.setFriction(0.5F);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
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

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle(R.string.activity_detail_maker);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_navigate_before_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private List<BaseFragment> getFragments() {

        final FragmentManager manager = getSupportFragmentManager();
        final List<BaseFragment> list = new ArrayList<>();

        PortfolioFragment portfolioFragment
                = (PortfolioFragment) manager.findFragmentByTag(RecyclerViewFragment.TAG);
        if (portfolioFragment == null) {
            portfolioFragment = PortfolioFragment.newInstance(maker_id);
        }

        CompleteTradeFragment completeTradeFragment
                = (CompleteTradeFragment) manager.findFragmentByTag(RecyclerViewFragment.TAG);
        if (completeTradeFragment == null) {
            completeTradeFragment = CompleteTradeFragment.newInstance(maker_id);
        }

        Collections.addAll(list, portfolioFragment, completeTradeFragment);

        return list;
    }

    public void setMakerData(MakerData makerData, boolean isMakerMe) {

        if (makerData != null) {
            Glide.with(this).load(makerData.getMaker_representation_img())
                    .bitmapTransform(new CropCircleTransformation(this)).into(profileImageView);
            nickNameView.setText(makerData.getMaker_name());
            ratingBar.setRating(makerData.getMaker_score());
            introView.setText(makerData.getMaker_line_tag());
            if (isMakerMe) { // 제작자가 본인일 경우
                addPortView.setVisibility(View.VISIBLE);

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        if (makerData != null) {
            if (makerData.getMaker_name().equals(PropertyManager.getInstance().getNickName())) {
                getMenuInflater().inflate(R.menu.activity_maker_ud_menu, menu);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_update) {
            Intent intent = new Intent(DetailMakerTwoActivity.this, UpdateMakerActivity.class);
            if (makerData != null) {
                intent.putExtra("makerData", makerData);
                startActivityForResult(intent, REQUEST_MAKER_UPDATE);
            }
        }

        return super.onOptionsItemSelected(item);
    }


    private void initData(MakerData makerData) {
        initToolBar();
        if (makerData != null) {
            if (makerData.getMaker_name().equals(makerNickName)) {
                Log.d("DetailMakerActivity", "제작자 동일 : " + makerData.getMaker_id());
                setMakerData(makerData, true);
            } else {
                Log.d("DetailMakerActivity", "제작자 다름 : " + makerData.getMaker_id());
                setMakerData(makerData, false);
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        switch (requestCode) {

            case REQUEST_MAKER_UPDATE:
                if (resultCode == RESULT_OK) {
                    makerData = (MakerData) intent.getSerializableExtra("makerData");
                    setMakerData(makerData, true);

                }
                break;
        }
    }

    @OnClick(R.id.btn_add_port)
    public void addPort() {
        Intent intent = new Intent(DetailMakerTwoActivity.this, AddPortActivity.class);
        startActivityForResult(intent, REQUEST_FOR_PORTFOLIO);

    }
}
