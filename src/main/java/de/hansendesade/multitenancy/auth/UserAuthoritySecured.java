package de.hansendesade.multitenancy.auth;

import de.hansendesade.multitenancy.model.Recht;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface UserAuthoritySecured {

  Recht[] value();

}
