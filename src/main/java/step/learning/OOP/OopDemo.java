package step.learning.OOP;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;

public class OopDemo {

    public void run() {
        Library library = new Library();

        List<Literature> allLiterature = library.getFunds();

        List<Literature> printable = Literature.getPrintable(allLiterature);
        System.out.println("Printable literature:");
        printable.forEach(lit -> System.out.println(lit.getCard()));

        List<Literature> nonPrintable = Literature.getNonPrintable(allLiterature);
        System.out.println("Non-Printable literature:");
        nonPrintable.forEach(lit -> System.out.println(lit.getCard()));

        List<Literature> multiple = Literature.getMultiple(allLiterature);
        System.out.println("Multiple copies literature:");
        multiple.forEach(lit -> System.out.println(lit.getCard()));

        List<Literature> single = Literature.getSingle(allLiterature);
        System.out.println("Single copy literature:");
        single.forEach(lit -> System.out.println(lit.getCard()));
    }

    public void run6() {
        Library library = new Library() ;
        try {
            library.load();
        }
        catch( RuntimeException ex ) {
            System.err.println( ex.getMessage() ) ;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        library.printAllCards() ;
    }
    public void run3() {
        String str = "{\"author\": \"D. Knuth\", \"title\": \"Art of programming\" }" ;
        JsonObject literatureObject = JsonParser.parseString( str ).getAsJsonObject() ;
        Literature literature = null ;
        if( literatureObject.has( "author" ) ) {
            literature = new Book(
                    literatureObject.get( "title" ).getAsString(),
                    literatureObject.get( "author" ).getAsString()
            ) ;
        }
        else if( literatureObject.has( "number" ) ) {
            literature = new Journal(
                    literatureObject.get( "title" ).getAsString(),
                    literatureObject.get( "number" ).getAsInt()
            ) ;
        }
        else if( literatureObject.has( "date" ) ) {
            try{
                literature = new Newspaper(
                        literatureObject.get( "title" ).getAsString(),
                        literatureObject.get( "date" ).getAsString()
                ) ;
            }
            catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println( literature.getCard() ) ;
    }

    public void run2() {
        Gson gson = new Gson() ;
        String str = "{\"author\": \"D. Knuth\", \"title\": \"Art of programming\" }" ;
        Book book = gson.fromJson( str, Book.class ) ;
        System.out.println( book.getCard() ) ;
        System.out.println( gson.toJson( book ) ) ;
        book.setAuthor( null ) ;
        System.out.println( gson.toJson( book ) ) ;

        Gson gson2 = new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        System.out.println( gson2.toJson( book ) ) ;

        try(
                InputStream bookStream =
                        this.getClass()
                                .getClassLoader()
                                .getResourceAsStream("book.json");
                InputStreamReader bookReader =
                        new InputStreamReader(
                                Objects.requireNonNull( bookStream ) )
        ) {
            book = gson.fromJson( bookReader, Book.class ) ;
            System.out.println( book.getCard() ) ;
        }
        catch( IOException ex ) {
            System.err.println( ex.getMessage() ) ;
        }
    }

    public void run1() {
        Library library = new Library() ;
        try {
            library.add( new Book("D. Knuth", "Art of programming" ) ) ;
            library.add( new Newspaper("Daily Mail", "2023-09-25" ) ) ;
            library.add( new Journal("Quantum Mechanics", 157 ) ) ;
            library.add( new Book("Richter", "Platform .NET" ) ) ;
            library.add( new Newspaper("Washington Post", "2023-09-25" ) ) ;
            library.add( new Journal("Amogus Spawning", 32 ) ) ;
            library.save() ;
        }
        catch( Exception ex ) {
            System.err.println( "Literature creation error: " + ex.getMessage() ) ;
        }
        library.printAllCards() ;

        System.out.println("--------- COPYABLE -------------- ");
        library.printCopyable() ;
        System.out.println("--------- NON COPYABLE -------------- ");
        library.printNonCopyable() ;

        System.out.println("--------- PERIODIC -------------- ");
        library.printPeriodicDuck() ;
        System.out.println("--------- NON PERIODIC -------------- ");
        library.printNonPeriodic() ;
    }
}
