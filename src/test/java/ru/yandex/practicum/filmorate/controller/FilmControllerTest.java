package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.yandex.practicum.filmorate.dto.film.NewFilmRequestDTO;
import ru.yandex.practicum.filmorate.dto.film.UpdateFilmRequestDTO;
import ru.yandex.practicum.filmorate.dto.genre.GenreFromNewOrUpdateFilmRequestDTO;
import ru.yandex.practicum.filmorate.dto.rating.RatingFromNewOrUpdateFilmRequestDTO;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@ExtendWith(MockitoExtension.class)
@Slf4j
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
    @DisplayName("GET/films")
    void getAllTest() throws Exception {
        mockMvc.perform(get("/films"));
        Mockito.verify(filmService).getAllFilms();
    }

    @Test
    @DisplayName("POST/films")
    void addFilmTest() throws Exception {
        NewFilmRequestDTO dto = NewFilmRequestDTO.builder()
                .name("name")
                .description("desc")
                .releaseDate(LocalDate.of(1980, 1, 1))
                .duration(10)
                .mpa(RatingFromNewOrUpdateFilmRequestDTO.builder().id(1).build())
                .build();
        String json = objectMapper.writeValueAsString(dto);
        mockMvc.perform(post("/films").contentType("application/json").content(json));
        log.info(json);
        Mockito.verify(filmService).addFilm(dto);
    }

    @Test
    @DisplayName("PUT/films")
    void updateUserTest() throws Exception {
        UpdateFilmRequestDTO dto = UpdateFilmRequestDTO.builder()
                .id(2)
                .name("name")
                .description("dsdsd")
                .releaseDate(LocalDate.of(1980, 1, 1))
                .duration(10)
                .mpa(RatingFromNewOrUpdateFilmRequestDTO.builder().id(1).build())
                .genres(List.of(GenreFromNewOrUpdateFilmRequestDTO.builder().id(1).build()))
                .build();
        String json = objectMapper.writeValueAsString(dto);
        mockMvc.perform(put("/films").contentType("application/json").content(json));
        Mockito.verify(filmService).updateFilm(dto);
    }
}