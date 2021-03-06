package com.example.photoviewer.main;

import com.example.photoviewer.R;
import com.example.photoviewer.lib.Info;
import com.example.photoviewer.lib.PhotoView;
import com.example.photoviewer.lib.PhotoViewer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;


/**
 * Created by liuheng on 2015/8/19.
 */
public class PhotoViewerActivity extends Activity {

    int[] imgs = new int[]{R.drawable.aaa, R.drawable.bbb, R.drawable.ccc, R.drawable.ddd, R.drawable.ic_launcher, R.drawable.image003};

    GridView gv;

    View mParent;
    View mBg;
    PhotoView mPhotoView;
    Info mInfo;
    
    PhotoViewer pvViewer;
    private ViewPager mViewPager;
    View mViewBg;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_photo_viewpager);

        pvViewer=(PhotoViewer)findViewById(R.id.acitivity_photo_frame);
        
        gv = (GridView) findViewById(R.id.gv);
        gv.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return imgs.length;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                PhotoView p = new PhotoView(PhotoViewerActivity.this);
                p.setLayoutParams(new AbsListView.LayoutParams((int) (getResources().getDisplayMetrics().density * 100), (int) (getResources().getDisplayMetrics().density * 100)));
                p.setScaleType(ImageView.ScaleType.CENTER_CROP);
                p.setImageResource(imgs[position]);
                // 把PhotoView当普通的控件把触摸功能关掉
                p.disenable();
                return p;
            }
        });

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                mPhotoView.setImageResource(imgs[position]);
            	//跳转当前position，去除跳转滚动动画
            	pvViewer.show(position);
            }
        });
        
        pvViewer.setViewer(imgs);
        pvViewer.setAnimation(true);
    }

    @Override
    public void onBackPressed() {
        if (pvViewer.isShow()==true) {
        	pvViewer.hide();
        } else {
            super.onBackPressed();
        }
    }
}
