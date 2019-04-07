package ee.taltech.debty.service;

import ee.taltech.debty.entity.Bill;
import ee.taltech.debty.repository.BillPaymentRepository;
import ee.taltech.debty.repository.BillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BillService {

    private final BillRepository billRepository;
    private final BillPaymentRepository billPaymentRepository;

    Bill saveBill(Bill bill) {
        billPaymentRepository.saveAll(bill.getBillPayments());
        return billRepository.save(bill);
    }

    void deleteBillById(Long id) {
        billRepository.findById(id).ifPresent(billRepository::delete);
    }
}
