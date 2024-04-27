import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Alimentacio extends Producte{

    LocalDate data_caducitat;

    public Alimentacio(String nom, float preu, int codi_barres, LocalDate data_caducitat){
        super(nom, preu, codi_barres);
        this.data_caducitat = data_caducitat;
    }

    public LocalDate getDataCaducitat() {
        return data_caducitat;
    }

    public void setDataCaducitat(LocalDate dataCaducitat) {
        this.data_caducitat = dataCaducitat;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataCaducitatFormatted = data_caducitat.format(formatter);
        return super.toString() + " - Data Caducitat: " + dataCaducitatFormatted;
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
}

