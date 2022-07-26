package org.nivell3;

import com.mongodb.client.MongoDatabase;
import org.nivell3.service.DBConnection;
import org.nivell3.service.StoreManager;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {

    static {
        Logger.getLogger("org.mongodb.driver").setLevel(Level.OFF);
        Logger.getLogger("org.bson").setLevel(Level.OFF);
    }
    private static final String currentStore = "floristeria";
    private static final MongoDatabase database = DBConnection.getConnection(currentStore);
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        //Menú
        int select;

        do {
            printMenu();

            select = scanner.nextInt();
            scanner.nextLine();

            switch (select) {

                //Introduir producte a botiga
                case 1 -> StoreManager.getInstance(database).addProduct();

                //Actualitzar producte a botiga
                case 2 -> StoreManager.getInstance(database).updateStock();

                //Esborrar producte de botiga
                case 3 -> StoreManager.getInstance(database).deleteProduct();

                //Mostrar stock amb quantitats
                case 4 -> StoreManager.getInstance(database).showStock();

                //Mostrar valor total
                case 5 -> StoreManager.getInstance(database).getTotalValue();

                //Crear ticket
                case 6 -> StoreManager.getInstance(database).generateTicket();

                //Mostrar vendes (historial)
                case 7 -> StoreManager.getInstance(database).showHistory();

                //Mostrat total diners vendes
                case 8 -> StoreManager.getInstance(database).showTotalSales();

                //Sortir del programa
                case 0 -> DBConnection.closeConnection();
            }

        } while (select != 0);

        System.out.println("Sortint del programa");
    }

    public static void printMenu() {
        System.out.println("""
                -------------------
                Escolliu una opció:
                1: Afegir un producte a una botiga
                2: Actualitzar un producte
                3: Esborrar un producte
                4: Mostrar stock total de la botiga
                5: Mostrar valor total del stock de la botiga
                6: Crear un nou tiquet de compra
                7: Mostrar historial de vendes
                8: Mostrar total guanyat amb les vendes
                0: Sortir
                ------------------
                """);
    }
}