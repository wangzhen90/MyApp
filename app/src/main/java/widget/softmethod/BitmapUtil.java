package widget.softmethod;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

public class BitmapUtil {
	public static  Bitmap compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//����ѹ������������100��ʾ��ѹ������ѹ��������ݴ�ŵ�baos��
		int options = 100;
		Log.e("compressImage", baos.toByteArray().length / 1024+"  kb");
		while ( baos.toByteArray().length / 1024>200) {	//ѭ���ж����ѹ����ͼƬ�Ƿ����100kb,���ڼ���ѹ��	
			baos.reset();//����baos�����baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);//����ѹ��options%����ѹ��������ݴ�ŵ�baos��
			options -= 10;//ÿ�ζ�����10	
		}
		Log.e("compressImage", baos.toByteArray().length / 1024+"  kb");
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//��ѹ���������baos��ŵ�ByteArrayInputStream��
		Bitmap bitmap = BitmapFactory.decodeStream(isBm);//��ByteArrayInputStream��������ͼƬ
		try {
			File f = new File(Environment.getExternalStorageDirectory(),
					"edtimg.jpg");
			f.createNewFile();
			
			FileOutputStream fOut = new FileOutputStream(f);
//			Log.e("-----", bitmap.getHeight() + "_____" + bitmap.getWidth()
//					+ "________" + bitmap.getByteCount()+ "________" + bitmap.getRowBytes());
//			bitmap = ThumbnailUtils.extractThumbnail(bitmap, 300, 300);
//			Log.e("======", bitmap.getHeight() + "_____" + bitmap.getWidth()
//					+ "________" + bitmap.getByteCount()+ "________" + bitmap.getRowBytes());
			bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fOut);
			fOut.flush();
			fOut.close();
		} catch (Exception e1) { 
			
			
			
			e1.printStackTrace();
		}
		
		return bitmap;
	}
	
	
	public static Bitmap comp(Bitmap image) {
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();		
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		Log.e("comp", baos.toByteArray().length / 1024+"  kb");
		if( baos.toByteArray().length / 1024>200) {//�ж����ͼƬ����1M,����ѹ������������ͼƬ��BitmapFactory.decodeStream��ʱ���	
			baos.reset();//����baos�����baos
			image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//����ѹ��50%����ѹ��������ݴ�ŵ�baos��
		}
		Log.e("comp2", baos.toByteArray().length / 1024+"  kb");
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		//��ʼ����ͼƬ����ʱ��options.inJustDecodeBounds ���true��
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
//		 DisplayMetrics metrics = new DisplayMetrics();
//		 activity.getWindowManager().getDefaultDisplay()
//	                .getMetrics(metrics);
//			float hh = metrics.heightPixels;//�������ø߶�Ϊ800f
//			float ww = metrics.heightPixels;//�������ÿ��Ϊ480f
		//���������ֻ��Ƚ϶���800*480�ֱ��ʣ����ԸߺͿ���������Ϊ
		float hh = 800f;//�������ø߶�Ϊ800f
		float ww = 480f;//�������ÿ��Ϊ480f
		//���űȡ������ǹ̶��������ţ�ֻ�ø߻��߿�����һ�����ݽ��м��㼴��
		int be = 1;//be=1��ʾ������
		if (w > h && w > ww) {//�����ȴ�Ļ����ݿ�ȹ̶���С����
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {//����߶ȸߵĻ����ݿ�ȹ̶���С����
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;//�������ű���
		//���¶���ͼƬ��ע���ʱ�Ѿ���options.inJustDecodeBounds ���false��
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		return compressImage(bitmap);//ѹ���ñ�����С���ٽ�������ѹ��
	}
}
