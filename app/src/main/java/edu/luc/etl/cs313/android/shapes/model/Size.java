package edu.luc.etl.cs313.android.shapes.model;
import java.util.List;
/**
 * A visitor to compute the number of basic shapes in a (possibly complex)
 * shape.
 */
public class Size implements Visitor<Integer> {

	// FIXED

	@Override
	public Integer onPolygon(final Polygon p) {
		return 1;
	}

	@Override
	public Integer onCircle(final Circle c) {
		return 1;
	}

	@Override
	public Integer onGroup(final Group g) {
		int totalDim = 0;
		List<? extends Shape> ShapeList = g.getShapes();
		for (Shape s:ShapeList){
			totalDim++;
		}
		return totalDim;
	}
	//iterate through list of shapes
	//add size of each shape together
	//send visitor to the returned total size

	@Override
	public Integer onRectangle(final Rectangle q) {
		return 1;
	}

	@Override
	public Integer onOutline(final Outline o) {
		Shape s = o.getShape();
		return s.accept(this);
	}

	@Override
	public Integer onFill(final Fill c) {
		Shape s = c.getShape();
		return s.accept(this);

	}

	@Override
	public Integer onLocation(final Location l) {
		Shape s = l.getShape();
		return s.accept(this);
	}

	@Override
	public Integer onStroke(final Stroke c) {

				Shape s = c.getShape();
		return s.accept(this);
	}
}
