package johnlittletable

class BootStrap {

    def init = { servletContext ->
        5.times { num ->
            new BankAccount(name: "Bank Account ${num + 1}").save()
        }
    }
    def destroy = {
    }
}
