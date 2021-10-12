# Generic Appointment Scheduling System (BME-GASSYS)

A BME-GASSYS egy általános időpontfoglaló rendszer, amelyben egy szolgáltató publikálhat eseményeket időponttal,
létszámkorláttal, ismétlődéssel, melyekre a fogyasztók tudnak jelentkezni.

## Felhasználók

A felhasználók szerepétől függően négy csoportot különböztethetünk meg

- Adminisztrátor szerep: Bejelentkezés után a rendszerben képes adminisztrátori feladatokat ellátni, dolgozót hozzáadni,
  felhasználót kitiltani, illetve mások által kiírt eseményeket listázni, módosítani, törölni.


- Szolgáltatói / dolgozói szerep: Bejelentkezés után a rendszerben képes új eseményeket kiírni, a meglévő eseményeket
  listázni, illetve saját eseményeit akár törölni is.


- Felhasználói / vásárlói szerep: Bejelentkezés után a rendszerben képes eseményeket listázni és azokra jelentkezni,
  vagy a jelentkezést lemondani.


- Tulajdonosi / vállalkozói szerep: Egy külön nézetben lát kimutatást az eseményekről és az eseményekre jelentkezőkről.

## Részletes funkciók

- Közös funkciók
    - Belépés
    - Kilépés
    - Jelszó reset
    - Belépés után jelszó megváltoztatása
    - Az eseményeket megjeleníteni heti nézetben.


- Felhasználó
    - Regisztráció megerősítő linkkel
    - Az események nézetben lehet szűrni hogy csak azok az események jelenjenek meg, amelyekre a felhasználó már
      feljelentkezett. Meg lehet nézni a következő hetek eseményeit is
    - Jelentkezni eseményekre, vagy lejelentkezni az eseményről. A jelentkezésről és lejelentkezésről értesítő e-mailt
      küld a rendszer


- Szolgáltató
    - Az események nézetben lehet szűrni hogy csak a saját események jelenjenek meg
    - Új esemény létrehozása - megadható a kezdeti időpont, az időtartam, a férőhelyek száma, egy cím, opcionálisan:
      listából kategória, az esemény színe, minimum létszám, jelentkezés / lejelentkezés lezárása.
    - Saját esemény módosítása, törlése. Módosítás vagy törlés esetén e-mail értesítő a már jelentkezett
      felhasználóknak.
    - E-mail küldése a saját eseményre jelentkezettek számára.


- Tulajdonos
    - Új kategória hozzáadása
    - Beállítható időszakról, választható kategóriákkal grafikonokat vagy kimutatásokat generálálás az eseményekről,
      jelentkezők számáról.
    - E-mail küldése az összes felhasználó és szolgáltató számára


- Adminisztrátor
    - A rendszerhez képes új felhasználót hozzáadni tulajdonosi vagy szolgáltatói szerepben
    - Képes a rendszerből bármilyen felhasználót törölni
    - Képes a rendszerben bármilyen eseményt törölni vagy módosítani
    - Képes kitenni egy bannert a főoldalra technikai információkkal. Ezzel párhuzamosan tiltja a regisztrációt és a
      belépést.