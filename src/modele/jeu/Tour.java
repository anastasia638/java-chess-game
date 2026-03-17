package modele.jeu;

import modele.plateau.DecorateurCasesEnLigne;
import modele.plateau.Plateau;

import java.util.List;

public class Tour extends Piece {

    public Tour(Plateau plateau, String couleur) {
        super(plateau);
        this.couleur = couleur;
        this.decorateurCasesAccessibles = new DecorateurCasesEnLigne(null);
    }

    @Override
    public String getCouleur() {
        return couleur;
    }

    @Override
    public List<modele.plateau.Case> getDeplacementsPossibles() {
        return decorateurCasesAccessibles.getMesCasesPossibles(this, plateau);
    }

    @Override
    public String getNomImage() {
        return (couleur.equals("blanc") ? "w" : "b") + "R";
    }
}