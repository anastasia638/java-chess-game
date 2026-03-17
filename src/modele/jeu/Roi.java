package modele.jeu;

import modele.plateau.DecorateurCasesRoi;
import modele.plateau.Plateau;
import modele.plateau.Case;

import java.util.List;

public class Roi extends Piece {

    private String couleur;

    public Roi(Plateau _plateau, String couleur) {
        super(_plateau);
        this.couleur = couleur;
        this.decorateurCasesAccessibles = new DecorateurCasesRoi(null);
    }

    @Override
    public String getCouleur() {
        return couleur;
    }

    @Override
    public List<Case> getDeplacementsPossibles() {
        return decorateurCasesAccessibles.getCasesPossibles(this, plateau);
    }

    @Override
    public String getNomImage() {
        return (couleur.equals("blanc") ? "w" : "b") + "K";
    }
}
