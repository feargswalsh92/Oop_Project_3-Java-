package edu.luc.etl.cs313.android.shapes.model;
import java.util.List;

import static edu.luc.etl.cs313.android.shapes.model.Fixtures.simpleOutline;

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
		List<? extends Shape> shapeList = g.getShapes();
		Location totalBox = shapeList.get(0).accept(this); // the first bounding box
		Shape totalR = totalBox.getShape();
		Location B = totalR.accept(this);

		int minX = totalBox.getX();
		int minY = totalBox.getY();
		int maxX = minX + B.getX();
		int maxY = minX + B.getY();

		for (int i = 1; i < shapeList.size(); i++) {
			Location nextBox = shapeList.get(i).accept(this);// the next bounding box
			Shape nextR = totalBox.getShape();
			Location nextB = totalR.accept(this);

			int minNextX = nextBox.getX();
			int minNextY = nextBox.getY();
			int maxNextX = minX + nextB.getX();
			int maxNextY = minX + nextB.getY();

			if (minNextX < minX){
				minX = minNextX;
			}
			if (minNextY <minY){
				minY = minNextY;
			}
			if (maxNextX < maxX){
				maxX = maxNextX;
			}
			if( maxNextY < maxY){
				maxY = maxNextY;
			}
			totalBox = new Location(minX, minY, new Rectangle(maxX - minX, maxY - minY));
		}
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
