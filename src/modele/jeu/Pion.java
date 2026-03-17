package modele.jeu;

import modele.plateau.Case;
import modele.plateau.Plateau;
import java.util.ArrayList;
import java.util.List;

public class Pion extends Piece {

    public Pion(Plateau plateau, String couleur) {
        super(plateau);
        this.couleur = couleur;
    }

    @Override
    public String getCouleur() {
        return couleur;
    }

    @Override
    public List<Case> getDeplacementsPossibles() {
        List<Case> deplacements = new ArrayList<>();

        int x = getCase().getX();
        int y = getCase().getY();

        int direction = couleur.equals("blanc") ? -1 : 1; // White goes up (-1), Black goes down (+1)
        int startRow = couleur.equals("blanc") ? 6 : 1;

        // Forward one step
        if (plateau.estDansLimites(x, y + direction) && plateau.getCase(x, y + direction).estLibre()) {
            deplacements.add(plateau.getCase(x, y + direction));

            // Forward two steps (only from initial position)
            if (y == startRow && plateau.estDansLimites(x, y + 2 * direction) &&
                    plateau.getCase(x, y + 2 * direction).estLibre()) {
                deplacements.add(plateau.getCase(x, y + 2 * direction));
            }
        }

        // Capture diagonally left
        if (plateau.estDansLimites(x - 1, y + direction)) {
            Case diagLeft = plateau.getCase(x - 1, y + direction);
            if (!diagLeft.estLibre() && !diagLeft.getPiece().getCouleur().equals(this.couleur)) {
                deplacements.add(diagLeft);
            }
        }

        // Capture diagonally right
        if (plateau.estDansLimites(x + 1, y + direction)) {
            Case diagRight = plateau.getCase(x + 1, y + direction);
            if (!diagRight.estLibre() && !diagRight.getPiece().getCouleur().equals(this.couleur)) {
                deplacements.add(diagRight);
            }
        }

        return deplacements;
    }

    @Override
    public String getNomImage() {
        return (couleur.equals("blanc") ? "w" : "b") + "P";
    }
}


