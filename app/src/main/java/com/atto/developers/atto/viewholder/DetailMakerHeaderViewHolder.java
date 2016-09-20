package com.atto.developers.atto.viewholder;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.atto.developers.atto.AddPortActivity;
import com.atto.developers.atto.R;
import com.atto.developers.atto.networkdata.makerdata.MakerData;
import com.bumptech.glide.Glide;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by goodn on 2016-08-31.
 */
public class DetailMakerHeaderViewHolder extends RecyclerView.ViewHolder {

    private static final int REQUEST_FOR_PORTFOLIO = 1000;

    @BindView(R.id.img_detail_maker_profile)
    ImageView profileImageView;

    @BindView(R.id.text_detail_maker_nickname)
    TextView nickNameView;

    @BindView(R.id.ratingbar_detail_maker_grade)
    RatingBar ratingBar;

    @BindView(R.id.text_detail_maker_intro)
    TextView introView;

    @BindView(R.id.text_detail_maker_keyword_one)
    TextView categoryOneView;

    @BindView(R.id.text_detail_maker_keyword_two)
    TextView categoryTwoView;

    @BindView(R.id.btn_add_port)
    TextView addPortView;


    Random r = new Random();

    MakerData makerData;

    private static final String TAG = "HeaderViewHolder";

    public DetailMakerHeaderViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
//        statusView.bringToFront();
    }


    public void setHeaderData(MakerData makerData, boolean isMakerMe) {
        this.makerData = makerData;

        if (makerData != null) {
            Glide.with(itemView.getContext()).load(makerData.getMaker_representation_img())
                    .bitmapTransform(new CropCircleTransformation(itemView.getContext())).into(profileImageView);
            nickNameView.setText(makerData.getMaker_name());
            ratingBar.setRating(makerData.getMaker_score());
            introView.setText(makerData.getMaker_line_tag());
            categoryOneView.setText(makerData.getMaker_product_category_info()[0] + "");
            categoryTwoView.setText(makerData.getMaker_product_category_info()[1] + "");
            if (isMakerMe) { // 제작자가 본인일 경우
                addPortView.setVisibility(View.VISIBLE);

            }
        }
    }

    @OnClick(R.id.btn_add_port)
    public void addPort() {
        Intent intent = new Intent(itemView.getContext(), AddPortActivity.class);
        ((Activity) itemView.getContext()).startActivityForResult(intent, REQUEST_FOR_PORTFOLIO);

    }
}
