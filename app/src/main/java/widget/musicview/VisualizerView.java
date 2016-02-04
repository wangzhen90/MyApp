package widget.musicview;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.view.View;

/**
 * �Զ�����������
 * 
 * @author baixiaokang
 * @category https://github.com/north2014
 */
public class VisualizerView extends View {

	private byte[] mBytes;// ��������
	private Paint mPaint = new Paint();// ������
	private int mSpectrumNum = 7;// ȡ����
	private int Padding = 100;// ���ұ߾�

	// ������Բϵ��
	RectF cricleR;
	private int R = 120;
	private int RcenterX;
	private int RcenterY;

	// ����СԲϵ��
	RectF cricleM;
	private int Rm = 5;// ����СԲ�뾶

	// ���˶�����̬Բϵ��
	RectF cricle2;
	private int r;// ��̬Բ�뾶

	// ���˵ײ��̶�Բϵ��
	RectF cricle1;
	private int r1;// �ײ�Բ�뾶

	Path path1;// �ײ�����
	Path path2;// ��������

	private int mOnBallHeight = 80;// ��������С��ĸ߶�

	List<Statue> mStatues;// ���в��˵ĵ���״̬
	List<Boll> mBolls;// ���е�С��
	List<Boll> mDrageBolls;// ���д�������״̬�ĵ�С��
	/**
	 * ���� ���˵Ĳ���
	 */

	private int mViewWidth;// ��
	private int mViewHeight;// ��

	private float mLevelLine = 0;// ˮλ��
	private float mWaveHeight = 80;// �����������
	private float mWaveWidth = 200;// ����
	private float mLeftSide;// �����ص�����ߵĲ���
	private float mMoveLen;// ˮ��ƽ�Ƶľ���
	public static final float SPEED = 1.7f;// ˮ��ƽ���ٶ�
	private List<Point> mPointsList;// ���˶����ĵ���
	private Path mWavePath;// ���˵�·��
	private boolean isMeasured = false;

	private Timer timer;
	private MyTimerTask mTask;

	private float progress = 100;// ����
	private double mHeight;// ���˷���

	public void setProgress(float i) {
		this.progress = (100 - i) / 100f;
	}

	protected float getProgress() {
		return this.progress;
	}

	public double getmHeight() {
		return this.mHeight;
	}

	public void setmHeight(double i) {
		this.mHeight = i;
	}

	@SuppressLint("HandlerLeak")
	Handler updateHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// ��¼ƽ����λ��
			mMoveLen += SPEED;
			// ����View�߶Ⱥʹ������Ľ��ȼ���ˮλ�߸߶�
			mLevelLine = getProgress() * mViewHeight;
			// ����View��Ⱥʹ�������������㲨�η�ֵ
			mWaveHeight = (float) (mViewWidth / (getmHeight() * 10f));

			mLeftSide += SPEED;
			// ����ƽ��
			for (int i = 0; i < mPointsList.size(); i++) {
				mPointsList.get(i).setX(mPointsList.get(i).getX() + SPEED);
				switch (i % 4) {
				case 0:
				case 2:
					mPointsList.get(i).setY(mLevelLine);
					break;
				case 1:
					mPointsList.get(i).setY(mLevelLine + mWaveHeight);
					break;
				case 3:
					mPointsList.get(i).setY(mLevelLine - mWaveHeight);
					break;
				}
			}
			if (mMoveLen >= mWaveWidth) {
				// ����ƽ�Ƴ���һ���������κ�λ
				mMoveLen = 0;
				resetPoints();
			}

		}

	};

	/**
	 * ���е��x���궼��ԭ����ʼ״̬��Ҳ����һ������ǰ��״̬
	 */
	private void resetPoints() {
		mLeftSide = -mWaveWidth;
		for (int i = 0; i < mPointsList.size(); i++) {
			mPointsList.get(i).setX(i * mWaveWidth / 4 - mWaveWidth);
		}
	}

	public VisualizerView(Context context) {
		super(context);
		init();
	}

	private void init() {
		mBytes = null;

		mStatues = new ArrayList<Statue>();// ����С���ڵ�״̬
		mBolls = new ArrayList<Boll>();// ���е�С��
		mDrageBolls = new ArrayList<Boll>();// ���д�������״̬�ĵ�С��

		for (int i = 0; i < mSpectrumNum; i++) {
			mStatues.add(new Statue(0, i));
		}

		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.parseColor("#E54B4C"));

		mPointsList = new ArrayList<Point>();
		timer = new Timer();
		mWavePath = new Path();
	}

	/**
	 * ��������������²��˵�����
	 * 
	 * @param fft
	 */
	public void updateVisualizer(byte[] fft) {

		byte[] model = new byte[fft.length / 2 + 1];

		model[0] = (byte) Math.abs(fft[0]);
		for (int i = 2, j = 1; j < mSpectrumNum;) {
			model[j] = (byte) Math.hypot(fft[i], fft[i + 1]);
			i += 2;
			j++;
		}
		mBytes = model;
		invalidate();// �ػ�

	}

	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		super.onWindowFocusChanged(hasWindowFocus);
		// ��ʼ����
		start();
	}

	private void start() {
		if (mTask != null) {
			mTask.cancel();
			mTask = null;
		}
		mTask = new MyTimerTask(updateHandler);
		timer.schedule(mTask, 0, 10);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if (!isMeasured) {
			isMeasured = true;

			mViewWidth = getMeasuredWidth();
			mViewHeight = getMeasuredWidth();

			// ����View��ȼ��㲨�η�ֵ
			mWaveHeight = mViewWidth / 10f;

			// ˮλ�ߴ�����¿�ʼ����
			mLevelLine = mViewHeight;

			// ���������ı�View���Ҳ����View��ֻ�ܿ����ķ�֮һ�����Σ���������ʹ���������
			mWaveWidth = mViewWidth / 4;
			// ������صľ���Ԥ��һ������
			mLeftSide = -mWaveWidth;
			// ��������ڿɼ���View����������ɼ������Σ�ע��n��ȡ��
			int n = (int) Math.round(mViewWidth / mWaveWidth + 0.5);
			// n��������Ҫ4n+1���㣬��������ҪԤ��һ�������������������������Ҫ4n+5����
			for (int i = 0; i < (4 * n + 5); i++) {
				// ��P0��ʼ��ʼ����P4n+4���ܹ�4n+5����
				float x = i * mWaveWidth / 4 - mWaveWidth;
				float y = 0;
				switch (i % 4) {
				case 0:
				case 2:
					// ���λ��ˮλ����
					y = mLevelLine + mWaveHeight;
					break;
				case 1:
					// ���²����Ŀ��Ƶ�
					y = mLevelLine + 2 * mWaveHeight;
					break;
				case 3:
					// ���ϲ����Ŀ��Ƶ�
					y = mLevelLine;
					break;
				}
				mPointsList.add(new Point(x, y));
			}
		}
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(Color.WHITE);
		if (mBytes == null) {
			return;
		}

		final int baseX = (getWidth() - 2 * Padding) / mSpectrumNum;
		final int height = getHeight();
		r = baseX / 2;

		// ����Ƶ��
		for (int i = 0; i < mSpectrumNum; i++) {
			if (mBytes[i] < 0) {
				mBytes[i] = 127;
			}

			int centerx = baseX * i + baseX / 2 + Padding;

			int left = baseX * i + Padding;
			int right = left + 2 * r;
			int top = height - mBytes[i] - 2 * r;
			int bottom = height;

			cricle1 = new RectF(left, height - 2 * r, right, bottom);// �ײ�Բ

			r1 = (int) (r - (mBytes[i] / 8) * 1.2);
			cricle2 = new RectF(centerx - r1, top, centerx + r1, top + 2// ����Բ
					* r1);

			canvas.drawOval(cricle1, mPaint);// ��Բ
			canvas.drawOval(cricle2, mPaint);// ��Բ

			path1 = new Path(); // ��
			path1.moveTo(left, height - r);
			path1.lineTo(centerx - r1, top + r1);
			path1.lineTo(centerx + r1, top + r1);
			path1.lineTo(right, height - r);
			path1.lineTo(left, height - r);

			canvas.drawPath(path1, mPaint);// ������

			if (mBytes[i] >= mOnBallHeight
					&& mStatues.get(i).getBollsNum() == 0) {// ����û�ж������ڵ��˴���λ�ã������С��
				int mid = (mSpectrumNum % 2 == 0) ? mSpectrumNum / 2
						: mSpectrumNum / 2 + 1;
				if (i == mid && mDrageBolls.size() > 0) {// �м��У��Ͳ����˰ɣ�����̫������
					continue;
				} else {// �������С�򣬸���mBytes[i]�����ó�ʼλ�úͳ�ʼ�ٶ�
					int v0 = (mBytes[i] - mOnBallHeight) / 8 + 40;
					int centery = height - mBytes[i] - r1;
					Boll mBoll = new Boll(centerx, centery, i, v0);
					mBolls.add(mBoll);
					mStatues.get(i).addNum();
				}

			}
		}

		int num = 0;
		mDrageBolls = new ArrayList<Boll>();// ���д�������״̬�ĵ�С��
		for (int j = 0; j < mBolls.size(); j++) {// ��֤���ֻ��1��С���������µ�״̬��������С����֮ǰ��С�������������
			if (mBolls.get(j).isDrage && mBolls.get(j).centerX == RcenterX) {
				num++;
				mDrageBolls.add(mBolls.get(j));
				if (mDrageBolls.size() > 1) {
					mStatues.get(mBolls.get(j).getId()).removeNum();
					mBolls.remove(j);
					int r = 8 + 2 * num;// ������һ���С��뾶�����Ա��
					mDrageBolls.get(0).setR(r);
				}
			}
		}

		for (int j = 0; j < mBolls.size(); j++) {// ��������С������ݲ���ͼ
			mBolls.get(j).update();
			mBolls.get(j).draw(canvas);

			if (mBolls.get(j).newy > height) {// ����ƶ������������С��
				mStatues.get(mBolls.get(j).getId()).removeNum();
				mBolls.remove(j);
			}
		}

		RcenterX = getWidth() / 2;
		RcenterY = R;

		// ������Բֻ�������ο�����ģ�û�б�Ҫ������
		// ���ƶ�����Բ
		// cricleR = new RectF(getWidth() / 2 - R, 0, getWidth() / 2 + R, R *
		// 2);
		// canvas.drawOval(cricleR, mPaint);

		// ���ƶ���СԲ
		// cricleM = new RectF(getWidth() / 2 - Rm, 2 * R - 2 * Rm, getWidth() /
		// 2
		// + Rm, 2 * R);
		// canvas.drawOval(cricleM, mPaint);

		// ���ײ��Ĳ���
		mWavePath.reset();
		int i = 0;
		mWavePath.moveTo(mPointsList.get(0).getX(), mPointsList.get(0).getY());
		for (; i < mPointsList.size() - 2; i = i + 2) {
			mWavePath.quadTo(mPointsList.get(i + 1).getX(),
					mPointsList.get(i + 1).getY(), mPointsList.get(i + 2)
							.getX(), mPointsList.get(i + 2).getY());
		}
		mWavePath.lineTo(mPointsList.get(i).getX(), height);
		mWavePath.lineTo(mLeftSide, height);
		mWavePath.close();
		// mPaint��Style��FILL�����������Path����
		canvas.drawPath(mWavePath, mPaint);
	}

	/**
	 * ���˵�״̬��
	 * 
	 * @author Administrator
	 * 
	 */
	public class Statue {
		private int bollsNum;// �׳���С����
		private int index;// ���˵�id

		public Statue(int i, int index) {
			this.setBollsNum(i);
			this.index = index;
		}

		public void addNum() {
			this.setBollsNum(this.getBollsNum() + 1);
		}

		public void removeNum() {
			this.setBollsNum(this.getBollsNum() - 1);
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public int getBollsNum() {
			return bollsNum;
		}

		public void setBollsNum(int bollsNum) {
			this.bollsNum = bollsNum;
		}

	}

	/**
	 * С��ʵ����
	 */
	public class Boll {

		private int index;// id
		private int r;// �뾶
		private int centerX;// Բ��x����
		private int oldy;// ֮ǰ��Բ��y����
		private int newy;// ��ǰԲ��y����
		private int v0;// ��ʼ�ٶ�
		private int vt;// tʱ���ٶ�

		private int a;// ���ٶ�
		private int t;// С�������ʱ��ʱ��

		private int vR;// ������ʱ���ٶ�
		private int aR;// ������ʱ�ļ��ٶ�
		private double tR;// ��������ʱ�䳤��

		private boolean isGingtoTap = false;// �Ƿ�Ҫ������
		boolean isTap = false;// �Ƿ�����

		boolean isDrage = false;// �Ƿ�����ק״̬
		boolean isGingtoDrag = false;// �Ƿ�Ҫ����ק

		public Boll(int x, int y, int index, int v0) {
			this.index = index;
			this.r = 10;
			this.centerX = x;
			this.oldy = y;
			this.newy = y;
			this.v0 = v0;
			this.vt = v0;
			this.vR = 0;
			this.a = 4;
			this.t = 0;
			this.tR = 0;
			this.aR = 1;
		}

		public void setR(int r) {
			this.r = r;

		}

		public int getId() {
			return this.index;
		}

		/**
		 * ����С�������
		 */
		public void update() {
			t++;
			if (vt > 0) {// ���������׶�
				vt -= a;
				newy = oldy - vt;
			} else {// �������µ��׶�
				vt += a;
				newy = oldy + vt * (t - v0 / a);
			}

			// �ж��Ƿ�������������Բ��
			if (r + R > Math.sqrt((centerX - RcenterX) * (centerX - RcenterX)
					+ (newy - RcenterY) * (newy - RcenterY))) {
				newy = (int) Math.sqrt(R * R - (centerX - RcenterX)
						* (centerX - RcenterX))
						+ R - r;
				isGingtoTap = true;
			}

			if (isGingtoTap) {
				if (newy <= 2 * R) {// ������״̬
					isTap = true;
					tR = tR + 1;
				} else { // ��������״̬
					isTap = false;
					isDrage = true;// ��������״̬
				}
			}

			if (isTap) {// ������״̬ʱ������x���꣬��С�����Ŵ�����
				vR += aR;
				newy = (int) (oldy + vR * tR);
				if (centerX > RcenterX) {// �������ұ�
					centerX = (int) Math.sqrt(R * R - (newy - RcenterY)
							* (newy - RcenterY))
							+ RcenterX;
				} else {// ���������
					centerX = RcenterX
							- (int) Math.sqrt(R * R - (newy - RcenterY)
									* (newy - RcenterY));
				}

			}

			if (isDrage) {// ��������״̬
				if (newy < 2 * R + 50) {// �������������ڣ�����
					centerX = RcenterX;
					isDrage = true;
				} else {// �������������⣬������
					isDrage = false;
				}
			}

			oldy = newy;// ����oldy
		}

		/**
		 * ��С��
		 */
		private void draw(Canvas canvas) {
			if (isDrage) {// ������״̬���������������

				path2 = new Path(); // ����
				path2.moveTo(getWidth() / 2 - Rm, 2 * R - Rm);
				path2.lineTo(getWidth() / 2 + Rm, 2 * R - Rm);
				path2.lineTo(centerX + r, newy);
				path2.lineTo(centerX - r, newy);
				path2.lineTo(getWidth() / 2 - Rm, 2 * R - Rm);
				canvas.drawPath(path2, mPaint);
			}
			// ��С��
			RectF cricle = new RectF(centerX - r, newy + r, centerX + r, newy
					- r);
			canvas.drawOval(cricle, mPaint);
		}
	}

	class MyTimerTask extends TimerTask {
		Handler handler;

		public MyTimerTask(Handler handler) {
			this.handler = handler;
		}

		@Override
		public void run() {
			handler.sendMessage(handler.obtainMessage());
		}

	}

	/**
	 * ������ʵ����
	 * 
	 * @author Administrator
	 * 
	 */

	class Point {
		private float x;
		private float y;

		public float getX() {
			return x;
		}

		public void setX(float x) {
			this.x = x;
		}

		public float getY() {
			return y;
		}

		public void setY(float y) {
			this.y = y;
		}

		public Point(float x, float y) {
			this.x = x;
			this.y = y;
		}

	}

}
