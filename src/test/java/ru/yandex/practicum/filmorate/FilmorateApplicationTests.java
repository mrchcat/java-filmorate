package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;


@SpringBootTest
class FilmorateApplicationTests {

    FilmController filmController;

    @BeforeEach
    void init(){
        filmController=new FilmController();
        System.out.println("FC");
    }

    @Test
    void contextLoads() {
        FilmController filmController=new FilmController();

    }

}
