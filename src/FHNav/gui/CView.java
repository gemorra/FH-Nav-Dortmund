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

/**
 * Prunkstück für Unkommentierten Code, den ich nie wieder verstehen werde.
 * 
 * Damals ging es um das Laden des Hintergrundes, das richtige Skalieren der
 * Auflösung des Bildes und des Graphens (x/y_scale_mapping) und um das Zoomen
 * in dieses Bild und der richtigen Darstellung des Graphen (x/y_scale_zoom)
 * Gelöst habe ich das mit Matrixabbildungen. Die Pinch2Zoomfunktion, also die
 * reine Tatsache, dass ein Pinch erkannt wird, ist übernommen aus einem
 * Beispiel im Internet.
 * 
 * @author Moritz Wiechers
 * 
 */
public class CView extends View {

	Paint mpaint = new Paint();
	Bitmap bmp;
	Bitmap ziel;
	Bitmap start;
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
	Context ctx;
	Rect dst;
	Rect src = null;

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

	protected float x1, x2, y1, y2, x1_pre, y1_pre, x1_pre2, y1_pre2,
			dist_curr = -1, dist_pre = -1, dist_delta;
	protected OnTouchListener touchListener = new OnTouchListener() {
		public boolean onTouch(View v, MotionEvent event) {

			_view.onTouchEvent(event);
			int action = event.getAction() & MotionEvent.ACTION_MASK, p_count = event
					.getPointerCount();
			long now = android.os.SystemClock.uptimeMillis();
			switch (action) {
			case MotionEvent.ACTION_MOVE:

				// point 1 coords
				x1 = event.getX(0);
				y1 = event.getY(0);

				if (p_count > 1) {
					// point 2 coords
					// System.out.println("touch 2");
					x2 = event.getX(1);
					y2 = event.getY(1);

					// distance between
					dist_curr = (float) Math.sqrt(Math.pow(x2 - x1, 2)
							+ Math.pow(y2 - y1, 2));
					dist_delta = dist_curr - dist_pre;

					if ((now - mLastGestureTime) > 100) {
						mLastGestureTime = 0;

						float view_mid = (_view.getWidth() / 2) / x_scale_zoom;
						System.out.println(view_mid);

						int mode = dist_delta > 0 ? GROW
								: (dist_curr == dist_pre ? 2 : SHRINK);
						switch (mode) {
						case GROW: // grow
							if ((x_scale_zoom + ZOOM) < MAX_SCALE) {
								x_scale_zoom += ZOOM;
								y_scale_zoom += ZOOM;
								shrink = false;
							} else {
								x_scale_zoom = MAX_SCALE;
								y_scale_zoom = MAX_SCALE;
							}
							break;
						case SHRINK: // shrink
							if ((x_scale_zoom - ZOOM) > MIN_SCALE) {
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

					x1_pre = x1;
					y1_pre = y1;
					dist_pre = dist_curr;
				}
				// drag
				else {

					if ((now - switch_time) > 500) {

						mLastGestureTime = 0;
						x_moved = x1 - x1_pre2;
						y_moved = y1 - y1_pre2;
						x_moved *= SPEED;
						y_moved *= SPEED;
						System.out.println("xm: " + x_moved + "ym: " + y_moved);

						System.out.println("Before: " + " xs: " + x_start
								+ "ys: " + y_start);
						if ((x_start - x_moved) < 0) {
							x_start = 0;
						} else if ((x_start - (x_moved)) > bmp.getWidth()) {
							x_start = bmp.getWidth();
						} else {
							x_start -= x_moved;
						}
						if ((y_start - y_moved) < 0) {
							y_start = 0;
						} else if ((y_start - (y_moved)) > bmp.getHeight()) {
							y_start = bmp.getHeight();
						} else {
							y_start -= y_moved;
						}

						System.out.println("After: " + "xs: " + x_start
								+ "ys: " + y_start);

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

	public CView(Context context) {
		super(context);
		ctx = context;
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inScaled = false;
		bmp = MainApplicationManager.getBfst().getBMP();

		if (bmp == null) {
			bmp = BitmapFactory.decodeResource(ctx.getResources(),
					R.drawable.ergeschoss, opt);
			MainApplicationManager.getBfst().setBMP(bmp);
		}
		x_start = bmp.getWidth() / 2;
		y_start = bmp.getHeight() / 2;

		ziel = BitmapFactory.decodeResource(ctx.getResources(),
				R.drawable.ziel, opt);

		start = BitmapFactory.decodeResource(ctx.getResources(),
				R.drawable.nadel, opt);

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

		src = new Rect((int) srcF.left, (int) srcF.top, (int) srcF.right,
				(int) srcF.bottom);

		canvas.drawBitmap(bmp, src, dst, mpaint);

		// Mapping Scale
		x_scale_mapping = (float) (this.getWidth()) / (float) (bmp.getWidth());
		y_scale_mapping = (float) (this.getHeight())
				/ (float) (bmp.getHeight());

		BreadthFirstSearchTest bfst = MainApplicationManager.getBfst();

		mpaint.setColor(Color.GREEN);
		mpaint.setStrokeWidth(4 * x_scale_zoom);
		try {
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
					matrix2.postScale(x_scale_zoom, y_scale_zoom, x_start,
							y_start);
					matrix2.mapPoints(pn);
					matrix2.mapPoints(pn1);
					canvas.drawLine(pn1[0] * x_scale_mapping, pn1[1]
							* y_scale_mapping, pn[0] * x_scale_mapping, pn[1]
							* y_scale_mapping, mpaint);
					if (i == (bfst.getPath().size() - 1)) {
						float side_x = (ziel.getWidth() / 2 / 5) * x_scale_zoom;
						float side_y = (ziel.getHeight() / 2 / 5)
								* y_scale_zoom;
						// pn[0]+=40;
						n_x -= 40;
						n_y += 11;
						float[] pn11 = { n_x, n_y };

						matrix2.mapPoints(pn11);
						int left = (int) (pn11[0] * x_scale_mapping);
						int top = (int) ((pn11[1] * y_scale_mapping) - (2 * side_y));
						int right = (int) ((pn11[0] * x_scale_mapping) + (2 * side_x));
						int bottom = (int) (pn11[1] * y_scale_mapping);

						Rect dst2 = new Rect(left, top, right, bottom);
						canvas.drawBitmap(ziel, null, dst2, mpaint);
					} else if (i == 1) {
						float side_x = (start.getWidth() / 2 / 3)
								* x_scale_zoom;
						float side_y = (start.getHeight() / 2 / 3)
								* y_scale_zoom;

						n1_x -= 2;
						n1_y += 29;
						float[] pn11 = { n1_x, n1_y };
						matrix2.mapPoints(pn11);
						int left = (int) (pn11[0] * x_scale_mapping);
						int top = (int) ((pn11[1] * y_scale_mapping) - (2 * side_y));
						int right = (int) ((pn11[0] * x_scale_mapping) + (2 * side_x));
						int bottom = (int) (pn11[1] * y_scale_mapping);

						Rect dst2 = new Rect(left, top, right, bottom);
						canvas.drawBitmap(start, null, dst2, mpaint);

					}
				}

			}
		} catch (Exception e) {

		}

	}

}
