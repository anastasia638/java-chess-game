package modele.plateau;

import modele.jeu.Piece;
import java.util.ArrayList;
import java.util.List;

public class DecorateurCasesDiagonales extends DecorateurCasesAccessibles {

    public DecorateurCasesDiagonales(DecorateurCasesAccessibles suivant) {
        super(suivant);
    }

    @Override
    public List<Case> getMesCasesPossibles(Piece piece, Plateau plateau) {
        List<Case> accessibles = new ArrayList<>();
        int x = piece.getCase().getX();
        int y = piece.getCase().getY();

        // Diagonal directions: top-left, top-right, bottom-left, bottom-right
        Direction[] directions = {Direction.HAUT_GAUCHE, Direction.HAUT_DROITE, Direction.BAS_GAUCHE, Direction.BAS_DROITE};

        for (Direction dir : directions) {
            int dx = dir.getDx();
            int dy = dir.getDy();

            int newX = x + dx;
            int newY = y + dy;

            // Check if we can move in this direction
            while (plateau.estDansLimites(newX, newY)) {
                Case cible = plateau.getCase(newX, newY);

                if (cible.estLibre()) {
                    accessibles.add(cible);
                } else {
                    if (!cible.getPiece().getCouleur().equals(piece.getCouleur())) {
                        accessibles.add(cible); // Opponent's piece can be captured
                    }
                    break; // Stop moving in this direction if there's a piece
                }

                newX += dx;
                newY += dy;
            }
        }

        return accessibles;
    }
}

