package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class FilmControllerTest {
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private MockMvc mockMvc;

    @Mock
    private FilmService filmService;

    @InjectMocks
    private FilmController filmController;

    @BeforeEach
    void setMockMvc() {
        mockMvc = MockMvcBuilders.standaloneSetup(filmController).build();
    }

    @Test
    void getAll_shouldReturnEmptyList() throws Exception {
        mockMvc.perform(get("/films"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void getAll_shouldReturnFilms() throws Exception {
        List<Film> films = List.of(film(), film());
        when(filmService.getAllFilms()).thenReturn(films);

        mockMvc.perform(get("/films"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(films)));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidFilms")
    void create_shouldResponseWithBadRequest_ifFilmIsInvalid(Film film) throws Exception {
        String json = objectMapper.writeValueAsString(film);
        mockMvc.perform(post("/films").contentType("application/json").content(json))
                .andExpect(status().isBadRequest());
        mockMvc.perform(put("/films").contentType("application/json").content(json))
                .andExpect(status().isBadRequest());
    }

    private static Stream<Arguments> provideInvalidFilms() {
        return Stream.of(
                Arguments.of(film(f -> f.setName(""))),
                Arguments.of(film(f -> f.setDescription("a long string".repeat(20)))),
                Arguments.of(film(f -> f.setReleaseDate(LocalDate.parse("1000-01-01")))),
                Arguments.of(film(f -> f.setDuration(-1)))
        );
    }

    private static Film film() {
        return Film.builder()
                .name("film")
                .description("description")
                .duration(2*60)
                .releaseDate(LocalDate.parse("2000-01-01"))
                .build();
    }

    private static Film film(Consumer<Film> consumer) {
        Film film = film();
        consumer.accept(film);
        return film;
    }
}