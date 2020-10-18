package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static java.time.LocalDateTime.of;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL_ID = START_SEQ + 2;
    public static final int ADMIN_MEAL_ID = START_SEQ + 10;

    public static final Meal MEAL1 = new Meal(MEAL_ID + 6, of(2020, Month.JANUARY, 30, 19, 0), "Dinner", 800);
    public static final Meal MEAL2 = new Meal(MEAL_ID + 5, of(2020, Month.JANUARY, 30, 14, 0), "Lunch", 900);
    public static final Meal MEAL3 = new Meal(MEAL_ID + 4, of(2020, Month.JANUARY, 30, 10, 0), "Breakfast", 300);
    public static final Meal MEAL4 = new Meal(MEAL_ID + 3, of(2020, Month.JANUARY, 30, 0, 10), "Border feast", 50);
    public static final Meal MEAL5 = new Meal(MEAL_ID + 2, of(2020, Month.JANUARY, 29, 20, 0), "Dinner", 500);
    public static final Meal MEAL6 = new Meal(MEAL_ID + 1, of(2020, Month.JANUARY, 29, 14, 0), "Lunch", 100);
    public static final Meal MEAL7 = new Meal(MEAL_ID, of(2020, Month.JANUARY, 29, 10, 0), "Breakfast", 500);
    public static final Meal ADMIN_MEAL1 = new Meal(ADMIN_MEAL_ID, of(2020, Month.JANUARY, 30, 14, 0), "admin lunch", 800);
    public static final Meal ADMIN_MEAL2 = new Meal(ADMIN_MEAL_ID + 1, of(2020, Month.JANUARY, 30, 10, 0), "admin breakfast", 800);

    public static final List<Meal> ALL_MEALS = Arrays.asList(MEAL1, MEAL2, MEAL3, MEAL4, MEAL5, MEAL6, MEAL7);

    public static Meal createNew() {
        return new Meal(null, of(2020, Month.JANUARY, 1, 15, 00), "new eat", 400);
    }

    public static Meal getUpdated() {
        Meal meal = new Meal(MEAL1);
        meal.setDescription("UpdatedMeal");
        meal.setCalories(400);
        return meal;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }


}
