import song.OompaLoompaSong;
import controller.FunctionLibrary;
import java.util.Scanner;

public class Main
{
    public static void main(String args[])
    {
        Scanner scanner = new Scanner(System.in);
        FunctionLibrary function = new FunctionLibrary();
        function.createInventory();
        int option;

        do
        {
            System.out.println("Press 0 to register Prize tickets\n" +
							 "Press 1 to list all prize tickets\n" +
							 "Press 2 to list only raffled tickets\n" +
							 "Press 3 to create a new Oompa Loompa song\n" +
							 "Press 4 to register the kids\n" +
							 "Press 5 to register oompaLoompas\n" +
							 "Press 6 to register products\n" +
							 "Press 7 to ruffle tickets\n" +
							 "Press 8 to register sale\n" +
							 "Press 9 to list winners\n" +
                             "Press 10 to quit\n");

            option = scanner.nextInt();

            switch(option)
            {

                case 0:
                    System.out.println("How many tickets do you want to register?");
                    function.registerPrizeTickets(scanner.nextInt());
                    break;
                case 1:
                    function.listPrizeTickets();
                    break;
                case 2:
                    function.listRaffledTickets();
                    break;
                case 3:
                    System.out.println("How many lines should it have?");
                    System.out.println(new OompaLoompaSong(scanner.nextInt()).sing());
                    break;
                case 4:
                    System.out.println("How many kids are there to register?");
                    function.registerKid(scanner.nextInt());
                    break;
				case 5:
					System.out.println("How many oompaloompas are there to register?");
                    function.registerOompaLoompa(scanner.nextInt());
                    break;
				case 6:
					System.out.println("How many products are there to register?");
                    function.registerProducts(scanner.nextInt());
                    break;
				case 7:
					System.out.println("How many tickets do you want to raffle?");
                    function.ruffleTickets(scanner.nextInt());
                    break;
				case 8:
					System.out.println("Type the code of the kid who purchased stuff: ");
                    function.registerSale(scanner.nextInt());
                    break;
				case 9:
                    function.listWinners();
                    break;
                case 10:
                    function.writeToFile();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Wrong option");
            }
        }
        while(true);
    }
}