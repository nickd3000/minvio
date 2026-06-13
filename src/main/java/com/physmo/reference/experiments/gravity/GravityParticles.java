package com.physmo.reference.experiments.gravity;


import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.MinvioApp;

import java.awt.Color;
import java.util.Arrays;
import java.util.Objects;

public class GravityParticles extends MinvioApp {

    private static final int DIRECT_INTERACTION_RADIUS_CELLS = 1;
    private static final int DIRECT_INTERACTION_RADIUS_SQUARED =
            DIRECT_INTERACTION_RADIUS_CELLS * DIRECT_INTERACTION_RADIUS_CELLS;
    private static final int AGGREGATION_PARTICLE_THRESHOLD = 200;
    private static final int CELL_DISTANCE_CULL = 8;
    private static final int CELL_DISTANCE_CULL_SQUARED = CELL_DISTANCE_CULL * CELL_DISTANCE_CULL;
    private static final double MAX_FRAME_DELTA_SECONDS = 0.05;
    private static final double MAX_SIMULATION_STEP_SECONDS = 1.0 / 120.0;
    private static final double GRAVITATIONAL_CONSTANT = 42.0;
    private static final boolean ENABLE_INCREASED_GRAVITY_SOFTENING = true;
    private static final double BASE_GRAVITY_SOFTENING_SQUARED = 4.0;
    private static final double INCREASED_GRAVITY_SOFTENING_SQUARED = 36.0;
    private static final boolean ENABLE_SHORT_RANGE_REPULSION = true;
    private static final double REPULSION_RADIUS = 10.0;
    private static final double REPULSION_RADIUS_SQUARED = REPULSION_RADIUS * REPULSION_RADIUS;
    private static final double REPULSION_ACCELERATION = 25.0;
    private static final double MAX_CONTACT_ACCELERATION = 30.0;
    private static final double CONTACT_DAMPING_PER_SECOND = 12.0;
    private static final double BOUNDARY_RESTITUTION = 0.9;
    private static final double VELOCITY_RETENTION_PER_SECOND = 0.995;
    private static final StartingState DEFAULT_STARTING_STATE = StartingState.ORBITAL_DISK;
    private static final double ORBITAL_DISK_RADIUS_FRACTION = 0.4;
    private static final double ORBITAL_SPEED_SCALE = 1.0;
    private static final double ORBITAL_VELOCITY_VARIATION = 1.5;

    public enum StartingState {
        UNIFORM_RANDOM,
        ORBITAL_DISK
    }

    int numParticles = 4096 / 2;

    int cellSize = 128 / 2;
    GravityGrid grid;

    // Instead of interleaved data, separate arrays for better cache locality
    double[] px = new double[numParticles];
    double[] py = new double[numParticles];
    double[] pdx = new double[numParticles];
    double[] pdy = new double[numParticles];
    double[] contactVelocityX = new double[numParticles];
    double[] contactVelocityY = new double[numParticles];
    boolean[] pa = new boolean[numParticles];

    public static void main(String... args) {
        MinvioApp app = new GravityParticles();
        app.start(800, 800, "GravityParticles", 30);
    }

    @Override
    public void init(BasicDisplay bd) {
        super.init(bd);
        grid = new GravityGrid(getWidth(), getHeight(), cellSize, numParticles);
        resetParticles(DEFAULT_STARTING_STATE);
    }

    public void resetParticles(StartingState startingState) {
        Objects.requireNonNull(startingState, "Starting state cannot be null");
        switch (startingState) {
            case UNIFORM_RANDOM -> initializeUniformRandom();
            case ORBITAL_DISK -> initializeOrbitalDisk();
        }
        refreshBucketGrid();
    }

    private void initializeUniformRandom() {
        for (int i = 0; i < numParticles; i++) {
            double x = Math.random() * getWidth();
            double y = Math.random() * getHeight();
            double dx = (Math.random() - 0.5) * 15;
            double dy = (Math.random() - 0.5) * 15;
            createParticle(i, x, y, dx, dy);
        }
    }

    private void initializeOrbitalDisk() {
        double centerX = getWidth() / 2.0;
        double centerY = getHeight() / 2.0;
        double diskRadius = Math.min(getWidth(), getHeight()) * ORBITAL_DISK_RADIUS_FRACTION;

        for (int i = 0; i < numParticles; i++) {
            double angle = Math.random() * Math.PI * 2;
            double radius = Math.sqrt(Math.random()) * diskRadius;
            double radialX = Math.cos(angle);
            double radialY = Math.sin(angle);
            double x = centerX + radialX * radius;
            double y = centerY + radialY * radius;

            double speed = ORBITAL_SPEED_SCALE * Math.sqrt(radius);
            double dx = -radialY * speed + randomOrbitalVelocityVariation();
            double dy = radialX * speed + randomOrbitalVelocityVariation();
            createParticle(i, x, y, dx, dy);
        }
    }

    private double randomOrbitalVelocityVariation() {
        return (Math.random() * 2 - 1) * ORBITAL_VELOCITY_VARIATION;
    }

    public void createParticle(int index, double x, double y, double dx, double dy) {

        px[index] = x;
        py[index] = y;
        pdx[index] = dx;
        pdy[index] = dy;
        pa[index] = true;
    }


    @Override
    public void draw(double delta) {
        cls(Color.black);
        setDrawColor(new Color(200, 200, 255));
        for (int i = 0; i < numParticles; i++) {
            drawPoint((int) px[i], (int) py[i]);
        }
    }

    @Override
    public void update(BasicDisplay bd, double delta) {
        if (!(delta > 0)) return;

        double simulationTime = Math.min(delta, MAX_FRAME_DELTA_SECONDS);
        int stepCount = (int) Math.ceil(simulationTime / MAX_SIMULATION_STEP_SECONDS);
        double stepDelta = simulationTime / stepCount;
        for (int step = 0; step < stepCount; step++) {
            simulateStep(stepDelta);
        }
    }

    private void simulateStep(double delta) {
        for (int i = 0; i < numParticles; i++) {
            integratePosition(i, delta);
        }

        refreshBucketGrid();
        clearContactVelocities();
        applyGravity(delta);
        applyContactVelocities(delta);
        applyDamping(delta);
    }

    private void applyGravity(double delta) {
        int gridWidth = grid.getWidth();
        int gridHeight = grid.getHeight();

        for (int gy = 0; gy < gridHeight; gy++) {
            for (int gx = 0; gx < gridWidth; gx++) {
                int minY = Math.max(0, gy - CELL_DISTANCE_CULL);
                int maxY = Math.min(gridHeight - 1, gy + CELL_DISTANCE_CULL);
                for (int oy = minY; oy <= maxY; oy++) {
                    int cellDy = gy - oy;
                    int maxCellDx = (int) Math.sqrt(CELL_DISTANCE_CULL_SQUARED - cellDy * cellDy);
                    int minX = Math.max(0, gx - maxCellDx);
                    int maxX = Math.min(gridWidth - 1, gx + maxCellDx);
                    for (int ox = minX; ox <= maxX; ox++) {
                        int cellDx = gx - ox;
                        int cellDistanceSquared = cellDx * cellDx + cellDy * cellDy;
                        processGridSquare(gx, gy, ox, oy, cellDistanceSquared, delta);
                    }
                }

            }
        }
    }

    public void processGridSquare(int gx, int gy, int ox, int oy, int cellDistanceSquared, double delta) {
        if (cellDistanceSquared > CELL_DISTANCE_CULL_SQUARED) return;

        int thisCellCount = grid.getCellCount(gx, gy);
        int otherCellCount = grid.getCellCount(ox, oy);

        // Skip if either cell is empty
        if (thisCellCount == 0 || otherCellCount == 0) return;

        int thisCellOffset = grid.getCellOffset(gx, gy);
        boolean sameCell = cellDistanceSquared == 0;
        boolean useDirectInteractions = sameCell
                || (cellDistanceSquared <= DIRECT_INTERACTION_RADIUS_SQUARED
                && thisCellCount <= AGGREGATION_PARTICLE_THRESHOLD
                && otherCellCount <= AGGREGATION_PARTICLE_THRESHOLD);

        if (useDirectInteractions) {
            int thisCellId = gx + gy * grid.getWidth();
            int otherCellId = ox + oy * grid.getWidth();
            if (otherCellId < thisCellId) return;

            int otherCellOffset = grid.getCellOffset(ox, oy);
            for (int i = 0; i < thisCellCount; i++) {
                int particleI = grid.getParticle(thisCellOffset + i);
                int firstOtherIndex = sameCell ? i + 1 : 0;
                for (int j = firstOtherIndex; j < otherCellCount; j++) {
                    int particleO = grid.getParticle(otherCellOffset + j);
                    interactParticlePair(particleI, particleO, delta);
                }
            }

        } else {
            double p2x = grid.getCellCenterX(ox, oy);
            double p2y = grid.getCellCenterY(ox, oy);

            for (int i = 0; i < thisCellCount; i++) {
                attractParticles(grid.getParticle(thisCellOffset + i), p2x, p2y, otherCellCount, delta);
            }

        }


    }

    public void integratePosition(int i, double delta) {
        double x = px[i];
        double y = py[i];
        double dx = pdx[i];
        double dy = pdy[i];

        x += dx * delta;
        y += dy * delta;

        if (x < 0) {
            x = 0;
            if (dx < 0) dx = -dx * BOUNDARY_RESTITUTION;
        } else if (x > getWidth()) {
            x = getWidth();
            if (dx > 0) dx = -dx * BOUNDARY_RESTITUTION;
        }
        if (y < 0) {
            y = 0;
            if (dy < 0) dy = -dy * BOUNDARY_RESTITUTION;
        } else if (y > getHeight()) {
            y = getHeight();
            if (dy > 0) dy = -dy * BOUNDARY_RESTITUTION;
        }

        px[i] = x;
        py[i] = y;
        pdx[i] = dx;
        pdy[i] = dy;
    }


    private void interactParticlePair(int p1, int p2, double delta) {
        double dx = px[p2] - px[p1];
        double dy = py[p2] - py[p1];
        double distanceSquared = dx * dx + dy * dy;
        double softeningSquared = getGravitySofteningSquared();
        double softenedDistanceSquared = distanceSquared + softeningSquared;
        double gravitationalAccelerationScale =
                GRAVITATIONAL_CONSTANT * delta / softenedDistanceSquared;
        if (ENABLE_SHORT_RANGE_REPULSION && distanceSquared < REPULSION_RADIUS_SQUARED) {
            gravitationalAccelerationScale *= Math.sqrt(distanceSquared) / REPULSION_RADIUS;
        }

        double velocityChangeX = dx * gravitationalAccelerationScale;
        double velocityChangeY = dy * gravitationalAccelerationScale;
        if (ENABLE_SHORT_RANGE_REPULSION && distanceSquared < REPULSION_RADIUS_SQUARED) {
            double directionX;
            double directionY;
            double distance;

            if (distanceSquared > 0) {
                distance = Math.sqrt(distanceSquared);
                directionX = dx / distance;
                directionY = dy / distance;
            } else {
                int lowId = Math.min(p1, p2);
                int highId = Math.max(p1, p2);
                double angle = ((lowId * 31L + highId * 17L) & 1023) * (Math.PI * 2.0 / 1024.0);
                distance = 0;
                directionX = Math.cos(angle);
                directionY = Math.sin(angle);
            }

            double overlap = 1.0 - distance / REPULSION_RADIUS;
            double repulsionVelocityChange = REPULSION_ACCELERATION * overlap * delta;
            double contactChangeX = -directionX * repulsionVelocityChange;
            double contactChangeY = -directionY * repulsionVelocityChange;

            double relativeVelocityX = pdx[p2] - pdx[p1];
            double relativeVelocityY = pdy[p2] - pdy[p1];
            double closingSpeed = relativeVelocityX * directionX + relativeVelocityY * directionY;
            if (closingSpeed < 0) {
                double dampingFraction = 1.0 - Math.exp(-CONTACT_DAMPING_PER_SECOND * delta);
                double dampingVelocityChange = -closingSpeed * dampingFraction * 0.5;
                contactChangeX -= directionX * dampingVelocityChange;
                contactChangeY -= directionY * dampingVelocityChange;
            }

            contactVelocityX[p1] += contactChangeX;
            contactVelocityY[p1] += contactChangeY;
            contactVelocityX[p2] -= contactChangeX;
            contactVelocityY[p2] -= contactChangeY;
        }

        pdx[p1] += velocityChangeX;
        pdy[p1] += velocityChangeY;
        pdx[p2] -= velocityChangeX;
        pdy[p2] -= velocityChangeY;
    }

    public void attractParticles(int p1, double p2x, double p2y, double p2mass, double delta) {
        double dx = p2x - px[p1];
        double dy = p2y - py[p1];
        double softeningSquared = getGravitySofteningSquared();
        double distSquared = dx * dx + dy * dy + softeningSquared;
        double d = Math.sqrt(distSquared);
        double force = (GRAVITATIONAL_CONSTANT / d) * p2mass;

        dx /= d;
        dy /= d;

        pdx[p1] += dx * force * delta;
        pdy[p1] += dy * force * delta;
    }

    private double getGravitySofteningSquared() {
        return ENABLE_INCREASED_GRAVITY_SOFTENING
                ? INCREASED_GRAVITY_SOFTENING_SQUARED
                : BASE_GRAVITY_SOFTENING_SQUARED;
    }

    private void clearContactVelocities() {
        Arrays.fill(contactVelocityX, 0);
        Arrays.fill(contactVelocityY, 0);
    }

    private void applyContactVelocities(double delta) {
        double maxVelocityChange = MAX_CONTACT_ACCELERATION * delta;
        double maxVelocityChangeSquared = maxVelocityChange * maxVelocityChange;

        for (int i = 0; i < numParticles; i++) {
            double velocityChangeX = contactVelocityX[i];
            double velocityChangeY = contactVelocityY[i];
            double velocityChangeSquared =
                    velocityChangeX * velocityChangeX + velocityChangeY * velocityChangeY;
            if (velocityChangeSquared > maxVelocityChangeSquared) {
                double scale = maxVelocityChange / Math.sqrt(velocityChangeSquared);
                velocityChangeX *= scale;
                velocityChangeY *= scale;
            }
            pdx[i] += velocityChangeX;
            pdy[i] += velocityChangeY;
        }
    }

    private void applyDamping(double delta) {
        double dampingFactor = Math.pow(VELOCITY_RETENTION_PER_SECOND, delta);
        for (int i = 0; i < numParticles; i++) {
            pdx[i] *= dampingFactor;
            pdy[i] *= dampingFactor;
        }
    }

    public void refreshBucketGrid() {
        grid.clear();
        for (int i = 0; i < numParticles; i++) {
            grid.countParticle(px[i], py[i]);
        }

        grid.prepareForPopulation();
        for (int i = 0; i < numParticles; i++) {
            grid.addParticle(px[i], py[i], i);
        }
    }
}
