package step.learning;

import com.google.inject.Guice;
import com.google.inject.Injector;
import step.learning.ioc.ConfigModule;
import step.learning.ioc.IocDemo;
import step.learning.async.AsyncDemo;
import step.learning.async.TaskDemo;


public class App {
    public static void main(String[] args) {

        Guice.createInjector(new ConfigModule()).getInstance(TaskDemo.class).run();
    }
}