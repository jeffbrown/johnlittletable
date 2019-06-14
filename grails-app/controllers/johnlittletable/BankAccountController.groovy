package johnlittletable

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class BankAccountController {

    BankAccountService bankAccountService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond bankAccountService.list(params), model:[bankAccountCount: bankAccountService.count()]
    }

    def show(Long id) {
        respond bankAccountService.get(id)
    }

    def create() {
        respond new BankAccount(params)
    }

    def save(BankAccount bankAccount) {
        if (bankAccount == null) {
            notFound()
            return
        }

        try {
            bankAccountService.save(bankAccount)
        } catch (ValidationException e) {
            respond bankAccount.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'bankAccount.label', default: 'BankAccount'), bankAccount.id])
                redirect bankAccount
            }
            '*' { respond bankAccount, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond bankAccountService.get(id)
    }

    def update(BankAccount bankAccount) {
        if (bankAccount == null) {
            notFound()
            return
        }

        try {
            bankAccountService.save(bankAccount)
        } catch (ValidationException e) {
            respond bankAccount.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'bankAccount.label', default: 'BankAccount'), bankAccount.id])
                redirect bankAccount
            }
            '*'{ respond bankAccount, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        bankAccountService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'bankAccount.label', default: 'BankAccount'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'bankAccount.label', default: 'BankAccount'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
