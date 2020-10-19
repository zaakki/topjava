package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))

public class MealServiceTest {
    static {
        SLF4JBridgeHandler.install();
    }
    @Autowired
    private MealService service;
//5.2 Сделать тесты на чужую еду (delete, get, update) с тем, чтобы получить NotFoundException.
    @Test
    public void get() {
        Meal meal = service.get(MEAL_ID,USER_ID);
        assertMatch(service.get(MEAL_ID,USER_ID), meal);
    }
    @Test(expected = NotFoundException.class)
    public void getSomeoneElseFood(){
        service.get(MEAL_ID,ADMIN_ID);
    }

    @Test
    public void delete() {
        service.delete(MEAL_ID,USER_ID);
        assertMatch(service.getAll(USER_ID),MEAL1,MEAL2,MEAL3,MEAL4,MEAL5,MEAL6);
    }
    @Test(expected = NotFoundException.class)
    public void deleteSomeoneElseFood(){
        service.delete(MEAL_ID,ADMIN_ID);
    }

    @Test
    public void getBetweenInclusive() {
        assertMatch(service.getBetweenInclusive(
                LocalDate.of(2020,Month.JANUARY,30),
                LocalDate.of(2020,Month.JANUARY,30),USER_ID),MEAL1,MEAL2,MEAL3,MEAL4);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER_ID);
        assertMatch(all,ALL_MEALS);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated,USER_ID);
        assertMatch(service.get(MEAL1.getId(),USER_ID),updated);
    }
    @Test(expected = NotFoundException.class)
    public void updateSomeoneElseFood(){
        Meal updated = getUpdated();
        service.update(updated,ADMIN_ID);
    }

    @Test
    public void create() {
        Meal newMeal = createNew();
        service.create(newMeal,USER_ID);
        assertMatch(service.getAll(USER_ID),MEAL1,MEAL2,MEAL3,MEAL4,MEAL5,MEAL6,MEAL7,newMeal);
    }
    //    public static final Meal MEAL3 = new Meal(MEAL_ID + 4, of(2020, Month.JANUARY, 30, 10, 0), "Breakfast", 300);
    @Test
    public void duplicateDateTimeCreate() throws Exception{
        assertThrows(DataAccessException.class,()->
                service.create((new Meal(null, LocalDateTime.of(2020,Month.JANUARY,30,10,0),"breakfast",500)),USER_ID));
    }
}