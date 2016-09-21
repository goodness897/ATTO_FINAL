package com.atto.developers.atto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.atto.developers.atto.fragment.MyTradeFragment;
import com.atto.developers.atto.fragment.ProgressDialogFragment;
import com.atto.developers.atto.manager.NetworkManager;
import com.atto.developers.atto.manager.NetworkRequest;
import com.atto.developers.atto.networkdata.tradedata.ListData;
import com.atto.developers.atto.networkdata.tradedata.TradeData;
import com.atto.developers.atto.networkdata.userdata.MyProfile;
import com.atto.developers.atto.networkdata.userdata.MyProfileData;
import com.atto.developers.atto.request.MyProfileRequest;
import com.atto.developers.atto.request.MyTradeListRequest;
import com.bumptech.glide.Glide;
import com.pixplicity.multiviewpager.MultiViewPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Tacademy on 2016-08-26.
 */
public class MyPageActivity extends AppCompatActivity {

    @BindView(R.id.text_mypage_nickname)
    TextView nickNameView;

    @BindView(R.id.pager)
    MultiViewPager pager;

    @BindView(R.id.img_mypage_profile)
    ImageView profileImageView;

    @BindView(R.id.view_mypage_maker_footerview)
    LinearLayout footerLayout;

    private MyTradePagerAdapter adapter;

    ProgressDialogFragment dialogFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);
        ButterKnife.bind(this);

        adapter = new MyTradePagerAdapter(getSupportFragmentManager());
        dialogFragment = new ProgressDialogFragment();
        pager.setAdapter(adapter);
        Intent intent = getIntent();
        int auth = intent.getIntExtra("AUTH", -1);
        if (auth == 1) {
            footerLayout.setVisibility(View.VISIBLE);
        } else {
            footerLayout.setVisibility(View.GONE);
        }
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);

//        tabs.setViewPager(pager);
        initData();
        initToolBar();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initTradeData();

    }

    @OnClick(R.id.btn_mypage_setting_myprofile)
    void onMyPageSettingClick() {
        Intent intent = new Intent(MyPageActivity.this, MyPageSetProfileActivity.class);
        startActivity(intent);
    }

    Intent intent;

    // 제작자일 때만 생기는 페이지
    @OnClick({R.id.btn_footer_move_maker_nego, R.id.btn_footer_move_accept_wait})
    void onMovePage(View view) {
        switch (view.getId()) {

            case R.id.btn_footer_move_maker_nego:
                intent = new Intent(MyPageActivity.this, MyDetailNegoActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_footer_move_accept_wait:
                intent = new Intent(MyPageActivity.this, AcceptWaitActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_my_page_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_setting) {
            Intent intent = new Intent(MyPageActivity.this, MyPageSettingActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle(R.string.activity_my_page);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_navigate_before_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void initData() {

        dialogFragment.show(getSupportFragmentManager(), "progress");

        MyProfileRequest request = new MyProfileRequest(this);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<MyProfile>() {
            @Override
            public void onSuccess(NetworkRequest<MyProfile> request, MyProfile result) {

                MyProfileData myProfileData = result.getData();
                String nickname = myProfileData.getMember_alias();
                String image_url = myProfileData.getMember_profile_img();
                if (image_url != null)
                    checkSetImage(image_url);
                if (!TextUtils.isEmpty(nickname))
                    nickNameView.setText(nickname);
                dialogFragment.dismiss();
                Log.d("MyPageActivity", "success : " + nickname);

            }

            @Override
            public void onFail(NetworkRequest<MyProfile> request, int errorCode, String errorMessage, Throwable e) {
                Log.e("error", request + " , " + errorCode + " , " + errorMessage);
                dialogFragment.dismiss();
                Toast.makeText(MyPageActivity.this, "fail" + errorCode, Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void checkSetImage(String url) {

        Glide.with(this).load(url).bitmapTransform(new CropCircleTransformation(this)).into(profileImageView);

    }

    private void initTradeData() {

        adapter.clear();
        MyTradeListRequest request = new MyTradeListRequest(this, "1", "30");
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<ListData<TradeData>>() {

            @Override
            public void onSuccess(NetworkRequest<ListData<TradeData>> request, ListData<TradeData> result) {

                TradeData[] tradeData = result.getData();
                if (tradeData != null) {
                    if (tradeData.length > 0)
                        adapter.addAll(Arrays.asList(tradeData));
                    Log.d(this.toString(), "성공 : " + tradeData[0].getTrade_id());

                }
            }

            @Override
            public void onFail(NetworkRequest<ListData<TradeData>> request, int errorCode, String errorMessage, Throwable e) {
                Log.d(this.toString(), "실패 errorCode : " + errorCode);

            }
        });

    }

    public class MyTradePagerAdapter extends FragmentStatePagerAdapter {

        List<TradeData> items = new ArrayList<>();

        public void addAll(List<TradeData> list) {
            items.addAll(list);
            notifyDataSetChanged();
        }

        public void clear() {
            items.clear();
            notifyDataSetChanged();
        }

        public MyTradePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return String.valueOf(position);
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Fragment getItem(int position) {
            return MyTradeFragment.newInstance(items.get(position));
        }

    }
}