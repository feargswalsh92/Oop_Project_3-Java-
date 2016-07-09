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

		int minX = totalBox.getX();
		int minY = totalBox.getY();

		Rectangle maxShape = (Rectangle) totalBox.getShape();
		int maxX = minX + maxShape.getWidth() ;
		int maxY = minY + maxShape.getHeight();
		/*int minnextX;
		int minnextY;
		int maxnextX;
		int maxnextY;*/

		for (int i = 1; i < shapeList.size(); i++) {
			Location nextBox = shapeList.get(i).accept(this);// the next bounding box
			int minnextX = nextBox.getX();
			int minnextY = nextBox.getY();
			Rectangle nextShape = (Rectangle) nextBox.getShape();
			int maxnextX = nextBox.getX() + nextShape.getWidth();
			int maxnextY = nextBox.getY() + nextShape.getHeight();


			if (minnextX < minX){
				minX = minnextX;
			}
			if (minnextY <minY){
				minY = minnextY;
			}

			if (maxnextX < maxX){
				maxX = maxnextX;
			}

			if( maxnextY < maxY){
				maxY = maxnextY;
			}

			// Now merge nextBox and totalBox
			/*if (nextBox minX < totalBox minX) {
				minX = nextBox minX;
			}

			if (nextBox maxX > totalBox maxX) {
				maxX = nextBox minX;
			}

			if (nextBox minY < totalBox minY) {
				minY = nextBox minY;
			}

			if (nextBox maxY > totalBox maxY) {
				maxY = nextBox maxY;
			} */

			// Calculate the minimum totalBox and nextBox x and y coordinates -
			// those are the x and y coordinates of the merged Location/bounding box.
			// Then calculate the maximum totalBox and nextBox x and y coordinates -
			// those minus the minimum x and y are the width and height of the needed
			// new totalBox Location's Rectangle for the merged Location/bounding box.
			// Finally, update totalBox like this (this is the merged bounding box):
			totalBox = new Location(minX, minY, new Rectangle(maxX - minX, maxY - minY));
		}
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
