package main;

import dsl.SensorDSL;

import java.io.File;

/**
 * Created by Matthieu on 29/01/2018.
 */
public class GroovsensorMain {
    public static void main(String[] args) {
        SensorDSL dsl = new SensorDSL();
        if(args.length > 0) {
            dsl.eval(new File(args[0]));
        } else {
            System.out.println("/!\\ Missing arg: Please specify the path to a Groovy script file to execute");
        }
    }
}

