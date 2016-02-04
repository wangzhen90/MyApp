package widget.ExpandleLayout;

import android.content.Context;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.wz.myapp.R;

import java.util.ArrayList;

import widget.utils.DensityUtil;

/**
 * Created by dell on 2016/1/12.
 */
public class ExpandableLayoutAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<CustomizeAction> mActions;
    ListView mListView;

    public ExpandableLayoutAdapter(Context context, ArrayList<CustomizeAction> actions, ListView listView) {
        mContext = context;
        mActions = actions;
        mListView = listView;
    }

    @Override
    public int getCount() {
        return mActions != null ? mActions.size() : 0;
    }

    @Override
    public CustomizeAction getItem(int position) {
        return mActions.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;
        CustomizeHolder holder;

        if (convertView != null) {
            view = convertView;
            holder = (CustomizeHolder) view.getTag();
        } else {
            holder = new CustomizeHolder();
            view = View.inflate(mContext, R.layout.expandable_list_item, null);
            holder.action_name = (TextView) view.findViewById(R.id.action_name);
            holder.expandableLayout = (ExpandableLayoutItem) view.findViewById(R.id.row);
            holder.content_view = holder.expandableLayout.getContentLayout();
            holder.action_icon = (ImageView) view.findViewById(R.id.action_icon);
            view.setTag(holder);
        }
        final CustomizeAction action = getItem(position);

        holder.action_name.setText(action.actionName);
        holder.action_icon.setImageResource(action.iconId);

        addActionsEvent(holder, action, position);

        addChildActions(holder, action);

        holder.expandableLayout.setExpandable(action.expandable);
        holder.expandableLayout.setStatus(action.isOpen);


        return view;
    }

    private void addActionsEvent(CustomizeHolder holder, final CustomizeAction action, final int position) {
        holder.expandableLayout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        action.clickEvent();
                        //上次动画未结束时
                        if (((ExpandableLayoutItem) v).isAnimationRunning)
                            return;
                        if (action.isOpen) {
                            ((ExpandableLayoutItem) v).hide();
                            action.isOpen = false;
                        } else {
                            collapseOthers(position);
                            ((ExpandableLayoutItem) v).show();


//                            new Handler().postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Log.e("click,haha", "first:" + mListView.getFirstVisiblePosition() + ",position:" + position);
//                                    if(mListView != null && mListView.getFirstVisiblePosition() >= position - mListView.getFirstVisiblePosition()){
//                                        mListView.smoothScrollToPosition(position - mListView.getFirstVisiblePosition());
//                                    }
//                                }
//                            },500);


                            action.isOpen = true;
                        }
                    }
                }

        );
    }

    private void collapseOthers(int position) {
        for (int i = 0; i < mActions.size(); i++) {
            CustomizeAction customizeAction = mActions.get(i);
            Log.e("click", "isOpen:" + customizeAction.isOpen + ",name:" + customizeAction.actionName);
            if (customizeAction.isOpen && i != position - mListView.getFirstVisiblePosition()) {
                customizeAction.isOpen = false;
                Log.e("click", "i:" + i + ",firstPositon:" + mListView.getFirstVisiblePosition());
                if (i != position && i >= mListView.getFirstVisiblePosition()) {
                    ExpandableLayoutItem currentExpandableLayout
                            = (ExpandableLayoutItem) mListView.getChildAt(i - mListView.getFirstVisiblePosition()).findViewById(R.id.row);
                    Log.e("click", "currentExpandableLayout:" + currentExpandableLayout);
                    currentExpandableLayout.hide();

                }
            }
        }
    }


    private void addChildActions(CustomizeHolder holder, CustomizeAction action) {
        if (action.childActions != null && action.childActions.size() > 0) {
            action.expandable = true;
            holder.content_view.removeAllViews();
            for (final CustomizeChildAction childAction : action.childActions) {
                TextView textView = new TextView(mContext);
                textView.setText(childAction.actionName);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(mContext, 26));
                layoutParams.setMargins(DensityUtil.dp2px(mContext, 20), 10, 0, 0);
                textView.setLayoutParams(layoutParams);
                holder.content_view.addView(textView);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        childAction.clickEvent();
                    }
                });
            }

        } else {
            holder.content_view.removeAllViews();
            holder.content_view.setVisibility(View.GONE);
            action.expandable = false;
        }
    }

    class CustomizeHolder {
        ExpandableLayoutItem expandableLayout;
        TextView action_name;
        ViewGroup content_view;
        ImageView action_icon;
    }

}
