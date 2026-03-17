package VueControleur;

import modele.jeu.Coup;
import modele.jeu.Jeu;
import modele.jeu.Piece;
import modele.plateau.Case;
import modele.plateau.Plateau;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class VueControleur extends JFrame implements Observer {

    private Jeu jeu;
    private Plateau plateau;

    private static final int TAILLE_CASE = 64; // taille d'une case en pixels
    private JLabel[][] tabJLabel;
    private Case caseSelectionnee;
    private boolean tourBlanc = true; // Commence avec les Blancs
    private JLabel labelTour; // Label pour afficher le joueur actuel
    private JLabel labelScore;

    private String nomJoueurBlanc; // Nom du joueur blanc
    private String nomJoueurNoir;  // Nom du joueur noir

    public VueControleur(Jeu jeu) {
        this.jeu = jeu;
        this.plateau = jeu.getPlateau();

        // Demander les noms des joueurs
        demanderNomsJoueurs();

        // Initialisation de l'interface
        setTitle("Jeu d'Échecs");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(plateau.SIZE_X * TAILLE_CASE, plateau.SIZE_Y * TAILLE_CASE);
        setLocationRelativeTo(null);
        setResizable(false);

        plateau.addObserver(this);

        // Initialisation de la barre de menu
        initialiserMenu();

        initialiserInterface();
        mettreAJourAffichage();
    }

    private void demanderNomsJoueurs() {
        nomJoueurBlanc = JOptionPane.showInputDialog(this, "Nom du joueur Blanc :", "Joueur Blanc");
        if (nomJoueurBlanc == null || nomJoueurBlanc.isEmpty()) {
            nomJoueurBlanc = "Blanc";
        }

        nomJoueurNoir = JOptionPane.showInputDialog(this, "Nom du joueur Noir :", "Joueur Noir");
        if (nomJoueurNoir == null || nomJoueurNoir.isEmpty()) {
            nomJoueurNoir = "Noir";
        }
    }

    private void initialiserMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuPartie = new JMenu("Partie");

        JMenuItem nouvellePartie = new JMenuItem("Nouvelle Partie");
        JMenuItem quitter = new JMenuItem("Quitter");

        nouvellePartie.addActionListener(e -> nouvellePartie());
        quitter.addActionListener(e -> quitter());

        menuPartie.add(nouvellePartie);
        menuPartie.addSeparator();
        menuPartie.add(quitter);

        menuBar.add(menuPartie);
        setJMenuBar(menuBar);
    }

    private void nouvellePartie() {
        jeu = new Jeu();
        plateau = jeu.getPlateau();
        caseSelectionnee = null;
        tourBlanc = true;
        mettreAJourAffichage();
    }

    private void quitter() {
        int reponse = JOptionPane.showConfirmDialog(this, "Voulez-vous vraiment quitter ?", "Quitter", JOptionPane.YES_NO_OPTION);
        if (reponse == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void initialiserInterface() {
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        labelScore = new JLabel("Score - " + nomJoueurBlanc + ": 0 | " + nomJoueurNoir + ": 0");
        add(labelScore, BorderLayout.NORTH);

        labelTour = new JLabel("Tour de " + nomJoueurBlanc, SwingConstants.CENTER);
        labelTour.setFont(new Font("Arial", Font.BOLD, 20));
        labelTour.setOpaque(true);
        labelTour.setBackground(Color.WHITE);
        labelTour.setForeground(Color.BLACK);
        labelTour.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JPanel panelGrille = new JPanel(new GridLayout(plateau.SIZE_Y, plateau.SIZE_X));
        tabJLabel = new JLabel[plateau.SIZE_X][plateau.SIZE_Y];

        for (int y = 0; y < plateau.SIZE_Y; y++) {
            for (int x = 0; x < plateau.SIZE_X; x++) {
                JLabel labelCase = new JLabel();
                labelCase.setPreferredSize(new Dimension(TAILLE_CASE, TAILLE_CASE));
                labelCase.setOpaque(true);
                labelCase.setHorizontalAlignment(SwingConstants.CENTER);

                if ((x + y) % 2 == 0) {
                    labelCase.setBackground(new Color(240, 217, 181));
                } else {
                    labelCase.setBackground(new Color(181, 136, 99));
                }

                final int xx = x;
                final int yy = y;

                labelCase.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        gestionClicCase(xx, yy);
                    }
                });

                tabJLabel[x][y] = labelCase;
                panelGrille.add(labelCase);
            }
        }

        panelPrincipal.add(labelTour, BorderLayout.NORTH);
        panelPrincipal.add(panelGrille, BorderLayout.CENTER);

        add(panelPrincipal);
        pack();
    }

    private void gestionClicCase(int x, int y) {
        Case caseCliquee = plateau.getCase(x, y);

        if (caseSelectionnee == null) {
            if (!caseCliquee.estLibre()) {
                Piece pieceCliquee = caseCliquee.getPiece();
                boolean pieceBlanche = pieceCliquee.estBlanc();

                if ((tourBlanc && pieceBlanche) || (!tourBlanc && !pieceBlanche)) {
                    caseSelectionnee = caseCliquee;
                    afficherCasesAccessibles(pieceCliquee);
                }
            }
        } else {
            List<Case> possibles = caseSelectionnee.getPiece().getDeplacementsPossibles();

            if (possibles.contains(caseCliquee)) {
                jeu.envoyerCoup(new Coup(caseSelectionnee, caseCliquee));

                String couleurAdverse = tourBlanc ? nomJoueurNoir : nomJoueurBlanc;
                if (plateau.estEnEchec(tourBlanc ? "noir" : "blanc")) {
                    JOptionPane.showMessageDialog(this, "Le joueur " + couleurAdverse + " est en échec !", "Échec", JOptionPane.WARNING_MESSAGE);
                }

                tourBlanc = !tourBlanc;
                mettreAJourTour();
            }

            caseSelectionnee = null;
            mettreAJourAffichage();
        }
    }

    private void mettreAJourTour() {
        if (tourBlanc) {
            labelTour.setText("Tour de " + nomJoueurBlanc);
            labelTour.setBackground(Color.WHITE);
            labelTour.setForeground(Color.BLACK);
        } else {
            labelTour.setText("Tour de " + nomJoueurNoir);
            labelTour.setBackground(Color.BLACK);
            labelTour.setForeground(Color.WHITE);
        }
    }

    private void afficherCasesAccessibles(Piece piece) {
        mettreAJourAffichage();

        List<Case> accessibles = piece.getDeplacementsPossibles();

        for (Case c : accessibles) {
            tabJLabel[c.getX()][c.getY()].setBackground(Color.YELLOW);
        }
    }

    private void mettreAJourAffichage() {
        labelScore.setText("Score - " + nomJoueurBlanc + ": " + jeu.getScoreBlanc() + " | " + nomJoueurNoir + ": " + jeu.getScoreNoir());

        for (int y = 0; y < plateau.SIZE_Y; y++) {
            for (int x = 0; x < plateau.SIZE_X; x++) {
                Case c = plateau.getCase(x, y);
                JLabel label = tabJLabel[x][y];

                if ((x + y) % 2 == 0) {
                    label.setBackground(new Color(240, 217, 181)); // Clair
                } else {
                    label.setBackground(new Color(181, 136, 99)); // Foncé
                }

                if (!c.estLibre()) {
                    Piece p = c.getPiece();
                    String imgPath = "/Images/" + p.getNomImage() + ".png";
                    ImageIcon icon = chargerImage(imgPath);
                    label.setIcon(icon);
                } else {
                    label.setIcon(null);
                }
            }
        }

        if (plateau.estEnEchec(tourBlanc ? "noir" : "blanc")) {
            labelTour.setText(labelTour.getText() + " (Échec)");
        }
    }

    private ImageIcon chargerImage(String chemin) {
        ImageIcon icone = new ImageIcon(getClass().getResource(chemin));
        Image image = icone.getImage().getScaledInstance(TAILLE_CASE, TAILLE_CASE, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }

    @Override
    public void update(Observable o, Object arg) {
        SwingUtilities.invokeLater(this::mettreAJourAffichage);
    }
}
