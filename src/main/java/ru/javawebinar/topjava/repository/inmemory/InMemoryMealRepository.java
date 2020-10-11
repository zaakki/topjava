package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.logging.Logger.getLogger;
import static ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository.ADMIN_ID;
import static ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository.USER_ID;
import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;
@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private final Map<Integer,Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal,USER_ID));
        save(new Meal(LocalDateTime.of(2020, Month.APRIL,25,17,30),
                "Admin diner",DEFAULT_CALORIES_PER_DAY),ADMIN_ID);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("user id{} save {}",userId, meal);
        Map<Integer,Meal> meals  =repository.computeIfAbsent(userId,ConcurrentHashMap::new);
        if(meal.isNew()){
            meal.setId(counter.incrementAndGet());
            meals.put(meal.getId(),meal);
            return meal;
        }
        return meals.computeIfPresent(meal.getId(),(id,oldMeal)->meal);
    }

    @Override
    public boolean delete(int id,int userId)
    {
        log.info("user id{} delete {}",userId, id);
        Map<Integer,Meal> meals = repository.get(userId);
        return meals != null && meals.remove(id) != null;
    }

    @Override
    public Meal get(int id,int userId)
    {
        log.info("user id{} save {}",userId, id);
        Map<Integer, Meal> meals = repository.get(userId);
        return meals.get(id) != null ? meals.get(id) : null;
    }

    @Override
    public Collection<Meal> getAll(int userId)
    {
        log.info("user id{} get all meals",userId);

        return getMealStream(userId).collect(Collectors.toList());
    }

    private Stream<Meal> getMealStream(int userId) {
        Map<Integer, Meal> meals = repository.get(userId);
        return meals == null ? Stream.empty() : meals.values().stream().sorted(Comparator.comparing(Meal::getDateTime).reversed());
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return getMealStream(userId)
                .filter(meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDateTime(),startDateTime,endDateTime))
                .collect(Collectors.toList());
    }
}

