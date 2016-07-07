package edu.luc.etl.cs313.android.shapes.model;

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

		return null;
	}

	@Override
	public Location onGroup(final Group g) {
		/*Shape s = g.getShapes();
		for (Shape s : shapeList){
			return new Location((s.accept(this));
		}*/
		return null;
	}

	@Override
	public Location onLocation(final Location l) {
		Shape s = l.getShape();

		return new Location(l.getX(), l.getY(), s.accept(this));
	}

	@Override
	public Location onRectangle(final Rectangle r) {
		final int width = r.getWidth();
		final int height = r.getHeight();

		return new Location(-width,-height,new Rectangle(width,height));
	}

	@Override
	public Location onStroke(final Stroke c) {

		return null;
	}

	@Override
	public Location onOutline(final Outline o) {

		return null;
	}

	@Override
	public Location onPolygon(final Polygon s) {

		return onGroup(s);
	}
}
