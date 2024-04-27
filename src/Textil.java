public class Textil extends Producte {

    String composicio_textil;

    public Textil(String nom, float preu, int codi_barres, String composicio_textil) {
        super(nom, preu, codi_barres);
        this.composicio_textil = composicio_textil;
    }

    public String getComposicioTextil() {
        return composicio_textil;
    }

    public void setComposicioTextil(String composicioTextil) {
        this.composicio_textil = composicioTextil;
    }

    @Override
    public double calcularPreuTotal() {
        return getPreu();
    }


}
