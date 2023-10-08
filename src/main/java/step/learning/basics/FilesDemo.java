package step.learning.basics;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Scanner;

public class FilesDemo {
    public void run(){
        String filename = "test.txt";
        try(OutputStream writer = new FileOutputStream(filename)) {
            writer.write("Hello world".getBytes());
        }
        catch (IOException ex){
            System.err.println(ex.getMessage());
        }
        StringBuilder sb = new StringBuilder();
        try (InputStream reader = new FileInputStream(filename)){
            int c;
            while ((c = reader.read()) != -1) {
                sb.append((char) c);
            }
            System.out.println(sb.toString());
        }
        catch(IOException ex){
            System.err.println(ex.getMessage());
        }

        System.out.println("____________________________________________");
        ByteArrayOutputStream byteBuilder = new ByteArrayOutputStream(4096);
        byte[] buf = new byte[512];
        try (InputStream reader = new BufferedInputStream(new FileInputStream((filename)))) {
            int cnt;
            while ((cnt = reader.read(buf)) > 0) {
                byteBuilder.write(buf , 0 , cnt);
            }
            String content = new String(byteBuilder.toByteArray(), StandardCharsets.UTF_16) ;
            System.out.println(content);
        }
        catch(IOException ex){
            System.err.println(ex.getMessage());
        }

        try( InputStream reader = new FileInputStream(filename);
             Scanner scanner = new Scanner(reader)) {
            while (scanner.hasNext()){
                System.out.println(scanner.next());
            }
        }
        catch(IOException ex){
            System.err.println(ex.getMessage());
        }

        Random random = new Random();

        System.out.println("Your name: paha");
        System.out.println("Hellow paha\n");

    }
    public void run2(){
        File dir = new File("./uploads");
        if (dir.exists()){
            if(dir.isDirectory())
            {
                System.out.printf("'%s' already exists%n", dir.getName());
            }
            else{
                System.out.printf("'%s' already exists BUT NOT AS DIRECTORY%n", dir.getName());
            }
        }
        else{
            if (dir.mkdir()){
                System.out.printf("Directory %s created%n", dir.getName());
            }
            else{
                System.out.printf("Directory %s already exists%n", dir.getName());
            }
        }
        File file = new File("./uploads/whitelist.txt");
        if (file.exists()){
            if(dir.isFile())
            {
                System.out.printf("'%s' already exists%n", dir.getName());
            }
            else{
                System.out.printf("'%s' already exists BUT NOT AS FILE%n", dir.getName());
            }
        }
        else{
            try {
                if(file.createNewFile()){
                    System.out.printf("File %s created%n", dir.getName());
                }
                else{
                    System.out.printf("File %s creation error%n", dir.getName());
                }
            }
            catch (IOException ex){
                System.err.println(ex.getMessage());
            }
        }
    }
    public void run1(){
        File dir = new File("./");
        if(dir.exists()){
            System.out.println("Path exists");
        }
        else{
            System.out.println("Path does not exist");
        }
        System.out.printf("Path is %s %n",
                dir.isDirectory() ? "directory" : "file");
        System.out.println(dir.getAbsolutePath());
        for ( String filename : dir.list()){ // dir.list() - только имена (String)
            System.out.println( filename); // dir.listFiles() - объекты (File)
        }
    }
}
