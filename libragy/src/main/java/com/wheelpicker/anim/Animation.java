package com.wheelpicker.anim;

import android.view.animation.Interpolator;

/**
 * Animation calculates a value according to the current input time.
 * 1. First we need to use setDuration(int) to set the duration of the
 *    animation. The duration is in milliseconds.
 * 2. Then we should call start(). The actual start time is the first value
 *    passed to calculate(long).
 * 3. Each time we want to get an animation value, we call
 *    calculate(long currentTimeMillis) to ask the Animation to calculate it.
 *    The parameter passed to calculate(long) should be nonnegative.
 * 4. Use get() to get that value.
 *
 * In step 3, onCalculate(float progress) is called so subclasses can calculate
 * the value according to progress (progress is a value in [0,1]).
 *
 * Before onCalculate(float) is called, There is an optional interpolator which
 * can change the progress value. The interpolator can be set by
 * setInterpolator(Interpolator). If the interpolator is used, the value passed
 * to onCalculate may be (for example, the overshoot effect).
 *
 * The isActive() method returns true after the animation start() is called and
 * before calculate is passed a value which reaches the duration of the
 * animation.
 *
 * The start() method can be called again to restart the Animation.
 */
abstract public class Animation {
	private static final long ANIMATION_START = -1;
	private static final long NO_ANIMATION = -2;

	private long mStartTime = NO_ANIMATION;
	private int mDuration;
	private Interpolator mInterpolator;

	public void setInterpolator(Interpolator interpolator) {
		mInterpolator = interpolator;
	}

	public void setDuration(int duration) {
		mDuration = duration;
	}

	public void start() {
		mStartTime = ANIMATION_START;
	}

	public void setStartTime(long time) {
		mStartTime = time;
	}

	public boolean isActive() {
		return mStartTime != NO_ANIMATION;
	}

	public synchronized void forceStop() {
		mStartTime = NO_ANIMATION;
	}

	public synchronized boolean calculate(long currentTimeMillis) {
		if (mStartTime == NO_ANIMATION) {
			return false;
		}

		if (mStartTime == ANIMATION_START) {
			mStartTime = currentTimeMillis;
		}
		int elapse = (int) (currentTimeMillis - mStartTime);
		float x = clamp((float) elapse / mDuration, 0f, 1f);
		Interpolator i = mInterpolator;
		onCalculate(i != null ? i.getInterpolation(x) : x);
		if (elapse >= mDuration) {
			mStartTime = NO_ANIMATION;
		}

		return mStartTime != NO_ANIMATION;
	}

	/**
	 * Returns the input value x clamped to the range [min, max].
	 * @param x
	 * @param min
	 * @param max
     * @return
     */
	public static float clamp(float x, float min, float max) {
		if (x > max) {
			return max;
		}
		
		if (x < min) {
			return min;
		}
		return x;
	}

	public void reset() {
	}

	abstract protected void onCalculate(float progress);

}
