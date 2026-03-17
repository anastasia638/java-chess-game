package modele.plateau;

import modele.jeu.Piece;
import java.util.ArrayList;
import java.util.List;

public class DecorateurCasesEnLigne extends DecorateurCasesAccessibles {

    public DecorateurCasesEnLigne(DecorateurCasesAccessibles suivant) {
        super(suivant);
    }

    @Override
    public List<Case> getMesCasesPossibles(Piece piece, Plateau plateau) {
        List<Case> accessibles = new ArrayList<>();
        int x = piece.getCase().getX();
        int y = piece.getCase().getY();

        // Directions: Vertical & Horizontal (Up, Down, Left, Right)
        Direction[] directions = {Direction.HAUT, Direction.BAS, Direction.GAUCHE, Direction.DROITE};

        for (Direction dir : directions) {
            int newX = x + dir.getDx();
            int newY = y + dir.getDy();

            // Continue moving in the current direction until blocked
            while (plateau.estDansLimites(newX, newY)) {
                Case cible = plateau.getCase(newX, newY);

                if (cible.estLibre()) {
                    accessibles.add(cible);
                } else {
                    if (!cible.getPiece().getCouleur().equals(piece.getCouleur())) {
                        accessibles.add(cible); // Capture if enemy
                    }
                    break; // Stop if any piece encountered
                }

                newX += dir.getDx();
                newY += dir.getDy();
            }
        }

        return accessibles;
    }
}


