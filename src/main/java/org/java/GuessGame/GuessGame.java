package org.java.GuessGame;

class GuessGame {
    public static void main(String[] args) {
        playGame(PanelManager.getNumberInterface());
    }

    /**
     * Žaidimo pagrindinė funkcija, kuri yra atsakinga už jo visą veiklą
     * Pats žaidimas yra skaičiaus spėjimas tarp intervalo [0; x], kur žaidėjas
     * turi atspėti atsitiktinai išrinktą sveikajį skaičių iš šio intervalo.
     * @param maxGuess Intervalo [0; x] maksimali reikšmė
     */
    public static void playGame(int maxGuess) {
        /*
           // Atsinešti su funkcija parametrai
           maxGuess - tas maksimalus skaičius iki kurio turi spėti.

           // Funkcijos vietiniai kintamieji
           shouldGiveHint - ar duoti užuomeną ar ne (true - duoti, false - neduoti)
           lastGuess - paskutinis spėjimas
           repeat - Ar žaidėjas bando išnaujo spėti tą patį skaičių (true - bando, false - nebando)
           randomInt - Skaičius kurį turės atspėti, pradžioje jis nulis, bet po to tampa į atsitiktinį skaičių.
           gameInput - žaidėjo įvestis (gali būti skaičius, gali būti kažkas kito)
           choiceInt - Pasirinkto mygtuko per input'ą ID, 0 - žaidimas tęsiasi, 1 - žaidimas atšaukiamas
           askReason - String tipo kintamasis, kuris nurodo klausiamojo lango (askForRepeat) klausimą.
             - Yra trys klausimų tipai: success, fail, incorrectInput.
           askQuestion - String tipo kintamasis, kuris nurodo klausimą kuris klausiamas žaidėjui.
           hint - Užuomena, kuri pasako žaidėjui ar skaičius kurį turi spėti yra didesnis, ar mažesnis už spėtą.
        */

        // KINTAMIEJI
        boolean repeat = false;
        boolean shouldGiveHint = false;
        String gameInput;
        int lastGuess = 0;
        int randomInt = 0;
        int choiceInt;
        String askQuestion;
        String askReason;

        // ŽAIDIMAS
        while (true) {
            if (!repeat) randomInt = randomNumGen(maxGuess);
            // Sukuria žinutę input langeliui, jeigu reikia hint'o, prideda ir jį
            if (shouldGiveHint) { // set shouldGiveHint to true if players repeats after a fail attempt
                String hint = giveHint(randomInt, lastGuess);
                askQuestion = "Spėkite skaičių kuris yra nuo 0 iki " + maxGuess + "!\n" + "UŽUOMENA: Spėjamasis " +
                        "skaičius yra " + hint + " už " + lastGuess + "!";
                shouldGiveHint = false;
            }
            else askQuestion = "Spėkite skaičių kuris yra nuo 0 iki " + maxGuess + "!";
            // Sukuria input langelį, kur klausia skaičiaus
            gameInput = PanelManager.askForNumber(askQuestion);
            // Jei langelis buvo tuščias arba paspaustas Cancel mygtukas (null)
            if (gameInput == null) System.exit(0);
            // Kitaip tikriname ar input'as buvo int tipo
            if (isInteger(gameInput)) {
                lastGuess = Integer.parseInt(gameInput);

                // Žinutės priežasčių nustatymai

                if (lastGuess == randomInt) askReason = "success";                              // Atspėjo
                else if (lastGuess > maxGuess || lastGuess < 0) askReason = "incorrectInput";   // Neteisinga įvestis
                else askReason = "fail";                                                        // Atspėjo ne tą skaičių, bet ribose

                // Klausiam klausimo kas toliau
                choiceInt = PanelManager.askForRepeat(askReason, randomInt);

            }
            else {
                // Blogas input'as, nes input'as ne int'as, siūlome išnaujo sužaisti
                askReason = "incorrectInput";
                choiceInt = PanelManager.askForRepeat(askReason, randomInt);
            }
            // Jeigu pasirinko 0 - kartojamas žaidimas, 1 - užbaigiam
            if (choiceInt == 1) {
                // Žaidimo uždarymas
                PanelManager.thankForGame();
                System.exit(0);
            }
            else {
                if (askReason.equals("fail") || askReason.equals("incorrectInput")) {
                    shouldGiveHint = true;
                    repeat = true;
                }
                else {
                    maxGuess = PanelManager.getNumberInterface();
                    repeat = false;
                    lastGuess = 0;
                }
            }
        }
    }

    /**
     * Sukuria užuomeną pagal tai, ar spėjamas skaičius buvo mažesnis, ar didesnis
     * už paslėptąjį skaičių.
     * @param randomInt Paslėptoji reikšmė, skaičius kurį turėjo atspėti
     * @param lastGuess Paskutinis žaidėjo spėjimas
     * @return Užuomena
     */
    public static String giveHint(int randomInt, int lastGuess) {
        return randomInt > lastGuess ? "didesnis" : "mažesnis";
    }

    /**
     * Patikrina reikšmę str (string) ir grąžiną boolean reikšmę:
     * true - jei str yra integer
     * false - jei str nėra integer
     * @param str String reikšmė, kurią tikrina
     * @return Boolean reikšmė, kuri parodo ar str yra int, ar ne
     */
    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Grąžina pseudo-atsitiktiniai sugeneruota skaičių, nuo 0 iki max reikšmių.
     * min reikšmė yra 0, nes taip pritaikyta žaidimui.
     * @param max Maksimali reikšmė
     * @return Atsitiktinė reikšmė nuo 0 iki max
    */
    public static int randomNumGen(int max) {
        return (int)(Math.random() * (max + 1));
    }
}

