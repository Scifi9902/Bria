package io.github.scifi9902.bria.utils.command.data;

import io.github.scifi9902.bria.utils.command.annotation.SubCommand;
import lombok.Getter;

import java.lang.reflect.Method;

@Getter
public class SubCommandData {

    private final Object object;

    private final Method method;

    private final SubCommand subCommand;

    public SubCommandData(Object object, Method method) {
        this.object = object;
        this.method = method;
        this.subCommand = this.method.getAnnotation(SubCommand.class);
    }
}
