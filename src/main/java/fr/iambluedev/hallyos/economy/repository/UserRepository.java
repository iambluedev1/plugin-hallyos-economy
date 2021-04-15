package fr.iambluedev.hallyos.economy.repository;

import fr.iambluedev.hallyos.economy.model.User;
import fr.iambluedev.pine.api.field.IField;
import fr.iambluedev.pine.api.field.IFields;
import xyz.anana.pine.core.field.Field;
import xyz.anana.pine.core.table.Table;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UserRepository extends Table {

    public UserRepository() {
        super("hallyos_users");
    }

    public boolean exist(String name) {
        return this.exist("user_id", Collections.singletonList(new Field("user_login", name)));
    }

    public void save(User user) {
        if (user.getId() == null) {
            this.insert(Arrays.asList(
                    new Field("user_login", user.getLogin()),
                    new Field("user_money", user.getMoney()),
                    new Field("created_at", user.getAt())
            ));
        } else {
            this.update(Arrays.asList(
                    new Field("user_login", user.getLogin()),
                    new Field("user_money", user.getMoney())
            ), new Field("user_id", user.getId()));
        }
    }

    public User get(String login) {
        IFields retrieved = this.select(Arrays.asList("user_login", "user_id", "user_money", "created_at"), new Field("user_login", login));
        List<IField> fields = retrieved.getFields();
        User user = new User();

        for (IField tmp : fields) {
            Field field = (Field) tmp;
            if (field.getName().equals("user_login")) {
                user.setLogin((String) field.getValue());
            } else if (field.getName().equals("user_money")) {
                user.setMoney((Integer) field.getValue());
            } else if (field.getName().equals("user_id")) {
                user.setId((Integer) field.getValue());
            } else if (field.getName().equals("created_at")) {
                user.setAt(Integer.toUnsignedLong((Integer) field.getValue()));
            }
        }

        if (user.getId() == null) {
            user.setLogin(login);
            user.setMoney(0);
        }

        return user;
    }
}
