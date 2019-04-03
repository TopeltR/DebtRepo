package ee.taltech.debty.service;

import ee.taltech.debty.entity.Debt;
import ee.taltech.debty.entity.Person;
import ee.taltech.debty.repository.DebtRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DebtServiceTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private DebtService debtService;

    @Mock
    private DebtRepository debtRepository;

    private Person person = Person.builder().firstName("Bob").lastName("Builder")
            .id(3L).build();

    @Test
    public void saveDebt_savesDebt() {
        Debt debt = new Debt();
        debt.setId(1L);
        debt.setTitle("Test debt");
        debt.setPayer(person);
        debtService.saveDebt(debt);
        verify(debtRepository, times(1)).save(debt);
    }

    @Test
    public void saveDebts_savesAllDebts() {
        Debt debt = new Debt();
        debt.setId(1L);
        debt.setTitle("Test debt");
        debt.setPayer(person);

        Debt debt1 = new Debt();
        debt.setId(2L);
        debt.setTitle("Test debt2");

        debtService.saveDebts(Arrays.asList(debt, debt1));
        assertNotNull(debt.getModifiedAt());
        verify(debtRepository).saveAll(Arrays.asList(debt, debt1));
    }

    @Test
    public void getAllDebtsByUserId_whenNoUser_returnsEmptyArrayList() {
        when(userService.getUserById(3L)).thenReturn(Optional.empty());

        List<Debt> debtList = debtService.getAllDebtsByUserId(3L);

        verify(debtRepository, never()).findAllByPersonParticipating(person);
        assertEquals(Collections.emptyList(), debtList);
    }

    @Test
    public void getAllDebtsByUserId_whenUserExisting_returnDebts() {
        when(userService.getUserById(anyLong())).thenReturn(Optional.of(person));

        Debt debt = new Debt();
        debt.setId(1L);
        debt.setTitle("Test debt");
        debt.setPayer(person);

        when(debtRepository.findAllByPersonParticipating(person)).thenReturn(Collections.singletonList(debt));
        assertEquals(1, debtService.getAllDebtsByUserId(3L).size());
    }





}
