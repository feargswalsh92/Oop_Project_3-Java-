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
		List<? extends Shape> shapeList = g.getShapes();
		Location totalBox = shapeList.get(0).accept(this); // the first bounding box
		for (int i = 1; i < shapeList.size(); i++) {
			Location nextBox = shapeList.get(i).accept(this);
			nextBox.equals(totalBox);// the next bounding box
			// Now merge nextBox and totalBox

			// Calculate the minimum totalBox and nextBox x and y coordinates -
			// those are the x and y coordinates of the merged Location/bounding box.
			int minX = nextBox.getX(); //maybe?
			int minY = nextBox.getY();
			// Then calculate the maximum totalBox and nextBox x and y coordinates -
			// those minus the minimum x and y are the width and height of the needed
			// new totalBox Location's Rectangle for the merged Location/bounding box.
			int maxX = nextBox.getX();
			int maxY = nextBox.getY();
			// Finally, update totalBox like this (this is the merged bounding box):
			totalBox = new Location(minX, minY, new Rectangle(maxX - minX, maxY - minY));
		}

// Just as an example, if you are processing a Polygon's Points by passing the
// Polygon object to onGroup (because a Polygon is a Group) then the initial
// bounding box you'll get will be a Location with the coordinates of the first
// Point and with a Rectangle of width and height 0. When you process the second
// Point in the Polygon/Group you will get a Rectangle that surrounds the line
// that would be drawn between those two Points at the minimum x and y coordinates
// of both Points, and so on for all future Points.
		return totalBox;
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
