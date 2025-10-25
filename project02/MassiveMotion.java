import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * CS245 Project 2 - Massive / Movement
 * Main animation panel. Reads config via java.util.Properties (file from args[0] if provided),
 * creates a red star plus random comets, and updates/draws them on a Swing canvas.
 */
public class MassiveMotion extends JPanel implements ActionListener {

    protected Timer tm;

    // TODO: Consider removing the next two lines (coordinates for two balls)
    protected int x1, y1;
    protected int x2, y2;

    // ======== Added: config & simulation fields (with safe defaults) ========
    private int windowW = 640;
    private int windowH = 480;
    private int delay   = 75;                 // timer_delay
    private String listKind = "arraylist";    // arraylist|single|double|dummyhead

    private int starX = 320, starY = 240, starSize = 30;
    private double starVx = 0, starVy = 0;

    private double genXProb = 0.06, genYProb = 0.06; // comet spawn probs
    private int bodySize = 10;                        // comet radius
    private int bodyVelocityRange = 3;                // random speed in [-v, v] excluding 0

    // Bodies stored in your custom List (NOT java.util.List)
    private List<CelestialBody> bodies;
    private final java.util.Random rng = new java.util.Random();
    private boolean sizedOnce = false; // resize top-level frame once on first paint

    // Small internal class for a moving circle
    private static class CelestialBody {
        double x, y, vx, vy;
        int radius;
        Color color;
        boolean isStar;
        CelestialBody(double x, double y, double vx, double vy, int r, Color c, boolean isStar) {
            this.x=x; this.y=y; this.vx=vx; this.vy=vy; this.radius=r; this.color=c; this.isStar=isStar;
        }
        void step() { x += vx; y += vy; }
        void draw(Graphics g) { g.setColor(color); int d=radius*2; g.fillOval((int)(x-radius),(int)(y-radius),d,d); }
        boolean isOutside(int W, int H) {
            return (x + radius < 0) || (x - radius > W) || (y + radius < 0) || (y - radius > H);
        }
    }

    // public MassiveMotion(String propfile) {
    /**
     * Construct the animation panel.
     * Reads properties from: args[0] if provided via System property "cfg",
     * otherwise tries MassiveMovement.txt, then MassiveMotion.txt, else defaults.
     */
    public MassiveMotion() {
        // TODO: insert your code to read from configuration file here.
        // --- Determine config path: prefer System property set by main from args[0] ---
        String cfgPath = System.getProperty("cfg", null);

        java.util.Properties p = new java.util.Properties();
        boolean loaded = false;
        if (cfgPath != null) {
            try (java.io.FileInputStream in = new java.io.FileInputStream(cfgPath)) {
                p.load(in); loaded = true;
                System.out.println("[OK] Loaded " + cfgPath);
            } catch (Exception e) {
                System.out.println("[WARN] Could not load " + cfgPath + " : " + e.getMessage());
            }
        }
        if (!loaded) {
            try (java.io.FileInputStream in = new java.io.FileInputStream("MassiveMovement.txt")) {
                p.load(in); loaded = true;
                System.out.println("[OK] Loaded MassiveMovement.txt");
            } catch (Exception ignore) {}
        }
        if (!loaded) {
            try (java.io.FileInputStream in = new java.io.FileInputStream("MassiveMotion.txt")) {
                p.load(in); loaded = true;
                System.out.println("[OK] Loaded MassiveMotion.txt");
            } catch (Exception e) {
                System.out.println("[INFO] No config file found, using defaults.");
            }
        }

        // --- Apply properties (fallback to defaults when missing) ---
        delay    = getInt(p, "timer_delay", delay);                            // Required by PDF
        listKind = getStr(p, "list", listKind).toLowerCase();                  // Required by PDF
        windowW  = getInt(p, "window_size_x", windowW);                        // Required by PDF
        windowH  = getInt(p, "window_size_y", windowH);                        // Required by PDF

        starX    = getInt(p, "star_position_x", starX);
        starY    = getInt(p, "star_position_y", starY);
        starSize = getInt(p, "star_size", starSize);
        starVx   = getDouble(p, "star_velocity_x", starVx);
        starVy   = getDouble(p, "star_velocity_y", starVy);

        genXProb = getDouble(p, "gen_x", genXProb);
        genYProb = getDouble(p, "gen_y", genYProb);
        bodySize = getInt(p, "body_size", bodySize);
        bodyVelocityRange = getInt(p, "body_velocity", bodyVelocityRange);

        // --- Create chosen list and add the red star as the first body (PDF requires single list) ---
        bodies = ListFactory.make(listKind);
        CelestialBody star = new CelestialBody(starX, starY, starVx, starVy, starSize, Color.RED, true);
        bodies.add(star);

        // Initialize timer using delay from config (keeps your original line shape)
        tm = new Timer(delay, this); // TODO: Replace the first argument with delay with value from config file.

        // Size preference; actual top-level frame resize happens on first paint
        setPreferredSize(new Dimension(windowW, windowH));
        setBackground(Color.BLACK);

        // TODO: Consider removing the next two lines (coordinates) for random starting locations.
        x1 = 100; y1 = 50;
        x2 = 200; y2 = 400;
    }

    private static int getInt(java.util.Properties p, String k, int def) {
        try { return Integer.parseInt(p.getProperty(k, ""+def).trim()); } catch (Exception e) { return def; }
    }
    private static double getDouble(java.util.Properties p, String k, double def) {
        try { return Double.parseDouble(p.getProperty(k, ""+def).trim()); } catch (Exception e) { return def; }
    }
    private static String getStr(java.util.Properties p, String k, String def) {
        try { String v = p.getProperty(k); return (v==null)?def:v.trim(); } catch (Exception e) { return def; }
    }

    /**
     * Paint two demo balls (kept from template) and then draw all bodies from our custom List.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Probably best you leave this as is.

        // TODO: Paint each ball. Here's how to paint two balls, one after the other:
        g.setColor(Color.BLUE);
        g.fillOval(x1, y1, 20, 20);

        g.setColor(Color.RED);
        g.fillOval(x2, y2, 20, 20);

        // Draw star+comets from custom List
        for (int i = 0; i < bodies.size(); i++) {
            bodies.get(i).draw(g);
        }

        // First paint: resize top-level frame to config size (meets "canvas size from properties")
        if (!sizedOnce) {
            Window w = SwingUtilities.getWindowAncestor(this);
            if (w != null) { w.setSize(windowW, windowH); sizedOnce = true; }
        }

        // Recommend you leave the next line as is
        tm.start();
    }

    /**
     * Each tick: move sample balls, spawn new comets by gen_x/gen_y, move all bodies, remove out-of-bounds.
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        // TODO: Change the location of each ball. Here's an example of them moving across the screen:
        x1 += 10;
        x2 -= 15;
        // These two "if" statements keep the balls on the screen in case they go off one side.
        if (x1 > 640)
            x1 = 0;
        if (x2 < 0)
            x2 = 640;

        // Spawn along top/bottom with probability gen_x
        if (rng.nextDouble() < genXProb) spawnCometAlongXEdge();
        // Spawn along left/right with probability gen_y
        if (rng.nextDouble() < genYProb) spawnCometAlongYEdge();

        // Move all bodies
        for (int i = 0; i < bodies.size(); i++) bodies.get(i).step();

        // Remove off-screen comets (keep the star)
        for (int i = bodies.size() - 1; i >= 0; i--) {
            CelestialBody b = bodies.get(i);
            if (!b.isStar && b.isOutside(windowW, windowH)) {
                bodies.remove(i); // interface requires remove(int)
            }
        }

        // Keep this at the end of the function (no matter what you do above):
        repaint();
    }

    private void spawnCometAlongXEdge() {
        boolean top = rng.nextBoolean();
        int y = top ? 0 : windowH;
        int x = rng.nextInt(windowW);
        int vx = nonZeroRand(-bodyVelocityRange, bodyVelocityRange);
        int vy = nonZeroRand(-bodyVelocityRange, bodyVelocityRange);
        bodies.add(new CelestialBody(x, y, vx, vy, bodySize, Color.WHITE, false));
    }

    private void spawnCometAlongYEdge() {
        boolean left = rng.nextBoolean();
        int x = left ? 0 : windowW;
        int y = rng.nextInt(windowH);
        int vx = nonZeroRand(-bodyVelocityRange, bodyVelocityRange);
        int vy = nonZeroRand(-bodyVelocityRange, bodyVelocityRange);
        bodies.add(new CelestialBody(x, y, vx, vy, bodySize, Color.LIGHT_GRAY, false));
    }

    private int nonZeroRand(int lo, int hi) {
        int v; do { v = lo + rng.nextInt(hi - lo + 1); } while (v == 0); return v;
    }

    /**
     * Entry point. If a property file path is provided as args[0], it is used for configuration.
     * This satisfies the PDF requirement: "name of the property file will be provided as the first argument".
     */
    public static void main(String[] args) {
        System.out.println("Massive Motion starting...");
        // MassiveMotion mm = new MassiveMotion(args[0]);

        // Support property file via command line arg (PDF hard requirement).
        if (args != null && args.length > 0) {
            System.setProperty("cfg", args[0]);
        }

        MassiveMotion mm = new MassiveMotion();

        JFrame jf = new JFrame();
        jf.setTitle("Massive Motion");
        jf.setSize(640, 480); // TODO: Replace with the size from configuration!
        jf.add(mm);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
