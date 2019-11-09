package f.b.o.g;

import java.lang.annotation.*;
import f.b.o.c.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface c$b {
    String name();
    
    String description();
    
    c category();
    
    boolean defaultOn() default false;
    
    boolean defaultIsVisible() default true;
}
