package sbt.lesson16goodcode.task1.model.converter.html;

import sbt.lesson16goodcode.task1.model.converter.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tag implements View {
    private final List<View> items = new ArrayList<>();
    private final String begin;
    private final String end;

    public Tag(String begin, String end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    public void addItem(View item) {
        items.add(item);
    }

    @Override
    public void removeItem(View item) {
        items.remove(item);
    }

    @Override
    public List<View> getItems() {
        return Collections.unmodifiableList(items);
    }

    @Override
    public StringBuilder toStringBuilder() {
        final StringBuilder report = new StringBuilder();

        report.append(begin);
        getItems().forEach((item) -> report.append(item.toStringBuilder()));
        report.append(end);

        return report;
    }

    @Override
    public String toString() {
        return toStringBuilder().toString();
    }
}
