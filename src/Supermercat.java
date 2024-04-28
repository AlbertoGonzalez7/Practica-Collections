import java.time.LocalDate;
import java.util.*;
import java.time.format.DateTimeFormatter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;

public class Supermercat {
    private TreeSet<Textil> productesTextils; // Per ordenar els productes textils
    private TreeSet<Electronica> productesElectronica; // Per ordenar els productes electronics
    private ArrayList<Producte> carroCompra; // Llista de productes al carro de la compra
    private Map<Integer, Integer> unitatsPerProducte; // HashMap per tal de guardar la quantitat de unitats per producte
    private static final int MAX_PRODUCTES = 100; // Quantitat màxima de productes
    private static final float IVA = 0.21f; // Iva del 21%

    public Supermercat() {

        carroCompra = new ArrayList<>();
        unitatsPerProducte = new HashMap<>();

        // Inicialització del conjunt d'objectes Textil ordenats per composició tèxtil
        productesTextils = new TreeSet<>(new Comparator<Textil>() {
            @Override
            public int compare(Textil t1, Textil t2) {
                return t2.getComposicioTextil().compareTo(t1.getComposicioTextil());
            }
        });

        // Inicialització del conjunt d'objectes Electrònica ordenats per dies de garantia
        productesElectronica = new TreeSet<>(new Comparator<Electronica>() {
            @Override
            public int compare(Electronica e1, Electronica e2) {
                return Integer.compare(e1.getDiesGarantia(), e2.getDiesGarantia());
            }
        });
    }

    // Mètode per afegir un producte al carro de la compra
    public void afegirProducte(Producte producte, int unitats) {
        // Verifiquem que el carro no estigui ple.
        if (carroCompra.size() < MAX_PRODUCTES) {
            int codiBarres = producte.getCodiBarres();
            boolean existeix = false;
            // Comprovem si ja hi ha un producte amb el mateix codi de barres al carro.
            for (Producte p : carroCompra) {
                if (p.getCodiBarres() == codiBarres) {
                    existeix = true;
                }
            }
            // Si ja existeix el producte, mostra un missatge d'error
            if (existeix) {
                System.out.println("Ja hi ha un producte amb el mateix codi de barres al carro.");
            } else {
                // Si no existeix, l'afegeix al carro i actualitza la quantitat d'unitats
                carroCompra.add(producte);
                if (unitatsPerProducte.containsKey(codiBarres)) {
                    unitatsPerProducte.put(codiBarres, unitatsPerProducte.get(codiBarres) + unitats);
                } else {
                    unitatsPerProducte.put(codiBarres, unitats);
                }
                System.out.println("Producte afegit al carro de la compra.");
            }
        } else {
            // Si el carro està ple, mostra un missatge d'error
            System.out.println("El carro de la compra està ple. No es poden afegir més productes.");
        }
    }

    // Mètode per processar la compra i generar el ticket
    public void passarPerCaixa() {
        System.out.println("------ TICKET DE COMPRA ------");
        System.out.println("Data de la compra: " + LocalDate.now());
        System.out.println("Nom del supermercat: SAPAMERCAT");
        System.out.println("Detall de la compra:");
        System.out.println("------------------------------");

        double total = 0;

        // Calcula el preu total i mostra la informació de cada producte al ticket
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
        System.out.println("IVA (21%): " + String.format("%.2f", totalIVA) + "€"); // Calculem el 21% de IVA
        System.out.println("Total a pagar: " + String.format("%.2f", totalAPagar) + "€"); // Total a pagar sumant el 21% de IVA
        System.out.println("S'ha buidat el carro.");

        // Buidar el carro de la compra
        carroCompra.clear();
        unitatsPerProducte.clear();
    }

    // Mètode per mostrar el contingut del carro de la compra
    public void mostrarCarroCompra() {
        System.out.println("------ CARRET DE LA COMPRA ------");
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

        // Menú principal
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
                opcio = -1; // Assignem un valor invàlid per a repetir el bucle
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

    // Mètode per mostrar el menú d'afegir producte
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
                registrarErrorEnLogs(new Exception("Error en el menu de productes."));
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

        float preu = 0;
        boolean preuValid = false;
        while (!preuValid) {
            System.out.print("Introdueix el preu del producte: ");
            try {
                preu = scanner.nextFloat();
                preuValid = true;
            } catch (Exception e) {
                System.out.println("Introdueix un valor numèric vàlid per al preu del producte.");
                scanner.nextLine();
                registrarErrorEnLogs(new Exception("Preu del producte alimentació incorrecte."));
            }
        }
        scanner.nextLine();

        int codiBarres = 0;
        boolean codiBarresValid = false;
        while (!codiBarresValid) {
            System.out.print("Introdueix el codi de barres: ");
            try {
                codiBarres = scanner.nextInt();
                codiBarresValid = true;
            } catch (Exception e) {
                System.out.println("Introdueix un valor numèric vàlid per al codi de barres.");
                scanner.nextLine();
                registrarErrorEnLogs(new Exception("Codi de barres del producte alimentació incorrecte."));
            }
        }
        scanner.nextLine();

        LocalDate dataCaducitat = null;
        boolean dataCaducitatValid = false;
        while (!dataCaducitatValid) {
            System.out.print("Introdueix la data de caducitat (format DD/MM/YYYY): ");
            String dataCaducitatStr = scanner.nextLine();
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                dataCaducitat = LocalDate.parse(dataCaducitatStr, formatter);
                dataCaducitatValid = true;
            } catch (Exception e) {
                System.out.println("Format de data no vàlid. Utilitza el format DD/MM/YYYY.");
                registrarErrorEnLogs(new Exception("Data caducitat del producte alimentació incorrecte."));
            }
        }

        int unitats = 0;
        boolean unitatsValid = false;
        while (!unitatsValid) {
            System.out.print("Introdueix el nombre d'unitats: ");
            try {
                unitats = scanner.nextInt();
                unitatsValid = true;
            } catch (Exception e) {
                System.out.println("Introdueix un valor numèric vàlid per al nombre d'unitats.");
                scanner.nextLine();
                registrarErrorEnLogs(new Exception("Numero d'unitats del producte alimentació incorrecte."));
            }
        }

        // Crea i afegeix el producte al carro de la compra
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

        float preu = 0;
        boolean preuValid = false;
        while (!preuValid) {
            System.out.print("Introdueix el preu del producte: ");
            try {
                preu = scanner.nextFloat();
                preuValid = true;
            } catch (Exception e) {
                System.out.println("Introdueix un valor numèric vàlid per al preu del producte.");
                scanner.nextLine();
                registrarErrorEnLogs(new Exception("Preu del producte textil incorrecte."));
            }
        }
        scanner.nextLine();

        int codiBarres = 0;
        boolean codiBarresValid = false;
        while (!codiBarresValid) {
            System.out.print("Introdueix el codi de barres: ");
            try {
                codiBarres = scanner.nextInt();
                codiBarresValid = true;
            } catch (Exception e) {
                System.out.println("Error: Introdueix un número vàlid per al codi de barres.");
                scanner.nextLine();
                registrarErrorEnLogs(new Exception("Codi de barres del producte textil incorrecte."));
            }
        }
        scanner.nextLine();

        System.out.print("Introdueix la composició tèxtil: ");
        String composicioTextil = scanner.nextLine();

        int unitats = 0;
        boolean unitatsValid = false;
        while (!unitatsValid) {
            System.out.print("Introdueix el nombre d'unitats: ");
            try {
                unitats = scanner.nextInt();
                unitatsValid = true;
            } catch (Exception e) {
                System.out.println("Error: Introdueix un número vàlid per al nombre d'unitats.");
                scanner.nextLine();
                registrarErrorEnLogs(new Exception("Numero d'unitats del producte textil incorrecte."));
            }
        }

        // Crea i afegeix el producte al carro de la compra
        Textil textil = new Textil(nom, preu, codiBarres, composicioTextil);
        afegirProducte(textil, unitats);
        productesTextils.add(textil);

        // Comprovem i actualitza els preus dels productes tèxtils
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

        float preu = 0;
        boolean preuValid = false;
        while (!preuValid) {
            System.out.print("Introdueix el preu del producte: ");
            try {
                preu = scanner.nextFloat();
                preuValid = true;
            } catch (Exception e) {
                System.out.println("Introdueix un valor numèric vàlid per al preu del producte.");
                scanner.nextLine();
                registrarErrorEnLogs(new Exception("Preu del producte electronic incorrecte."));
            }
        }
        scanner.nextLine();

        int codiBarres = 0;
        boolean codiBarresValid = false;
        while (!codiBarresValid) {
            System.out.print("Introdueix el codi de barres: ");
            try {
                codiBarres = scanner.nextInt();
                codiBarresValid = true;
            } catch (Exception e) {
                System.out.println("Error: Introdueix un número vàlid per al codi de barres.");
                scanner.nextLine();
                registrarErrorEnLogs(new Exception("Codi de barres del producte electronic incorrecte."));
            }
        }

        int diesGarantia = 0;
        boolean diesGarantiaValid = false;
        while (!diesGarantiaValid) {
            System.out.print("Introdueix el nombre de dies de garantia: ");
            try {
                diesGarantia = scanner.nextInt();
                diesGarantiaValid = true;
            } catch (Exception e) {
                System.out.println("Error: Introdueix un número vàlid per al nombre de dies de garantia.");
                scanner.nextLine();
                registrarErrorEnLogs(new Exception("Dies de garantia del producte electronic incorrecte."));
            }
        }

        int unitats = 0;
        boolean unitatsValid = false;
        while (!unitatsValid) {
            System.out.print("Introdueix el nombre d'unitats: ");
            try {
                unitats = scanner.nextInt();
                unitatsValid = true;
            } catch (Exception e) {
                System.out.println("Error: Introdueix un número vàlid per al nombre d'unitats.");
                scanner.nextLine();
                registrarErrorEnLogs(new Exception("Numero d'unitats del producte electronic incorrecte."));
            }
        }

        // Crea i afegeix el producte al carro de la compra
        Electronica electronica = new Electronica(nom, preu, codiBarres, diesGarantia);
        afegirProducte(electronica, unitats);
        productesElectronica.add(electronica);
    }

    // Buscador de productes amb stream i lambda expressions
    public void buscarProductesCodiBarres(int codiBarres) {
        boolean trobat = carroCompra.stream()
                .filter(producte -> producte.getCodiBarres() == codiBarres)
                .peek(producte -> System.out.println("Producte trobat: " + producte.getNom() + " | Quantitat: " + unitatsPerProducte.get(producte.getCodiBarres())))
                .findAny()
                .isPresent();

        // Si no es troba, mostra un missatge d'error
        if (!trobat) {
            System.out.println("No s'ha trobat cap producte amb el codi de barres: " + codiBarres);
            registrarErrorEnLogs(new Exception("Error al buscar producte per codi de barres."));
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
