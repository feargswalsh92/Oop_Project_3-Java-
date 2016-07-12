package edu.luc.etl.cs313.android.shapes.model;
import java.util.List;

import static edu.luc.etl.cs313.android.shapes.model.Fixtures.simpleOutline;

/**
 * A shape visitor for calculating the bounding box, that is, the smallest
 * rectangle containing the shape. The resulting bounding box is returned as a
 * rectangle at a specific location.
 */
public class BoundingBox implements Visitor<Location> {

	// FIXED entirely your job (except onCircle)

	@Override
	public Location onCircle(final Circle c) {
		final int radius = c.getRadius();
		return new Location(-radius, -radius, new Rectangle(2 * radius, 2 * radius));
	}

	@Override
	public Location onFill(final Fill f) {
		Shape s = f.getShape();

		return s.accept(this);
	}

	@Override
	public Location onGroup(final Group g) {
		List<? extends Shape> shapeList = g.getShapes();
		Location totalBox = shapeList.get(0).accept(this); // the first bounding box
		Rectangle r   = (Rectangle)totalBox.getShape();

		int minX = totalBox.getX();
		int minY = totalBox.getY();
		int maxX = minX + r.getWidth();
		int maxY = minY + r.getHeight();

		for (int i = 1; i < shapeList.size(); i++) {
			Location nextBox = shapeList.get(i).accept(this); // the next bounding box
			Rectangle nextR = (Rectangle)nextBox.getShape();

			int minNextX = nextBox.getX();
			int minNextY = nextBox.getY();
			int maxNextX = minNextX + nextR.getWidth();
			int maxNextY = minNextY + nextR.getHeight();

			if (minNextX < minX){
				minX = minNextX;
			}
			if (minNextY < minY){
				minY = minNextY;
			}
			if (maxX < maxNextX){
				maxX = maxNextX;
			}
			if(maxY < maxNextY){
				maxY = maxNextY;
			}
		}
		totalBox = new Location(minX, minY, new Rectangle(maxX - minX, maxY - minY));

		return totalBox;
	}

	@Override
	public Location onLocation(final Location l) {
		Shape s = l.getShape();
		Location newL = s.accept(this);

		return new Location(newL.getX() + l.getX(), newL.getX() + l.getY(), newL.getShape());
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

		return s.accept(this);
	}

	@Override
	public Location onOutline(final Outline o) {
		Shape s = o.getShape();

		return s.accept(this);
	}

	@Override
	public Location onPolygon(final Polygon s) {

		return onGroup(s);
	}
}
