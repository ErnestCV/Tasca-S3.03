package org.nivell3;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.nivell3.products.Decoration;
import org.nivell3.products.Flower;
import org.nivell3.products.Product;
import org.nivell3.products.Tree;
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

    protected static Class<?>[] getSubClasses() {
        return new Class<?>[]{Decoration.class, Flower.class, Tree.class};
    }

    public static void main(String[] args) {

        MongoCollection<Document> products = database.getCollection("products");

        products.deleteMany(new Document());

        MongoCollection<Product> collection = database.getCollection("products", Product.class);
        collection.insertOne(new Decoration(new ObjectId(), "nom", 1f, 1, "fusta"));
        collection.insertOne(new Flower(new ObjectId(), "nom", 1f, 1, "blanc"));
        collection.insertOne(new Tree(new ObjectId(), "nom", 1f, 1, 1d));

        FindIterable<Product> productsFound = collection.find();
        productsFound.forEach(product -> System.out.println(product.getProperty()));


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