package FHNav.gui;

import FHNav.controller.BreadthFirstSearchTest;
import FHNav.controller.BreadthFirstSearchTest.Node;
import FHNav.controller.MainApplicationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class CView extends View {

	Paint mpaint = new Paint();
	Bitmap bmp;
	float scaleX;
	float scaleY;
	


	public CView(Context context) {
		super(context);
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inScaled = false;
		bmp = BitmapFactory.decodeResource(MainApplicationManager.getCtx().getResources(), R.drawable.egfertig2,opt);
		
	}

	public void init()
	{

	}
	
	@Override
	public void onDraw(Canvas canvas)
	{
//		System.out.println("BMPWidth:" + bmp.getWidth());
//		System.out.println("BMPHeight:" + bmp.getHeight());
//		System.out.println("ThisWidth:" + this.getWidth());
//		System.out.println("ThisHeight:" + this.getHeight());
		
		scaleX = (float) (this.getWidth())/(float) (bmp.getWidth());
		scaleY = (float) (this.getHeight())/(float) (bmp.getHeight());
//		System.out.println(scaleX);
//		System.out.println(scaleY);
		
		
		
		
		BreadthFirstSearchTest bfst = MainApplicationManager.getBfst();
		Rect dst = new Rect(0, 0, this.getWidth(),this.getHeight());
		canvas.drawBitmap(bmp, null, dst, mpaint);
		mpaint.setColor(Color.RED);
		mpaint.setStrokeWidth(4);
		
		for(int i= 0; i< bfst.getPath().size();i++)
		{
			Node n = (Node) bfst.getPath().get(i);
//			System.out.println("NX " + i + " "+ (n.getX()*scaleX));
//			System.out.println("NY "+ i + " "+ (n.getY()*scaleY));
			if(i!=0)
			{
				Node n_1 = (Node) bfst.getPath().get(i-1);
				n = (Node) bfst.getPath().get(i);
//				System.out.println("N1X"+ (n_1.getX()*scaleX));
//				System.out.println("N1Y"+ (n_1.getY()*scaleY));
				
				
				canvas.drawLine(n_1.getX()*scaleX, n_1.getY()*scaleY, n.getX()*scaleX, n.getY()*scaleY, mpaint);
			}
		}
		

	}
}
