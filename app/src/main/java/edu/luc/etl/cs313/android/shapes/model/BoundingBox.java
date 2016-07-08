package edu.luc.etl.cs313.android.shapes.model;
import java.util.List;

/**
 * A shape visitor for calculating the bounding box, that is, the smallest
 * rectangle containing the shape. The resulting bounding box is returned as a
 * rectangle at a specific location.
 */
public class BoundingBox implements Visitor<Location> {

	// TODO entirely your job (except onCircle)

	@Override
	public Location onCircle(final Circle c) {
		final int radius = c.getRadius();
		return new Location(-radius, -radius, new Rectangle(2 * radius, 2 * radius));
	}

	@Override
	public Location onFill(final Fill f) {
		Shape s = f.getShape();

		return new Location(0, 0, s);
	}

	@Override
	public Location onGroup(final Group g) {
		/*List<? extends Shape> shapeList = g.getShapes();
		for (Shape s : shapeList){
			return new Location(0, 0, s);
		}*/
		return null;
	}

	@Override
	public Location onLocation(final Location l) {
		Shape s = l.getShape();

		return new Location(l.getX(), l.getY(), s);
	}

	@Override
	public Location onRectangle(final Rectangle r) {
		final int width = r.getWidth();
		final int height = r.getHeight();

		return new Location(0, 0, new Rectangle(width,height));
	}

	@Override
	public Location onStroke(final Stroke c) {
		Shape s = c.getShape();

		return new Location(0, 0, s);
	}

	@Override
	public Location onOutline(final Outline o) {
		Shape s = o.getShape();

		return new Location(0, 0, s);
	}

	@Override
	public Location onPolygon(final Polygon s) {
		return onGroup(s);
	}
}
