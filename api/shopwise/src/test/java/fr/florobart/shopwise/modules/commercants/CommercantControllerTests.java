package fr.florobart.shopwise.modules.commercants;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommercantController.class)
class CommercantControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CommercantService commercantService;

    @Test
    @DisplayName("GET /commercants - doit retourner la liste de tous les commercants et un statut 200 OK")
    void shouldGetAllCommercantsSuccessfully() throws Exception {
        // Given
        List<Commercant> commercants = List.of(
            new Commercant(1L, "Curie"),
            new Commercant(2L, "Einstein")
        );
        when(commercantService.getAll()).thenReturn(commercants);

        // When & Then
        mockMvc.perform(get("/commercants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nom").value("Curie"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].nom").value("Einstein"));
    }

    @Test
    @DisplayName("GET /commercants/{id} - doit retourner le commercant correspondant et un statut 200 OK")
    void shouldGetCommercantByIdSuccessfully() throws Exception {
        // Given
        Long commercantId = 1L;
        Commercant commercant = new Commercant(commercantId, "Curie");
        when(commercantService.getById(commercantId)).thenReturn(commercant);

        // When & Then
        mockMvc.perform(get("/commercants/{id}", commercantId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(commercantId))
                .andExpect(jsonPath("$.nom").value("Curie"));
    }

    @Test
    @DisplayName("POST /commercants - doit créer un commercant et retourner 201 Created")
    void shouldCreateCommercantSuccessfully() throws Exception {
        // Given
        Commercant savedCommercant = new Commercant(1L, "Curie");
        when(commercantService.create(any(Commercant.class))).thenReturn(savedCommercant);

        String requestBody = """
            {
                "nom": "Curie"
            }
        """;

        // When & Then
        mockMvc.perform(post("/commercants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nom").value("Curie"));
    }

    @Test
    @DisplayName("PUT /commercants/{id} - doit mettre à jour le commercant et retourner 200 OK")
    void shouldUpdateCommercantSuccessfully() throws Exception {
        // Given
        Long commercantId = 1L;
        Commercant updatedCommercant = new Commercant(commercantId, "Curie");
        when(commercantService.update(eq(commercantId), any(Commercant.class))).thenReturn(updatedCommercant);

        String requestBody = """
            {
                "nom": "Curie"
            }
        """;

        // When & Then
        mockMvc.perform(put("/commercants/{id}", commercantId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(commercantId))
                .andExpect(jsonPath("$.nom").value("Curie"));
    }

    @Test
    @DisplayName("DELETE /commercants/{id} - doit supprimer le commercant et retourner 204 No Content")
    void shouldDeleteCommercantSuccessfully() throws Exception {
        // Given
        Long commercantId = 1L;
        doNothing().when(commercantService).delete(commercantId);

        // When & Then
        mockMvc.perform(delete("/commercants/{id}", commercantId))
                .andExpect(status().isNoContent());

        verify(commercantService).delete(commercantId);
    }
}
