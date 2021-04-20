package io.github.scifi9902.bria.utils.command.data;

import io.github.scifi9902.bria.utils.command.annotation.Command;
import lombok.Getter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CommandData {

    private final Command command;

    private final Object object;

    private final Method method;

    private final List<SubCommandData> subCommands = new ArrayList<>();

    public CommandData(Object object, Method method) {
        this.object = object;
        this.method = method;
        this.command = this.method.getAnnotation(Command.class);
    }
}
