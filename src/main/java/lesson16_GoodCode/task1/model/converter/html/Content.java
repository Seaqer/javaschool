package lesson16_GoodCode.task1.model.converter.html;

import lesson16_GoodCode.task1.model.converter.View;

import java.util.List;

public class Content implements View {
    private final StringBuilder stringBuilder;

    public Content(String begin, String body, String end) {
        this.stringBuilder = new StringBuilder(begin).append(body).append(end);
    }

    @Override
    public void addItem(View item) {
        throw new UnsupportedOperationException("добавление элемента в Content");
    }

    @Override
    public void removeItem(View item) {
        throw new UnsupportedOperationException("Удаление элемента из Content");
    }

    @Override
    public List<View> getItems() {
        return null;
    }

    @Override
    public StringBuilder toStringBuilder() {
        return stringBuilder;
    }

    @Override
    public String toString() {
        return stringBuilder.toString();
    }
}
