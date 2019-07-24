package com.xiaoshu.rabbitmq;

import com.xiaoshu.common.config.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MsgReceiver2 {

    @RabbitListener(queues = RabbitConfig.QUEUE_B)
    public void process(Message message) {
        String jsonString = new String(message.getBody());
        log.info("第二个消费者，接收处理队列A当中的消息： " + jsonString);
    }

}