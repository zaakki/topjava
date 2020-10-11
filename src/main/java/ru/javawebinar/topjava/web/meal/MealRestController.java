package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceImp;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.Collection;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;
@Controller
public class MealRestController  {
    protected final Logger log = getLogger(getClass());
    private MealService service;
    @Autowired
    public MealRestController(MealService service){
        this.service = service;
    }

    public void delete(int id){
        int userId = authUserId();
        log.info("delete {}", id);
        service.delete(id,userId);
    }
    public void update(Meal meal, int id){
        int userId = authUserId();
        ValidationUtil.assureIdConsistent(meal,id);
        log.info("update {} with id = {} ", meal, userId);
        service.update(meal, userId);

    }
    public Meal create(Meal meal){
        int userId = authUserId();
        ValidationUtil.checkNew(meal);
        log.info("create {}", meal);
        return service.create(meal, userId);
    }
    public List<MealTo> getAll(){
        int userId = authUserId();
        log.info("get all for user id = {}", userId);
        return MealsUtil.getTos(service.getAll(userId),MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }
    public Meal get(int id){
        int userId = authUserId();
        log.info("get meal by id = {} for user {}", id, userId);
        return service.get(id,userId);

    }

}