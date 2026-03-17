package modele.plateau;

import modele.jeu.Piece;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstraite du Pattern Décorateur pour les déplacements
 */
public abstract class DecorateurCasesAccessibles {

    protected DecorateurCasesAccessibles suivant; // Permet d'enchaîner plusieurs décorateurs

    public DecorateurCasesAccessibles(DecorateurCasesAccessibles suivant) {
        this.suivant = suivant;
    }

    /**
     * Retourne toutes les cases accessibles pour une pièce (en combinant les décorateurs)
     */
    public List<Case> getCasesPossibles(Piece piece, Plateau plateau) {
        List<Case> accessibles = getMesCasesPossibles(piece, plateau);

        // Si un décorateur suivant est présent, on lui ajoute ses cases aussi
        if (suivant != null) {
            accessibles.addAll(suivant.getCasesPossibles(piece, plateau));
        }

        return accessibles;
    }

    /**
     * À implémenter dans chaque sous-classe pour définir un type de déplacement
     */
    public abstract List<Case> getMesCasesPossibles(Piece piece, Plateau plateau);
}
