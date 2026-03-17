package modele.jeu;

import modele.plateau.Case;
import modele.plateau.Plateau;
import javax.swing.JOptionPane;


import javax.swing.*;

public class Jeu {
    private Plateau plateau;
    public Coup coupRecu;
    private int scoreBlanc = 0;
    private int scoreNoir = 0;
    private boolean partieTerminee = false;
    private int coupSansCapture = 0;  // Compte des coups sans capture ni déplacement de pion
    private int dernierCoupPion = -1; // Dernier coup qui a déplacé un pion

    public Jeu() {
        plateau = new Plateau();
        plateau.placerPieces();
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public void envoyerCoup(Coup c) {
        if (partieTerminee) return; // Ne pas jouer si la partie est finie

        Piece pieceCapturee = c.getArr().getPiece(); // Vérifier s'il y a une capture
        plateau.deplacerPiece(c.dep, c.arr);

        // Vérifier la promotion
        plateau.verifierPromotion(c.arr);

        if (pieceCapturee != null) {
            if (pieceCapturee.estBlanc()) {
                scoreNoir++; // Si une pièce blanche est capturée, noir gagne un point
            } else {
                scoreBlanc++; // Si une pièce noire est capturée, blanc gagne un point
            }
            coupSansCapture = 0; // Réinitialiser le compteur de coups sans capture
        } else {
            coupSansCapture++; // Incrémenter si aucun coup de capture
        }

        // Vérifier si un roi a été capturé
        if (pieceCapturee instanceof Roi) {
            partieTerminee = true;
            afficherMessageVictoire(pieceCapturee.estBlanc() ? "Noir" : "Blanc");
        }

        // Vérification d'une partie nulle : 50 coups sans capture ni déplacement de pion
        if (coupSansCapture >= 50) {
            partieTerminee = true;
            afficherMessagePartieNulle();
        }



        plateau.notifyObservers(); // Notifier la vue
        System.out.println("Coup joué de (" + c.dep.getX() + "," + c.dep.getY() + ") → (" + c.arr.getX() + "," + c.arr.getY() + ")");
    }

    private void afficherMessageVictoire(String gagnant) {
        JOptionPane.showMessageDialog(null, "Le joueur " + gagnant + " a gagné la partie !", "Victoire", JOptionPane.INFORMATION_MESSAGE);
    }

    private void afficherMessagePartieNulle() {
        JOptionPane.showMessageDialog(null, "La partie est nulle !", "Match nul", JOptionPane.INFORMATION_MESSAGE);
    }

    public boolean estPat(String couleur) {
        for (int x = 0; x < Plateau.SIZE_X; x++) {
            for (int y = 0; y < Plateau.SIZE_Y; y++) {
                Case caseActuelle = plateau.getCase(x, y);
                Piece piece = caseActuelle.getPiece();
                if (piece != null && piece.getCouleur().equals(couleur)) {
                    // Vérifier si la pièce a un coup légal
                    for (Case destination : piece.getDeplacementsPossibles()) {
                        if (!plateau.estEnEchec(couleur)) {
                            return false; // Si un coup légal existe, ce n'est pas un pat
                        }
                    }
                }
            }
        }
        return true; // Aucun coup légal trouvé -> pat
    }

    public int getScoreBlanc() {
        return scoreBlanc;
    }

    public int getScoreNoir() {
        return scoreNoir;
    }


}
