package johnlittletable

import grails.gorm.services.Service

@Service(BankAccount)
interface BankAccountService {

    BankAccount get(Serializable id)

    List<BankAccount> list(Map args)

    Long count()

    void delete(Serializable id)

    BankAccount save(BankAccount bankAccount)

}