package me.aaronfulton.debug;

import me.aaronfulton.debug.logger.AsciiComponent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestLogger {

    @Test
    public void testBasicColorComponent() {
        // Create component with red text color
        AsciiComponent basic_colorComponent = AsciiComponent.text("this text is red").color(AsciiComponent.Color.RED);
        String basic_colorString = AsciiComponent.Color.RED.getCode() + "this text is red" + AsciiComponent.ANSI_RESET;

        assertEquals(basic_colorString, basic_colorComponent.build());
    }

    @Test
    public void testBasicBackgroundColorComponent() {
        // Create component with red background
        AsciiComponent basic_bgColorComponent = AsciiComponent.text("this text has a red background").bgcolor(AsciiComponent.BackgroundColor.RED);
        String basic_bgColorString = AsciiComponent.BackgroundColor.RED.getCode() + "this text has a red background" + AsciiComponent.ANSI_RESET;

        assertEquals(basic_bgColorString, basic_bgColorComponent.build());
    }


    @Test
    public void testComplexComponent() {
        // Create a complex component with different styles
        AsciiComponent complexComponent = AsciiComponent.text("this is a test of the ascii component").color(AsciiComponent.Color.RED);
        complexComponent.add(AsciiComponent.text(" this should be able to do all types of things like ")
                .color(AsciiComponent.Color.RED)
                .bgcolor(AsciiComponent.BackgroundColor.RED));
        complexComponent.add(AsciiComponent.text("background colors")
                .bgcolor(AsciiComponent.BackgroundColor.CYAN)
                .color(AsciiComponent.Color.RED));
        complexComponent.add(AsciiComponent.text(" and ")
                .bgcolor(AsciiComponent.BackgroundColor.BLUE));
        complexComponent.add(AsciiComponent.text("formatting")
                .format(AsciiComponent.Format.BOLD));

        String complexString = "<?[31mthis is a test of the ascii component?[0m?[41m?[31m this should be able to do all types of things like ?[0m?[31m?[46mbackground colors?[0m?[44m and ?[0m?[1mformatting?[0m>";

                assertEquals(complexString, complexComponent.build());
    }

    @Test
    public void addComponents() {
        // Create two components and add them
        AsciiComponent colorComponent = AsciiComponent.text("this text is red").color(AsciiComponent.Color.RED);
        AsciiComponent bgComponent = AsciiComponent.text("this text has a red background").bgcolor(AsciiComponent.BackgroundColor.RED);

        colorComponent.add(bgComponent);

        String colorString =
                AsciiComponent.Color.RED.getCode() + "this text is red" + AsciiComponent.ANSI_RESET +
                        AsciiComponent.BackgroundColor.RED.getCode() + "this text has a red background" + AsciiComponent.ANSI_RESET;
        assertEquals(colorString, colorComponent.build());
    }
}
