/**
 * Created by Danny on 11/05/2018.
 */
import java.util.*;
public class AstronautScheduler
{
    static int oxygenLimit = 0;
    static int time = 0;
    static int suitLocation = 1;
    static LinkedList<Astronaut> locationA = new LinkedList<Astronaut>();
    static LinkedList<Astronaut> locationB = new LinkedList<Astronaut>();
    //Simply intialise and assign some global variables
    // which will be altered throughout the course of the program
    public static void main(String[] args)
    {
        System.out.println("This program helps make saving astronauts easier");
        boolean mainLoop = true;
        //This is a menu system. The use of while loops ensures that the menu is re-displayed
        // every time after an invalid entry, until 0, the quit option is chosen.
        while(mainLoop)
        {
            System.out.println("MENU:");
            System.out.println("(1) Default scenario");
            System.out.println("(2) Enter your own parameters");
            System.out.println("(0) Quit");
            System.out.println("Please enter your choice: ");

            Scanner reader = new Scanner(System.in);
            boolean exception = true;
            while (exception)
            {
                reset();
                    //As the program doesnt terminate after a single run, and instead redisplays the menu with an option to go again
                    //We need to ensure that the global variables are reset, so data from a previous run doesn't stay in linked list,
                    // or global variables like time start at a incorrect value.
                try
                {
                    exception = false;
                    int input = reader.nextInt();
                    switch(input)
                    {
                        case 0: mainLoop = false;
                                break;
                        case 1: defaultScenario();
                                Scheduler();
                                //Case 1 selects defaultScenario, which simply loads in the values on the sheet, then calls the scheduler
                                //method, which is actually responsible for the execution of the algorithm
                                break;
                        case 2: customParameters();
                                Scheduler();
                                //This calls customParameters, which allows the user to input a bespoke scenario. After doing so, scheduler
                                //is then called to solve the problem
                                break;
                        default:
                            System.out.println("Invalid input, please try again");
                            exception = true;
                            break;
                    }
                }
                catch (Exception e)
                {
                    reader.nextLine();
                    System.out.println("You entered invalid input, please try again");
                }
            }
        }
    }

    //resets all global variables and the linked lists to prevent different runs of the program interfering
    public static void reset()
    {
        oxygenLimit = 0;
        time = 0;
        suitLocation = 1;
        while(!locationA.isEmpty())
        {
            locationA.removeFirst();
        }
        while(!locationB.isEmpty())
        {
            locationB.removeFirst();
        }
    }

    //Doesn't really have to be a method, but makes the code cleaner
    public static void defaultScenario()
    {
        oxygenLimit = 21;
        Astronaut neil = new Astronaut(1,"Neil");
        Astronaut michael = new Astronaut(2,"Michael");
        Astronaut valentina = new Astronaut(3,"Valentina");
        Astronaut yuri = new Astronaut(5,"Yuri");
        Astronaut edwin = new Astronaut(10,"Edwin");
        locationB.add(neil);
        locationB.add(michael);
        locationB.add(valentina);
        locationB.add(yuri);
        locationB.add(edwin);
    }

    //Somewhat complex, just has a series of while loops, doing the same as the ones in main - looping and continuously redisplaying the
    //options the user can chose from in case of an error/invalid entry.
    public static void customParameters()
    {
        int walkTime;                   // set as what the user enters as walkTime for a given astronaut
        int input = -1;                 // set as number of astronauts to be saved
        String name = null;             // set as name of astronaut
        boolean walkLoop = true;        // set as true, determines whether we stay in while loop asking for walkTime entry
        boolean timeLimitLoop = true;   // set as true, determines whether we stay in while loop asking for a time limit
        boolean astroNumberLoop = true; // set as true, determines whether we stay in while loop asking for number of astronauts
        boolean nameLoop = true;        // set as true, determines whether we stay in while loop asking for name


        System.out.println("You've opted to choose your own parameters");
        System.out.println("You can cancel this at any point by entering '0' where you will be returned to the menu");
        System.out.println("What is time limit (in minutes) to save these astronauts?");

        Scanner timeLimit = new Scanner(System.in);
        //This while loop, loops until a valid entry for the oxygenLimit is given
        while(timeLimitLoop)
        {

            try
            {
                timeLimitLoop = false;
                oxygenLimit = timeLimit.nextInt();
                if (oxygenLimit == 0)
                {
                    System.out.println("Returning to menu..");
                    return;
                }
            }
            catch (Exception e)
            {
                timeLimitLoop = true;
                timeLimit.nextLine(); //moves timeLimit to next line so can read next input
                System.out.println("You entered invalid input, please try again");
            }
        }

        System.out.println("How many astronauts do you wish to save today?");
        Scanner reader = new Scanner(System.in);
        //Again, loops until a valid entry for the number of astronauts to save is given
        while (astroNumberLoop)
        {
            try
            {
                astroNumberLoop = false;
                input = reader.nextInt();
                if (input == 0) //Stated that a 0 entry would cancel and return us to menu
                {
                    System.out.println("Returning to menu..");
                    return;
                }
                else if (input > 30) //rather arbitrary cap introduced at 30, no real reason other than its near impossible to follow
                {
                    System.out.println("That's too many astronauts for me!");
                    return;
                }
            }
            catch (Exception e)
            {
                reader.nextLine(); //moves reader to next line so can read next input
                System.out.println("You entered invalid input, please try again");
                astroNumberLoop = true;
            }
        }

        //We then iterate through this for loop as many times as astronauts to be saved
        //We ask for astronaut name and walktime, then instantiate the Astronaut class and place in locationB
        //linked list which is our starting position
        for (int i = 0; i < input; i++)
        {
            System.out.println("Please enter name for Astronaut " + i);
            nameLoop = true;
            while (nameLoop)
            {
                Scanner astronautName = new Scanner(System.in);
                try
                {
                    name = astronautName.nextLine();
                    if (name.equals("0"))
                    {
                        System.out.println("Returning to menu..");
                        return;
                    }
                    nameLoop = false;
                }
                catch (Exception e)
                {
                    astronautName.nextLine();
                    System.out.println("Invalid input, please try re-entering this value");
                }
            }
            walkLoop = true;
            while(walkLoop)
            {
                System.out.println("Please enter the walk time for " + name);
                Scanner astronautWalkTime = new Scanner(System.in);
                try
                {
                    walkLoop = false;
                    walkTime = astronautWalkTime.nextInt();

                    Astronaut astronaut = new Astronaut(walkTime, name);
                    locationB.add(astronaut);
                }
                catch(Exception e)
                {
                    System.out.println("Invalid entry! Please try again");
                    walkLoop = true;
                }
            }
        }

    }

    public static void Scheduler()
    {
        Pair tempPair = selectPair(1);
        Astronaut first = tempPair.getFirst();
        Astronaut second = tempPair.getSecond();
        //This is the very first part of the problem, we select the two quickest astronauts

        printState();
        spaceWalk(first, second);
        suitLocation++;
        printState();
        transportSpaceSuit(first);
        suitLocation++;
        printState();
        //We print our initial state, move the two fastest from B to A, print the state after this walk is completed
        //Increment suitLocation to show it is at location A (even)
        //We then select the quickest of these two (who implicitly is first) to transport the spacesuit back
        //We increment suit to show its at location B (odd)

        while(locationB.size() > 0)
        {
            //Says if there are more than 3 astronauts at B and the suit is at locationB then choose the slowest pair
            if (locationB.size() > 3 && suitLocation % 2 == 1)
                tempPair = selectPair(2);
            else //Otherwise chose the quickest 2 (this means we have 3 or less astronauts here, in which case choosing the fastest pair is optimum)
                tempPair = selectPair(1);

            first = tempPair.getFirst();
            second = tempPair.getSecond();

            spaceWalk(first, second);
                //from the if-else statement we assign tempPair to be the quickest or slowest pair at location B,
                //regardless, we then walk these astronauts to location A
            suitLocation++;
                //Imcrement suitLocation to show it is at locationA (even)
            printState();
                //Prints state of system
            first = selectQuickest();
                //We now have the suits at A, so we know we want to select the quickest person here to return them
            if (locationB.size() > 0)
            {
                //This says to only return the suit to B if there is actually someone there, otherwise our goal is complete
                transportSpaceSuit(first);
                suitLocation++;
                printState();
            }
        }
        //Just the end decision of if we have managed to save these astronauts or not
        System.out.println("The minimum time taken to save these astronauts is " + time + " minutes");
        System.out.println("For this scenario, an oxygen limit of " + oxygenLimit + " minutes was stated");
        if(time <= oxygenLimit)
            System.out.println("Therefore, we managend to save all the astronauts in time!");
        else
            System.out.println("Unfortunately we cannot save all the astronauts in this timeframe..");
    }

    //This means astronaut a is moving from A to B with a spare space suit
    public static void transportSpaceSuit(Astronaut a)
    {
        System.out.println(a.getName() + " is returning to location B with the spare spacesuit");
            //prints out some information so the user can track what is taking place
        locationA.remove(a);
        locationB.add(a);
        time += a.getWalkTime();
        //We remove astronaut a from locationA and add him/her to locationB. The add the time taken for this trip
    }

    //Just provides an overview of what state the problem is in, so the user can track the progress of the solution
    public static void printState()
    {
        System.out.println("--------------------TIME " + time + "-------------------------");
        System.out.printf("%-30.30s%-30.30s \n", "LOCATION A", "LOCATION B");
        for (int i = 0; i < Math.max(locationA.size(), locationB.size()); i++)
        {
            try {
                System.out.printf("%-30.30s", locationA.get(i).getName());
            }
            catch(Exception e)
            {
                System.out.printf("%-30.30s", "");
            }
            try {
                System.out.printf("%-30.30s \n", locationB.get(i).getName());
            }
            catch(Exception e)
            {
                System.out.printf("%-30.30s \n", "");
            }
        }
        System.out.println("---------------------------------------------------");
    }

    //This means Astronaut a and b are moving from location B to A, so we remove them from linked list locationB
    // and append them to linked list locationA
    public static void spaceWalk(Astronaut a, Astronaut b)
    {
        System.out.println(a.getName() + " and " + b.getName() + " begin spacewalk to location A");
        locationB.remove(a);
        locationB.remove(b);

        locationA.add(a);
        locationA.add(b);

        time += Math.max(b.getWalkTime(), a.getWalkTime());
            //update the time, adding on the time taken to complete this spacewalk, which is the maximum of the two walkTimes
    }

    //Returns the quickestAstronaut from Location A
    //used to select which astronaut will take spare space suit back
    public static Astronaut selectQuickest()
    {
        Astronaut quickest = null;
        Astronaut temp = null;

        for(int i = 0; i < locationA.size(); i++)
        {
            temp = locationA.get(i);
            if(quickest == null)
            {
                quickest = locationA.get(i);
                continue;
            }

            if(temp.getWalkTime() < quickest.getWalkTime())
            {
                quickest = temp;
            }
        }
        return quickest;
    }

    //This returns an object of type Pair, meaning it returns two astronauts at once
    //The mode determines whether the function retrieves the quickest or slowest 2 astronauts at location B
    //1 means quickest, 2 means slowest
    public static Pair selectPair(int mode)
    {
        int tempWalkTime = -1;
        Astronaut temp = null;
        Astronaut swap = null;
        Astronaut firstAstronaut = null;
        Astronaut secondAstronaut = null;

        //Iterates over all values in the linkedList
        for(int i = 0; i < locationB.size(); i++)
        {
            //temp stores current astronaut being looked at
            temp = locationB.get(i);
            //tempWalkTime contains walktime of this astronaut
            tempWalkTime = temp.getWalkTime();

            //If our firstAstronaut is null, i.e. this is the first run of the for loop then assign to firstAstronaut whatever
            //is current available
            if(firstAstronaut == null)
            {
                firstAstronaut = temp;
                continue;
            }
            //Same process for secondAstronaut
            if(secondAstronaut == null)
            {
                secondAstronaut = temp;
                continue;
            }
            //Mode == 1 means we are looking to select the smallest pair of astronauts
            if(mode == 1)
            {
                //This checks that our initial pair are oriented correctly. Say we assign first 3 and second 2, this causes
                //a major issue if we don't swap these two values, as first should be the smallest, and second the second smallest
                //if second is smaller than first, and we compare second with a value 1, we will then compare first which is also smaller
                //first will be set 1 and second will be assigned 3 instead of keeping its value of 2. So this swap is v important.
                if(firstAstronaut.getWalkTime() > secondAstronaut.getWalkTime())
                {
                    swap = firstAstronaut;
                    firstAstronaut = secondAstronaut;
                    secondAstronaut = swap;
                }
                //If its smaller than both, then second becomes value of first and first takes value of temp
                if (tempWalkTime < secondAstronaut.getWalkTime())
                {
                    if (tempWalkTime < firstAstronaut.getWalkTime())
                    {
                        secondAstronaut = firstAstronaut;
                        firstAstronaut = temp;
                    }
                    else
                        secondAstronaut = temp;
                }
            }
            else //select the slowest pair
            {
                //Same principle as the swap covered above
                if(firstAstronaut.getWalkTime()< secondAstronaut.getWalkTime())
                {
                    swap = firstAstronaut;
                    firstAstronaut = secondAstronaut;
                    secondAstronaut = swap;
                }
                //Again, same principle as above
                if (tempWalkTime > secondAstronaut.getWalkTime())
                {
                    if (tempWalkTime > firstAstronaut.getWalkTime())
                    {
                        secondAstronaut = firstAstronaut;
                        firstAstronaut = temp;
                    }
                    else
                        secondAstronaut = temp;
                }
            }
        }
        Pair astroPair = new Pair(firstAstronaut, secondAstronaut);
        return astroPair;
    }
}

//Simple class that takes two entries, used to allow the "selectPair" method to return 2 astronauts at once, opposed to
//having to make 2 calls to the method
class Pair
{
    private Astronaut first;
    private Astronaut second;

    public Pair(Astronaut first, Astronaut second)
    {
        this.first = first;
        this.second = second;
    }
    public Astronaut getFirst()
    {
        return first;
    }
    public Astronaut getSecond()
    {
        return second;
    }
}

//Astronaut class, simple getters (and setters although not needed)
//Each astronaut has a name and a walkTime
class Astronaut
{
    private int walkTime;
    private String name;

    public Astronaut(int walkTime, String name)
    {
        this.walkTime = walkTime;
        this.name = name;
    }

    public void setWalkTime(int walkTime)
    {
        this.walkTime = walkTime;
    }

    public int getWalkTime()
    {
        return walkTime;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}