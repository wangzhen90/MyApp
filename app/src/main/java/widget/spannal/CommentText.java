package widget.spannal;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by dell on 2016/3/22.
 */
public class CommentText extends TextView {
    public CommentText(Context context) {
        super(context);
    }

    public CommentText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CommentText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int mStart = -1;

    private int mEnd = -1;


    private boolean isMove = false;
    private float lastX;
    private float lastY;
    private int originalStart = -1;
    private int originalEnd = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = super.onTouchEvent(event);

        int action = event.getAction();

        int x = (int) event.getX();
        int y = (int) event.getY();
        if (action == MotionEvent.ACTION_DOWN) {
            lastX = event.getX();
            lastY = event.getY();
            isMove = false;
        } else if (action == MotionEvent.ACTION_MOVE) {
            float distanceX = Math.abs(lastX - event.getX());
            float distanceY = Math.abs(lastY - event.getY());
            if (distanceX > 3f || distanceY > 3f) {
                isMove = true;
            }
        }

        x -= getTotalPaddingLeft();
        y -= getTotalPaddingTop();

        x += getScrollX();
        y += getScrollY();

        Layout layout = getLayout();
        int line = layout.getLineForVertical(y);
        int off = layout.getOffsetForHorizontal(line, x);
        CharSequence text = getText();
        if (TextUtils.isEmpty(text) || !(text instanceof Spannable)) {
            return result;
        }
        Spannable buffer = (Spannable) text;
        ClickableSpan[] link = buffer.getSpans(off, off, ClickableSpan.class);
        if (link.length != 0) {
            if (action == MotionEvent.ACTION_DOWN) {
                mStart = buffer.getSpanStart(link[0]);
                mEnd = buffer.getSpanEnd(link[0]);
                if (mStart >= 0 && mEnd >= mStart) {
                    buffer.setSpan(new BackgroundColorSpan(Color.GRAY), mStart, mEnd,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
                if (mStart >= 0 && mEnd >= mStart) {
                    buffer.setSpan(new BackgroundColorSpan(Color.TRANSPARENT), mStart, mEnd,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    mStart = -1;
                    mEnd = -1;
                }
            } else if (action == MotionEvent.ACTION_MOVE) {
                if (isMove) {
                    if (mStart >= 0 && mEnd >= mStart) {
                        buffer.setSpan(new BackgroundColorSpan(Color.TRANSPARENT), mStart, mEnd,
                                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        mStart = -1;
                        mEnd = -1;
                    }
                }
            }
            return true;
        } else {

            if (mStart >= 0 && mEnd >= mStart) {
                buffer.setSpan(new BackgroundColorSpan(Color.TRANSPARENT), mStart, mEnd,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                mStart = -1;
                mEnd = -1;
            }
            if (action == MotionEvent.ACTION_DOWN) {
                setBackgroundColor(Color.GRAY);
                //开始计数
//                leftTime = 5;
//                handler.post(countDownRunnable);
                return true;
            } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
                setBackgroundColor(Color.TRANSPARENT);
                //如果没有调用长按 调用点击整个的监听
//                if (leftTime > -1) {
//                    leftTime = 10;
//                    handler.removeCallbacks(countDownRunnable);//移除统计
//                    if (listener != null && !isMove) {
//                        listener.onBlankClick(this);
//                    }
//                }
                if(clickListener != null) clickListener.onClick();

            } else if (action == MotionEvent.ACTION_MOVE) {
                if (isMove) {
                    setBackgroundColor(Color.TRANSPARENT);
                }
            }
            Selection.removeSelection(buffer);
            return false;
        }
    }

   public interface CommonContentClick {
        void onClick();
    }

    public void setClickListener(CommonContentClick clickListener) {
        this.clickListener = clickListener;
    }

    CommonContentClick clickListener;


}
