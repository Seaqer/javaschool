package lesson6.DynamicProxies;

import java.lang.reflect.Method;

/**
 * Created by Артём on 06.10.2016.
 */
public class Signature {
    Method method;
    String args;

    Signature(Method method, String args) {
        this.method = method;
        this.args = args;
    }

    public boolean equals(Object obj) {
        boolean result;

        if (obj != null && obj instanceof Signature) {
            Signature signature = (Signature) obj;

            if (this == signature) {
                result = true;
            } else {
                result = method.equals(signature.method) && args.equals(args);
            }
        } else {
            result = false;
        }
        return result;
    }

    public int hashCode() {
        return method.hashCode()+args.hashCode();
    }
}
