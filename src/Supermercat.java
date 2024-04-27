import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.Comparator;
import java.time.format.DateTimeFormatter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Supermercat {
    private TreeSet<Textil> productesTextils;
    private ArrayList<Producte> carroCompra;
    private Map<Integer, Integer> unitatsPerProducte;
    private static final int MAX_PRODUCTES = 100; // Quantitat màxima de productes
    private static final float IVA = 0.21f; // Iva del 21%

    public Supermercat() {
        productesTextils = new TreeSet<>(new Comparator<Textil>() {
            @Override
            public int compare(Textil t1, Textil t2) {
                return t2.getComposicioTextil().compareTo(t1.getComposicioTextil());
            }
        });
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
            System.out.println("4. Buscar producte");
            System.out.println("0. Sortir");
            System.out.print("Selecciona una opció: ");
            try {
            opcio = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Error: Introdueix un número vàlid com a opció.");
                opcio = -1; // Assignem un valor invàlid per a repetir el bucle.
                scanner.next(); // Netegem el buffer del scanner
            }
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
                case 4:
                    System.out.print("Introdueix el codi del producte: ");
                    try {
                    int codiBarresBusqueda = scanner.nextInt();
                    supermercat.buscarProductesCodiBarres(codiBarresBusqueda);
                    } catch (Exception e) {
                        System.out.println("Error: Introdueix un número vàlid per al codi de barres.");
                        scanner.next(); // Netegem el buffer del scanner
                    }
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

            try {
            opcio = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Error: Introdueix un número vàlid com a opció.");
                opcio = -1; // Assignem un valor invàlid per a repetir el bucle.
                scanner.next(); // Netegem el buffer del scanner
            }
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
        scanner.nextLine();
        System.out.print("Introdueix el nom del producte: ");
        String nom = scanner.nextLine();

        if (nom.length() > 15) {
            System.out.println("El nom del producte no pot tenir més de 15 caràcters.");
            return;
        }

        System.out.print("Introdueix el preu del producte: ");
        float preu = 0;
        boolean preuValid = false;
        while (!preuValid) {
            try {
                preu = scanner.nextFloat();
                preuValid = true;
            } catch (Exception e) {
                System.out.println("Introdueix un valor numèric vàlid per al preu del producte.");
                scanner.nextLine();
            }
        }
        scanner.nextLine();

        System.out.print("Introdueix el codi de barres: ");
        int codiBarres = scanner.nextInt();
        scanner.nextLine();
            System.out.print("Introdueix la data de caducitat (format DD/MM/YYYY): ");
            String dataCaducitatStr = scanner.nextLine();
            LocalDate dataCaducitat = null;
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                dataCaducitat = LocalDate.parse(dataCaducitatStr, formatter);
            } catch (Exception e) {
                System.out.println("Format de data no vàlid. Utilitza el format DD/MM/YYYY.");
                return;
            }
        System.out.print("Introdueix el nombre d'unitats: ");
        int unitats = scanner.nextInt();
        Alimentacio alimentacio = new Alimentacio(nom, preu, codiBarres, dataCaducitat);
        afegirProducte(alimentacio, unitats);
    }

    // Opció Textil, preguntem nom, preu, codi de barres, composició i la quantitat.
    private void afegirProducteTextil(Scanner scanner) {
        scanner.nextLine();
        System.out.print("Introdueix el nom del producte: ");
        String nom = scanner.nextLine();

        if (nom.length() > 15) {
            System.out.println("Error: El nom del producte no pot superar els 15 caràcters.");
            return;
        }

        System.out.print("Introdueix el preu del producte: ");
        float preu = 0;
        boolean preuValid = false;
        while (!preuValid) {
            try {
                preu = scanner.nextFloat();
                preuValid = true;
            } catch (Exception e) {
                System.out.println("Introdueix un valor numèric vàlid per al preu del producte.");
                scanner.nextLine();
            }
        }

        scanner.nextLine();

        System.out.print("Introdueix el codi de barres: ");
        try {
        int codiBarres = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Introdueix la composició tèxtil: ");
        String composicioTextil = scanner.nextLine();
        System.out.print("Introdueix el nombre d'unitats: ");
        int unitats = scanner.nextInt();
        Textil textil = new Textil(nom, preu, codiBarres, composicioTextil);
        afegirProducte(textil, unitats);
        productesTextils.add(textil);
        } catch (Exception e) {
            System.out.println("Error: Introdueix un número vàlid per al codi de barres.");
            scanner.next(); // Netegem el buffer del scanner
        }
        comprovarActualitzacioPreusTextils();
    }

    // Opció Electronica, preguntem nom, preu, codi de barres, dies garantia i la quantitat.
    private void afegirProducteElectronica(Scanner scanner) {
        scanner.nextLine();
        System.out.print("Introdueix el nom del producte: ");
        String nom = scanner.nextLine();

        if (nom.length() > 15) {
            System.out.println("Error: El nom del producte no pot superar els 15 caràcters.");
            return;
        }

        System.out.print("Introdueix el preu del producte: ");
        float preu = 0;
        boolean preuValid = false;
        while (!preuValid) {
            try {
                preu = scanner.nextFloat();
                preuValid = true;
            } catch (Exception e) {
                System.out.println("Introdueix un valor numèric vàlid per al preu del producte.");
                scanner.nextLine();
            }
        }

        scanner.nextLine();

        System.out.print("Introdueix el codi de barres: ");
        try {
        int codiBarres = scanner.nextInt();
        System.out.print("Introdueix el nombre de dies de garantia: ");
        int diesGarantia = scanner.nextInt();
        System.out.print("Introdueix el nombre d'unitats: ");
        int unitats = scanner.nextInt();
        Electronica electronica = new Electronica(nom, preu, codiBarres, diesGarantia);
        afegirProducte(electronica, unitats);
        } catch (Exception e) {
            System.out.println("Error: Introdueix un número vàlid per al codi de barres.");
            scanner.next(); // Netegem el buffer del scanner
        }
    }

    // Buscador de productes amb stream i lambda expressions
    public void buscarProductesCodiBarres(int codiBarres) {
        boolean trobat = carroCompra.stream()
                .filter(producte -> producte.getCodiBarres() == codiBarres)
                .peek(producte -> System.out.println("Producte trobat: " + producte.getNom() + " | Quantitat: " + unitatsPerProducte.get(producte.getCodiBarres())))
                .findAny()
                .isPresent();

        if (!trobat) {
            System.out.println("No s'ha trobat cap producte amb el codi de barres " + codiBarres);
        }
    }

    // APARTAT: COMPROVAR FICHER DE PREUS TEXTILS
    public void comprovarActualitzacioPreusTextils() {
        File file = new File("./src/updates/UpdateTextilPrices.dat");
        if (file.exists()) {
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split(",");
                    if (parts.length == 2) {
                        try {
                            int codiBarres = Integer.parseInt(parts[0]);
                            float nouPreu = Float.parseFloat(parts[1]);
                            // Actualitzem el preu del textil si el trobem al fitxer.
                            for (Producte producte : carroCompra) {
                                if (producte instanceof Textil && producte.getCodiBarres() == codiBarres) {
                                    ((Textil) producte).setPreu(nouPreu);
                                    System.out.println("Preus de tèxtil actualitzats correctament.");
                                }
                            }
                        } catch (NumberFormatException e) {
                            // Si trobem un error al convertir el codi de barres o el preu, el registrem
                            registrarErrorEnLogs(e);
                        }
                    } else {
                        // Si la linea no te el format que correcte.
                        registrarErrorEnLogs(new IllegalArgumentException("Format de línia incorrecte: " + line));
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("No s'ha trobat el fitxer d'actualització de preus de tèxtil.");
            } catch (Exception e) {
                System.out.println("S'ha produït un error en processar l'actualització de preus de tèxtil.");
                // Registrar el error en el fitxer de logs
                registrarErrorEnLogs(e);
            }
        } else {
            System.out.println("No hi ha cap fitxer d'actualització de preus de tèxtil.");
        }
    }

    // APARTAT: REGISTRAR LOGS D'ERROR
    private void registrarErrorEnLogs(Exception e) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("./src/logs/Exceptions.dat", true))) {
            writer.println("Excepció: " + e.getMessage());
            e.printStackTrace(writer);
            writer.println("-------------------------------------------");
        } catch (IOException ex) {
            System.out.println("No s'ha pogut escriure a l'arxiu de logs.");
            ex.printStackTrace();
        }
    }
}
