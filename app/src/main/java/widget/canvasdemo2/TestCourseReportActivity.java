package widget.canvasdemo2;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wz.myapp.R;

/**
�
 * @author DuGuang
 * blog��ַ��http://blog.csdn.net/duguang77
 *
 */
public class TestCourseReportActivity extends Activity {

	private FrameLayout mFlMain;
	private ArrayList<String> mPointList;
	private CustomRect mCusRect;
	private CustomTrigon mTrigon;
	private TextView mTvHideOne, mTvLevel, mTvHideTwo,mTvHide;
	private View mViewLine;

	private int mWidth;
	private int mHeight;
	
	private String mPoint;

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			String[] split = mPoint.split("-");
			mTrigon.setData(new PointBean(Integer.parseInt(split[0]), Integer
					.parseInt(split[1])));
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_testcourse_report);

		initView();
		initData();
	}

	private void initView() {
		mFlMain = (FrameLayout) findViewById(R.id.fl_mian);
		mCusRect = (CustomRect) findViewById(R.id.cus_rect);

		mTvHideOne = (TextView) findViewById(R.id.tv_hide_one);
		mTvLevel = (TextView) findViewById(R.id.tv_level);
		mTvHideTwo = (TextView) findViewById(R.id.tv_hide_two);
		mViewLine = findViewById(R.id.view_line);
		mTvHide = (TextView) findViewById(R.id.tv_hide);

	}

	private void initData() {

		mPointList = new ArrayList<String>();
		CustomCircle circle = new CustomCircle(this);
		mTrigon = new CustomTrigon(this);

		mFlMain.addView(mTrigon,2);
		mFlMain.addView(circle,3);

		//��ʼ��������
		startDraw();

		// ��ȡ��Ļ��ߣ�����1��
		mWidth = getWindowManager().getDefaultDisplay().getWidth(); // ��Ļ��
		mHeight = getWindowManager().getDefaultDisplay().getHeight(); // ��Ļ��

		int width = mWidth / 2 - mWidth /8 ;
		int height = mHeight / 4 - mHeight/12;

		//�����һ��TextView��Ȼ��ʾ����������֪��Ϊʲô��������ǣ��Ժ��޸�
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		params.topMargin = height+40;
		params.leftMargin = width;
		mTvHideOne.setLayoutParams(params);
		
		
		
		FrameLayout.LayoutParams params4 = new FrameLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		params4.topMargin = height-10;
		params4.leftMargin = width;
		mTvHide.setLayoutParams(params4);
		
		FrameLayout.LayoutParams params1 = new FrameLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		params1.topMargin = height+40;
		params1.leftMargin = width;
		mTvLevel.setTextColor(getResources().getColor(R.color.text_hide));
		mTvLevel.setLayoutParams(params1);
		
		FrameLayout.LayoutParams params2 = new FrameLayout.LayoutParams(
				300, 1);
		params2.topMargin = height+140;
		params2.leftMargin = width;
		mViewLine.setBackgroundColor(getResources().getColor(R.color.view_backgroud));
		mViewLine.setLayoutParams(params2);
		
		FrameLayout.LayoutParams params3 = new FrameLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		params3.topMargin = height+150;
		params3.leftMargin = width;
		mTvHideTwo.setTextColor(getResources().getColor(R.color.text_level));
		mTvHideTwo.setLayoutParams(params3);
		

	}

	/**
	 * ��ʼ��������
	 */
	private void startDraw() {
		new Thread() {
			public void run() {
				//���������߳���Ϊ�˷�ֹlayout�����ļ���û����ɣ�ȥ��ȡ��״�嶥�������ʱ��Null�쳣
				SystemClock.sleep(300);
				mPointList.addAll(mCusRect.getPointList());
				for (int i = 0; i < mCusRect.getPointList().size(); i++) {
					SystemClock.sleep(1200);
					mPoint = mPointList.get(i);
					mHandler.sendEmptyMessage(0);
					
					//����ж���Ϊ��չʾDemoʱ����ѭ��
					if(i == 5){
						startDraw();
					}
				}
			

			};
		}.start();
	}

}
