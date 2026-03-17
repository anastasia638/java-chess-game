package modele.jeu;

// Importation de la classe Jeu
import modele.jeu.Jeu;

public class Joueur {
    private Jeu jeu;  // L'objet Jeu doit être importé correctement

    public Joueur(Jeu _jeu) {
        jeu = _jeu;
    }

    public Coup getCoup() {
        synchronized (jeu) {
            try {
                jeu.wait();  // Attente du coup du joueur
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return jeu.coupRecu;  // Récupère le coup joué
    }
}

