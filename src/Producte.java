import java.time.LocalDate;

public abstract class Producte implements Comparable<Producte> {

    String nom;
    float preu;
    int codi_barres;

    public Producte(String nom, float preu, int codi_barres) {
        this.nom = nom;
        this.preu = preu;
        this.codi_barres = codi_barres;
    }

    public abstract double calcularPreuTotal();

    public String getNom() {
        return nom;
    }

    public float getPreu() {
        return preu;
    }

    public void setPreu(float preu) {
        this.preu = preu;
    }

    public int getCodiBarres() {
        return codi_barres;
    }

    @Override
    public int compareTo(Producte altreProducte) {
        // Comparem els preus dels productes
        return Float.compare(this.preu, altreProducte.preu);
    }
}
