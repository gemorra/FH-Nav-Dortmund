package FHNav.gui;

import FHNav.controller.BreadthFirstSearchTest;
import FHNav.controller.BreadthFirstSearchTest.Node;
import FHNav.controller.MainApplicationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.ScaleAnimation;

public class CView extends View {

	Paint mpaint = new Paint();
	Bitmap bmp;
	float x_scale_mapping;
	float y_scale_mapping;
	private View _view;
	float x_scale_zoom = 1.0f;
	float y_scale_zoom = 1.0f;
	float x_start = 0;
	float y_start = 0;
	boolean shrink = false;
	float x_moved = 0;
	float y_moved = 0;

	Rect dst;
	Rect src = null;

	public CView(Context context) {
		super(context);
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inScaled = false;
		bmp = BitmapFactory.decodeResource(MainApplicationManager.getCtx().getResources(), R.drawable.egfertig2, opt);
		x_start = bmp.getWidth() / 2;
		y_start = bmp.getHeight() / 2;
		_view = this;
		this.setOnTouchListener(touchListener);
	}

	public void init() {

	}

	@Override
	public void onDraw(Canvas canvas) {

		dst = new Rect(0, 0, this.getWidth(), this.getHeight());
		RectF srcF = new RectF(0, 0, bmp.getWidth(), bmp.getHeight());
		Matrix matrix = new Matrix();

		if (x_scale_zoom == 1) {
			x_start = bmp.getWidth() / 2;
			y_start = bmp.getHeight() / 2;
		}
		matrix.postScale(1 / x_scale_zoom, 1 / y_scale_zoom, x_start, y_start);
		matrix.mapRect(srcF);

		src = new Rect((int) srcF.left, (int) srcF.top, (int) srcF.right, (int) srcF.bottom);
		System.out.println("DRAW");


		canvas.drawBitmap(bmp, src, dst, mpaint);

		//Mapping Scale
		x_scale_mapping = (float) (this.getWidth()) / (float) (bmp.getWidth());
		y_scale_mapping = (float) (this.getHeight()) / (float) (bmp.getHeight());


		BreadthFirstSearchTest bfst = MainApplicationManager.getBfst();

		mpaint.setColor(Color.RED);
		mpaint.setStrokeWidth(4 * x_scale_zoom);

		for (int i = 0; i < bfst.getPath().size(); i++) {
			Node n = (Node) bfst.getPath().get(i);

			if (i != 0) {
				Node n_1 = (Node) bfst.getPath().get(i - 1);
				n = (Node) bfst.getPath().get(i);

				float n_x = n.getX();
				float n_y = n.getY();
				float n1_x = n_1.getX();
				float n1_y = n_1.getY();

				float[] pn = { n_x, n_y };
				float[] pn1 = { n1_x, n1_y };
				Matrix matrix2 = new Matrix();
				matrix2.postScale(x_scale_zoom, y_scale_zoom, x_start, y_start);
				matrix2.mapPoints(pn);
				matrix2.mapPoints(pn1);
				canvas.drawLine(pn1[0] * x_scale_mapping, pn1[1] * y_scale_mapping, pn[0] * x_scale_mapping, pn[1] * y_scale_mapping, mpaint);


			}
		}



	}

	public static final int GROW = 0;
	public static final int SHRINK = 1;
	public static final int DURATION = 150;

	public static final float SPEED = 3f;

	public static final float MIN_SCALE = 1f;
	public static final float MAX_SCALE = 4.5f;
	public static final float ZOOM = 0.4f;

	float x_old, y_old;
	private long switch_time;
	private long mLastGestureTime;
	protected float x1, x2, y1, y2, x1_pre, y1_pre, x1_pre2, y1_pre2, dist_curr = -1, dist_pre = -1, dist_delta;

	protected OnTouchListener touchListener = new OnTouchListener() {
		public boolean onTouch(View v, MotionEvent event) {

			_view.onTouchEvent(event);
			int action = event.getAction() & MotionEvent.ACTION_MASK, p_count = event.getPointerCount();
			long now = android.os.SystemClock.uptimeMillis();
			switch (action) {
			case MotionEvent.ACTION_MOVE:
				int interpolator = android.R.anim.accelerate_interpolator;

				// point 1 coords
				x1 = event.getX(0);
				y1 = event.getY(0);

				if (p_count > 1) {
					// point 2 coords
					// System.out.println("touch 2");
					x2 = event.getX(1);
					y2 = event.getY(1);

					// distance between
					dist_curr = (float) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
					dist_delta = dist_curr - dist_pre;

					if (now - mLastGestureTime > 100) {
						mLastGestureTime = 0;

						ScaleAnimation scale = null;

						float view_mid = (_view.getWidth() / 2) / x_scale_zoom;
						System.out.println(view_mid);

						int mode = dist_delta > 0 ? GROW : (dist_curr == dist_pre ? 2 : SHRINK);
						switch (mode) {
						case GROW: // grow
							if (x_scale_zoom + ZOOM < MAX_SCALE) {
								x_scale_zoom += ZOOM;
								y_scale_zoom += ZOOM;
								shrink = false;
							} else {
								x_scale_zoom = MAX_SCALE;
								y_scale_zoom = MAX_SCALE;
							}
							break;
						case SHRINK: // shrink
							if (x_scale_zoom - ZOOM > MIN_SCALE) {
								x_scale_zoom -= ZOOM;
								y_scale_zoom -= ZOOM;
								shrink = true;
							} else {
								x_scale_zoom = MIN_SCALE;
								y_scale_zoom = MIN_SCALE;
							}
							break;
						}


						switch_time = now;
						mLastGestureTime = now;
					}

					_view.invalidate();

					float touch_mid_x = (x2 + x1) / 2;
					float touch_mid_y = (y2 + y1) / 2;

					x1_pre = x1;
					y1_pre = y1;
					dist_pre = dist_curr;
				}
				// drag
				else {

					if (now - switch_time > 500) {

						mLastGestureTime = 0;
						x_moved = x1 - x1_pre2;
						y_moved = y1 - y1_pre2;
						x_moved *= SPEED;
						y_moved *= SPEED;
						System.out.println("xm: " + x_moved + "ym: " + y_moved);

						System.out.println("Before: " + " xs: " + x_start + "ys: " + y_start);
						if (x_start - x_moved < 0)
							x_start = 0;
						else if(x_start - (x_moved) > bmp.getWidth())
						{
							x_start = bmp.getWidth();
						}
						else
							x_start -= x_moved;
						if (y_start - y_moved < 0)
							y_start = 0;
						else if(y_start - (y_moved) > bmp.getHeight())
						{
							y_start = bmp.getHeight();
						}
						else
							y_start -= y_moved;

						System.out.println("After: " + "xs: " + x_start + "ys: " + y_start);

						_view.invalidate();
						
						x1_pre2 = x1;
						y1_pre2 = y1;
					}
				}
				break;
			case MotionEvent.ACTION_DOWN:

				
				x1_pre2 = event.getX();
				y1_pre2 = event.getY();
				break;
			case MotionEvent.ACTION_POINTER_1_DOWN:
				// point 1 coords
				x2 = event.getX(1);
				y2 = event.getY(1);


				x1_pre = event.getX(0);
				y1_pre = event.getY(0);
				x_moved = 0;
				y_moved = 0;
				mLastGestureTime = android.os.SystemClock.uptimeMillis();
				break;

			}
			return true;
		}
	};

}
