import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.junit.Test;

/**
 * Created by  on 2019/1/22.
 */
public class GuavaEventBus {
    @Test
    public void shouldReceiveEvent() throws Exception {

        // given
        EventBus eventBus = new EventBus("test");
        EventListener listener = new EventListener();

        eventBus.register(listener);

        // when
        eventBus.post(new OurTestEvent(200));
        eventBus.post(new OurTestEvent(300));
        eventBus.post(new OurTestEvent(400));

        // then
        System.out.println("LastMessage:"+listener.getLastMessage());
    }
}

class EventListener {

    public int lastMessage = 0;

    @Subscribe
    public void listen(OurTestEvent event) {
        lastMessage = event.getMessage();
        System.out.println(lastMessage);
    }

    public int getLastMessage() {
        return lastMessage;
    }
}

class OurTestEvent {

    private final int message;

    public OurTestEvent(int message) {
        this.message = message;
    }

    public int getMessage() {
        return message;
    }
}
