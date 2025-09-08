package by.samtsov;

import by.samtsov.service.MainService;
import by.samtsov.util.Logger;


public class Main {
    public static final Logger LOG = new Logger();

    public static void main(String[] args) {
        try {
            MainService mainService = new MainService();
            mainService.process(args);

            LOG.finilize();
        }catch (Exception e) {
            System.out.println("Unexpected error");
        }
    }
}