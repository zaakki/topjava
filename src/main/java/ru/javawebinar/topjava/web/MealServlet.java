package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoImp;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private MealDao repo;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        repo = new MealDaoImp();
        log.info("init meals list");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String id = req.getParameter("id");
        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id)
                , LocalDateTime.parse(req.getParameter("dateTime"))
                , req.getParameter("description")
                , Integer.valueOf(req.getParameter("calories")));
        repo.save(meal);
        resp.sendRedirect("meals");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");
        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(req);
                repo.delete(id);
                log.info("delete meal by id " + id);
                resp.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 800) :
                        repo.get(getId(req));
                log.info("create or update new meal by id " + meal.getId());
                req.setAttribute("meal", meal);
                req.getRequestDispatcher("/createOrUpdate.jsp").forward(req, resp);
                break;
            case "all":
            default:
                log.info("show all meals");
                req.setAttribute("meals", MealsUtil.createWithExcessWithoutTime(repo.getAll(), 2000));
                req.getRequestDispatcher("/meals.jsp").forward(req, resp);
        }
    }
    private int getId(HttpServletRequest req) {
        return Integer.parseInt(Objects.requireNonNull(req.getParameter("id")));
    }

}
