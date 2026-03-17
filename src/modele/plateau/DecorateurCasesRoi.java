package modele.plateau;

import modele.jeu.Piece;
import java.util.ArrayList;
import java.util.List;

public class DecorateurCasesRoi extends DecorateurCasesAccessibles {

    public DecorateurCasesRoi(DecorateurCasesAccessibles suivant) {
        super(suivant);
    }

    @Override
    public List<Case> getMesCasesPossibles(Piece piece, Plateau plateau) {
        List<Case> accessibles = new ArrayList<>();
        int x = piece.getCase().getX();
        int y = piece.getCase().getY();

        // Le roi bouge d'une seule case dans toutes les directions
        Direction[] directions = {
                Direction.HAUT, Direction.BAS, Direction.GAUCHE, Direction.DROITE,
                Direction.HAUT_GAUCHE, Direction.HAUT_DROITE,
                Direction.BAS_GAUCHE, Direction.BAS_DROITE
        };

        for (Direction dir : directions) {
            int newX = x + dir.getDx();
            int newY = y + dir.getDy();

            if (plateau.estDansLimites(newX, newY)) {
                Case cible = plateau.getCase(newX, newY);

                if (cible.estLibre() ||
                        (!cible.estLibre() && !cible.getPiece().getCouleur().equals(piece.getCouleur()))) {
                    accessibles.add(cible);
                }
            }
        }

        return accessibles;
    }
}

