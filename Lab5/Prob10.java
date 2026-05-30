public class Prob10 {

    // Draw the Sierpinski triangle by printing it to the console (as text).
    // ax,ay = vertex A | bx,by = vertex B | cx,cy = vertex C
    static void sierpinski(double ax, double ay,
                           double bx, double by,
                           double cx, double cy, int depth) {
        // Base case: depth = 0 → draw the smallest triangle
        if (depth == 0) {
            System.out.printf("Draw triangle: (%.1f,%.1f) (%.1f,%.1f) (%.1f,%.1f)%n",
                              ax, ay, bx, by, cx, cy);
            return;
        }

        // Calculate the midpoints of the three sides
        double mx1 = (ax + bx) / 2,  my1 = (ay + by) / 2;
        double mx2 = (bx + cx) / 2,  my2 = (by + cy) / 2;
        double mx3 = (ax + cx) / 2,  my3 = (ay + cy) / 2;

        // Recursively draw the three smaller triangles (excluding the central one)
        sierpinski(ax, ay,  mx1, my1,  mx3, my3,  depth - 1); // left
        sierpinski(mx1, my1, bx, by,   mx2, my2,  depth - 1); // right
        sierpinski(mx3, my3, mx2, my2, cx,  cy,   depth - 1); // bottom
    }

    public static void main(String[] args) {
        sierpinski(0, 0,  100, 0,  50, 87,  3);
    }
}