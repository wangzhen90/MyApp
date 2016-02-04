
package widget.ExpandleLayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.wz.myapp.R;

public class ExpandableLayoutItem extends RelativeLayout {
    public Boolean isAnimationRunning = false;
    private Boolean isOpened = false;
    private Integer duration;
    private LinearLayout contentLayout;
    private FrameLayout headerLayout;
    private Boolean closeByUser = true;
    private ImageView arrow;

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
        if (expandable) {
            arrow.setVisibility(View.VISIBLE);
        } else {
            arrow.setVisibility(View.GONE);

        }

    }

    private boolean expandable = false;


    public ExpandableLayoutItem(Context context) {
        super(context);
    }

    public ExpandableLayoutItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ExpandableLayoutItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(final Context context, AttributeSet attrs) {
        final View rootView = View.inflate(context, R.layout.expandable_view, this);
        headerLayout = (FrameLayout) rootView.findViewById(R.id.view_expandable_headerlayout);
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableLayout);
        final int headerID = typedArray.getResourceId(R.styleable.ExpandableLayout_el_headerLayout, -1);
        final int contentID = typedArray.getResourceId(R.styleable.ExpandableLayout_el_contentLayout, -1);
        contentLayout = (LinearLayout) rootView.findViewById(R.id.view_expandable_contentLayout);

        if (headerID == -1 || contentID == -1)
            throw new IllegalArgumentException("HeaderLayout and ContentLayout cannot be null!");

        if (isInEditMode())
            return;

        duration = typedArray.getInt(R.styleable.ExpandableLayout_el_duration, getContext().getResources().getInteger(android.R.integer.config_shortAnimTime));
        final View headerView = View.inflate(context, headerID, null);
        headerView.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        headerLayout.addView(headerView);
        setTag(ExpandableLayoutItem.class.getName());
        final View contentView = View.inflate(context, contentID, null);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        contentLayout.addView(contentView);
        contentLayout.setVisibility(GONE);

        arrow = (ImageView) headerView.findViewById(R.id.action_arrow);
        if (expandable) {
            arrow.setVisibility(View.VISIBLE);
        } else {
            arrow.setVisibility(View.GONE);

        }
//        arrow.setOnTouchListener(new OnTouchListener()
//        {
//            @Override
//            public boolean onTouch(View v, MotionEvent event)
//            {
//                Log.e("test","click");
//                if (isOpened() && event.getAction() == MotionEvent.ACTION_UP)
//                {
//                    hide();
//                    closeByUser = true;
//                }
//
//                return isOpened() && event.getAction() == MotionEvent.ACTION_DOWN;
//            }
//        });

//        arrow.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isOpened()) {
//                    hide();
//                    closeByUser = true;
//                } else {
//                    show();
//                    closeByUser = true;
//                }
//            }
//        });

    }

    private void expand(final View v) {
        isOpened = true;
        v.measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();
        v.getLayoutParams().height = 0;
        v.setVisibility(VISIBLE);

        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = (interpolatedTime == 1) ? LayoutParams.WRAP_CONTENT : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }


            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        animation.setDuration(duration);
        animation.setInterpolator(new AccelerateInterpolator());
        arrow.setImageResource(R.drawable.menu_selecter_arrow_open);
        RotateAnimation rotateAnimation = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(duration);

        v.startAnimation(animation);
        arrow.startAnimation(rotateAnimation);


    }

    private void collapse(final View v) {
        isOpened = false;
        final int initialHeight = v.getMeasuredHeight();
        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                    isOpened = false;
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }


            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        animation.setDuration(duration);
        animation.setInterpolator(new AccelerateInterpolator());
        arrow.setImageResource(R.drawable.menu_selecter_arrow_close);
        RotateAnimation rotateAnimation = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(duration);

        v.startAnimation(animation);
        arrow.startAnimation(rotateAnimation);

    }

    public void hideNow() {
        contentLayout.getLayoutParams().height = 0;
        contentLayout.invalidate();
        contentLayout.setVisibility(View.GONE);
        isOpened = false;
    }

    public void showNow() {
        if (!this.isOpened()) {
            contentLayout.setVisibility(VISIBLE);
            this.isOpened = true;
            contentLayout.getLayoutParams().height = LayoutParams.WRAP_CONTENT;
            contentLayout.invalidate();
        }
    }

    public Boolean isOpened() {
        return isOpened;
    }

    public void show() {
        if (!expandable) return;

        if (!isAnimationRunning) {
            expand(contentLayout);
            isAnimationRunning = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isAnimationRunning = false;
                }
            }, duration);
        }
    }

    public FrameLayout getHeaderLayout() {
        return headerLayout;
    }

    public LinearLayout getContentLayout() {
        return contentLayout;
    }

    public void hide() {
        if (!expandable) return;

        if (!isAnimationRunning) {
            collapse(contentLayout);
            isAnimationRunning = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isAnimationRunning = false;
                }
            }, duration);
        }
        closeByUser = false;
    }

    public Boolean getCloseByUser() {
        return closeByUser;
    }

    /**
     * 重新显示时的状态初始化
     *
     * @param isOpened
     */
    public void setStatus(boolean isOpened) {

        if (!expandable) {
            this.isOpened = false;
            arrow.setVisibility(View.GONE);
            getContentLayout().setVisibility(View.GONE);
            return;
        }

        this.isOpened = isOpened;
        if (isOpened) {
            arrow.setImageResource(R.drawable.menu_selecter_arrow_open);
            getContentLayout().setVisibility(View.VISIBLE);
        } else {
            arrow.setImageResource(R.drawable.menu_selecter_arrow_close);
            getContentLayout().setVisibility(View.GONE);
        }
    }

}
