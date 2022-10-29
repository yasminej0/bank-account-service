package org.sid.bankaccountservice.web;

import org.sid.bankaccountservice.dto.BankAccountRequestDTO;
import org.sid.bankaccountservice.dto.BankAccountResponseDTO;
import org.sid.bankaccountservice.entities.BankAccount;
import org.sid.bankaccountservice.enums.repositories.BankAccountRepository;
import org.sid.bankaccountservice.mappers.AccountMapper;
import org.sid.bankaccountservice.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class AccountRestController {
    private BankAccountRepository bankAccountRepository;
    private AccountService accountService;
    private AccountMapper accountMapper;
    public AccountRestController(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.accountService=accountService;
        this.accountMapper=accountMapper;
    }
    @GetMapping("/bankAccounts")
    public List<BankAccount> bankAccounts(){
        return  bankAccountRepository.findAll();
    }
    @GetMapping("/bankAccounts/{id}")
    public BankAccount bankAccount(@PathVariable String id){
        return  bankAccountRepository.findById(id)
                .orElseThrow(()->new RuntimeException(String.format("Account %s is not found ",id)));
    }

    @PostMapping("/bankAccounts")
    public BankAccountResponseDTO save(BankAccountRequestDTO requestDTO)
    {
        return accountService.addAccount(requestDTO);

    }
    @PostMapping("/bankAccounts/{id}")
    public BankAccount update(@PathVariable String id, @RequestBody BankAccount bankAccount)
    {
        BankAccount account=bankAccountRepository.findById(id).orElseThrow();
        if(account.getBalance()!=null) account.setBalance(bankAccount.getBalance());
        if(account.getCurrency()!=null)account.setCurrency(bankAccount.getCurrency());
        if(account.getType()!=null)account.setType(bankAccount.getType());
        if(account.getCreatedAt()!=null)account.setCreatedAt(new Date());
            return bankAccountRepository.save(account);
    }
    @DeleteMapping("/bankAcounts/{id}")
    public void deleteBankAccount(@PathVariable String id)
    {
         bankAccountRepository.deleteById(id);
    }
}
