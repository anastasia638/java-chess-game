package modele.plateau;

import modele.jeu.Piece;

/**
 * Représente une case du plateau
 */
public class Case {
    private final int x;
    private final int y;
    private Piece piece;

    public Case(int x, int y) {
        this.x = x;
        this.y = y;
        this.piece = null;
    }

    public boolean estLibre() {
        return piece == null;
    }

    public Piece getPiece() {
        return piece;
    }

    public void quitterLaCase() {
        piece = null;
    }

    public void poserPiece(Piece p) {
        piece = p;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}


