package modele.jeu;

import modele.plateau.Case;

public class Coup {
    protected Case dep;  // Departure case
    protected Case arr;  // Arrival case

    // Constructor
    public Coup(Case _dep, Case _arr) {
        this.dep = _dep;  // Using 'this' to refer to instance variables
        this.arr = _arr;
    }

    // Getter for departure case
    public Case getDep() {
        return dep;
    }

    // Getter for arrival case
    public Case getArr() {
        return arr;
    }
}
