package modele.plateau;

import modele.jeu.Piece;

import java.util.ArrayList;
import java.util.List;

/**
 * Décorateur pour les déplacements en L (Cavalier)
 */
public class DecorateurCasesEnL extends DecorateurCasesAccessibles {

    public DecorateurCasesEnL(DecorateurCasesAccessibles suivant) {
        super(suivant);
    }

    @Override
    public List<Case> getMesCasesPossibles(Piece piece, Plateau plateau) {
        List<Case> accessibles = new ArrayList<>();

        int x = piece.getCase().getX();
        int y = piece.getCase().getY();

        // Tous les mouvements possibles en "L" (Cavalier)
        int[][] deplacements = {
                { 2, 1 }, { 1, 2 }, { -1, 2 }, { -2, 1 },
                { -2, -1 }, { -1, -2 }, { 1, -2 }, { 2, -1 }
        };

        for (int[] d : deplacements) {
            int newX = x + d[0];
            int newY = y + d[1];

            if (plateau.estDansLimites(newX, newY)) {
                Case cible = plateau.getCase(newX, newY);

                if (cible.estLibre() || !cible.getPiece().getCouleur().equals(piece.getCouleur())) {
                    accessibles.add(cible);
                }
            }
        }

        return accessibles;
    }
}
