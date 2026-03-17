package modele.plateau;

import modele.jeu.*;

import java.awt.Point;
import java.util.HashMap;
import java.util.Observable;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;

public class Plateau extends Observable {

    public static final int SIZE_X = 8;
    public static final int SIZE_Y = 8;

    private HashMap<Case, Point> map = new HashMap<>();
    private Case[][] grilleCases = new Case[SIZE_X][SIZE_Y];

    public Plateau() {
        initPlateauVide();
        placerPieces();
    }

    public Case[][] getCases() {
        return grilleCases;
    }

    private void initPlateauVide() {
        for (int x = 0; x < SIZE_X; x++) {
            for (int y = 0; y < SIZE_Y; y++) {
                grilleCases[x][y] = new Case(x, y);
                map.put(grilleCases[x][y], new Point(x, y));
            }
        }
    }

    private String nomJoueurBlanc;
    private String nomJoueurNoir;

    // Méthode pour initialiser les noms des joueurs
    public void setNomsJoueurs(String joueurBlanc, String joueurNoir) {
        this.nomJoueurBlanc = joueurBlanc;
        this.nomJoueurNoir = joueurNoir;
    }

    // Récupérer les noms des joueurs
    public String getNomJoueurBlanc() {
        return nomJoueurBlanc;
    }

    public String getNomJoueurNoir() {
        return nomJoueurNoir;
    }

    public void placerPieces() {
        // ♟️ Pièces blanches (ligne 7 + pions ligne 6)
        new Tour(this, "blanc").allerSurCase(grilleCases[0][7]);
        new Cavalier(this, "blanc").allerSurCase(grilleCases[1][7]);
        new Fou(this, "blanc").allerSurCase(grilleCases[2][7]);
        new Reine(this, "blanc").allerSurCase(grilleCases[3][7]);
        new Roi(this, "blanc").allerSurCase(grilleCases[4][7]);
        new Fou(this, "blanc").allerSurCase(grilleCases[5][7]);
        new Cavalier(this, "blanc").allerSurCase(grilleCases[6][7]);
        new Tour(this, "blanc").allerSurCase(grilleCases[7][7]);

        for (int x = 0; x < SIZE_X; x++) {
            new Pion(this, "blanc").allerSurCase(grilleCases[x][6]);
        }

        // ♟️ Pièces noires (ligne 0 + pions ligne 1)
        new Tour(this, "noir").allerSurCase(grilleCases[0][0]);
        new Cavalier(this, "noir").allerSurCase(grilleCases[1][0]);
        new Fou(this, "noir").allerSurCase(grilleCases[2][0]);
        new Reine(this, "noir").allerSurCase(grilleCases[3][0]);
        new Roi(this, "noir").allerSurCase(grilleCases[4][0]);
        new Fou(this, "noir").allerSurCase(grilleCases[5][0]);
        new Cavalier(this, "noir").allerSurCase(grilleCases[6][0]);
        new Tour(this, "noir").allerSurCase(grilleCases[7][0]);

        for (int x = 0; x < SIZE_X; x++) {
            new Pion(this, "noir").allerSurCase(grilleCases[x][1]);
        }

        setChanged();
        notifyObservers();
    }

    public void arriverCase(Case c, Piece p) {
        c.poserPiece(p);
    }

    public void deplacerPiece(Case c1, Case c2) {
        if (!c1.estLibre()) {
            c1.getPiece().allerSurCase(c2);
        }
        setChanged();
        notifyObservers();
    }

    public List<Piece> getPiecesParCouleur(String couleur) {
        List<Piece> pieces = new ArrayList<>();
        for (int x = 0; x < SIZE_X; x++) {
            for (int y = 0; y < SIZE_Y; y++) {
                Case currentCase = grilleCases[x][y];
                if (!currentCase.estLibre() && couleur.equals(currentCase.getPiece().getCouleur())) {
                    pieces.add(currentCase.getPiece());
                }
            }
        }
        return pieces;
    }

    public boolean estDansLimites(int x, int y) {
        return x >= 0 && x < SIZE_X && y >= 0 && y < SIZE_Y;
    }

    public Case getCase(int x, int y) {
        if (estDansLimites(x, y)) {
            return grilleCases[x][y];
        }
        return null;
    }

    public Point getCoordonnees(Case c) {
        return map.get(c);
    }

    public void verifierPromotion(Case c) {
        if (!c.estLibre() && c.getPiece() instanceof Pion) {
            Pion pion = (Pion) c.getPiece();
            int lignePromotion = pion.getCouleur().equals("blanc") ? 0 : SIZE_Y - 1;

            // Vérifier si le pion atteint la dernière rangée
            if (c.getY() == lignePromotion) {
                promouvoirPion(c, pion);
            }
        }
    }

    private void promouvoirPion(Case c, Pion pion) {
        // Afficher un menu pour choisir la promotion
        String[] options = {"Reine", "Tour", "Fou", "Cavalier"};
        String choix = (String) JOptionPane.showInputDialog(
                null,
                "Choisissez une pièce pour la promotion :",
                "Promotion",
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
        );

        // Créer la nouvelle pièce
        Piece nouvellePiece;
        switch (choix) {
            case "Tour":
                nouvellePiece = new Tour(this, pion.getCouleur());
                break;
            case "Fou":
                nouvellePiece = new Fou(this, pion.getCouleur());
                break;
            case "Cavalier":
                nouvellePiece = new Cavalier(this, pion.getCouleur());
                break;
            case "Reine":
            default:
                nouvellePiece = new Reine(this, pion.getCouleur());
                break;
        }

        // Remplacer le pion par la nouvelle pièce
        c.quitterLaCase(); // Libérer la case pour éviter toute incohérence
        c.poserPiece(nouvellePiece); // Poser la nouvelle pièce sur la case
        nouvellePiece.allerSurCase(c); // Mettre à jour la case de la pièce

        setChanged();
        notifyObservers();
    }

    public boolean estEnEchec(String couleur) {
        Roi roi = null;
        // Trouver le roi de la couleur spécifiée
        for (Piece piece : getPiecesParCouleur(couleur)) {
            if (piece instanceof Roi) {
                roi = (Roi) piece;
                break;
            }
        }
        if (roi == null) return false; // Si pas de roi trouvé, pas d'échec (erreur logique)

        Case caseRoi = roi.getCase();

        // Vérifier si une pièce adverse peut atteindre le roi
        String couleurAdverse = couleur.equals("blanc") ? "noir" : "blanc";
        for (Piece pieceAdverse : getPiecesParCouleur(couleurAdverse)) {
            if (pieceAdverse.getDeplacementsPossibles().contains(caseRoi)) {
                return true; // Roi attaqué
            }
        }
        return false; // Pas d'échec
    }

    public boolean estCheckmate(String couleur) {
        if (!estEnEchec(couleur)) return false; // Pas d'échec, donc pas de checkmate

        // Vérifier si le joueur peut éviter l'échec
        for (Piece piece : getPiecesParCouleur(couleur)) {
            for (Case destination : piece.getDeplacementsPossibles()) {
                Case caseOrigine = piece.getCase();

                // Simuler le déplacement
                piece.quitterCase();
                piece.allerSurCase(destination);

                boolean enEchecApresCoup = estEnEchec(couleur);

                // Annuler le déplacement
                piece.quitterCase();
                piece.allerSurCase(caseOrigine);

                if (!enEchecApresCoup) {
                    return false; // Il existe un coup qui évite l'échec
                }
            }
        }
        return true; // Aucun coup ne peut éviter l'échec, donc checkmate
    }

    // Vérifier si le roque est possible pour un joueur donné
    public boolean estRoquePossible(String couleur, boolean roqueCourt) {
        Roi roi = null;
        Tour tour = null;
        int ligne = couleur.equals("blanc") ? 7 : 0; // Ligne 7 pour blanc, ligne 0 pour noir

        // Trouver le roi et la tour de la couleur donnée
        for (Piece piece : getPiecesParCouleur(couleur)) {
            if (piece instanceof Roi && piece.getCase().getY() == ligne) {
                roi = (Roi) piece;
            }
            if (piece instanceof Tour && piece.getCase().getY() == ligne) {
                if (piece.getCase().getX() == 0 || piece.getCase().getX() == 7) {
                    tour = (Tour) piece;
                }
            }
        }

        if (roi == null || tour == null || roi.estDeplace() || tour.estDeplace()) {
            return false; // Roi ou tour ont bougé ou non trouvés
        }

        // Vérifier qu'il n'y a pas de pièces entre le roi et la tour
        int startX = roi.getCase().getX();
        int endX = tour.getCase().getX();
        int step = startX < endX ? 1 : -1;

        for (int i = startX + step; i != endX; i += step) {
            if (!grilleCases[i][ligne].estLibre()) {
                return false; // Une pièce bloque le roque
            }
        }

        // Vérifier que le roi ne traverse pas une case attaquée
        for (int i = startX + step; i != endX; i += step) {
            Case caseTest = grilleCases[i][ligne];
            if (estCaseAttaqueeParAdversaire(caseTest, couleur)) {
                return false; // La case est attaquée
            }
        }

        return true; // Le roque est possible
    }

    // Effectuer le roque
    public void effectuerRoque(String couleur, boolean roqueCourt) {
        if (!estRoquePossible(couleur, roqueCourt)) {
            System.out.println("Roque impossible.");
            return;
        }

        Roi roi = null;
        Tour tour = null;
        int ligne = couleur.equals("blanc") ? 7 : 0;

        // Trouver le roi et la tour
        for (Piece piece : getPiecesParCouleur(couleur)) {
            if (piece instanceof Roi && piece.getCase().getY() == ligne) {
                roi = (Roi) piece;
            }
            if (piece instanceof Tour && piece.getCase().getY() == ligne) {
                if (piece.getCase().getX() == 0 || piece.getCase().getX() == 7) {
                    tour = (Tour) piece;
                }
            }
        }

        // Déplacer le roi
        Case caseRoi = roi.getCase();
        Case caseDestRoi = roqueCourt ? grilleCases[caseRoi.getX() + 2][caseRoi.getY()] : grilleCases[caseRoi.getX() - 2][caseRoi.getY()];
        roi.allerSurCase(caseDestRoi);

        // Déplacer la tour
        Case caseTour = tour.getCase();
        Case caseDestTour = roqueCourt ? grilleCases[caseTour.getX() - 1][caseTour.getY()] : grilleCases[caseTour.getX() + 2][caseTour.getY()];
        tour.allerSurCase(caseDestTour);

        setChanged();
        notifyObservers();
    }

    // Vérifier si une case est attaquée par l'adversaire
    public boolean estCaseAttaqueeParAdversaire(Case caseTest, String couleur) {
        String couleurAdverse = couleur.equals("blanc") ? "noir" : "blanc";

        for (Piece piece : getPiecesParCouleur(couleurAdverse)) {
            if (piece.getDeplacementsPossibles().contains(caseTest)) {
                return true; // La case est attaquée par l'adversaire
            }
        }
        return false; // La case n'est pas attaquée
    }
}
