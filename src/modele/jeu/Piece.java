package modele.jeu;

import modele.plateau.Case;
import modele.plateau.DecorateurCasesAccessibles;
import modele.plateau.Plateau;

import java.util.List;

public abstract class Piece {

    protected Case c;
    protected Plateau plateau;
    protected DecorateurCasesAccessibles decorateurCasesAccessibles;
    protected String couleur;

    public Piece(Plateau _plateau) {
        this.plateau = _plateau;
    }

    public void quitterCase() {
        if (c != null) {
            c.quitterLaCase();
        }
    }



    public void allerSurCase(Case _c) {
        if (c != null) {
            quitterCase(); // Libérer la case actuelle si elle existe
        }
        c = _c; // Mettre à jour la référence à la nouvelle case
        if (c != null) {
            c.poserPiece(this); // Poser la pièce sur la nouvelle case
        }
    }

    public Case getCase() {
        return c;
    }

    public abstract String getCouleur();

    public abstract List<Case> getDeplacementsPossibles();

    public abstract String getNomImage();
    public boolean estBlanc() {
        return "blanc".equalsIgnoreCase(getCouleur());
    }
    public boolean estNoir() {
        return "noir".equalsIgnoreCase(getCouleur());
    }
    protected boolean deplace; // Indicateur pour savoir si la pièce a bougé

    // Méthode pour obtenir l'état de déplacement
    public boolean estDeplace() {
        return deplace;
    }

    // Méthode pour marquer la pièce comme ayant bougé
    public void marquerCommeDeplace() {
        this.deplace = true;
    }
}


