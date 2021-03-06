package controller;

import being.Kid;
import being.OompaLoompa;
import model.GoldenTicket;
import model.Product;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class FunctionLibrary
{
    List<Kid> kidList = new ArrayList<Kid>();

    public void registerKid(int howMany)
    {
        Scanner userInput = new Scanner(System.in);
        String birthday = "";

        int code;
        // String name="";
        String placeOfBirth = "";

        for (int ctr = 0; ctr < howMany; ++ctr)
        {
            System.out.println("Type code of the kid: ");
            code = userInput.nextInt();

            userInput.nextLine();   //it is a workaround so it wont skip the input after nextInt()

            System.out.println("Type name of the kid: ");
            String name = userInput.nextLine();

            System.out.println("Type birthday of the kid (yyyy-MM-dd): ");
            birthday = userInput.nextLine();

            System.out.println("Type birth place of the kid: ");
            placeOfBirth = userInput.nextLine();


            Kid kid = new Kid(code, name, birthday, null, placeOfBirth);
            kidList.add(kid);
        }
    }

    List<OompaLoompa> oompaLoompaList = new ArrayList<OompaLoompa>();

    public void registerOompaLoompa(int howMany)
    {
        Scanner userInput = new Scanner(System.in);

        int code;
        String name = "";
        int height;
        String favoriteFood = "";

        for (int ctr = 0; ctr < howMany; ++ctr)
        {
            System.out.println("Type code of oompaloompa: ");
            code = userInput.nextInt();
            userInput.nextLine();   //it is a workaround so it wont skip the input after nextInt()

            System.out.println("Type name of oompaloompa: ");
            name = userInput.nextLine();

            System.out.println("Type height of oompaloompa: ");
            height = userInput.nextInt();
            userInput.nextLine();

            System.out.println("Type favorite food of oompaloompa: ");
            favoriteFood = userInput.nextLine();

            OompaLoompa oompaLoompa = new OompaLoompa(code, name, height, favoriteFood);
            oompaLoompaList.add(oompaLoompa);
        }
    }

    List<Product> productList = new ArrayList<Product>();

    public void registerProducts(int howMany)
    {
        Scanner userInput = new Scanner(System.in);

        String description = "";
        long barcode;
        String serialNumber = "";

        for (int ctr = 0; ctr < howMany; ++ctr)
        {
            System.out.println("Type description of the product: ");
            description = userInput.nextLine();

            System.out.println("Type barcode of the product: ");
            barcode = userInput.nextLong();
            userInput.nextLine();

            System.out.println("Type serial number of the product: ");
            serialNumber = userInput.nextLine();

            Product product = new Product(description, barcode, serialNumber, null);
            productList.add(product);
        }
    }

    public void registerSale(int kidsCode)
    {
        int userCode = 123;
        Scanner userInput = new Scanner(System.in);

        System.out.println("Type user code to proceed: ");
        int userCodeInput = userInput.nextInt();

        if (userCodeInput == userCode)
        {
            for (int i = 0; i < kidList.size(); ++i)
            {
                if (kidList.get(i).getCode() == kidsCode)
                {
                    System.out.println("Type product barcode: ");
                    long barcode = userInput.nextLong();
                    int randomizedProductIndex = 0;

                    Random randomIndex = new Random();
                    while (productList.get(randomizedProductIndex).getBarcode() != barcode)
                    {
                        randomizedProductIndex = randomIndex.nextInt(productList.size() - 1);
                    }
                    kidList.get(i).addPurchasedProduct(productList.get(randomizedProductIndex));
                    productList.remove(randomizedProductIndex);
                    break;
                }
            }
        } else System.out.println("user code is wrong");
    }

    public String codeGenerator()
    {
        String charList = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        Random randomIndex = new Random();
        int randomizedCharIndex;
        String code = "";

        while (code.length() < 11)
        {
            randomizedCharIndex = randomIndex.nextInt(charList.length() - 1);
            code += ( charList.charAt(randomizedCharIndex) );
        }
        return code;
    }

    public void ruffleTickets(int howMany)
    {
        Random randomIndex = new Random();
        int randomizedTicketIndex;

        for (int ctr = 0; ctr < howMany; ++ctr)
        {
            Date ruffleDate = new Date();
            String code = codeGenerator();
            GoldenTicket goldenTicket = new GoldenTicket(code, ruffleDate);

            randomizedTicketIndex = randomIndex.nextInt(productList.size());

            while(productList.get(randomizedTicketIndex).getPrizeTicket()!=null &&
                    productList.get(randomizedTicketIndex).getPrizeTicket().isRaffled())  //to prevent inserting golden ticket multiple times to the same product
                randomizedTicketIndex = randomIndex.nextInt(productList.size());

            productList.get(randomizedTicketIndex).setPrizeTicket(goldenTicket);
            productList.get(randomizedTicketIndex).setTicketState(true);    //it is true if it has golden ticket
        }
    }

    public void listPrizeTickets()
    {
        for (int i = 0; i < productList.size(); ++i)
        {
            System.out.println(productList.get(i).getDescription() + "Barcode: " + productList.get(i).getBarcode() + "\n");

            if (productList.get(i).getPrizeTicket() != null)    //use get(i) instead of [i]
                System.out.println("This product contains Golden Ticket!");
            else if (productList.get(i).getPrizeTicket() == null)
                System.out.println("This product does not contain a Golden Ticket!");
            else System.out.println("N/A");
        }
    }

    public void listRaffledTickets()
    {
        for (int i = 0; i < productList.size(); ++i)
        {
            if (productList.get(i).getPrizeTicket()!=null && productList.get(i).getPrizeTicket().isRaffled())
                System.out.println("Ticket code: " + productList.get(i).getPrizeTicket().getTicketCode());
        }
    }

    public void registerPrizeTickets(int howMany)
    {
        Scanner userInput = new Scanner(System.in);
        String serialNumber = "";

        for (int ctr = 0; ctr < howMany; ++ctr)
        {
            System.out.println("Type the serial number of the product you want to insert ticket");
            serialNumber = userInput.nextLine();
            System.out.println("Press 1 to register a Golden Ticket.\nOtherwise, press 2");
            int choiceInput = userInput.nextInt();
            userInput.nextLine();

            for (int i = 0; i < productList.size(); ++i)
            {
                if (productList.get(i).getSerialNumber().equals(serialNumber))
                {
                    if (choiceInput == 1)
                    {
                        Date date = new Date();
                        String code = codeGenerator();
                        GoldenTicket goldenTicket = new GoldenTicket(code, date);
                        productList.get(i).setPrizeTicket(goldenTicket);
                    }
                    else if (choiceInput == 2)
                    {
                        productList.get(i).setPrizeTicket(null);
                    }
                }
            }
        }
    }

    public void listWinners()
    {
        List<Product> purchasedProducts=new ArrayList<Product>();

        if(kidList.size()!=0)
        {
            for (int i = 0; i < kidList.size(); ++i)
            {
                purchasedProducts = kidList.get(i).getListOfPurchasedProducts();

                if(purchasedProducts!=null && purchasedProducts.size()!=0)
                {
                    for (int j = 0; j < kidList.get(i).getListOfPurchasedProducts().size(); ++j)
                    {
                        if (purchasedProducts.get(j).getPrizeTicket() != null)
                        {
                            System.out.println(kidList.get(i).getName());
                            break;
                        }
                    }
                    break;
                }
                else
                    System.out.println("There is no winner");
            }
        }
        else
            System.out.println("There is no kid");
    }

    public void createInventory()
    {
        Kid osman = new Kid(123, "Osman", "1990-10-10", null, "moon");
        kidList.add(osman);

        Kid ali = new Kid(456, "Ali", "1991-11-11", null, "mars");
        kidList.add(ali);

        Kid veli = new Kid(789, "Veli", "1992-12-12", null, "venus");
        kidList.add(veli);

        Product chocolate = new Product("Chocolate", 12345, "9876", null);
        productList.add(chocolate);

        Product chocolate2 = new Product("Chocolate", 12345, "8765", null);
        productList.add(chocolate2);

        Product gum = new Product("Gum", 678910, "5678", null);
        productList.add(gum);

        Product milk = new Product("Milk", 11121214, "6810", null);
        productList.add(milk);

        Product gum2 = new Product("Gum", 678945410, "567465478", null);
        productList.add(gum2);

        OompaLoompa oompa1 = new OompaLoompa(1234, "oompa1", 165, "Apple");
        oompaLoompaList.add(oompa1);

        OompaLoompa oompa2 = new OompaLoompa(5678, "oompa2", 175, "Banana");
        oompaLoompaList.add(oompa2);

        OompaLoompa oompa3 = new OompaLoompa(91011, "oompa3", 185, "Pineapple");
        oompaLoompaList.add(oompa3);

        OompaLoompa oompa4 = new OompaLoompa(121314, "oompa4", 181, "Milkshake");
        oompaLoompaList.add(oompa4);

    }

    public void writeToFile()
    {
        PrintWriter writer = null;
        try
        {
            writer = new PrintWriter("Output.txt");
        }
        catch (FileNotFoundException err)
        {
            System.out.println(err);
        }

        writer.println("The list of the kids:");
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd");
        for(Kid kid : kidList)
        {
            writer.println("Name: "+kid.getName()+" Code: "+kid.getCode()+" Birthday: "+timeFormat.format(kid.getBirthday())+" Birth Place: "+kid.getPlaceOfBirth());

            if(kid.getListOfPurchasedProducts()!=null)
            {
                writer.println("This kid bought following: ");
                for(Product product : kid.getListOfPurchasedProducts())
                {
                    writer.println(product.getDescription()+" Barcode: "+product.getBarcode()+" Serial No: "+product.getSerialNumber());
                }
            }
        }
        writer.println("\n");

        writer.println("The list of the products:");
        for(Product product : productList)
        {
            writer.println(product.getDescription()+" Barcode: "+product.getBarcode()+" Serial No: "+product.getSerialNumber());
            if(product.getPrizeTicket()!=null&&product.getPrizeTicket().isRaffled())
                writer.println("This product contains golden ticket!");
        }
        writer.println("\n");

        writer.println("The list of oompaloompas:");
        for(OompaLoompa oompaLoompa : oompaLoompaList)
        {
            writer.println("Name: "+oompaLoompa.getName()+" Code: "+oompaLoompa.getCode()+" Height: "+oompaLoompa.getHeight()+" Favorite Food: "+oompaLoompa.getFavoriteFood());
        }
        writer.close();
    }
}