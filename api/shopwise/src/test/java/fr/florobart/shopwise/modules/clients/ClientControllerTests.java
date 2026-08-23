package fr.florobart.shopwise.modules.clients;

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

@WebMvcTest(ClientController.class)
class ClientControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClientService clientService;

    @Test
    @DisplayName("GET /clients - doit retourner la liste de tous les clients et un statut 200 OK")
    void shouldGetAllClientsSuccessfully() throws Exception {
        // Given
        List<Client> clients = List.of(
            new Client(1L, "Dupont", "Jean", "jean.dupont@example.com", "+33612345678"),
            new Client(2L, "Curie", "Marie", "marie.curie@example.com", "+33687654321")
        );
        when(clientService.getAll()).thenReturn(clients);

        // When & Then
        mockMvc.perform(get("/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nom").value("Dupont"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].nom").value("Curie"));
    }

    @Test
    @DisplayName("GET /clients/{id} - doit retourner le client correspondant et un statut 200 OK")
    void shouldGetClientByIdSuccessfully() throws Exception {
        // Given
        Long clientId = 1L;
        Client client = new Client(clientId, "Dupont", "Jean", "jean.dupont@example.com", "+33612345678");
        when(clientService.getById(clientId)).thenReturn(client);

        // When & Then
        mockMvc.perform(get("/clients/{id}", clientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(clientId))
                .andExpect(jsonPath("$.nom").value("Dupont"))
                .andExpect(jsonPath("$.prenom").value("Jean"))
                .andExpect(jsonPath("$.email").value("jean.dupont@example.com"));
    }

    @Test
    @DisplayName("POST /clients - doit créer un client et retourner 201 Created")
    void shouldCreateClientSuccessfully() throws Exception {
        // Given
        Client savedClient = new Client(1L, "Dupont", "Jean", "jean.dupont@example.com", "+33612345678");
        when(clientService.create(any(Client.class))).thenReturn(savedClient);

        String requestBody = """
            {
                "nom": "Dupont",
                "prenom": "Jean",
                "email": "jean.dupont@example.com",
                "phone": "+33612345678"
            }
        """;

        // When & Then
        mockMvc.perform(post("/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nom").value("Dupont"))
                .andExpect(jsonPath("$.prenom").value("Jean"))
                .andExpect(jsonPath("$.email").value("jean.dupont@example.com"))
                .andExpect(jsonPath("$.phone").value("+33612345678"));
    }

    @Test
    @DisplayName("PUT /clients/{id} - doit mettre à jour le client et retourner 200 OK")
    void shouldUpdateClientSuccessfully() throws Exception {
        // Given
        Long clientId = 1L;
        Client updatedClient = new Client(clientId, "Dupont", "Paul", "paul.dupont@example.com", "+33600000000");
        when(clientService.update(eq(clientId), any(Client.class))).thenReturn(updatedClient);

        String requestBody = """
            {
                "nom": "Dupont",
                "prenom": "Paul",
                "email": "paul.dupont@example.com",
                "phone": "+33600000000"
            }
        """;

        // When & Then
        mockMvc.perform(put("/clients/{id}", clientId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(clientId))
                .andExpect(jsonPath("$.prenom").value("Paul"))
                .andExpect(jsonPath("$.email").value("paul.dupont@example.com"))
                .andExpect(jsonPath("$.phone").value("+33600000000"));
    }

    @Test
    @DisplayName("DELETE /clients/{id} - doit supprimer le client et retourner 204 No Content")
    void shouldDeleteClientSuccessfully() throws Exception {
        // Given
        Long clientId = 1L;
        doNothing().when(clientService).delete(clientId);

        // When & Then
        mockMvc.perform(delete("/clients/{id}", clientId))
                .andExpect(status().isNoContent());

        verify(clientService).delete(clientId);
    }
}
