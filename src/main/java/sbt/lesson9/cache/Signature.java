package sbt.lesson9.cache;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * Created by Артём on 06.10.2016.
 */
public class Signature implements Serializable {
    private final String method;
    private final String args;
    private static final long serialVersionUID = 773530713475795375L;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    private Object result;

    public Signature(Method method, String args, Object result) {
        this.method = method.getName();
        this.args = args;
        this.result = result;
    }

    @Override
    public boolean equals(Object obj) {
        boolean result;

        if (obj != null && obj instanceof Signature) {
            Signature signature = (Signature) obj;
            result = this == signature || method.equals(signature.method) && args.equals(args);
        } else {
            result = false;
        }
        return result;
    }

    @Override
    public String toString() {
        return method + "{"
                + hashCode()
                + "}";
    }

    @Override
    public int hashCode() {
        int result = method.hashCode();
        result = 31 * result + args.hashCode();
        return result;
    }
}
