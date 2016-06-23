package com.wz.myapp;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import widget.spannal.CommentText;
import widget.spannal.ContentSpan;
import widget.spannal.LinkTouchMoveMethod;
import widget.spannal.UserSpan;

/**
 * Created by dell on 2015/12/25.
 */
public class SpannableActivity extends AppCompatActivity {
    TextView textView ;
    CommentText commentText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spannable);
        textView = (TextView) findViewById(R.id.tv);
        commentText = (CommentText) findViewById(R.id.comment_text);
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder("小明回复了小红");
        //设置点击后的颜色为透明，否则会一直出现高亮

//        ForegroundColorSpan span = new ForegroundColorSpan(Color.BLUE);


//        stringBuilder.setSpan(userSpan, 0, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        SpannableString spannableString = new SpannableString("小明回复了小红：ellen你这是怎么了啊，啊啊啊啊啊啊啊啊啊");
        UserSpan userSpan = new UserSpan("哈哈",this,textView);

        UserSpan xiaohong = new UserSpan("",this,textView);

        spannableString.setSpan(userSpan,0,2,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        spannableString.setSpan(xiaohong, 5, 7, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        ContentSpan all = new ContentSpan("",this,textView);
        spannableString.setSpan(all, 8, spannableString.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        textView.setHighlightColor(Color.TRANSPARENT);
        //设置点击后的颜色为透明，否则会一直出现高亮
        textView.setText(spannableString);
        textView.setMovementMethod(new LinkTouchMoveMethod());



        SpannableStringBuilder builder = new SpannableStringBuilder("日子只能往前走，一个方向顺时针，回忆永远不改变，是不停的改变");

        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(SpannableActivity.this,"click 1",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.RED);
                ds.setUnderlineText(false);


            }
        },0,3,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);




        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(SpannableActivity.this,"click 2",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.RED);
                ds.setUnderlineText(false);


            }
        },6,9,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);



        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(SpannableActivity.this, "click 3", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.RED);
                ds.setUnderlineText(false);
                ds.bgColor= Color.TRANSPARENT;
                ds.linkColor= Color.TRANSPARENT;



            }
        }, 11, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        builder.setSpan(new UserSpan(null,this,commentText),20,25,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        commentText.setText(builder);
        commentText.setMovementMethod(LinkMovementMethod.getInstance());

        commentText.setClickListener(new CommentText.CommonContentClick() {
            @Override
            public void onClick() {
                Toast.makeText(SpannableActivity.this, "click common content", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
