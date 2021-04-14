package bin.data.Models;

public class User extends Model{
    private String Login;
    private String Password;
    private String Firstname;
    private String Lastname;

    public User()
    {
        super();
    }
    public User(Long id){
        super(id);
    }

    public String getLogin() {
        return Login;
    }

    public User setLogin(String login) {
        Login = login;
        return this;
    }

    public String getPassword() {
        return Password;
    }

    public User setPassword(String password) {
        Password = password;
        return this;
    }

    public String getFirstname() {
        return Firstname;
    }

    public User setFirstname(String firstname) {
        Firstname = firstname;
        return this;
    }

    public String getLastname() {
        return Lastname;
    }

    public User setLastname(String lastname) {
        Lastname = lastname;
        return this;
    }
}
/*
Модель:
1. Описание сущности

DAO:
1. Возможность (1 Дао на одну сущность)

Не должно быть:
1. Транзакций
2. Напрямую работать с дао

 */