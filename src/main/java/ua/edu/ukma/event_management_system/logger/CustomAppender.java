package ua.edu.ukma.event_management_system.logger;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.Layout;
import lombok.NoArgsConstructor;
import org.slf4j.LoggerFactory;
import lombok.AllArgsConstructor;

import static java.lang.System.*;

@NoArgsConstructor
@AllArgsConstructor
public class CustomAppender extends AppenderBase<ILoggingEvent> {
    private Layout<ILoggingEvent> layout;

    public void setLayout(Layout<ILoggingEvent> layout) {
        this.layout = layout;
    }

    @Override
    protected void append(ILoggingEvent iLoggingEvent) {
        if (layout != null) {
            out.println(layout.doLayout(iLoggingEvent));
        } else {
            out.println(iLoggingEvent.getFormattedMessage());
        }
    }

    public static void main(String[] args) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        CustomLayout layout = new CustomLayout();
        CustomAppender app = new CustomAppender();
        app.setLayout(layout);
        app.setContext(loggerContext);
        app.start();

        Logger logg = (Logger) LoggerFactory.getLogger(CustomAppender.class);
        logg.addAppender(app);

        logg.info("Hello World");
    }
}
