package lesson6.Reflection.x;

import lesson6.Reflection.Component;
import lesson6.Reflection.D;

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
