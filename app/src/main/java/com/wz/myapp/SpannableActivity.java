package com.wz.myapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.widget.TextView;

import widget.spannal.LinkTouchMoveMethod;
import widget.spannal.UserSpan;

/**
 * Created by dell on 2015/12/25.
 */
public class SpannableActivity extends AppCompatActivity {
    TextView textView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spannable);
        textView = (TextView) findViewById(R.id.tv);

        SpannableStringBuilder stringBuilder = new SpannableStringBuilder("小明回复了小红");
        //设置点击后的颜色为透明，否则会一直出现高亮

//        ForegroundColorSpan span = new ForegroundColorSpan(Color.BLUE);


//        stringBuilder.setSpan(userSpan, 0, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        SpannableString spannableString = new SpannableString("小明回复了小红：ellen你这是怎么了啊，啊啊啊啊啊啊啊啊啊");
        UserSpan userSpan = new UserSpan("哈哈",this,textView);

        UserSpan xiaohong = new UserSpan("",this,textView);

        spannableString.setSpan(userSpan,0,2,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        spannableString.setSpan(xiaohong, 5, 7, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        textView.setHighlightColor(Color.TRANSPARENT);
        //设置点击后的颜色为透明，否则会一直出现高亮
        textView.setText(spannableString);
        textView.setMovementMethod(new LinkTouchMoveMethod());



    }
}
