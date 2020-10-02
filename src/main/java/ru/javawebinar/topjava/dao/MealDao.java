package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.dao.mock.DataSource;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.List;


public class MealDao {
    private DataSource dataSource = DataSource.getInstance();
    public List<Meal> getAllMeal(){
        return dataSource.getMeals();
    }
    public List<MealTo> getAllMealToWithFilter(){
        return dataSource.getMealToWithFilter();
    }
    public List<MealTo> getAllMealTo(){
        return dataSource.getMealsTo();
    }
}
