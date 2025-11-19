import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.Random;

/**
 * CS245 Project 2 - Massive / Movement
 * This version keeps the original template structure exactly as given,
 * but now also fully implements the project requirements:
 * - Reads config file
 * - Creates star + comets
 * - Uses custom List implementation
 * - Animates objects using Swing Timer
 */
public class MassiveMotion extends JPanel implements ActionListener {

    protected Timer tm;   // keep template variable

    // template ball coordinates (we keep them, but they are separate from project objects)
    protected int x1, y1;
    protected int x2, y2;

    // ==== fields added for actual project animation ====
    private int windowW = 640;
    private int windowH = 480;
    private int delay   = 75;

    private double genXProb;
    private double genYProb;
    private int bodySize;
    private int bodyVelocityRange;

    private int starX, starY, starSize;
    private double starVx, starVy;

    private List<CelestialBody> bodies;   // student's custom List
    private final Random rng = new Random();

    /**
     * Represents any celestial object (star or comet).
     */
    private static class CelestialBody {
        double x, y, vx, vy;
        int radius;
        boolean isStar;
        Color color;

        CelestialBody(double x, double y, double vx, double vy, int r,
                      Color c, boolean isStar) {
            this.x = x; this.y = y;
            this.vx = vx; this.vy = vy;
            this.radius = r;
            this.color = c;
            this.isStar = isStar;
        }

        void step() { x += vx; y += vy; }

        void draw(Graphics g) {
            g.setColor(color);
            int d = radius * 2;
            g.fillOval((int)(x - radius), (int)(y - radius), d, d);
        }

        boolean isOutside(int W, int H) {
            return x + radius < 0 || x - radius > W ||
                   y + radius < 0 || y - radius > H;
        }
    }

    // ==== constructor MUST remain the template signature ====
    public MassiveMotion() {

        // ===========================
        // 1. Load config from args[0]
        // ===========================
        String cfg = System.getProperty("cfg", null);
        Properties p = new Properties();

        if (cfg != null) {
            try (FileInputStream in = new FileInputStream(cfg)) {
                p.load(in);
                System.out.println("[OK] Loaded config: " + cfg);
            } catch (Exception e) {
                System.out.println("[ERROR] Cannot load config, exiting.");
                System.exit(1);
            }
        } else {
            System.out.println("[ERROR] No config provided. Run using:");
            System.out.println("  java MassiveMotion <config-file>");
            System.exit(1);
        }

        // read config values
        delay   = getInt(p, "timer_delay", 75);
        windowW = getInt(p, "window_size_x", 640);
        windowH = getInt(p, "window_size_y", 480);

        genXProb = getDouble(p, "gen_x", 0.06);
        genYProb = getDouble(p, "gen_y", 0.06);
        bodySize = getInt(p, "body_size", 10);
        bodyVelocityRange = getInt(p, "body_velocity", 3);

        starX = getInt(p, "star_position_x", 320);
        starY = getInt(p, "star_position_y", 240);
        starSize = getInt(p, "star_size", 30);
        starVx = getDouble(p, "star_velocity_x", 0);
        starVy = getDouble(p, "star_velocity_y", 0);

        String listKind = p.getProperty("list", "arraylist").toLowerCase();
        bodies = ListFactory.make(listKind);

        // create the star as the first object (required by PDF)
        bodies.add(new CelestialBody(
                starX, starY, starVx, starVy, starSize, Color.RED, true
        ));

        // keep template initial values for the two sample balls
        x1 = 100; y1 = 50;
        x2 = 200; y2 = 400;

        // timer uses config delay but still uses the template variable tm
        tm = new Timer(delay, this);

        // set preferred window size for pack()
        setPreferredSize(new Dimension(windowW, windowH));
    }

    private static int getInt(Properties p, String k, int def) {
        try { return Integer.parseInt(p.getProperty(k, ""+def).trim()); }
        catch (Exception e) { return def; }
    }

    private static double getDouble(Properties p, String k, double def) {
        try { return Double.parseDouble(p.getProperty(k, ""+def).trim()); }
        catch (Exception e) { return def; }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // keep the template’s two balls
        g.setColor(Color.BLUE);
        g.fillOval(x1, y1, 20, 20);

        g.setColor(Color.RED);
        g.fillOval(x2, y2, 20, 20);

        // draw the star + comets from our custom list
        for (int i = 0; i < bodies.size(); i++) {
            bodies.get(i).draw(g);
        }

        // template requires calling tm.start() here
        tm.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // template ball movement (kept)
        x1 += 10;
        x2 -= 15;
        if (x1 > windowW) x1 = 0;
        if (x2 < 0) x2 = windowW;

        // ====== comet spawning ======
        if (rng.nextDouble() < genXProb) spawnCometX();
        if (rng.nextDouble() < genYProb) spawnCometY();

        // move all celestial bodies
        for (int i = 0; i < bodies.size(); i++) {
            bodies.get(i).step();
        }

        // remove comets that left the screen (star stays)
        for (int i = bodies.size() - 1; i >= 0; i--) {
            CelestialBody b = bodies.get(i);
            if (!b.isStar && b.isOutside(windowW, windowH)) {
                bodies.remove(i);
            }
        }

        repaint();
    }

    private void spawnCometX() {
        boolean top = rng.nextBoolean();
        int y = top ? 0 : windowH;
        int x = rng.nextInt(windowW);

        int vx = nonZero(-bodyVelocityRange, bodyVelocityRange);
        int vy = nonZero(-bodyVelocityRange, bodyVelocityRange);

        bodies.add(new CelestialBody(x, y, vx, vy, bodySize, Color.WHITE, false));
    }

    private void spawnCometY() {
        boolean left = rng.nextBoolean();
        int x = left ? 0 : windowW;
        int y = rng.nextInt(windowH);

        int vx = nonZero(-bodyVelocityRange, bodyVelocityRange);
        int vy = nonZero(-bodyVelocityRange, bodyVelocityRange);

        bodies.add(new CelestialBody(x, y, vx, vy, bodySize, Color.LIGHT_GRAY, false));
    }

    private int nonZero(int lo, int hi) {
        int v;
        do {
            v = lo + rng.nextInt(hi - lo + 1);
        } while (v == 0);
        return v;
    }

    /**
     * Template main() must be preserved.
     * Only difference: we pass args[0] to the constructor using System property.
     */
    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Usage: java MassiveMotion <config-file>");
            System.exit(1);
        }

        // pass config path using system property (to preserve constructor signature)
        System.setProperty("cfg", args[0]);

        System.out.println("Massive Motion starting...");
        MassiveMotion mm = new MassiveMotion();

        JFrame jf = new JFrame();
        jf.setTitle("Massive Motion");
        jf.add(mm);
        jf.pack(); // use preferred size
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
