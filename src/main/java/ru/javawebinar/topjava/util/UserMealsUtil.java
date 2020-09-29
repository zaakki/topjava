package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
        System.out.println(onePass(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> calories = getCaloriesByDayByCycle(meals);
        List<UserMealWithExcess> mealsTo = new ArrayList<>();
        meals.forEach(meal -> {
            if (TimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime))
                mealsTo.add(createMealWithExcess(meal, calories.get(meal.getDate()) > caloriesPerDay));
        });
        return mealsTo;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> calories = getCaloriesByDayByStream(meals);
        return meals.stream()
                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime))
                .map(meal -> createMealWithExcess(meal, calories.get(meal.getDate()) > caloriesPerDay))
                .collect(toList());
    }

    private static UserMealWithExcess createMealWithExcess(UserMeal meal, boolean excess) {
        return new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }

    private static Map<LocalDate, Integer> getCaloriesByDayByCycle(List<UserMeal> meals) {
        Map<LocalDate, Integer> calories = new HashMap<>();
        meals.forEach(meal -> calories.merge(meal.getDate(), meal.getCalories(), Integer::sum));
        return calories;
    }

    private static Map<LocalDate, Integer> getCaloriesByDayByStream(List<UserMeal> meals) {
        return meals.stream()
                .collect(Collectors.groupingBy(UserMeal::getDate, Collectors.summingInt(UserMeal::getCalories)));
    }

    public static List<UserMealWithExcess> onePass(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Collection<Stream<UserMealWithExcess>> mealsTo = getStreams(meals, startTime, endTime, caloriesPerDay);
        return mealsTo.stream().flatMap(Function.identity()).collect(Collectors.toList());
    }

    private static Collection<Stream<UserMealWithExcess>> getStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        final class ExcessCollector {
            private final List<UserMeal> dailyMeal = new ArrayList<>();
            private int dailyCalories;

            private void accumulates(UserMeal meal) {
                dailyCalories += meal.getCalories();
                if (TimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime)) {
                    dailyMeal.add(meal);
                }
            }

            public ExcessCollector collector(ExcessCollector ex) {
                this.dailyCalories += ex.dailyCalories;
                this.dailyMeal.addAll(ex.dailyMeal);
                return this;
            }

            private Stream<UserMealWithExcess> finish() {
                final boolean excess = dailyCalories > caloriesPerDay;
                return dailyMeal.stream().map((meal -> createMealWithExcess(meal, excess)));
            }
        }
        return meals.stream()
                .collect(Collectors.groupingBy(UserMeal::getDate,
                        Collector.of(ExcessCollector::new, ExcessCollector::accumulates, ExcessCollector::collector, ExcessCollector::finish))
                ).values();
    }
}