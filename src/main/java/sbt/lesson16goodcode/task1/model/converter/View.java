package sbt.lesson16goodcode.task1.model.converter;

import java.util.List;

/**
 * Created by Артём on 19.11.2016.
 */
public interface View {

    void addItem(View item);

    void removeItem(View item);

    List<View> getItems();

    StringBuilder toStringBuilder();

}
