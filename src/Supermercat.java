import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Supermercat {
    private ArrayList<Producte> carroCompra;
    private Map<Integer, Integer> unitatsPerProducte;
    private static final int MAX_PRODUCTES = 100; // Quantitat màxima de productes
    private static final float IVA = 0.21f; // Iva del 21%

    public Supermercat() {
        carroCompra = new ArrayList<>();
        unitatsPerProducte = new HashMap<>();
    }

    public void afegirProducte(Producte producte, int unitats) {
        if (carroCompra.size() < MAX_PRODUCTES) {
            int codiBarres = producte.getCodiBarres();
            boolean existeix = false;
            for (Producte p : carroCompra) {
                if (p.getCodiBarres() == codiBarres) {
                    existeix = true;
                    break;
                }
            }
            if (existeix) {
                System.out.println("Ja hi ha un producte amb el mateix codi de barres al carro.");
            } else {
                carroCompra.add(producte); // Agregamos el producto al carro de la compra.
                if (unitatsPerProducte.containsKey(codiBarres)) {
                    unitatsPerProducte.put(codiBarres, unitatsPerProducte.get(codiBarres) + unitats);
                } else {
                    unitatsPerProducte.put(codiBarres, unitats);
                }
                System.out.println("Producte afegit al carro de la compra.");
            }
        } else {
            System.out.println("El carro de la compra està ple. No es poden afegir més productes.");
        }
    }

    public void passarPerCaixa() {
        System.out.println("------ TICKET DE COMPRA ------");
        System.out.println("Data de la compra: " + LocalDate.now());
        System.out.println("Nom del supermercat: SAPAMERCAT");
        System.out.println("Detall de la compra:");
        System.out.println("------------------------------");

        double total = 0;

        for (Producte producte : carroCompra) {
            int codiBarres = producte.getCodiBarres();
            int unitats = unitatsPerProducte.get(codiBarres);
            double preuUnitari = producte.calcularPreuTotal();
            double preuTotal = preuUnitari * unitats;
            total += preuTotal;
            System.out.println("- " + producte.getNom() + " | Unitats: " + unitats + " | Preu unitari: " + String.format("%.2f", preuUnitari) + "€ | Preu total: " + String.format("%.2f", preuTotal) + "€");
        }

        double totalIVA = total * IVA; // Calculem l'IVA
        double totalAPagar = total + totalIVA;
        System.out.println("------------------------------");
        System.out.println("IVA (21%): " + String.format("%.2f", totalIVA) + "€"); //Calculem el 21% de IVA
        System.out.println("Total a pagar: " + String.format("%.2f", totalAPagar) + "€"); //Total a pagar sumant el 21% de IVA
        System.out.println("S'ha buidat el carro.");

        // Buidar el carro de la compra
        carroCompra.clear();
        unitatsPerProducte.clear();
    }

    public void mostrarCarroCompra() {
        System.out.println("------ CARRITO DE LA COMPRA ------");
        for (Map.Entry<Integer, Integer> entry : unitatsPerProducte.entrySet()) {
            int codiBarres = entry.getKey();
            int unitats = entry.getValue();
            for (Producte producte : carroCompra) {
                if (producte.getCodiBarres() == codiBarres) {
                    System.out.println("- " + producte.getNom() + " | Unitats: " + unitats);
                    break;
                }
            }
        }
        System.out.println("----------------------------------");
    }

    public static void main(String[] args) {
        Supermercat supermercat = new Supermercat();
        Scanner scanner = new Scanner(System.in);
        int opcio;

        do {
            System.out.println("----- MENÚ PRINCIPAL -----");
            System.out.println("1. Introduir producte");
            System.out.println("2. Passar per caixa");
            System.out.println("3. Mostrar carro de la compra");
            System.out.println("0. Sortir");
            System.out.print("Selecciona una opció: ");
            opcio = scanner.nextInt();

            switch (opcio) {
                case 1:
                    supermercat.menuAfegirProducte(scanner);
                    break;
                case 2:
                    supermercat.passarPerCaixa();
                    break;
                case 3:
                    supermercat.mostrarCarroCompra();
                    break;
                case 0:
                    System.out.println("Has sortit de SAPAMERCAT. Fins la següent!");
                    break;
                default:
                    System.out.println("Opció no vàlida. Si us plau, tria una opció vàlida.");
            }
        } while (opcio != 0);
    }

    private void menuAfegirProducte(Scanner scanner) {
        int opcio;
        do {
            System.out.println("--------------------");
            System.out.println("----- PRODUCTE -----");
            System.out.println("--------------------");
            System.out.println("Quin tipus de producte vols afegir?");
            System.out.println("1. Alimentació");
            System.out.println("2. Tèxtil");
            System.out.println("3. Electrònica");
            System.out.println("0. Tornar");
            System.out.print("Selecciona una opció: ");
            opcio = scanner.nextInt();

            switch (opcio) {
                case 1:
                    afegirProducteAlimentacio(scanner);
                    break;
                case 2:
                    afegirProducteTextil(scanner);
                    break;
                case 3:
                    afegirProducteElectronica(scanner);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opció no vàlida. Si us plau, tria una opció vàlida.");
            }
        } while (opcio != 0);
    }

    // Opció Producte, preguntem nom, preu, codi de barres, data caducitat i la quantitat.
    private void afegirProducteAlimentacio(Scanner scanner) {
        System.out.print("Introdueix el nom del producte: ");
        String nom = scanner.next();
        System.out.print("Introdueix el preu del producte: ");
        float preu = scanner.nextFloat();
        System.out.print("Introdueix el codi de barres: ");
        int codiBarres = scanner.nextInt();
        System.out.print("Introdueix la data de caducitat (format YYYY-MM-DD): ");
        String dataCaducitatStr = scanner.next();
        LocalDate dataCaducitat = LocalDate.parse(dataCaducitatStr);
        System.out.print("Introdueix el nombre d'unitats: ");
        int unitats = scanner.nextInt();
        Alimentacio alimentacio = new Alimentacio(nom, preu, codiBarres, dataCaducitat);
        afegirProducte(alimentacio, unitats);
    }

    // Opció Textil, preguntem nom, preu, codi de barres, composició i la quantitat.
    private void afegirProducteTextil(Scanner scanner) {
        System.out.print("Introdueix el nom del producte: ");
        String nom = scanner.next();
        System.out.print("Introdueix el preu del producte: ");
        float preu = scanner.nextFloat();
        System.out.print("Introdueix el codi de barres: ");
        int codiBarres = scanner.nextInt();
        System.out.print("Introdueix la composició tèxtil: ");
        String composicioTextil = scanner.next();
        System.out.print("Introdueix el nombre d'unitats: ");
        int unitats = scanner.nextInt();
        Textil textil = new Textil(nom, preu, codiBarres, composicioTextil);
        afegirProducte(textil, unitats);
    }

    // Opció Electronica, preguntem nom, preu, codi de barres, dies garantia i la quantitat.
    private void afegirProducteElectronica(Scanner scanner) {
        System.out.print("Introdueix el nom del producte: ");
        String nom = scanner.next();
        System.out.print("Introdueix el preu del producte: ");
        float preu = scanner.nextFloat();
        System.out.print("Introdueix el codi de barres: ");
        int codiBarres = scanner.nextInt();
        System.out.print("Introdueix el nombre de dies de garantia: ");
        int diesGarantia = scanner.nextInt();
        System.out.print("Introdueix el nombre d'unitats: ");
        int unitats = scanner.nextInt();
        Electronica electronica = new Electronica(nom, preu, codiBarres, diesGarantia);
        afegirProducte(electronica, unitats);
    }
}
