# My First Cafe

**My First Cafe** je Android aplikacija namenjena malim ugostiteljskim objektima (kafićima), koja omogućava upravljanje cenovnikom, izdavanje računa i pregled dnevnih prihoda.  
Aplikacija je osmišljena kao jednostavan **Point of Sale (POS)** sistem prilagođen svakodnevnom radu.

---

## Funkcionalnosti

Aplikacija se sastoji iz **dva glavna taba (Bottom Navigation)**:

### Lista računa
- Prikaz svih izdatih računa, grupisanih po danima
- Header svakog dana sadrži:
  - datum
  - ukupnu zaradu tog dana
- Svaki račun prikazuje:
  - vreme izdavanja
  - ukupnu cenu (sa PDV-om i bakšišem)
  - listu prodatih artikala (količina × naziv)
  - iznos bakšiša
- Klikom na račun otvara se ekran sa detaljima računa
- Ako nema računa, prikazuje se poruka:
  > *You don’t have any receipt yet*
- FAB dugme za dodavanje novog računa

---

### Dodavanje novog računa
- Prikaz svih artikala iz ponude, grupisanih po kategorijama
- Svaki artikal ima:
  - naziv
  - cenu
  - dugmad `+` i `-` za izbor količine
- Klikom na **Next**:
  - ako nema izabranih artikala → prikazuje se poruka  
    > *You don’t have any item selected yet*
  - ako postoje izabrani artikli → otvara se ekran **Detalji računa**

---

### Detalji računa
- Prikaz svih izabranih artikala sa:
  - količinom
  - cenom po artiklu
  - ukupnom cenom po stavci
- Polje za unos bakšiša (numerička tastatura)
- Automatski obračun ukupne cene:
(cene artikla * bakšiš) + 20% PDV
- Klikom na **Charge receipt**:
  - račun se čuva u bazi
  - korisnik se vraća na listu računa

---

### Cenovnik
- Prikaz artikala po kategorijama:
  - Pića
  - Predjelo
  - Glavno jelo
  - Dezert
- Pretraga artikala po nazivu
- FAB dugme za dodavanje novog artikla
- Klik na artikal → izmena artikla
- Long click → dijalog za brisanje artikla uz potvrdu

---

### Dodavanje / izmena artikla
- Unos:
  - naziva artikla
  - kategorije
  - cene
- Validacija:
  - ako nisu popunjena sva polja, prikazuje se poruka  
    > *You didn’t set all fields*

---

## Arhitektura

Aplikacija je implementirana korišćenjem **MVVM (Model–View–ViewModel)** arhitekture radi:
- jasne podele odgovornosti
- lakšeg održavanja
- bolje testabilnosti

---

## Korišćene tehnologije

- **Kotlin**
- **Jetpack Compose**
- **Room Database**
- **Hilt (Dependency Injection)**
- **Navigation Compose**
- **Coroutines & Flow**
- **Material 3**

---

## Skladištenje podataka

- Svi podaci se čuvaju lokalno
- Aplikacija radi **offline**
- Room baza se koristi za:
  - artikle
  - kategorije
  - račune
  - stavke računa

---

## Namena projekta

Ovaj projekat je razvijen kao:
- studentski projekat
- praktična primena Android tehnologija
- demonstracija MVVM arhitekture i Jetpack Compose UI-a

---
