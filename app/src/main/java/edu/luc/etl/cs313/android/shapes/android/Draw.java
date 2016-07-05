package edu.luc.etl.cs313.android.shapes.android;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;

import java.util.Iterator;
import java.util.List;

import edu.luc.etl.cs313.android.shapes.model.*;

/**
 * A Visitor for drawing a shape to an Android canvas.
 */
public class Draw implements Visitor<Void> {

	// TODO entirely your job (except onCircle)

	private final Canvas canvas;

	private final Paint paint;


	public Draw(final Canvas canvas, final Paint paint) {
		this.canvas = canvas; // FIXED
		this.paint = paint; // FIXED

		paint.setStyle(Style.STROKE);
	}



	@Override
	public Void onCircle(final Circle c) {
		canvas.drawCircle(0, 0, c.getRadius(), paint);
		return null;
	}

	@Override
	public Void onStroke(final Stroke c) {
		paint.setColor(c.getColor());
		return null;
	}

	@Override
	public Void onFill(final Fill f) {
		paint.setStyle(Style.FILL);
		return null;
	}

	@Override
	public Void onGroup(final Group g) {

		return null;
	}

	@Override
	public Void onLocation(final Location l) {
    	canvas.translate(l.getX(),l.getY());
		return null;
	}


	@Override
	public Void onRectangle(final Rectangle r) {
		canvas.drawRect(0, 0, r.getWidth(), r.getHeight(), paint);
		return null;
	}

	@Override
	public Void onOutline(Outline o) {

		return null;
	}

	@Override
	public Void onPolygon(final Polygon s) {
		int polysize =  (s.getPoints().size());

		float[] pts = new float[4*polysize];

		for (int i = 0;i < polysize-1;i++) {
			pts[4*i] = s.getPoints().get(i).getX();
			pts[4*i+1] = s.getPoints().get(i).getY();
			pts[4*i+2] = s.getPoints().get(i+1).getX();
			pts[4*i+3] = s.getPoints().get(i+1).getY();
		}

		pts[4*(polysize-1)] = s.getPoints().get(polysize-1).getX();
		pts[4*(polysize-1)+1] = s.getPoints().get(polysize-1).getY();
		pts[4*(polysize-1)+2] = s.getPoints().get(0).getX();
		pts[4*(polysize-1)+3] = s.getPoints().get(0).getY();

		canvas.drawLines(pts, paint);
		return null;
	}
}
