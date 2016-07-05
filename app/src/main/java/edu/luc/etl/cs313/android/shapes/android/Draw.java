package edu.luc.etl.cs313.android.shapes.android;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.renderscript.Float2;

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

	private int polysize;
	private List<Float> polyList;


	public Draw(final Canvas canvas, final Paint paint,int polysize,List<Float> PolyList) {
		this.canvas = canvas; // FIXED
		this.paint = paint; // FIXED
		this.polysize = polysize;
		this.polyList = polyList;

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
		float[] pts = null;
		polysize =  (s.getPoints().size());

		final Iterator<Float> i = polyList.iterator();

		while (i.hasNext()) {


			}



		canvas.drawLines(pts, paint);
		return null;
	}
}
