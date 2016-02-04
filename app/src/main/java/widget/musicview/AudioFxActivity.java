package widget.musicview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.audiofx.Equalizer;
import android.media.audiofx.Visualizer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.SeekBar;

import com.wz.myapp.R;

@SuppressLint("NewApi")
public class AudioFxActivity extends Activity {
	private static final String TAG = "AudioFxActivity";

	private static final float VISUALIZER_HEIGHT_DIP = 360f;
	private SeekBar seekBar;
	private MediaPlayer mMediaPlayer;
	private Visualizer mVisualizer;

	private LinearLayout mLinearLayout;

	private VisualizerView mVisualizerView;

	private Equalizer mEqualizer;

	Handler handler = new Handler();
	Runnable updateThread = new Runnable() {
		public void run() {
			// ��ø������ڲ���λ�ò����óɲ��Ž�������ֵ
			seekBar.setProgress(mMediaPlayer.getCurrentPosition());
			// ÿ���ӳ�100�����������߳�
			handler.postDelayed(updateThread, 100);
		}
	};

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.activity_music_view);
		mLinearLayout = (LinearLayout) findViewById(R.id.ll);

		seekBar = (SeekBar) findViewById(R.id.seekbar);
		// Create the MediaPlayer
		mMediaPlayer = MediaPlayer.create(this, R.raw.a);
		Log.d(TAG,
				"MediaPlayer audio session ID: "
						+ mMediaPlayer.getAudioSessionId());
		seekBar.setMax(mMediaPlayer.getDuration());

		setupVisualizerFxAndUI();

		mVisualizer.setEnabled(true);
		
		// �����˾���������������С�޹���
		mEqualizer = new Equalizer(0, mMediaPlayer.getAudioSessionId());
		mEqualizer.setEnabled(true);

		mMediaPlayer
				.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
					public void onCompletion(MediaPlayer mediaPlayer) {
						mVisualizer.setEnabled(false);
						getWindow().clearFlags(
								WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
						setVolumeControlStream(AudioManager.STREAM_SYSTEM);

					}
				});

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		mMediaPlayer.start();
		handler.post(updateThread);

		seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// fromUser�ж����û��ı�Ļ����ֵ
				if (fromUser == true) {
					mMediaPlayer.seekTo(progress);
				}
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}
		});

	}

	private void setupVisualizerFxAndUI() {
		mVisualizerView = new VisualizerView(this);
		mVisualizerView.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT,
				(int) (VISUALIZER_HEIGHT_DIP * getResources()
						.getDisplayMetrics().density)));

		mVisualizerView.setProgress(13);// ���ò��εĸ߶�
		mVisualizerView.setmHeight(8);// ��ˮλ����������
		mLinearLayout.addView(mVisualizerView);

		final int maxCR = Visualizer.getMaxCaptureRate();
		// ʵ����Visualizer������SessionId����ͨ��MediaPlayer�Ķ�����
		mVisualizer = new Visualizer(mMediaPlayer.getAudioSessionId());
		// ������Ҫת�����������ݳ��ȣ�רҵ��˵����ǲ���,�ò���ֵһ��Ϊ2��ָ����
		mVisualizer.setCaptureSize(256);
		// �������ͺ����������һ�����������������϶��������ɼ������ݡ�һ����4����������һ���Ǽ����ߣ��ڶ�����λ�Ǻ����ȣ���ʾ���ǲɼ���Ƶ�ʣ����������Ƿ�ɼ����Σ����ĸ����Ƿ�ɼ�Ƶ��
		mVisualizer.setDataCaptureListener(
		// ����ص�Ӧ�òɼ����ǲ�������
				new Visualizer.OnDataCaptureListener() {
					public void onWaveFormDataCapture(Visualizer visualizer,
							byte[] bytes, int samplingRate) {
						mVisualizerView.updateVisualizer(bytes); // ���ղ�������ͼ
					}

					// ����ص�Ӧ�òɼ����ǿ��ٸ���Ҷ�任�йص�����
					public void onFftDataCapture(Visualizer visualizer,
							byte[] fft, int samplingRate) {
						mVisualizerView.updateVisualizer(fft);
					}
				}, maxCR / 2, false, true);
	}

	@Override
	protected void onPause() {
		super.onPause();

		if (isFinishing() && mMediaPlayer != null) {
			handler.removeCallbacks(updateThread);
			mVisualizer.release();
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
	}

}