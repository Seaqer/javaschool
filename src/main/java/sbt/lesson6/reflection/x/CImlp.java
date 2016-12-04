package sbt.lesson6.reflection.x;

import sbt.lesson6.reflection.Component;
import sbt.lesson6.reflection.D;

/**
 * Created by svetlana on 26.09.16.
 */
@Component
public class CImlp implements D {

    @Override
    public String getSomeStr() {
        return "CImpl";
    }
}
