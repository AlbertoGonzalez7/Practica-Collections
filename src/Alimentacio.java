import java.time.LocalDate;

public class Alimentacio extends Producte{

    LocalDate data_caducitat;

    public Alimentacio(String nom, float preu, int codi_barres, LocalDate data_caducitat){
        super(nom, preu, codi_barres);
        this.data_caducitat = data_caducitat;
    }

    @Override
    public double calcularPreuTotal() {
        // Agafem el dia que és alhora de executar el codi
        LocalDate ara = LocalDate.now();
        // Calculem cuants dies falten fins que el producte caduca.
        int diesFinsCaducitat = (int) (data_caducitat.toEpochDay() - ara.toEpochDay()) + 1;
        // Calculem preu del producte segons els dies de caducitat que falten
        return getPreu() - getPreu() * (1.0 / diesFinsCaducitat) + (getPreu() * 0.1);
    }

    // Falta modificar conforme enunciat. Simple per comprovar.
    @Override
    public String toString() {
        return super.toString() + " - Caducitat: " + data_caducitat;
    }
}
