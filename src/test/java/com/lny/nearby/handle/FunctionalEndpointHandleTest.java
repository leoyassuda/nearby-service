package com.lny.nearby.handle;

import com.lny.nearby.NearbyApplication;
import com.lny.nearby.document.Place;
import com.lny.nearby.handler.PlaceHandler;
import com.lny.nearby.repository.PlaceRepository;
import com.lny.nearby.repository.PlaceRepositoryCustom;
import com.lny.nearby.router.PlaceRouter;
import com.lny.nearby.service.AsyncService;
import com.lny.nearby.service.PlaceService;
import com.lny.nearby.validator.PlaceRequestValidator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@Disabled("For some reason, validation bean does not working in webflux functional handler.")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = NearbyApplication.class)
@ContextConfiguration(classes = {PlaceRouter.class, PlaceHandler.class})
@Import(PlaceRequestValidator.class)
@ActiveProfiles("test")
public class FunctionalEndpointHandleTest {
    //TODO: Find some solution about the validation bean error in test.

    @MockBean
    PlaceRepository placeRepository;

    @MockBean
    PlaceRepositoryCustom placeRepositoryCustom;

    @Autowired
    private PlaceRouter placeRouter;

    @MockBean
    private AsyncService asyncService;

    @MockBean
    private PlaceService placeService;

    @Autowired
    private WebTestClient webTestClient;

    private static final String FIND_PLACE_PATH = "/places";
    private static final String STATISTICS_PATH = "/statistics";

    @Test
    public void testFindPlace() {
        Place place = new Place();
        place.setDistance((double) 10);
        place.setName("Cafe");
        place.setAddress("Av. Test, 100 - Centro");
        List<Place> placeList = new ArrayList<>();
        placeList.add(place);
        Flux<Place> placeFlux = Flux.fromIterable(placeList);

        when(placeService.findPlace((double) -10, (double) -20, "testeMock", null)).thenReturn(placeFlux);
        webTestClient.get()
                .uri("/places?keyword=starbucks&latitude=-23.577011&longitude=-46.687090")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Place.class)
                .value(userResponse -> {
                            Assertions.assertThat(userResponse.getDistance()).isEqualTo((double) 10);
                            Assertions.assertThat(userResponse.getName()).isEqualTo("Cafe");
                            Assertions.assertThat(userResponse.getAddress()).isEqualTo("Av. Test, 100 - Centro");
                        }
                );
    }

    @Test
    public void whenRequestingPlaceWithInvalidBody_thenObtainBadRequest() {
        Place place = new Place();
        place.setDistance((double) 10);
        place.setName("Cafe");
        place.setAddress("Av. Test, 100 - Centro");
        List<Place> placeList = new ArrayList<>();
        placeList.add(place);

        WebTestClient.ResponseSpec response = webTestClient.get()
                .uri(FIND_PLACE_PATH)
                .exchange();

        response.expectStatus().isBadRequest();
    }

}
