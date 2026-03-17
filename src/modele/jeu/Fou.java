package modele.jeu;

import modele.plateau.DecorateurCasesDiagonales;
import modele.plateau.Plateau;

import java.util.List;

public class Fou extends Piece {

    private String couleur;  // Add this field for color

    public Fou(Plateau plateau, String couleur) {
        super(plateau);
        this.couleur = couleur;  // Sets the color of the bishop
        this.decorateurCasesAccessibles = new DecorateurCasesDiagonales(null);  // Bishop's diagonal movement
    }

    @Override
    public String getCouleur() {
        return couleur;  // Returns the color of the bishop
    }

    @Override
    public List<modele.plateau.Case> getDeplacementsPossibles() {
        return decorateurCasesAccessibles.getMesCasesPossibles(this, plateau);  // Get possible diagonal moves
    }

    @Override
    public String getNomImage() {
        // Returns the image file name based on the color
        return (couleur.equals("blanc") ? "w" : "b") + "B";  // "wB" for white bishop, "bB" for black bishop
    }
}

