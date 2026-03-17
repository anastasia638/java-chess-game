package modele.jeu;

import modele.plateau.Case; // ← Add this import
import modele.plateau.DecorateurCasesDiagonales;
import modele.plateau.DecorateurCasesEnLigne;
import modele.plateau.Plateau;

import java.util.List;

public class Reine extends Piece {

    // Constructor
    public Reine(Plateau plateau, String couleur) {
        super(plateau);
        this.couleur = couleur;

        // Combination of movements: line + diagonal
        decorateurCasesAccessibles = new DecorateurCasesEnLigne(
                new DecorateurCasesDiagonales(null)
        );
    }

    @Override
    public String getCouleur() {
        return couleur;
    }

    @Override
    public List<Case> getDeplacementsPossibles() {
        // Correctly chaining decorators
        List<Case> possibleMoves = decorateurCasesAccessibles.getCasesPossibles(this, plateau);

        // Debugging: print possible moves
        System.out.println("Possible moves for " + getNomImage() + ":");
        for (Case move : possibleMoves) {
            System.out.println("Move to: " + move.getX() + ", " + move.getY());
        }

        return possibleMoves;
    }

    @Override
    public String getNomImage() {
        return (couleur.equals("blanc") ? "w" : "b") + "Q";
    }
}
