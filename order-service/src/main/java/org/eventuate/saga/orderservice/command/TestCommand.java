package org.eventuate.saga.orderservice.command;

import io.eventuate.tram.commands.common.Command;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
public class TestCommand implements Command {

    private String testString;

    public TestCommand(String testString) {
        this.testString = testString;
    }

    public String getTestString() {
        return testString;
    }
}
