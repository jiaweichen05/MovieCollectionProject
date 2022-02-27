import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie object to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast()
    {
        System.out.print("Enter a cast search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();
        ArrayList<String> castList = new ArrayList<String>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            boolean temp = true;
            String movieCast = movies.get(i).getCast();

            String[] castArray = movieCast.split("\\|");

            for (int j = 0; j < castArray.length; j++)
            {
                String lower = castArray[j].toLowerCase();
                if(lower.contains(searchTerm) && !castList.contains(castArray[j])){
                    castList.add(castArray[j]);
                    if (temp)
                    {
                        results.add(movies.get(i));
                        temp = false;
                    }
                }
            }
        }

        //changed sort (alphabetical)
        for (int j = 1; j < castList.size(); j++)
        {
            String temp = castList.get(j);

            int possibleIndex = j;
            while (possibleIndex > 0 && temp.compareTo(castList.get(possibleIndex - 1)) < 0)
            {
                castList.set(possibleIndex, castList.get(possibleIndex - 1));
                possibleIndex--;
            }
            castList.set(possibleIndex, temp);
        }

        // now, display them all to the user
        for (int i = 0; i < castList.size(); i++)
        {
            String castMember = castList.get(i);
            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + castMember);
        }

        System.out.println("Which cast member would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        String castPick = castList.get(choice - 1);
        ArrayList<Movie> results2 = new ArrayList<Movie>();

        //adding movies with selected cast to ArrayList
        for (int i = 0; i < results.size(); i++)
        {
            if (results.get(i).getCast().contains(castPick))
            {
                results2.add(results.get(i));
            }
        }

        //Displaying movies with selected cast
        for (int i = 0; i < results2.size(); i++)
        {
            Movie print = results2.get(i);
            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + print.getTitle());
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice2 = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results2.get(choice2 - 1);
        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void searchKeywords()
    {
        System.out.print("Enter a keyword search term: ");
        String searchTerm = scanner.nextLine();

        searchTerm = searchTerm.toLowerCase();

        ArrayList<Movie> results = new ArrayList<Movie>();

        for (int i = 0; i < movies.size(); i++)
        {
            String movieKeywords = movies.get(i).getKeywords();
            movieKeywords = movieKeywords.toLowerCase();

            if (movieKeywords.indexOf(searchTerm) != -1)
            {
                //add the Movie object to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listGenres()
    {
        ArrayList<String> genresList = new ArrayList<String>();
        ArrayList<Movie> results = new ArrayList<Movie>();

        for (int i = 0; i < movies.size(); i++)
        {
            boolean limit = true;
            String genres = movies.get(i).getGenres();
            String[] genresArray = genres.split("\\|");

            for (int j = 0; j < genresArray.length; j++)
            {
                String element = genresArray[j];
                if (!genresList.contains(genresArray[j]))
                {
                    genresList.add(genresArray[j]);
                    if (limit)
                    {
                        results.add(movies.get(i));
                        limit = false;
                    }
                }
            }
        }

        //changed sort (alphabetical)
        for (int j = 1; j < genresList.size(); j++)
        {
            String temp = genresList.get(j);

            int possibleIndex = j;
            while (possibleIndex > 0 && temp.compareTo(genresList.get(possibleIndex - 1)) < 0)
            {
                genresList.set(possibleIndex, genresList.get(possibleIndex - 1));
                possibleIndex--;
            }
            genresList.set(possibleIndex, temp);
        }

        // now, display them all to the user
        for (int i = 0; i < genresList.size(); i++)
        {
            String genre = genresList.get(i);
            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + genre);
        }

        System.out.println("Which genre would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        String genrePick = genresList.get(choice - 1);
        ArrayList<Movie> results2 = new ArrayList<Movie>();

        //adding movies with selected cast to ArrayList
        for (int i = 0; i < results.size(); i++)
        {
            if (results.get(i).getGenres().contains(genrePick))
            {
                results2.add(results.get(i));
            }
        }

        //Displaying movies with selected cast
        for (int i = 0; i < results2.size(); i++)
        {
            Movie print = results2.get(i);
            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + print.getTitle());
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice2 = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results2.get(choice2 - 1);
        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRated()
    {
        ArrayList<Double> highRatedList = new ArrayList<Double>();
        ArrayList<Movie> results = new ArrayList<Movie>();
        ArrayList<Movie> tempMovies = movies;
        double maxRating = 0;
        int count = 0;

        for (int i = 0; i < tempMovies.size(); i++)
        {
            maxRating = tempMovies.get(i).getUserRating();
            int maxPos = 0;

            for (int j = i + 1; j < tempMovies.size(); j++)
            {
                double temp = tempMovies.get(j).getUserRating();
                if (maxRating <= temp)
                {
                    maxRating = temp;
                    maxPos = j;
                }
            }

            highRatedList.add(maxRating);
            results.add(tempMovies.get(maxPos));
            tempMovies.remove(maxPos);
            i--;
            count++;
            if (count == 50)
            {
                break;
            }
        }

        // now, display them all to the user
        for (int i = 0; i < highRatedList.size(); i++)
        {
            double rating = highRatedList.get(i);
            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + results.get(i).getTitle() + " " + rating);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);
        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRevenue()
    {
        ArrayList<Integer> highRevenueList = new ArrayList<Integer>();
        ArrayList<Movie> results = new ArrayList<Movie>();
        ArrayList<Movie> tempMovies = movies;
        int maxRev = 0;
        int count = 0;

        for (int i = 0; i < tempMovies.size(); i++)
        {
            maxRev = tempMovies.get(i).getRevenue();
            int maxPos = 0;

            for (int j = i + 1; j < tempMovies.size(); j++)
            {
                int temp = tempMovies.get(j).getRevenue();
                if (maxRev <= temp)
                {
                    maxRev = temp;
                    maxPos = j;
                }
            }

            highRevenueList.add(maxRev);
            results.add(tempMovies.get(maxPos));
            tempMovies.remove(maxPos);
            i--;
            count++;
            if (count == 50)
            {
                break;
            }
        }

        // now, display them all to the user
        for (int i = 0; i < highRevenueList.size(); i++)
        {
            double revenue = highRevenueList.get(i);
            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + results.get(i).getTitle() + " " + revenue);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);
        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}
