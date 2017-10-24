package com.boil.hospitalorder.hospitaldoctor.listener;

import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;

import com.boil.hospitalorder.hospitaldoctor.base.BaseBackActivity;
/**
 * 返回手势监听接口
 */
public class BackGestureListener implements OnGestureListener {
	BaseBackActivity activity;
	
	public BackGestureListener(BaseBackActivity activity) {
		this.activity = activity; 
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		if ((e2.getX() - e1.getX()) >100 && Math.abs(e1.getY() - e2.getY()) < 60 ) {
			activity.onBackPressed();
			return true;
		}
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

}
