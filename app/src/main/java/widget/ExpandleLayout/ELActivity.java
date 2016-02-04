package widget.ExpandleLayout;

import android.app.Activity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.wz.myapp.R;

import java.util.ArrayList;

import widget.utils.DensityUtil;


public class ELActivity extends Activity {

    private final String[] array = {"item1", "item2", "item3", "item4", "item5", "item6", "item7", "item8", "item9", "item10", "item11", "item12", "item13", "item14", "item15", "item16", "item17"};
    ArrayList<CustomizeAction> actions = new ArrayList<>();
    ListView expandableLayoutListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_el);
        initData();

        expandableLayoutListView = (ListView) findViewById(R.id.listview);

        expandableLayoutListView.setAdapter(new ExpandableLayoutAdapter(this,actions,expandableLayoutListView));


    }

    void initData() {
        actions.clear();
        for (int i = 0; i < 7; i++) {
            final int position = i;
            CustomizeAction action = new CustomizeAction(R.drawable.ic_launcher,"父条目"+i,null) {
                @Override
                public void clickEvent() {
                    Toast.makeText(ELActivity.this,"点击父条目"+position,Toast.LENGTH_SHORT).show();
                }
            };

            if (i == 5) {
                action.expandable = true;
                action.childActions = new ArrayList<>();
                for (int j = 0; j < 3; j++) {
                    final CustomizeChildAction childAction = new CustomizeChildAction("字条目"+j) {
                        @Override
                        public void clickEvent() {
                            Toast.makeText(ELActivity.this,"点击"+this.actionName,Toast.LENGTH_SHORT).show();
                        }
                    };

                    action.childActions.add(childAction);
                }
            }

            if (i == 10) {
                action.expandable = true;
                action.childActions = new ArrayList<>();
                for (int j = 0; j < 2; j++) {
                    final CustomizeChildAction childAction = new CustomizeChildAction("字条目"+j) {
                        @Override
                        public void clickEvent() {
                            Toast.makeText(ELActivity.this,"点击"+this.actionName,Toast.LENGTH_SHORT).show();
                        }
                    };

                    action.childActions.add(childAction);
                }
            }
            actions.add(action);
        }


    }


//    class MyAdapter extends BaseAdapter {
//
//        @Override
//        public int getCount() {
//            return parents.size();
//        }
//
//        @Override
//        public Parent getItem(int position) {
//            return parents.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return 0;
//        }
//
//        @Override
//        public View getView(final int position, View convertView, final ViewGroup parent) {
//            View view = null;
//            ViewHolder holder = null;
//            if (convertView != null) {
//                view = convertView;
//                holder = (ViewHolder) view.getTag();
//            } else {
//                holder = new ViewHolder();
//                holder.parent_name = (TextView) view.findViewById(R.id.header_text);
//                holder.expandableLayoutItem = (ExpandableLayoutItem) view.findViewById(R.id.row);
//                holder.content_view = holder.expandableLayoutItem.getContentLayout();
//                view.setTag(holder);
//            }
//
//           final Parent p = getItem(position);
//            if(holder.parent_name != null)
//            holder.parent_name.setText(p.name);
//
//
//            if (p.children != null && p.children.size() > 0) {
//                p.expandable = true;
//                holder.content_view.removeAllViews();
//                for(final String string : p.children){
//                    TextView textView = (TextView) View.inflate(ELActivity.this, R.layout.content_item, null);
//                    textView.setText(string);
//                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,DensityUtil.dp2px(ELActivity.this,26));
//                    layoutParams.setMargins(DensityUtil.dp2px(ELActivity.this,20),10,0,0);
//                    textView.setLayoutParams(layoutParams);
//                    holder.content_view.addView(textView);
//                    textView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Toast.makeText(ELActivity.this,"click "+ string,Toast.LENGTH_SHORT).show();
//
//                        }
//                    });
//                }
//
//            } else {
//                holder.content_view.removeAllViews();
//                holder.content_view.setVisibility(View.GONE);
//                p.expandable = false;
//            }
//
//            holder.expandableLayoutItem.getHeaderLayout().setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    if (((ExpandableLayoutItem) (v.getParent().getParent())).isOpened()){
//                        ((ExpandableLayoutItem) (v.getParent().getParent())).hide();
//                        p.isOpen = false;
//                    }
//                    else{
//                        ((ExpandableLayoutItem) (v.getParent().getParent())).show();
//                        p.isOpen = true;
//                    }
//                }
//            });
//
//            if(p.isOpen){
//                holder.content_view.setVisibility(View.VISIBLE);
//            }else {
//                holder.content_view.setVisibility(View.GONE);
//            }
//
//            Log.e("isOpen","isOpen:"+p.isOpen + ",position:"+position);
//
//            holder.expandableLayoutItem.setExpandable(p.expandable);
//
//
//            return view;
//        }
//    }
//
//    class ViewHolder {
//
//        ExpandableLayoutItem expandableLayoutItem;
//        TextView parent_name;
//        ViewGroup content_view;
//
//    }


}
