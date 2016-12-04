package sbt.lesson6.reflection;

/**
 * Created by svetlana on 25.09.16.
 */
@Component
public class BImpl implements B{

    @Autowired
    private A a;

    @Override
    public String getSomeData() {
        return "BImpl";
    }
}
