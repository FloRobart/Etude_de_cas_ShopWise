package fr.florobart.shopwise.modules.rendezvous;

import java.sql.Timestamp;
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

@WebMvcTest(RendezvousController.class)
class RendezvousControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RendezvousService rendezvousService;

    private final Timestamp now = new Timestamp(System.currentTimeMillis());

    @Test
    @DisplayName("GET /rendez-vous - doit retourner la liste de tous les rendez-vous et un statut 200 OK")
    void shouldGetAllRendezvousSuccessfully() throws Exception {
        // Given
        
        List<Rendezvous> rendezvousList = List.of(
            new Rendezvous(1L, 1L, 1L, now, "Consultation", "Confirmé"),
            new Rendezvous(2L, 2L, 2L, now, "Rendez-vous", "Annulé")
        );
        when(rendezvousService.getAll()).thenReturn(rendezvousList);

        // When & Then
        mockMvc.perform(get("/rendez-vous"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].clientId").value(1L))
                .andExpect(jsonPath("$[0].commercantId").value(1L))
                .andExpect(jsonPath("$[0].appointmentDate").value(now.toInstant().toString()))
                .andExpect(jsonPath("$[0].serviceType").value("Consultation"))
                .andExpect(jsonPath("$[0].status").value("Confirmé"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].clientId").value(2L))
                .andExpect(jsonPath("$[1].commercantId").value(2L))
                .andExpect(jsonPath("$[1].appointmentDate").value(now.toInstant().toString()))
                .andExpect(jsonPath("$[1].serviceType").value("Rendez-vous"))
                .andExpect(jsonPath("$[1].status").value("Annulé"));

    }

    @Test
    @DisplayName("GET /rendez-vous/{id} - doit retourner le rendez-vous correspondant et un statut 200 OK")
    void shouldGetRendezvousByIdSuccessfully() throws Exception {
        // Given
        Long rendezvousId = 1L;
        Rendezvous rendezvous = new Rendezvous(rendezvousId, 1L, 1L, now, "Consultation", "Confirmé");
        when(rendezvousService.getById(rendezvousId)).thenReturn(rendezvous);

        // When & Then
        mockMvc.perform(get("/rendez-vous/{id}", rendezvousId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(rendezvousId))
                .andExpect(jsonPath("$.clientId").value(1L))
                .andExpect(jsonPath("$.commercantId").value(1L))
                .andExpect(jsonPath("$.appointmentDate").value(now.toInstant().toString()))
                .andExpect(jsonPath("$.serviceType").value("Consultation"))
                .andExpect(jsonPath("$.status").value("Confirmé"));
    }

    @Test
    @DisplayName("POST /rendez-vous - doit créer un rendez-vous et retourner 201 Created")
    void shouldCreateRendezvousSuccessfully() throws Exception {
        // Given
        Rendezvous savedRendezvous = new Rendezvous(1L, 1L, 1L, now, "Consultation", "Confirmé");
        when(rendezvousService.create(any(Rendezvous.class))).thenReturn(savedRendezvous);

        String requestBody = """
            {
                "clientId": 1,
                "commercantId": 1,
                "appointmentDate": "%s",
                "serviceType": "Consultation",
                "status": "Confirmé"
            }
        """.formatted(now.toInstant().toString());

        // When & Then
        mockMvc.perform(post("/rendez-vous")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.clientId").value(1L))
                .andExpect(jsonPath("$.commercantId").value(1L))
                .andExpect(jsonPath("$.appointmentDate").value(now.toInstant().toString()))
                .andExpect(jsonPath("$.serviceType").value("Consultation"))
                .andExpect(jsonPath("$.status").value("Confirmé"));
    }

    @Test
    @DisplayName("PUT /rendez-vous/{id} - doit mettre à jour le rendez-vous et retourner 200 OK")
    void shouldUpdateRendezvousSuccessfully() throws Exception {
        // Given
        Long rendezvousId = 1L;
        Rendezvous updatedRendezvous = new Rendezvous(rendezvousId, 1L, 1L, now, "Consultation", "Confirmé");
        when(rendezvousService.update(eq(rendezvousId), any(Rendezvous.class))).thenReturn(updatedRendezvous);

        String requestBody = """
            {
                "clientId": 1,
                "commercantId": 1,
                "appointmentDate": "%s",
                "serviceType": "Consultation",
                "status": "Confirmé"
            }
        """.formatted(now.toInstant().toString());

        // When & Then
        mockMvc.perform(put("/rendez-vous/{id}", rendezvousId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(rendezvousId))
                .andExpect(jsonPath("$.clientId").value(1L))
                .andExpect(jsonPath("$.commercantId").value(1L))
                .andExpect(jsonPath("$.appointmentDate").value(now.toInstant().toString()))
                .andExpect(jsonPath("$.serviceType").value("Consultation"))
                .andExpect(jsonPath("$.status").value("Confirmé"));
    }

    @Test
    @DisplayName("DELETE /rendez-vous/{id} - doit supprimer le rendez-vous et retourner 204 No Content")
    void shouldDeleteRendezvousSuccessfully() throws Exception {
        // Given
        Long rendezvousId = 1L;
        doNothing().when(rendezvousService).delete(rendezvousId);

        // When & Then
        mockMvc.perform(delete("/rendez-vous/{id}", rendezvousId))
                .andExpect(status().isNoContent());

        verify(rendezvousService).delete(rendezvousId);
    }
}
