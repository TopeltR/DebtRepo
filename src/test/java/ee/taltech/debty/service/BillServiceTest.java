package ee.taltech.debty.service;

import ee.taltech.debty.entity.Bill;
import ee.taltech.debty.entity.BillPayment;
import ee.taltech.debty.entity.Person;
import ee.taltech.debty.repository.BillPaymentRepository;
import ee.taltech.debty.repository.BillRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class BillServiceTest {
    @Mock
    private BillRepository billRepository;
    @Mock
    private BillPaymentRepository billPaymentRepository;

    @InjectMocks
    private BillService billService;

    @Test
    public void saveBill_shouldSaveAllBillPayments() {
        Person person = Person.builder().firstName("Ingmar").lastName("Liibert").email("ingmar@liibert").build();
        List<BillPayment> billPayments = Collections.singletonList(BillPayment.builder().id(1L).sum(BigDecimal.TEN).person(person).build());
        Bill bill = Bill.builder().billPayments(billPayments).description("description").buyer(person).id(2L).build();

        when(billRepository.save(bill)).thenReturn(bill);

        Bill returnedBill = billService.saveBill(bill);

        verify(billPaymentRepository).saveAll(billPayments);
        assertEquals(bill, returnedBill);
    }
}
