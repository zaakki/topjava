package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;


public interface MealDao {
    Meal save(Meal meal);

    void delete(int id);

    Meal get(int id);

    Collection<Meal> getAll();
}
