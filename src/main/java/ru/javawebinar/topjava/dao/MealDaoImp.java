package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDaoImp implements MealDao {
    private Map<Integer, Meal> repo = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);
    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if(meal.isNew()){
            meal.setId(counter.incrementAndGet());
        }
        repo.put(meal.getId(),meal);
        return meal;
    }

    @Override
    public void delete(int id) {
        repo.remove(id);
    }

    @Override
    public Meal get(int id) {
        return repo.get(id);
    }

    @Override
    public Collection<Meal> getAll() {
        return repo.values();
    }
}
