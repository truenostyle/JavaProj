package step.learning.ioc.services.random;

public interface RandomService {
    void seed(String iv);
    String randomHex (int charLenght);
}
