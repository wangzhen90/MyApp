package widget.deckview.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import widget.deckview.helper.DeckChildViewTransform;
import widget.deckview.helper.DeckViewConfig;
import widget.deckview.helper.DeckViewLayoutAlgorithm;

/**
 * Created by dell on 2016/2/4.
 */
public class DeckView<T> extends FrameLayout {

    Rect mTaskStackBounds = new Rect();
    DeckViewLayoutAlgorithm<T> mLayoutAlgorithm;
    DeckViewConfig mConfig;
    Rect mTmpRect = new Rect();
    boolean mStackViewsDirty = true;
    DeckViewScroller mStackScroller;

    public DeckView(Context context) {
        this(context,null);
    }

    public DeckView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DeckView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mConfig = DeckViewConfig.getInstance(getContext());
    }

   public void init(){
        requestLayout();
       mLayoutAlgorithm = new DeckViewLayoutAlgorithm<T>(mConfig);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        //可见区域
        Rect _taskStackBounds = new Rect();
        _taskStackBounds.set(0, 0, width, height);

        setStackInsetRect(_taskStackBounds);
        computeRects(width, height, _taskStackBounds, mConfig.launchedWithAltTab,
                mConfig.launchedFromHome);

        // Measure each of the TaskViews
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            DeckChildView tv = (DeckChildView) getChildAt(i);
            if (tv.getBackground() != null) {
                tv.getBackground().getPadding(mTmpRect);
            } else {
                mTmpRect.setEmpty();
            }
            tv.measure(
                    MeasureSpec.makeMeasureSpec(
                            mLayoutAlgorithm.mTaskRect.width() + mTmpRect.left + mTmpRect.right,
                            MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(
                            mLayoutAlgorithm.mTaskRect.height() + mTmpRect.top + mTmpRect.bottom,
                            MeasureSpec.EXACTLY));

//            tv.measure(
//                    widthMeasureSpec,
//                    heightMeasureSpec);
        }

        setMeasuredDimension(width, height);


    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();

        synchronizeStackViewsWithModel();

    }


    /**
     * Synchronizes the views with the model
     */
    boolean synchronizeStackViewsWithModel() {
////        Log.e("test","child,synchronizeStackViewsWithModel");
//        if (mStackViewsDirty) {
//            // Get all the task transforms
//            ArrayList<T> data = mCallback.getData();
//            float stackScroll = mStackScroller.getStackScroll();
//            int[] visibleRange = mTmpVisibleRange;
//            boolean isValidVisibleRange = updateStackTransforms(mCurrentTaskTransforms,
//                    data, stackScroll, visibleRange, false);
//
//            // Return all the invisible children to the pool
//            mTmpTaskViewMap.clear();
//            int childCount = getChildCount();
//            for (int i = childCount - 1; i >= 0; i--) {
//                DeckChildView<T> tv = (DeckChildView) getChildAt(i);
//                T key = tv.getAttachedKey();
//                int taskIndex = data.indexOf(key);
//
//                if (visibleRange[1] <= taskIndex
//                        && taskIndex <= visibleRange[0]) {
//                    mTmpTaskViewMap.put(key, tv);
//                } else {
//                    mViewPool.returnViewToPool(tv);
//                }
//            }
//
//            for (int i = visibleRange[0]; isValidVisibleRange && i >= visibleRange[1]; i--) {
//                T key = data.get(i);
//                DeckChildViewTransform transform = mCurrentTaskTransforms.get(i);
//                DeckChildView tv = mTmpTaskViewMap.get(key);
//
//                if (tv == null) {
//                    // TODO Check
//                    tv = mViewPool.pickUpViewFromPool(key, key);
//
//                    if (mStackViewsAnimationDuration > 0) {
//                        // For items in the list, put them in start animating them from the
//                        // approriate ends of the list where they are expected to appear
//                        if (Float.compare(transform.p, 0f) <= 0) {
//                            mLayoutAlgorithm.getStackTransform(0f, 0f, mTmpTransform, null);
//                        } else {
//                            mLayoutAlgorithm.getStackTransform(1f, 0f, mTmpTransform, null);
//                        }
//                        tv.updateViewPropertiesToTaskTransform(mTmpTransform, 0);
//                    }
//                }
//
//                // Animate the task into place
//                tv.updateViewPropertiesToTaskTransform(mCurrentTaskTransforms.get(i),
//                        mStackViewsAnimationDuration, mRequestUpdateClippingListener);
//            }
//
//            // Reset the request-synchronize params
//            mStackViewsAnimationDuration = 0;
//            mStackViewsDirty = false;
//            mStackViewsClipDirty = true;
//            return true;
//        }
        return false;
    }




    public void setStackInsetRect(Rect r) {
        mTaskStackBounds.set(r);
    }


    /**
     * Computes the stack and task rects
     */
    public void computeRects(int windowWidth, int windowHeight, Rect taskStackBounds,
                             boolean launchedWithAltTab, boolean launchedFromHome) {
        // Compute the rects in the stack algorithm
        mLayoutAlgorithm.computeRects(windowWidth, windowHeight, taskStackBounds);

        // Update the scroll bounds
        updateMinMaxScroll(false, launchedWithAltTab, launchedFromHome);
    }

    /**
     * Updates the min and max virtual scroll bounds
     */
    void updateMinMaxScroll(boolean boundScrollToNewMinMax, boolean launchedWithAltTab,
                            boolean launchedFromHome) {
        // Compute the min and max scroll values
        mLayoutAlgorithm.computeMinMaxScroll(mCallback.getData(), launchedWithAltTab, launchedFromHome);

        // Debug logging
//        if (true) {
//            mStackScroller.boundScroll();
//        }
    }

    Callback<T> mCallback;

    public interface Callback<T> {
        public ArrayList<T> getData();

        public void loadViewData(WeakReference<DeckChildView<T>> dcv, T item);

        public void unloadViewData(T item);

        public void onViewDismissed(T item);

        public void onItemClick(T item);

        public void onNoViewsToDeck();
    }

}
