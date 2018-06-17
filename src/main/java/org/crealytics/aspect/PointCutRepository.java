package org.crealytics.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PointCutRepository {
    /**
     * A join point is in the Rest layer.
     * if the method is defined in a type within the org.crealytics.controller.*Controller
     * package or any sub-package under that then {@link Aspect} using this {@link Pointcut} will run
     */
    @Pointcut("within(org.crealytics.controller.*Controller)")
    public void restLayer() {}

    /**
     * A join point is in the service layer if the method is defined
     * if the method is defined in a type within the org.crealytics.service.*ServiceImpl
     * package or any sub-package under that then {@link Aspect} using this {@link Pointcut} will run
     */
    @Pointcut("within(org.crealytics.service.*)")
    public void serviceLayer() {}

    /**
     * A join point is in the service layer if the method is defined
     * if the method is defined in a type within the org.crealytics.service.*ServiceImpl
     * package or any sub-package under that then {@link Aspect} using this {@link Pointcut} will run
     */
}
