package org.java.GuessGame;

import javax.swing.JOptionPane;

public class PanelManager {

    // OBJEKTAI

    /**
     * Sukuria lentelę kurioje klausia žaidėjo, ar jis nori žaidimą pakartotį (jei neatspėjo skaičiaus arba
     * įvedė blogą įvestį), ar sužaisti išnaujo (jei laimėjo/atspėjo skaičių). Taip pat gali pasirinkti užbaigti žaidimą.
     * @param reason Priežastis dėl kurios klausia
     * @param correctGuess Atspėtasis skaičius
     * @return Funkcija grąžina arba 0 arba 1: 0 - tęsiasi žaidimas, 1 - žaidimas užbaigiamas.
     */
    public static int askForRepeat(String reason, int correctGuess) {
        Object[] options = { "Išnaujo", "Užbaigti" };
        String titlemsg = ""; String msg = "";
        int msgType = 0; // Pakeičia kokia informacinę ikonelę rodo prie žinutės. 0 - klaida, 1 - info, 2 - pavojus, 3 - klausimas
        switch (reason) {
            case "fail" -> {
                options[0] = "Pakartoti";
                titlemsg = "Suklydote!";
                msg = "Paspauskite 'Pakartoti', kad pabandyti dar kartą spėti su tuo pačiu skaičiumi.";
            }
            case "incorrectInput" -> {
                options[0] = "Pakartoti";
                titlemsg = "Neteisinga įvestis!";
                msg = "Įvedėte neteisingą įvestį. Įvestis turi būti sveikasis skaičius, kuris yra\n" +
                        "didesnis už 0! Paspauskite 'Pakartoti', jei norite išbandyti spėti pernaujo.";
                msgType = 2;
            }
            case "success" -> {
                titlemsg = "Teisingai!";
                msg = "Pataikėte tiesiai į šimtuką! Skaičius buvo " + correctGuess +
                        "!\nJeigu norite sužaisti dar kartą, paspauskite ant 'Išnaujo' mygtuko!";
                msgType = 1;
            }
        }
        return JOptionPane.showOptionDialog(null, msg, titlemsg,
                JOptionPane.DEFAULT_OPTION, msgType,
                null, options, options[0]);
    }

    /**
     * Prašo žaidėjo įvesti maksimalią reikšmę (>0), nuo kurios bus sukurtas intervalas [0; x],
     * kur x - maksimali reikšmė, tarp jos bus išrinktas skaičius kurį reiks atspėti.
     * @return Maksimali reikšmė
     */
    public static int getNumberInterface() {
        String guessintInput;
        while (true) {
            guessintInput = JOptionPane.showInputDialog("Įveskite sveikąjį skaičių, didesnį už 0, iki kurio spėsite:");
            if (guessintInput == null) System.exit(0);
            else if (GuessGame.isInteger(guessintInput) && Integer.parseInt(guessintInput) > 0) break;
        }
        return Integer.parseInt(guessintInput);
    }

    /**
     * Sukuria lentelę kurioje padėkoja žaidėjui už žaidimą.
     */
    public static void thankForGame() {
        JOptionPane.showInternalMessageDialog(null, "Ačiū, kad žaidėte!",
                "Žaidimas užbaigiamas!", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Sukuria lentelę kurioje prašo žaidėjo įvesti skaičių.
     * @param askQuestion Lentelės klausimas. String.
     * @return Grąžina žaidėjo įvestį, String formatu.
     */
    public static String askForNumber(String askQuestion) {
        return JOptionPane.showInputDialog(askQuestion);
    }
}
