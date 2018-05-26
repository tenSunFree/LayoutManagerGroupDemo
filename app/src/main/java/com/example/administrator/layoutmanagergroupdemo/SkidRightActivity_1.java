package com.example.administrator.layoutmanagergroupdemo;


import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dingmouren.layoutmanagergroup.skidright.SkidRightLayoutManager;

public class SkidRightActivity_1 extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private SkidRightLayoutManager mSkidRightLayoutManager;

    private boolean isCgangeState = false;
    private int clickOnTheXCoordinateOfTheScreen;
    private int clickOnTheYCoordinateOfTheScreen;
    private int imgPosition;
    private TextView testTextView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skid_1);

        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;

        /** 讓字體大小自動縮放 */
        testTextView = findViewById(R.id.testTextView);
        testTextView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);                             // 下划线
        testTextView.getPaint().setAntiAlias(true);                                                 // 抗锯齿
        TextViewCompat.setAutoSizeTextTypeWithDefaults(testTextView, TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(testTextView, 5, 100, 1, TypedValue.COMPLEX_UNIT_SP);

        /** 套用ttf格式的字体 */
        String fonts = "fonts/avocet_light.ttf";
        Typeface typeface = Typeface.createFromAsset(getAssets(), fonts);
        testTextView.setTypeface(typeface);
        testTextView.setTypeface(typeface);

        /** 取得自動點擊的座標點 */
        clickOnTheXCoordinateOfTheScreen = width * 2 / 3;
        clickOnTheYCoordinateOfTheScreen = height / 2;

        initView();
    }


    private void initView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mSkidRightLayoutManager = new SkidRightLayoutManager(1.4f, 0.7f);
        mRecyclerView.setLayoutManager(mSkidRightLayoutManager);
        mRecyclerView.setAdapter(new MyAdapter());

        /** 自動點擊可視範圍的第一個Item, 以便初始化相關資料 */
        new Handler().postDelayed(new Runnable() {
            public void run() {
                CustomTouchEvent.setFingerClick(clickOnTheXCoordinateOfTheScreen, clickOnTheYCoordinateOfTheScreen, SkidRightActivity_1.this);
            }
        }, 200);

        /** 當每次滑動完RecyclerView, 會自動點擊可視範圍的第一個Item, 以便初始化相關資料 */
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState != 0) {
                    isCgangeState = true;
                }
                if (isCgangeState == true && newState == 0) {
                    isCgangeState = false;
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            CustomTouchEvent.setFingerClick(clickOnTheXCoordinateOfTheScreen, clickOnTheYCoordinateOfTheScreen, SkidRightActivity_1.this);
                        }
                    }, 200);
                }
            }
        });
    }

    /**
     * 适配器
     */
    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private int[] imgs = {
                R.drawable.image01_kenya,
                R.drawable.image02_italia,
                R.drawable.image03_tasmania,
                R.drawable.image04_columbia,
                R.drawable.image05_argentina,
                R.drawable.image06_namibia,
                R.drawable.image07_sinkiang,
                R.drawable.image08_new_zealand,
                R.drawable.image09_state_of_hawaii,
                R.drawable.image10_south_africa
        };
        private String[] names = {
                " Kenya ", " Italia ", " Tasmania ", " Columbia ", " Argentina ",
                " Namibia ", " Sinkiang ", " New zealand ", " State of hawaii ", " South africa "
        };

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(
                    getApplicationContext()).inflate(R.layout.item_skid_right_1, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            /** 初始化每個Item的圖片 */
            imgPosition = (position + 1) % imgs.length;
            if (imgPosition != 0) {
                Glide.with(getApplicationContext()).load(imgs[imgPosition - 1]).into(holder.imgBg);
            } else if (imgPosition == 0) {
                Glide.with(getApplicationContext()).load(imgs[imgs.length - 1]).into(holder.imgBg);
            }

            /** 初始化每個Item對應的文字 */
            holder.imgBg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgPosition = (position + 1) % imgs.length;
                    if (imgPosition != 0) {
                        testTextView.setText(names[imgPosition - 1]);
                    } else if (imgPosition == 0) {
                        testTextView.setText(names[names.length - 1]);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return imgs.length * 100;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imgBg;

            public ViewHolder(View itemView) {
                super(itemView);
                imgBg = itemView.findViewById(R.id.img_bg);
            }
        }
    }
}
