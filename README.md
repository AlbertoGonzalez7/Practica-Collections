# 📦 Practica Collections 🛒

Pràctica Collections 1rDAW M03 - Alberto González Benítez -

---

## Descripció

Aquest projecte consisteix en una aplicació de supermercat que utilitza col·leccions de Java per gestionar els productes, el carro de la compra i altres funcionalitats relacionades amb la compra de productes. Les classes principals del projecte són `Supermercat`, `Producte`, `Alimentació`, `Textil` i `Electrònica`.
## Justificacions 📋

### Elecció de la Col·lecció List

Ens demanen treballar amb la Collection List. Tot i que tant `Stack` com `Vector` funcionen correctament per a processos multithreading, en aquest context no és necessari. Per tant, hem optat per utilitzar `ArrayList`, ja que proporciona un accés ràpid a les dades i té una implementació més simple.

```sh
private ArrayList<Producte> carroCompra;
```

### Utilització de Col·lecció Map per a Quantitat de Productes

He utilitzat la col·lecció `Map` per gestionar la quantitat de cada producte al carret de la compra. Això ens permet mantenir una associació entre els codis de barres dels productes i la seva respectiva quantitat. Utilitzar un `Map` ens proporciona un accés eficient per a consultes basades en el codi de barres, com ara saber quanta quantitat d'un producte determinat s'ha afegit al carro.

```sh
private Map<Integer, Integer> unitatsPerProducte;
```

### Justificació del Paràmetre de Quantitat

He decidit incloure un paràmetre de quantitat en la funció `afegirProducte` per proporcionar una manera flexible de manejar la quantitat de productes afegits al carret de la compra. Això permet als usuaris especificar el nombre d'unitats de cada producte que volen afegir, oferint una experiència més personalitzada i adaptable a les necessitats dels clients.


```sh
private Map<Integer, Integer> unitatsPerProducte;
```

### Gestió dels Codis de Barres

Per evitar que es repeteixin els codis de barres, hem implementat una comprovació en la funció `afegirProducte` per assegurar-nos que no hi hagi duplicats, tant en productes tèxtils com en altres tipus de productes.

```sh
for (Producte p : carroCompra) {
                if (p.getCodiBarres() == codiBarres) {
                    existeix = true;
                }
            }
            // Si ja existeix el producte, mostra un missatge de error
            if (existeix) {
                System.out.println("Ja hi ha un producte amb el mateix codi de barres al carro.");
```

---

# 💥 Maneig d'Errors 🚨

En el cas que es produeixi un error durant l'execució del codi, hem implementat diverses estratègies per gestionar-los de manera adequada.

#### Informació i Gestió d'Errors

En cas d'error, el programa proporciona un missatge d'error descriptiu que informa a l'usuari sobre la naturalesa del problema. Això ajuda a l'usuari a identificar i comprendre el problema més fàcilment, millorant la transparència i l'usabilitat del programa.

```sh
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
```

#### Recuperació i Continuïtat

En molts casos, el programa intenta recuperar-se de l'error i continuar amb l'execució del codi de manera adequada. Per exemple, en el cas de l'entrada de dades incorrectes, el programa pot demanar a l'usuari que introdueixi de nou les dades correctes.

```sh
Introdueix el nom del producte: Poma
Introdueix el preu del producte: ASD
Introdueix un valor numèric vàlid per al preu del producte.
Introdueix el preu del producte:
```

#### Registre d'Errors

També he implementat una funcionalitat per enregistrar els errors en un fitxer de registre dedicat. Això ens permet tenir un seguiment dels errors que es produeixen durant l'execució del programa i facilita la seva resolució posterior. El registre d'errors inclou informació detallada sobre l'excepció, com el missatge d'error i la traça de pila associada. Això és el que veuríem.

```sh
-------------------------------------------
Excepció: Preu del producte alimentació incorrecte.
java.lang.Exception: Preu del producte alimentació incorrecte.
	at Supermercat.afegirProducteAlimentacio(Supermercat.java:231)
	at Supermercat.menuAfegirProducte(Supermercat.java:194)
	at Supermercat.main(Supermercat.java:143)
-------------------------------------------
```