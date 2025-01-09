package main.filter;

public interface AbstractFilter<Entity> {
        boolean apply(Entity entity);
}