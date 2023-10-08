
package step.learning.ioc;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Names;
import step.learning.ioc.services.hash.HashService;
import step.learning.ioc.services.hash.Md5HashService;
import step.learning.ioc.services.hash.Sha256HashService;
import step.learning.ioc.services.random.RandomService;
import step.learning.ioc.services.random.RandomServiceV1;

public class ConfigModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(HashService.class).annotatedWith(Names.named("MD5")).to(Md5HashService.class);
        bind(HashService.class).annotatedWith(Names.named("SHA256")).to(Sha256HashService.class);

    }

    private RandomService randomService;
    @Provides
    private RandomService InjectRandomService(){
        if (randomService == null){
            randomService = new RandomServiceV1();
            randomService.seed("0");
        }
        return randomService;
    }
}

