import java.util.Comparator;
public class Electronica extends Producte{

    int dies_garantia;

    public Electronica(String nom, float preu, int codi_barres, int dies_garantia) {
        super(nom, preu, codi_barres);
        this.dies_garantia = dies_garantia;
    }

    public int getDiesGarantia() {
        return dies_garantia;
    }

    public void setDiesGarantia(int diesGarantia) {
        this.dies_garantia = diesGarantia;
    }

    @Override
    public double calcularPreuTotal() {
        //Calculem el preu segons els dies que hi ha de garantia.
        return getPreu() + getPreu() * (dies_garantia / 365.0) * 0.1;
    }


    // Comparator para ordenar por días de garantía
    public static Comparator<Electronica> compararPorDiasGarantia() {
        return Comparator.comparingInt(Electronica::getDiesGarantia);
    }
}
