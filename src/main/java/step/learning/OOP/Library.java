package step.learning.OOP;

import com.google.gson.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Library {
    private final List<Literature> funds ;

    public List<Literature> getFunds() {
        return funds;
    }
    public Library() {
        funds = new LinkedList<>() ;
    }
    public void add( Literature literature ) {
        funds.add( literature ) ;
    }
    private List<Literature> getSerializableFunds() {
        List<Literature> serializableFunds = new ArrayList<>() ;
        for( Literature literature : getFunds() ) {
            if( literature.getClass().isAnnotationPresent( Serializable.class ) ) {
                serializableFunds.add( literature ) ;
            }
        }
        return serializableFunds ;
    }
    public void save() throws IOException {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        List<Literature> serializableFunds = getSerializableFunds(); // Обновите этот метод, если необходимо
        String json = gson.toJson(serializableFunds);
    }
    public void load() throws IOException {
        Gson gson = new Gson();

        try (InputStreamReader reader = new InputStreamReader(
                Objects.requireNonNull(
                        this.getClass().getClassLoader().getResourceAsStream("library.json")
                )
        )) {
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();

            for (JsonElement jsonElement : jsonArray) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                Literature literature = parseJsonToLiterature(jsonObject);
                funds.add(literature);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Error while reading JSON: " + e.getMessage());
        }
    }

    private Literature parseJsonToLiterature(JsonObject jsonObject) throws ParseException {
        List<Class<?>> literatures = this.getSerializableClasses();
        try {
            for (Class<?> literatureClass : literatures) {
                Method isParseableFromJson = null;
                Method fromJson = null;
                for (Method method : literatureClass.getDeclaredMethods()) {
                    if (method.isAnnotationPresent(ParseChecker.class)) {
                        isParseableFromJson = method;
                    }
                    if (method.isAnnotationPresent(FromJsonParser.class)) {
                        fromJson = method;
                    }
                }
                if (isParseableFromJson != null && fromJson != null) {
                    isParseableFromJson.setAccessible(true);
                    fromJson.setAccessible(true);

                    boolean isParseable = (boolean) isParseableFromJson.invoke(null, jsonObject);
                    if (isParseable) {
                        return (Literature) fromJson.invoke(null, jsonObject);
                    }
                }
            }
        } catch (Exception e) {
            throw new ParseException("Reflection error: " + e.getMessage(), 0);
        }

        throw new ParseException("Literature type unrecognized", 0);
    }

    private List<Class<?>> getSerializableClasses() {
        List<Class<?>> literatures = new LinkedList<>() ;
        String className = Literature.class.getName() ;
        String packageName = className
                .substring( 0, className.lastIndexOf('.' ) ) ;
        String packagePath = packageName.replace(".", "/") ;
        String absolutePath = Literature.class.getClassLoader()
                .getResource(packagePath).getPath() ;
        File[] files = new File( absolutePath ).listFiles() ;
        if( files == null ) {
            throw new RuntimeException( "Class path inaccessible" ) ;
        }
        for( File file : files ) {
            if( file.isFile() ) {
                String filename = file.getName() ;
                if( filename.endsWith( ".class" ) ) {
                    String fileClassName = filename.substring( 0, filename.lastIndexOf( '.' ) ) ;
                    try {
                        Class<?> fileClass = Class.forName( packageName + "." + fileClassName ) ;
                        if( fileClass.isAnnotationPresent( Serializable.class ) ) {
                            literatures.add( fileClass ) ;
                        }
                    }
                    catch( ClassNotFoundException ignored ) {
                        continue ;
                    }
                }
            }
            else if( file.isDirectory() ) {
                continue ;
            }
        }
        return literatures ;
    }

    private Literature fromJson( JsonObject jsonObject ) throws ParseException {
        List<Class<?>> literatures = this.getSerializableClasses() ;
        try {
            for( Class<?> literature : literatures ) {
                Method isParseableFromJson = null ;
                for( Method method : literature.getDeclaredMethods() ) {
                    if( method.isAnnotationPresent( ParseChecker.class ) ) {
                        if( isParseableFromJson != null ) {
                            throw new ParseException( "Multiple ParseChecker annotations", 0 ) ;
                        }
                        isParseableFromJson = method ;
                    }
                }
                if( isParseableFromJson == null ) {
                    continue ;
                }
                isParseableFromJson.setAccessible(true);
                boolean res = (boolean) isParseableFromJson.invoke(null, jsonObject);
                if (res) {
                    Method fromJson = literature.getDeclaredMethod("fromJson", JsonObject.class);
                    fromJson.setAccessible(true);
                    return (Literature) fromJson.invoke(null, jsonObject);
                }
            }
        }
        catch( Exception ex ) {
            throw new ParseException( "Reflection error: " + ex.getMessage(), 0);
        }
        throw new ParseException( "Literature type unrecognized", 0 ) ;
    }
    public void printAllCards() {
        for( Literature literature : funds ) {
            System.out.println( literature.getCard() ) ;
        }
    }

    public void printCopyable() {
        for( Literature literature : funds ) {
            if( isCopiable( literature ) ) {
                System.out.println( literature.getCard() ) ;
            }
        }
    }
    public void printNonCopyable() {
        for( Literature literature : funds ) {
            if( ! isCopiable( literature ) ) {
                System.out.println( literature.getCard() ) ;
            }
        }
    }
    public boolean isCopiable( Literature literature ) {
        return ( literature instanceof Copyable ) ;
    }
    public void printPeriodic() {
        for( Literature literature : funds ) {
            if( isPeriodic( literature ) ) {
                Periodic litAsPeriodic = (Periodic) literature ;
                System.out.println( litAsPeriodic.getPeriod() + " " + literature.getCard() ) ;
            }
        }
    }
    public void printPeriodicDuck() {
        for( Literature literature : funds ) {
            try {
                Method getPeriodMethod = literature.getClass()
                        .getDeclaredMethod("getPeriod" ) ;

                System.out.println(
                        getPeriodMethod.invoke( literature ) +
                                " " + literature.getCard()
                ) ;
            }
            catch( Exception ignored ) {
            }
        }
    }
    public void printNonPeriodic() {
        for( Literature literature : funds ) {
            if( ! isPeriodic( literature ) ) {
                System.out.println( literature.getCard() ) ;
            }
        }
    }
    public boolean isPeriodic( Literature literature ) {
        return ( literature instanceof Periodic ) ;
    }
}