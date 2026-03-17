package modele.plateau;

/**
 * Directions possibles dans le plateau (cardinales + diagonales)
 */
public enum Direction {
    HAUT(0, -1),
    BAS(0, 1),
    GAUCHE(-1, 0),
    DROITE(1, 0),
    HAUT_GAUCHE(-1, -1),
    HAUT_DROITE(1, -1),
    BAS_GAUCHE(-1, 1),
    BAS_DROITE(1, 1);

    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }
}
