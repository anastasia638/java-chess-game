package modele.jeu;

import modele.plateau.DecorateurCasesEnL;
import modele.plateau.Plateau;

import java.util.List;

public class Cavalier extends Piece {

    public Cavalier(Plateau plateau, String couleur) {
        super(plateau);
        this.couleur = couleur;
        this.decorateurCasesAccessibles = new DecorateurCasesEnL(null);
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
        return (couleur.equals("blanc") ? "w" : "b") + "N";
    }
}
