package ee.taltech.debty.service;

import ee.taltech.debty.entity.Event;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EventServiceIntegrationTest {

    @Autowired
    private EventService eventService;

    @Test
    public void saveEvent_shouldSave() {
        Event event1 = new Event();
        event1.setTitle("Kardiga rallimine");
        event1.setDescription("Saame kord kuus kokku ning vaatame, kes kõige kiirem on rajal. Teeme standardiks Laagri" +
                "kardiraja, seal hea mõnus sõita. Mul seal käpp ka sees");
        event1.setPeople(new ArrayList<>());

        Event event2 = eventService.saveEvent(event1);

        assertEquals(event1.getTitle(), event2.getTitle());
    }
}
