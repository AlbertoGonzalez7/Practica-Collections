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
    @Override
    public double calcularPreuTotal() {
        //Calculem el preu segons els dies que hi ha de garantia.
        return getPreu() + getPreu() * (dies_garantia / 365.0) * 0.1;
    }

    // Falta modificar conforme enunciat. Simple per comprovar.
    @Override
    public String toString() {
        return super.toString() + " - Dies garantia: " + dies_garantia;
    }

    // Comparator para ordenar por días de garantía
    public static Comparator<Electronica> compararPorDiasGarantia() {
        return Comparator.comparingInt(Electronica::getDiesGarantia);
    }
}
